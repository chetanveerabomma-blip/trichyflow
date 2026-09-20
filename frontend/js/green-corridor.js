/**
 * TRICHYFLOW Emergency Green Corridor Controller
 * Dijkstra shortest path routing, glowing pulsating polylines,
 * animated moving ambulance, and signal priority pre-emption.
 */

const GreenCorridor = {
  activeCorridorPolyline: null,
  activeCorridorCore: null,
  ambulanceMarker: null,
  animationInterval: null,

  init() {
    this.populateSelects();
    this.bindEvents();
  },

  populateSelects() {
    const vehicleSelect = document.getElementById('corridorVehicleSelect');
    const hospitalSelect = document.getElementById('corridorHospitalSelect');

    if (vehicleSelect) {
      vehicleSelect.innerHTML = TrichyMockDB.emergencyVehicles.map(v => 
        `<option value="${v.id}">${v.code} (${v.type}) - Near J${v.junctionId || 1}</option>`
      ).join('');
    }

    if (hospitalSelect) {
      hospitalSelect.innerHTML = TrichyMockDB.hospitals.map(h => 
        `<option value="${h.id}">${h.name} (${h.occupied}/${h.capacity} beds)</option>`
      ).join('');
    }
  },

  bindEvents() {
    const btn = document.getElementById('activateCorridorBtn');
    if (btn) {
      btn.addEventListener('click', () => {
        const vehicleId = document.getElementById('corridorVehicleSelect').value;
        const hospitalId = document.getElementById('corridorHospitalSelect').value;
        this.activateRoute(vehicleId, hospitalId);
      });
    }

    const clearBtn = document.getElementById('clearCorridorBtn');
    if (clearBtn) {
      clearBtn.addEventListener('click', () => {
        this.clearCorridor();
      });
    }
  },

  selectDestinationHospital(hospitalId) {
    const hospitalSelect = document.getElementById('corridorHospitalSelect');
    if (hospitalSelect) {
      hospitalSelect.value = hospitalId;
    }
    const vehicleSelect = document.getElementById('corridorVehicleSelect');
    const vehicleId = vehicleSelect ? vehicleSelect.value : 1;
    this.activateRoute(vehicleId, hospitalId);

    // Switch to Emergency tab if needed
    const emergencyTab = document.querySelector('[data-view="emergency-view"]');
    if (emergencyTab) emergencyTab.click();
  },

  activateCorridorForVehicle(vehicleId) {
    const vehicleSelect = document.getElementById('corridorVehicleSelect');
    if (vehicleSelect) vehicleSelect.value = vehicleId;
    const vehicle = TrichyMockDB.emergencyVehicles.find(v => v.id === parseInt(vehicleId));
    const targetHospId = vehicle && vehicle.destHospitalId ? vehicle.destHospitalId : 1;
    this.activateRoute(vehicleId, targetHospId);
  },

  activateRoute(vehicleId, hospitalId) {
    const vehicle = TrichyMockDB.emergencyVehicles.find(v => v.id === parseInt(vehicleId));
    const hospital = TrichyMockDB.hospitals.find(h => h.id === parseInt(hospitalId));
    if (!vehicle || !hospital) return;

    // Calculate Dijkstra path
    const originJunctionId = vehicle.junctionId || 1;
    const result = TrichyAPI.calculateGreenCorridor(originJunctionId, hospitalId);

    // Show Emergency Banner
    const banner = document.getElementById('emergencyCorridorBanner');
    if (banner) {
      banner.style.display = 'flex';
      banner.innerHTML = `
        <div>
          <i class="fa fa-exclamation-circle fa-beat text-warning"></i>
          <span style="letter-spacing: 0.06em; margin-left: 8px;">EMERGENCY GREEN CORRIDOR ACTIVATED:</span> 
          <b>${vehicle.code}</b> ➔ <b>${hospital.name}</b> (Est: ${result.estimatedTimeMin} mins)
        </div>
        <button class="btn-secondary-trichy" style="padding: 4px 12px; font-size: 0.75rem;" onclick="GreenCorridor.clearCorridor()">Deactivate</button>
      `;
    }

    // Render Corridor on Map
    this.renderCorridorOnMap(result);

    // Render Junction Priority Sequence
    const listEl = document.getElementById('corridorSequenceList');
    if (listEl) {
      listEl.innerHTML = result.recommendations.map(r => `
        <div class="glass-panel" style="padding: 12px 14px; margin-bottom: 8px; border-left: 4px solid #ef4444;">
          <div style="display: flex; justify-content: space-between; align-items: center;">
            <b style="color: #fff; font-size: 0.88rem;">Step ${r.step}: ${r.name}</b>
            <span class="status-badge badge-critical">EMERGENCY PRE-EMPT</span>
          </div>
          <div style="font-size: 0.78rem; color: #9ca3af; margin-top: 4px;">
            <i class="fa fa-traffic-light text-warning"></i> Action: <span style="color: #38bdf8;">${r.action}</span>
          </div>
        </div>
      `).join('');
    }

    // Trigger Notification
    TrichyAPI.addNotification(
      "Green Corridor Engaged",
      `Emergency Priority for ${vehicle.code} towards ${hospital.name}. All intermediate signals prioritized.`,
      "EMERGENCY"
    );

    // Zoom map to corridor
    if (MapController.map && result.coordinates.length > 0) {
      const bounds = L.latLngBounds(result.coordinates);
      MapController.map.fitBounds(bounds, { padding: [60, 60] });
    }
  },

  renderCorridorOnMap(result) {
    if (!MapController.map) return;
    this.clearCorridorMapLayers();

    // 1. Glowing background stroke
    this.activeCorridorPolyline = L.polyline(result.coordinates, {
      className: 'emergency-corridor-glow',
      lineCap: 'round'
    }).addTo(MapController.layers.corridor);

    // 2. Bright core stroke
    this.activeCorridorCore = L.polyline(result.coordinates, {
      className: 'emergency-corridor-core',
      lineCap: 'round'
    }).addTo(MapController.layers.corridor);

    // 3. Mark junctions on route with pulse-blue
    result.junctionPath.forEach(jid => {
      const marker = MapController.junctionMarkers[jid];
      if (marker && marker._icon) {
        const div = marker._icon.querySelector('.junction-marker');
        if (div) {
          div.classList.remove('marker-green', 'marker-yellow', 'marker-red');
          div.classList.add('pulse-blue');
          div.style.backgroundColor = '#2563eb';
        }
      }
    });

    // 4. Animate ambulance moving along the corridor
    this.startAmbulanceAnimation(result.coordinates);
  },

  startAmbulanceAnimation(coords) {
    if (!coords || coords.length < 2) return;

    if (this.ambulanceMarker) {
      MapController.map.removeLayer(this.ambulanceMarker);
    }

    const icon = L.divIcon({
      className: 'ambulance-animated-icon',
      html: `<div class="vehicle-ambulance-dot pulse-blue"><i class="fa fa-ambulance" style="font-size: 9px; color: #fff; margin-left: 2px;"></i></div>`,
      iconSize: [16, 16],
      iconAnchor: [8, 8]
    });

    this.ambulanceMarker = L.marker(coords[0], { icon: icon }).addTo(MapController.layers.corridor);

    let step = 0;
    const totalSteps = 180;

    if (this.animationInterval) clearInterval(this.animationInterval);

    this.animationInterval = setInterval(() => {
      step++;
      const progress = (step % totalSteps) / totalSteps;
      const pos = TrafficAnimation.interpolatePosition(coords, progress);
      this.ambulanceMarker.setLatLng(pos);
    }, 50);
  },

  clearCorridor() {
    this.clearCorridorMapLayers();
    if (this.animationInterval) clearInterval(this.animationInterval);

    const banner = document.getElementById('emergencyCorridorBanner');
    if (banner) banner.style.display = 'none';

    const listEl = document.getElementById('corridorSequenceList');
    if (listEl) {
      listEl.innerHTML = `<div style="color: #6b7280; font-size: 0.85rem; padding: 12px;">Select an emergency vehicle and destination hospital to compute Dijkstra green corridor.</div>`;
    }

    // Restore junction colors
    MapController.renderJunctions();
  },

  clearCorridorMapLayers() {
    if (this.activeCorridorPolyline) {
      MapController.layers.corridor.removeLayer(this.activeCorridorPolyline);
      this.activeCorridorPolyline = null;
    }
    if (this.activeCorridorCore) {
      MapController.layers.corridor.removeLayer(this.activeCorridorCore);
      this.activeCorridorCore = null;
    }
    if (this.ambulanceMarker) {
      MapController.layers.corridor.removeLayer(this.ambulanceMarker);
      this.ambulanceMarker = null;
    }
  }
};

window.GreenCorridor = GreenCorridor;
