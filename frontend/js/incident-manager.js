/**
 * TRICHYFLOW Incident & Road Block Management
 * Accident dispatch, road closure rerouting, and real-time alerts.
 */

const IncidentManager = {
  init() {
    this.populateRoadDropdown();
    this.bindForms();
  },

  populateRoadDropdown() {
    const roadSelect = document.getElementById('blockRoadSelect');
    if (roadSelect) {
      roadSelect.innerHTML = TrichyMockDB.roads.map(r => 
        `<option value="${r.id}">${r.name} (${r.distKm} km)</option>`
      ).join('');
    }
  },

  bindForms() {
    // Accident Reporting Form
    const accidentForm = document.getElementById('reportAccidentForm');
    if (accidentForm) {
      accidentForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const type = document.getElementById('accidentType').value;
        const location = document.getElementById('accidentLocation').value.trim();
        const lat = parseFloat(document.getElementById('accidentLat').value) || 10.8201;
        const lng = parseFloat(document.getElementById('accidentLng').value) || 78.6872;
        const severity = document.getElementById('accidentSeverity').value;
        const vehicles = parseInt(document.getElementById('accidentVehicles').value) || 1;
        const injuries = parseInt(document.getElementById('accidentInjuries').value) || 0;
        const description = document.getElementById('accidentDescription').value.trim();

        // Calculate detour
        let detour = "Divert light vehicles via Karur Bypass Road and Salai Road.";
        if (lat < 10.80) detour = "Divert through TVS Tollgate Overbridge and Subramaniapuram.";

        const newInc = {
          type,
          location,
          lat,
          lng,
          severity,
          vehicles,
          injuries,
          description,
          detour
        };

        await TrichyAPI.reportIncident(newInc);

        // Update UI
        MapController.renderIncidents();
        MapController.panTo(lat, lng, 16);
        this.renderIncidentTable();
        if (window.updateDashboardCounters) updateDashboardCounters();

        alert(`Accident Reported Successfully!\nMarker added to map. Detour active: ${detour}`);
        accidentForm.reset();
      });
    }

    // Road Block Reporting Form
    const blockForm = document.getElementById('reportBlockForm');
    if (blockForm) {
      blockForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const roadId = document.getElementById('blockRoadSelect').value;
        const road = TrichyMockDB.roads.find(r => r.id === parseInt(roadId));
        const reason = document.getElementById('blockReason').value.trim();
        const severity = document.getElementById('blockSeverity').value;
        const duration = document.getElementById('blockDuration').value.trim();
        const diversion = document.getElementById('blockDiversion').value.trim();

        const block = {
          roadId,
          roadName: road ? road.name : "Trichy Road",
          reason,
          severity,
          start: "Now",
          end: duration || "Until further notice",
          diversion: diversion || "Follow police signboards for local arterial bypass."
        };

        await TrichyAPI.reportRoadBlock(block);

        // Update UI
        MapController.renderRoads();
        TrafficAnimation.refreshVehicleFlows();
        this.renderRoadBlockTable();
        if (window.updateDashboardCounters) updateDashboardCounters();

        alert(`Road Block Enforced on ${block.roadName}.\nTraffic flows stopped and detour routes assigned.`);
        blockForm.reset();
      });
    }
  },

  renderIncidentTable() {
    const listEl = document.getElementById('incidentsTableBody');
    if (!listEl) return;

    listEl.innerHTML = TrichyMockDB.incidents.map(inc => `
      <tr>
        <td>#INC-${inc.id}</td>
        <td><b style="color: ${inc.severity === 'CRITICAL' ? '#ef4444' : '#f59e0b'}">${inc.type.replace('_', ' ')}</b></td>
        <td>${inc.location}</td>
        <td><span class="status-badge badge-${inc.severity.toLowerCase()}">${inc.severity}</span></td>
        <td>${inc.injuries}</td>
        <td><span style="color: #38bdf8;">${inc.detour}</span></td>
        <td><span class="status-badge badge-${inc.status === 'RESOLVED' ? 'normal' : 'heavy'}">${inc.status}</span></td>
        <td>
          <button class="btn-secondary-trichy" style="padding: 3px 8px; font-size: 0.72rem;" onclick="MapController.panTo(${inc.lat}, ${inc.lng}, 16)">
            <i class="fa fa-crosshairs"></i> Locate
          </button>
        </td>
      </tr>
    `).join('');
  },

  renderRoadBlockTable() {
    const listEl = document.getElementById('roadBlocksTableBody');
    if (!listEl) return;

    listEl.innerHTML = TrichyMockDB.roadBlocks.map(b => `
      <tr>
        <td>#BLK-${b.id}</td>
        <td><b>${b.roadName}</b></td>
        <td>${b.reason}</td>
        <td><span class="status-badge ${b.severity === 'COMPLETE' ? 'badge-critical' : 'badge-moderate'}">${b.severity}</span></td>
        <td>${b.start} to ${b.end}</td>
        <td><span style="color: #10b981;">${b.diversion}</span></td>
        <td><span class="status-badge badge-heavy">${b.status}</span></td>
      </tr>
    `).join('');
  }
};

window.IncidentManager = IncidentManager;
