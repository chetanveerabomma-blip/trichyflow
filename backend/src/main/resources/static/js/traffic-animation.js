/**
 * TRICHYFLOW Real-Time Traffic Animation Engine
 * Animates vehicles traversing road network based on density:
 * - Normal: fast moving, regular headway
 * - Moderate: medium speed, more vehicles
 * - Heavy: slow moving, high density
 * - Blocked: vehicles stop or divert before block
 */

const TrafficAnimation = {
  map: null,
  activeVehicles: [],
  animationFrameId: null,
  lastTime: 0,

  init(map) {
    this.map = map;
    this.spawnVehicles();
    this.startLoop();
  },

  spawnVehicles() {
    const roads = TrichyMockDB.roads;
    this.activeVehicles = [];

    roads.forEach(road => {
      // Determine number of vehicles on this road based on status
      let count = 2;
      let speedFactor = 0.00035; // base progress per ms

      if (road.status === 'CONGESTED') {
        count = 5;
        speedFactor = 0.00012; // slow crawling in heavy traffic
      } else if (road.status === 'PARTIALLY_BLOCKED') {
        count = 3;
        speedFactor = 0.00018;
      } else if (road.status === 'FULLY_BLOCKED') {
        count = 1; // stopped vehicle
        speedFactor = 0.00002;
      }

      for (let i = 0; i < count; i++) {
        const vehicle = {
          roadId: road.id,
          coords: road.coords,
          progress: (i / count), // staggered start between 0.0 and 1.0
          speed: speedFactor * (0.8 + Math.random() * 0.4),
          marker: null,
          color: road.status === 'CONGESTED' ? '#EF4444' : (road.status === 'PARTIALLY_BLOCKED' ? '#F59E0B' : '#FBBF24')
        };

        const initialPos = this.interpolatePosition(road.coords, vehicle.progress);
        const icon = L.divIcon({
          className: 'custom-vehicle-icon',
          html: `<div class="vehicle-moving-dot" style="background-color: ${vehicle.color}; box-shadow: 0 0 6px ${vehicle.color};"></div>`,
          iconSize: [8, 8],
          iconAnchor: [4, 4]
        });

        vehicle.marker = L.marker([initialPos[0], initialPos[1]], { icon: icon }).addTo(this.map);
        this.activeVehicles.push(vehicle);
      }
    });
  },

  interpolatePosition(coords, progress) {
    if (!coords || coords.length === 0) return [10.8062, 78.6948];
    if (coords.length === 1) return coords[0];

    // Multi-segment polyline interpolation
    const totalSegments = coords.length - 1;
    const scaledProgress = progress * totalSegments;
    const segmentIndex = Math.min(Math.floor(scaledProgress), totalSegments - 1);
    const segmentT = scaledProgress - segmentIndex;

    const p1 = coords[segmentIndex];
    const p2 = coords[segmentIndex + 1];

    const lat = p1[0] + (p2[0] - p1[0]) * segmentT;
    const lng = p1[1] + (p2[1] - p1[1]) * segmentT;

    return [lat, lng];
  },

  startLoop() {
    const animate = (time) => {
      if (!this.lastTime) this.lastTime = time;
      const dt = time - this.lastTime;
      this.lastTime = time;

      this.activeVehicles.forEach(v => {
        const road = TrichyMockDB.roads.find(r => r.id === v.roadId);
        if (!road) return;

        // Advance vehicle along road
        if (road.status === 'FULLY_BLOCKED') {
          // Blocked: vehicle stops at 40% of road
          if (v.progress < 0.4) {
            v.progress += v.speed * dt;
          }
        } else {
          v.progress += v.speed * dt;
          if (v.progress > 1.0) {
            v.progress = 0.0;
          }
        }

        const newPos = this.interpolatePosition(v.coords, v.progress);
        v.marker.setLatLng(newPos);
      });

      this.animationFrameId = requestAnimationFrame(animate);
    };

    this.animationFrameId = requestAnimationFrame(animate);
  },

  refreshVehicleFlows() {
    this.activeVehicles.forEach(v => {
      if (v.marker && this.map) {
        this.map.removeLayer(v.marker);
      }
    });
    this.spawnVehicles();
  }
};

window.TrafficAnimation = TrafficAnimation;
