/**
 * TRICHYFLOW Traffic Analytics Engine
 * Chart.js dashboards: 8 charts covering volume, incidents, response times, and violations.
 */

const AnalyticsCharts = {
  charts: {},
  currentFilter: '7d',

  init() {
    this.initFilterButtons();
    this.renderAllCharts();
  },

  initFilterButtons() {
    document.querySelectorAll('.analytics-filter-btn').forEach(btn => {
      btn.addEventListener('click', (e) => {
        document.querySelectorAll('.analytics-filter-btn').forEach(b => b.classList.remove('active'));
        e.currentTarget.classList.add('active');
        this.currentFilter = e.currentTarget.getAttribute('data-filter');
        this.renderAllCharts();
      });
    });
  },

  renderAllCharts() {
    this.renderHourlyTrafficChart();
    this.renderDailyTrafficChart();
    this.renderJunctionVolumeChart();
    this.renderAccidentsMonthChart();
    this.renderChallansMonthChart();
    this.renderRoadBlockDoughnut();
    this.renderEmergencyResponseChart();
    this.renderCongestionTrendChart();
  },

  getChartConfig(type, data, options = {}) {
    return {
      type: type,
      data: data,
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            labels: { color: '#9CA3AF', font: { family: 'Inter', size: 11 } }
          }
        },
        scales: (type !== 'doughnut' && type !== 'pie') ? {
          x: {
            grid: { color: 'rgba(255, 255, 255, 0.05)' },
            ticks: { color: '#9CA3AF', font: { size: 10 } }
          },
          y: {
            grid: { color: 'rgba(255, 255, 255, 0.05)' },
            ticks: { color: '#9CA3AF', font: { size: 10 } }
          }
        } : {},
        ...options
      }
    };
  },

  // 1. Traffic by Hour
  renderHourlyTrafficChart() {
    const ctx = document.getElementById('chartHourlyTraffic');
    if (!ctx) return;
    if (this.charts.hourly) this.charts.hourly.destroy();

    const hours = ['06:00', '08:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00', '22:00'];
    const data = {
      labels: hours,
      datasets: [
        {
          label: 'Chathiram Bus Stand',
          data: [85, 240, 210, 140, 160, 230, 260, 190, 95],
          borderColor: '#FF9933',
          backgroundColor: 'rgba(255, 153, 51, 0.15)',
          tension: 0.35,
          fill: true
        },
        {
          label: 'Central Bus Stand',
          data: [120, 310, 290, 180, 210, 340, 380, 280, 150],
          borderColor: '#EF4444',
          backgroundColor: 'rgba(239, 68, 68, 0.12)',
          tension: 0.35,
          fill: true
        }
      ]
    };
    this.charts.hourly = new Chart(ctx, this.getChartConfig('line', data));
  },

  // 2. Traffic by Day
  renderDailyTrafficChart() {
    const ctx = document.getElementById('chartDailyTraffic');
    if (!ctx) return;
    if (this.charts.daily) this.charts.daily.destroy();

    const days = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
    const data = {
      labels: days,
      datasets: [{
        label: 'Daily Vehicular Index (Thousands)',
        data: [142, 138, 145, 149, 168, 185, 130],
        backgroundColor: '#10B981',
        borderRadius: 6
      }]
    };
    this.charts.daily = new Chart(ctx, this.getChartConfig('bar', data));
  },

  // 3. Traffic by Junction
  renderJunctionVolumeChart() {
    const ctx = document.getElementById('chartJunctionVolume');
    if (!ctx) return;
    if (this.charts.junctions) this.charts.junctions.destroy();

    const juncs = ['Central Bus Stand', 'TVS Tollgate', 'Chathiram', 'Railway Junc', 'Srirangam', 'Thillai Nagar'];
    const data = {
      labels: juncs,
      datasets: [{
        label: 'Peak Volume (Vehicles/min)',
        data: [360, 388, 246, 312, 258, 155],
        backgroundColor: ['#EF4444', '#DC2626', '#F59E0B', '#3B82F6', '#10B981', '#6366F1'],
        borderRadius: 6
      }]
    };
    this.charts.junctions = new Chart(ctx, this.getChartConfig('bar', data, { indexAxis: 'y' }));
  },

  // 4. Accidents per Month
  renderAccidentsMonthChart() {
    const ctx = document.getElementById('chartAccidentsMonth');
    if (!ctx) return;
    if (this.charts.accidents) this.charts.accidents.destroy();

    const months = ['Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep'];
    const data = {
      labels: months,
      datasets: [{
        label: 'Reported Accidents',
        data: [28, 34, 25, 31, 22, 16],
        backgroundColor: '#F97316',
        borderRadius: 6
      }]
    };
    this.charts.accidents = new Chart(ctx, this.getChartConfig('bar', data));
  },

  // 5. Challans per Month
  renderChallansMonthChart() {
    const ctx = document.getElementById('chartChallansMonth');
    if (!ctx) return;
    if (this.charts.challans) this.charts.challans.destroy();

    const months = ['Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep'];
    const data = {
      labels: months,
      datasets: [
        {
          label: 'Paid (₹ in Lakhs)',
          data: [12.4, 15.2, 14.1, 16.8, 18.5, 21.0],
          backgroundColor: '#10B981',
          borderRadius: 4
        },
        {
          label: 'Pending (₹ in Lakhs)',
          data: [4.2, 5.1, 4.8, 6.0, 5.2, 4.9],
          backgroundColor: '#EF4444',
          borderRadius: 4
        }
      ]
    };
    this.charts.challans = new Chart(ctx, this.getChartConfig('bar', data));
  },

  // 6. Road Block Frequency
  renderRoadBlockDoughnut() {
    const ctx = document.getElementById('chartRoadBlocks');
    if (!ctx) return;
    if (this.charts.roadBlocks) this.charts.roadBlocks.destroy();

    const data = {
      labels: ['UGD Pipeline', 'Storm Drainage', 'Culvert / Bridge Repair', 'Religious / Festival', 'Emergency Accidents'],
      datasets: [{
        data: [40, 25, 15, 12, 8],
        backgroundColor: ['#3B82F6', '#10B981', '#F59E0B', '#EC4899', '#EF4444'],
        borderWidth: 0
      }]
    };
    this.charts.roadBlocks = new Chart(ctx, this.getChartConfig('doughnut', data, { cutout: '65%' }));
  },

  // 7. Emergency Response Time
  renderEmergencyResponseChart() {
    const ctx = document.getElementById('chartEmergencyResponse');
    if (!ctx) return;
    if (this.charts.emergency) this.charts.emergency.destroy();

    const months = ['Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep'];
    const data = {
      labels: months,
      datasets: [
        {
          label: 'Without Green Corridor (Mins)',
          data: [18.5, 17.8, 19.2, 18.0, 17.5, 16.9],
          borderColor: '#EF4444',
          tension: 0.3,
          borderDash: [5, 5]
        },
        {
          label: 'With TrichyFlow Green Corridor (Mins)',
          data: [8.2, 7.8, 7.1, 6.9, 6.4, 6.1],
          borderColor: '#10B981',
          backgroundColor: 'rgba(16, 185, 129, 0.15)',
          tension: 0.3,
          fill: true
        }
      ]
    };
    this.charts.emergency = new Chart(ctx, this.getChartConfig('line', data));
  },

  // 8. Congestion Trends
  renderCongestionTrendChart() {
    const ctx = document.getElementById('chartCongestionTrend');
    if (!ctx) return;
    if (this.charts.congestion) this.charts.congestion.destroy();

    const times = ['07:00', '09:00', '11:00', '13:00', '15:00', '17:00', '19:00', '21:00'];
    const data = {
      labels: times,
      datasets: [{
        label: 'Overall City Congestion Index (1.0 - 5.0)',
        data: [1.6, 4.4, 2.7, 2.1, 2.4, 4.7, 4.2, 2.0],
        borderColor: '#38BDF8',
        backgroundColor: 'rgba(56, 189, 248, 0.2)',
        tension: 0.4,
        fill: true
      }]
    };
    this.charts.congestion = new Chart(ctx, this.getChartConfig('line', data));
  }
};

window.AnalyticsCharts = AnalyticsCharts;
