/**
 * TRICHYFLOW Authentication & Session Management
 */

const AuthManager = {
  initLogin() {
    const loginForm = document.getElementById('loginForm');
    if (!loginForm) return;

    // Quick demo autofill buttons
    document.querySelectorAll('.demo-credential-btn').forEach(btn => {
      btn.addEventListener('click', (e) => {
        const u = e.currentTarget.getAttribute('data-user');
        const p = e.currentTarget.getAttribute('data-pass');
        const r = e.currentTarget.getAttribute('data-role');
        
        document.getElementById('username').value = u;
        document.getElementById('password').value = p;
        document.getElementById('roleSelect').value = r;
      });
    });

    loginForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      const username = document.getElementById('username').value.trim();
      const password = document.getElementById('password').value.trim();
      const role = document.getElementById('roleSelect').value;
      const errorMsg = document.getElementById('loginError');

      if (!username || !password || !role) {
        if (errorMsg) {
          errorMsg.textContent = "Please enter username, password and select a role.";
          errorMsg.style.display = "block";
        }
        return;
      }

      const btn = loginForm.querySelector('button[type="submit"]');
      const originalText = btn.innerHTML;
      btn.innerHTML = `<i class="fa fa-spinner fa-spin"></i> Authenticating...`;
      btn.disabled = true;

      try {
        const result = await TrichyAPI.login(username, password, role);
        if (result.success) {
          if (role === 'TRAFFIC_POLICE') {
            window.location.href = 'police-dashboard.html';
          } else {
            window.location.href = 'dashboard.html';
          }
        } else {
          if (errorMsg) {
            errorMsg.textContent = result.message || "Invalid credentials.";
            errorMsg.style.display = "block";
          }
        }
      } catch (err) {
        if (errorMsg) {
          errorMsg.textContent = "Authentication server error. Try again.";
          errorMsg.style.display = "block";
        }
      } finally {
        btn.innerHTML = originalText;
        btn.disabled = false;
      }
    });
  },

  checkSession(requiredRole = null) {
    const user = TrichyAPI.getCurrentUser();
    if (!user) {
      window.location.href = 'index.html';
      return null;
    }

    // Role check if needed
    if (requiredRole && user.role !== requiredRole && user.role !== 'ADMIN') {
      alert("Unauthorized: Your role does not have permission to view this view.");
      window.location.href = user.role === 'TRAFFIC_POLICE' ? 'police-dashboard.html' : 'dashboard.html';
      return null;
    }

    // Populate user profile info in navigation
    const nameEl = document.getElementById('navUserName');
    const roleEl = document.getElementById('navUserRole');
    const badgeEl = document.getElementById('navUserBadge');

    if (nameEl) nameEl.textContent = user.fullName;
    if (roleEl) {
      roleEl.textContent = user.role.replace('_', ' ');
      roleEl.className = `role-pill role-${user.role.toLowerCase().replace('_', '-')}`;
    }
    if (badgeEl) badgeEl.textContent = user.badgeNumber || '';

    // Setup logout button
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
      logoutBtn.addEventListener('click', (e) => {
        e.preventDefault();
        TrichyAPI.logout();
      });
    }

    return user;
  }
};

window.AuthManager = AuthManager;
