/**
 * TRICHYFLOW E-Challan & Traffic Violation Management
 */

const ChallanManager = {
  currentStatusFilter: 'ALL',
  currentSearchQuery: '',

  init() {
    this.bindEvents();
    this.renderChallans();
  },

  bindEvents() {
    // Search input
    const searchInput = document.getElementById('challanSearchInput');
    if (searchInput) {
      searchInput.addEventListener('input', (e) => {
        this.currentSearchQuery = e.target.value;
        this.renderChallans();
      });
    }

    // Status filter tabs/select
    const statusSelect = document.getElementById('challanStatusFilter');
    if (statusSelect) {
      statusSelect.addEventListener('change', (e) => {
        this.currentStatusFilter = e.target.value;
        this.renderChallans();
      });
    }

    // Create Challan Form
    const createForm = document.getElementById('createChallanForm');
    if (createForm) {
      createForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const vehicleNo = document.getElementById('newChallanVehicle').value.trim().toUpperCase();
        const owner = document.getElementById('newChallanOwner').value.trim();
        const violation = document.getElementById('newChallanViolation').value;
        const location = document.getElementById('newChallanLocation').value.trim();
        const fine = parseFloat(document.getElementById('newChallanFine').value) || 1000;

        if (!vehicleNo || !owner || !location) {
          alert("Please fill in all vehicle and violation details.");
          return;
        }

        await TrichyAPI.createChallan({
          vehicleNo,
          owner,
          violation,
          location,
          fine,
          status: 'UNPAID'
        });

        alert(`E-Challan issued for ${vehicleNo} (${violation}) - ₹${fine}.\nSMS notification dispatched to vehicle owner.`);
        createForm.reset();
        
        // Hide modal if open
        const modal = document.getElementById('createChallanModal');
        if (modal) modal.style.display = 'none';

        this.renderChallans();
        if (window.updateDashboardCounters) updateDashboardCounters();
      });
    }
  },

  async renderChallans() {
    const tableBody = document.getElementById('challansTableBody');
    if (!tableBody) return;

    const challans = await TrichyAPI.getChallans(this.currentSearchQuery, this.currentStatusFilter);

    if (challans.length === 0) {
      tableBody.innerHTML = `<tr><td colspan="8" style="text-align: center; color: #9ca3af; padding: 24px;">No challans found matching your query.</td></tr>`;
      return;
    }

    tableBody.innerHTML = challans.map(c => `
      <tr>
        <td><b>#CHL-${c.id}</b></td>
        <td><span class="tricolour-pill" style="font-family: monospace; font-size: 0.85rem;">${c.vehicleNo}</span></td>
        <td>${c.owner}</td>
        <td><span style="color: #f87171; font-weight: 600;">${c.violation}</span></td>
        <td>${c.location}</td>
        <td><b>₹${c.fine}</b></td>
        <td>
          <span class="status-badge ${c.status === 'PAID' ? 'badge-normal' : (c.status === 'PENDING' ? 'badge-moderate' : 'badge-critical')}">
            ${c.status}
          </span>
        </td>
        <td>
          <button class="btn-secondary-trichy" style="padding: 2px 8px; font-size: 0.72rem;" onclick="ChallanManager.viewEvidence(${c.id})">
            <i class="fa fa-receipt"></i> Notice
          </button>
        </td>
      </tr>
    `).join('');
  },

  viewEvidence(id) {
    const c = TrichyMockDB.challans.find(item => item.id === id);
    if (!c) return;
    alert(`E-CHALLAN VIOLATION NOTICE\nNotice No: TN-TRICHY-2026-${c.id}\nVehicle: ${c.vehicleNo} (${c.owner})\nOffense: ${c.violation}\nLocation: ${c.location}\nFine: ₹${c.fine} (${c.status})\nOfficer: ${c.officer}\nDate: ${c.date}\nPortal: https://echallan.parivahan.gov.in`);
  }
};

window.ChallanManager = ChallanManager;
