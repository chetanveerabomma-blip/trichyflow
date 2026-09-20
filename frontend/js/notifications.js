/**
 * TRICHYFLOW Real-time Notification System
 * Animated slide-in toasts, notification drawer, and audio tone.
 */

const NotificationManager = {
  container: null,
  drawer: null,

  init() {
    this.createContainer();
    this.bindEvents();
    this.renderDrawer();
  },

  createContainer() {
    if (!document.getElementById('trichyToastContainer')) {
      this.container = document.createElement('div');
      this.container.id = 'trichyToastContainer';
      this.container.className = 'toast-container-trichy';
      document.body.appendChild(this.container);
    } else {
      this.container = document.getElementById('trichyToastContainer');
    }
  },

  bindEvents() {
    window.addEventListener('trichy-notification', (e) => {
      this.showToast(e.detail);
      this.renderDrawer();
    });

    const notifBtn = document.getElementById('notificationBellBtn');
    const drawer = document.getElementById('notificationDrawer');
    if (notifBtn && drawer) {
      notifBtn.addEventListener('click', () => {
        drawer.style.display = drawer.style.display === 'block' ? 'none' : 'block';
      });
    }
  },

  showToast(notif) {
    if (!this.container) this.createContainer();

    const toast = document.createElement('div');
    const isEmergency = notif.type === 'EMERGENCY' || notif.type === 'ACCIDENT';
    toast.className = `toast-trichy slide-in ${isEmergency ? 'toast-emergency' : ''}`;

    let icon = 'fa-info-circle text-info';
    if (notif.type === 'EMERGENCY') icon = 'fa-ambulance text-danger';
    if (notif.type === 'ACCIDENT') icon = 'fa-car-crash text-warning';
    if (notif.type === 'WARNING') icon = 'fa-exclamation-triangle text-warning';
    if (notif.type === 'CONGESTION') icon = 'fa-traffic-light text-danger';

    toast.innerHTML = `
      <i class="fa ${icon}" style="font-size: 1.2rem; margin-top: 2px;"></i>
      <div style="flex: 1;">
        <div style="font-weight: 700; font-size: 0.88rem; color: #fff;">${notif.title}</div>
        <div style="color: #9ca3af; font-size: 0.78rem; margin-top: 2px;">${notif.message}</div>
      </div>
      <button style="background: none; border: none; color: #6b7280; cursor: pointer;" onclick="this.parentElement.remove()">
        <i class="fa fa-times"></i>
      </button>
    `;

    this.container.appendChild(toast);

    // Auto dismiss after 6 seconds
    setTimeout(() => {
      if (toast && toast.parentElement) {
        toast.style.opacity = '0';
        toast.style.transform = 'translateX(120%)';
        toast.style.transition = 'all 0.4s ease';
        setTimeout(() => toast.remove(), 400);
      }
    }, 6000);
  },

  renderDrawer() {
    const listEl = document.getElementById('notificationDrawerList');
    const badgeEl = document.getElementById('unreadNotifBadge');
    if (!listEl) return;

    const notifs = TrichyMockDB.notifications;
    const unreadCount = notifs.filter(n => !n.read).length;

    if (badgeEl) {
      badgeEl.textContent = unreadCount;
      badgeEl.style.display = unreadCount > 0 ? 'inline-block' : 'none';
    }

    listEl.innerHTML = notifs.map(n => `
      <div style="padding: 10px 14px; border-bottom: 1px solid rgba(255, 255, 255, 0.05); background: ${n.read ? 'transparent' : 'rgba(255, 153, 51, 0.05)'};">
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <b style="font-size: 0.82rem; color: #fff;">${n.title}</b>
          <span style="font-size: 0.68rem; color: #6b7280;">${n.time}</span>
        </div>
        <div style="font-size: 0.74rem; color: #9ca3af; margin-top: 2px;">${n.message}</div>
      </div>
    `).join('');
  }
};

window.NotificationManager = NotificationManager;
