/**
 * TRICHYFLOW Leaflet Map Controller
 * Renders realistic Trichy road network, custom status markers, popups, and layer controls.
 */

const MapController = {
  map: null,
  layers: {
    junctions: null,
    roads: null,
    incidents: null,
    hospitals: null,
    emergency: null,
    corridor: null
  },
  junctionMarkers: {},
  roadPolylines: {},

  init(containerId = 'trichyMap') {
    if (!document.getElementById(containerId)) return;

    // Centered at Trichy (Tiruchirappalli)
    this.map = L.map(containerId, {
      center: [10.8062, 78.6948],
      zoom: 13,
      minZoom: 11,
      maxZoom: 18,
      zoomControl: true
    });

    // OpenStreetMap Tile Layer with dark carto style
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors | Trichy Traffic Police Simulation',
      maxZoom: 19
    }).addTo(this.map);

    // Initialize layer groups
    this.layers.roads = L.layerGroup().addTo(this.map);
    this.layers.junctions = L.layerGroup().addTo(this.map);
    this.layers.incidents = L.layerGroup().addTo(this.map);
    this.layers.hospitals = L.layerGroup().addTo(this.map);
    this.layers.emergency = L.layerGroup().addTo(this.map);
    this.layers.corridor = L.layerGroup().addTo(this.map);

    // Load elements
    this.renderRoads();
    this.renderJunctions();
    this.renderHospitals();
    this.renderIncidents();
    this.renderEmergencyVehicles();

    // Start traffic vehicle motion
    if (window.TrafficAnimation) {
      TrafficAnimation.init(this.map);
    }
  },

  renderJunctions() {
    const junctions = TrichyMockDB.junctions;
    this.layers.junctions.clearLayers();
    this.junctionMarkers = {};

    junctions.forEach(j => {
      let colorClass = "marker-green";
      if (j.trafficDensity === "MODERATE") colorClass = "marker-yellow";
      if (j.trafficDensity === "HEAVY") colorClass = "marker-red";
      if (j.trafficDensity === "CRITICAL") colorClass = "marker-critical";

      const icon = L.divIcon({
        className: 'custom-div-icon',
        html: `<div class="junction-marker ${colorClass}" title="${j.name}">J${j.id}</div>`,
        iconSize: [26, 26],
        iconAnchor: [13, 13]
      });

      const marker = L.marker([j.lat, j.lng], { icon: icon });

      // Build Rich Interactive Popup
      const popupHtml = `
        <div class="junction-popup-box">
          <div class="popup-title">${j.name}</div>
          <div class="popup-subtitle"><i class="fa fa-map-marker-alt text-warning"></i> ${j.landmark}</div>
          
          <div class="popup-metric-row">
            <span class="popup-metric-label">Traffic Density:</span>
            <span class="badge-${j.trafficDensity.toLowerCase()} status-badge">${j.trafficDensity}</span>
          </div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Live VPM:</span>
            <span class="popup-metric-val">${j.vpm} vehicles/min</span>
          </div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Current Phase:</span>
            <span class="popup-metric-val" style="color: #60a5fa;">${j.currentPhase}</span>
          </div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Signal Timers:</span>
            <span class="popup-metric-val">🟢 ${j.greenTime}s | 🔴 ${j.redTime}s</span>
          </div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Congestion Index:</span>
            <span class="popup-metric-val" style="color: ${j.congestionIndex > 3.5 ? '#ef4444' : '#10b981'}">${j.congestionIndex} / 5.0</span>
          </div>

          <button class="btn-primary-trichy popup-action-btn" onclick="SignalPlanner.openModal(${j.id})">
            <i class="fa fa-sliders-h"></i> Optimize Signal Simulation
          </button>
        </div>
      `;

      marker.bindPopup(popupHtml, { maxWidth: 300 });
      marker.addTo(this.layers.junctions);
      this.junctionMarkers[j.id] = marker;
    });
  },

  renderRoads() {
    const roads = TrichyMockDB.roads;
    this.layers.roads.clearLayers();
    this.roadPolylines = {};

    roads.forEach(r => {
      let color = "#38bdf8";
      let weight = 4;
      let dashArray = null;
      let opacity = 0.65;

      if (r.status === 'CONGESTED') {
        color = "#ef4444";
        weight = 5;
      } else if (r.status === 'PARTIALLY_BLOCKED') {
        color = "#eab308";
        dashArray = "6, 6";
      } else if (r.status === 'FULLY_BLOCKED') {
        color = "#475569";
        dashArray = "8, 8";
        weight = 6;
      }

      const polyline = L.polyline(r.coords, {
        color: color,
        weight: weight,
        opacity: opacity,
        dashArray: dashArray,
        lineCap: 'round'
      });

      polyline.bindTooltip(`<b>${r.name}</b><br>Speed: ${r.speed} km/h (Limit: ${r.limit})<br>Status: ${r.status}`, {
        sticky: true
      });

      polyline.addTo(this.layers.roads);
      this.roadPolylines[r.id] = polyline;
    });
  },

  renderHospitals() {
    const hospitals = TrichyMockDB.hospitals;
    this.layers.hospitals.clearLayers();

    hospitals.forEach(h => {
      const icon = L.divIcon({
        className: 'custom-div-icon',
        html: `<div class="hospital-marker-icon" title="${h.name}"><i class="fa fa-hospital"></i></div>`,
        iconSize: [28, 28],
        iconAnchor: [14, 14]
      });

      const marker = L.marker([h.lat, h.lng], { icon: icon });
      marker.bindPopup(`
        <div class="junction-popup-box">
          <div class="popup-title" style="color: #60a5fa;"><i class="fa fa-hospital-symbol"></i> ${h.name}</div>
          <div class="popup-subtitle">${h.location}</div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Emergency Capacity:</span>
            <span class="popup-metric-val">${h.capacity} beds (${h.occupied} occupied)</span>
          </div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Status:</span>
            <span class="status-badge badge-${h.status === 'AVAILABLE' ? 'normal' : 'heavy'}">${h.status}</span>
          </div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Emergency Helpline:</span>
            <span class="popup-metric-val">${h.contact}</span>
          </div>
          <button class="btn-green-trichy popup-action-btn" onclick="GreenCorridor.selectDestinationHospital(${h.id})">
            <i class="fa fa-route"></i> Route Ambulance Here
          </button>
        </div>
      `);
      marker.addTo(this.layers.hospitals);
    });
  },

  renderIncidents() {
    const incidents = TrichyMockDB.incidents;
    this.layers.incidents.clearLayers();

    incidents.filter(i => i.status !== 'RESOLVED').forEach(inc => {
      const icon = L.divIcon({
        className: 'custom-div-icon',
        html: `<div class="accident-marker-icon" title="${inc.type}"><i class="fa fa-car-crash"></i></div>`,
        iconSize: [32, 32],
        iconAnchor: [16, 16]
      });

      const marker = L.marker([inc.lat, inc.lng], { icon: icon });
      marker.bindPopup(`
        <div class="junction-popup-box">
          <div class="popup-title" style="color: #f87171;"><i class="fa fa-exclamation-triangle"></i> ${inc.type.replace('_', ' ')}</div>
          <div class="popup-subtitle">${inc.location}</div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Severity:</span>
            <span class="status-badge badge-${inc.severity.toLowerCase()}">${inc.severity}</span>
          </div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Injuries:</span>
            <span class="popup-metric-val">${inc.injuries} casualties</span>
          </div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Recommended Detour:</span>
            <span class="popup-metric-val" style="color: #f59e0b;">${inc.detour}</span>
          </div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Reported By:</span>
            <span class="popup-metric-val">${inc.reportedBy}</span>
          </div>
        </div>
      `);
      marker.addTo(this.layers.incidents);
    });
  },

  renderEmergencyVehicles() {
    const vehicles = TrichyMockDB.emergencyVehicles;
    this.layers.emergency.clearLayers();

    vehicles.forEach(ev => {
      const isAmbulance = ev.type === 'AMBULANCE';
      const iconClass = isAmbulance ? 'ambulance-marker-icon' : (ev.type === 'FIRE_TRUCK' ? 'accident-marker-icon' : 'police-marker-icon');
      const faIcon = isAmbulance ? 'fa-ambulance' : (ev.type === 'FIRE_TRUCK' ? 'fa-fire-extinguisher' : 'fa-shield-alt');

      const icon = L.divIcon({
        className: 'custom-div-icon',
        html: `<div class="${iconClass}" title="${ev.code}"><i class="fa ${faIcon}"></i></div>`,
        iconSize: [34, 34],
        iconAnchor: [17, 17]
      });

      const marker = L.marker([ev.currentLat, ev.currentLng], { icon: icon });
      marker.bindPopup(`
        <div class="junction-popup-box">
          <div class="popup-title" style="color: #ef4444;"><i class="fa ${faIcon}"></i> ${ev.code} (${ev.type})</div>
          <div class="popup-subtitle">Driver: ${ev.driver} | ${ev.contact}</div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Status:</span>
            <span class="status-badge badge-${ev.status === 'EN_ROUTE' ? 'critical' : 'normal'}">${ev.status}</span>
          </div>
          <div class="popup-metric-row">
            <span class="popup-metric-label">Priority:</span>
            <span class="popup-metric-val" style="color: #ef4444;">${ev.priority}</span>
          </div>
          <button class="btn-danger-trichy popup-action-btn" onclick="GreenCorridor.activateCorridorForVehicle(${ev.id})">
            <i class="fa fa-broadcast-tower"></i> Engage Green Corridor
          </button>
        </div>
      `);
      marker.addTo(this.layers.emergency);
    });
  },

  panTo(lat, lng, zoom = 15) {
    if (this.map) {
      this.map.setView([lat, lng], zoom, { animate: true });
    }
  }
};

window.MapController = MapController;
