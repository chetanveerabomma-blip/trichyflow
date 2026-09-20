/**
 * TRICHYFLOW Dynamic Signal Timing Planner
 * Rule-driven Webster optimization interface comparing Current vs Recommended plans
 * with an interactive "Apply Simulation" feature.
 */

const SignalPlanner = {
  currentJunctionId: null,

  init() {
    const modal = document.getElementById('signalPlanModal');
    if (!modal) return;

    const closeBtn = modal.querySelector('.close-modal-btn');
    if (closeBtn) {
      closeBtn.addEventListener('click', () => {
        modal.style.display = 'none';
      });
    }

    const applyBtn = document.getElementById('applySignalSimulationBtn');
    if (applyBtn) {
      applyBtn.addEventListener('click', () => {
        this.applySimulation();
      });
    }
  },

  openModal(junctionId) {
    this.currentJunctionId = junctionId;
    const plan = TrichyAPI.optimizeSignalPlan(junctionId);
    if (!plan) return;

    const junction = TrichyMockDB.junctions.find(j => j.id === parseInt(junctionId));
    const modal = document.getElementById('signalPlanModal');

    // Populate modal values
    document.getElementById('signalModalTitle').textContent = `Adaptive Signal Optimization: ${plan.name}`;
    
    // 4-way flow breakdown
    document.getElementById('planNorthFlow').textContent = `${junction.northFlow} vpm`;
    document.getElementById('planSouthFlow').textContent = `${junction.southFlow} vpm`;
    document.getElementById('planEastFlow').textContent = `${junction.eastFlow} vpm`;
    document.getElementById('planWestFlow').textContent = `${junction.westFlow} vpm`;

    // Current Plan
    document.getElementById('currNSGreen').textContent = `${plan.currentPlan.northSouthGreen}s`;
    document.getElementById('currEWGreen').textContent = `${plan.currentPlan.eastWestGreen}s`;
    document.getElementById('currPhase').textContent = plan.currentPlan.phase;

    // Recommended Plan
    document.getElementById('recomNSGreen').textContent = `${plan.recommendedPlan.northSouthGreen}s`;
    document.getElementById('recomEWGreen').textContent = `${plan.recommendedPlan.eastWestGreen}s`;
    document.getElementById('recomPhase').textContent = plan.recommendedPlan.recommendedPhase;
    document.getElementById('recomReason').textContent = plan.recommendedPlan.rationale;
    document.getElementById('recomReduction').textContent = plan.recommendedPlan.congestionReductionExpected;

    modal.style.display = 'flex';
  },

  applySimulation() {
    if (!this.currentJunctionId) return;

    const junction = TrichyMockDB.junctions.find(j => j.id === parseInt(this.currentJunctionId));
    const plan = TrichyAPI.optimizeSignalPlan(this.currentJunctionId);

    if (junction && plan) {
      // Update junction simulation state
      junction.greenTime = plan.recommendedPlan.northSouthGreen;
      junction.redTime = plan.recommendedPlan.eastWestGreen;
      junction.currentPhase = plan.recommendedPlan.recommendedPhase;
      junction.trafficDensity = "MODERATE"; // relieved
      junction.congestionIndex = Math.max(1.5, (junction.congestionIndex * 0.75)).toFixed(2);

      // Re-render map and junction table
      MapController.renderJunctions();
      if (window.renderJunctionTable) renderJunctionTable();

      // Notification
      TrichyAPI.addNotification(
        "Signal Simulation Applied",
        `Adaptive timing applied to ${junction.name}. Green split: NS ${junction.greenTime}s | EW ${junction.redTime}s.`,
        "INFO"
      );

      // Close modal
      const modal = document.getElementById('signalPlanModal');
      if (modal) modal.style.display = 'none';
    }
  }
};

window.SignalPlanner = SignalPlanner;
