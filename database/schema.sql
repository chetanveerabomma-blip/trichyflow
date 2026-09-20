-- ==============================================================================
-- TRICHYFLOW: Intelligent City Traffic Coordination & Emergency Response Platform
-- Database Schema for MySQL 8.0+
-- College Major Project: Advanced Programming Practice
-- ==============================================================================

DROP DATABASE IF EXISTS trichyflow_db;
CREATE DATABASE trichyflow_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE trichyflow_db;

-- ------------------------------------------------------------------------------
-- Table 1: USERS (Role-Based Authentication: ADMIN, TRAFFIC_POLICE, CONTROL_ROOM)
-- ------------------------------------------------------------------------------
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(30) NOT NULL,
    badge_number VARCHAR(30) DEFAULT NULL,
    email VARCHAR(100) DEFAULT NULL,
    phone VARCHAR(20) DEFAULT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 2: JUNCTIONS (Trichy City Traffic Intersections)
-- ------------------------------------------------------------------------------
CREATE TABLE junctions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    landmark VARCHAR(150),
    latitude DECIMAL(10, 6) NOT NULL,
    longitude DECIMAL(10, 6) NOT NULL,
    traffic_density VARCHAR(30) DEFAULT 'NORMAL',
    current_phase VARCHAR(50) DEFAULT 'North-South Green',
    green_time INT DEFAULT 45,
    red_time INT DEFAULT 45,
    congestion_index DECIMAL(4, 2) DEFAULT 1.00,
    status VARCHAR(30) DEFAULT 'ACTIVE',
    north_flow INT DEFAULT 20,
    south_flow INT DEFAULT 20,
    east_flow INT DEFAULT 15,
    west_flow INT DEFAULT 15,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 3: ROADS (Road Network & Corridor Segments)
-- ------------------------------------------------------------------------------
CREATE TABLE roads (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    start_junction_id BIGINT NOT NULL,
    end_junction_id BIGINT NOT NULL,
    distance_km DECIMAL(5, 2) NOT NULL,
    speed_limit INT DEFAULT 40,
    current_speed INT DEFAULT 35,
    lanes INT DEFAULT 4,
    status VARCHAR(30) DEFAULT 'OPEN',
    coordinates_json TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (start_junction_id) REFERENCES junctions(id) ON DELETE CASCADE,
    FOREIGN KEY (end_junction_id) REFERENCES junctions(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 4: TRAFFIC_DATA (Historical and Live 4-way Split Traffic Recordings)
-- ------------------------------------------------------------------------------
CREATE TABLE traffic_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    junction_id BIGINT NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    north_count INT DEFAULT 0,
    south_count INT DEFAULT 0,
    east_count INT DEFAULT 0,
    west_count INT DEFAULT 0,
    total_vpm INT DEFAULT 0,
    congestion_level VARCHAR(30) DEFAULT 'LOW',
    FOREIGN KEY (junction_id) REFERENCES junctions(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 5: INCIDENTS (Accidents, Breakdowns, and Hazardous Obstructions)
-- ------------------------------------------------------------------------------
CREATE TABLE incidents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    incident_type VARCHAR(50) NOT NULL,
    location VARCHAR(200) NOT NULL,
    latitude DECIMAL(10, 6) NOT NULL,
    longitude DECIMAL(10, 6) NOT NULL,
    severity VARCHAR(30) DEFAULT 'MEDIUM',
    vehicles_involved INT DEFAULT 1,
    injuries INT DEFAULT 0,
    description TEXT,
    reported_by VARCHAR(100),
    status VARCHAR(30) DEFAULT 'REPORTED',
    detour_route TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 6: ROAD_BLOCKS (Infrastructure Maintenance, Civil Works, Protests)
-- ------------------------------------------------------------------------------
CREATE TABLE road_blocks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    road_id BIGINT NOT NULL,
    reason VARCHAR(200) NOT NULL,
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expected_end_time TIMESTAMP NULL,
    severity VARCHAR(30) DEFAULT 'PARTIAL',
    status VARCHAR(30) DEFAULT 'ACTIVE',
    diversion_notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (road_id) REFERENCES roads(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 7: HOSPITALS (Trichy Major Healthcare & Emergency Facilities)
-- ------------------------------------------------------------------------------
CREATE TABLE hospitals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    location VARCHAR(150) NOT NULL,
    latitude DECIMAL(10, 6) NOT NULL,
    longitude DECIMAL(10, 6) NOT NULL,
    emergency_contact VARCHAR(30) NOT NULL,
    emergency_capacity INT DEFAULT 20,
    current_occupancy INT DEFAULT 5,
    status VARCHAR(30) DEFAULT 'AVAILABLE'
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 8: EMERGENCY_VEHICLES (Ambulances, Fire Brigades, Police Cruisers)
-- ------------------------------------------------------------------------------
CREATE TABLE emergency_vehicles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_code VARCHAR(50) NOT NULL UNIQUE,
    vehicle_type VARCHAR(30) NOT NULL,
    current_lat DECIMAL(10, 6) NOT NULL,
    current_lng DECIMAL(10, 6) NOT NULL,
    destination_hospital_id BIGINT DEFAULT NULL,
    priority VARCHAR(30) DEFAULT 'CRITICAL',
    status VARCHAR(30) DEFAULT 'IDLE',
    assigned_driver VARCHAR(100),
    contact_no VARCHAR(20),
    FOREIGN KEY (destination_hospital_id) REFERENCES hospitals(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 9: EMERGENCY_ROUTES (Green Corridor Paths & Pre-emption Schedules)
-- ------------------------------------------------------------------------------
CREATE TABLE emergency_routes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id BIGINT NOT NULL,
    origin_junction_id BIGINT NOT NULL,
    destination_hospital_id BIGINT NOT NULL,
    route_junctions_json TEXT NOT NULL,
    estimated_travel_time_min DECIMAL(4, 1),
    active BOOLEAN DEFAULT TRUE,
    activated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (vehicle_id) REFERENCES emergency_vehicles(id) ON DELETE CASCADE,
    FOREIGN KEY (origin_junction_id) REFERENCES junctions(id) ON DELETE CASCADE,
    FOREIGN KEY (destination_hospital_id) REFERENCES hospitals(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 10: CHALLANS (E-Challan Traffic Violations Management)
-- ------------------------------------------------------------------------------
CREATE TABLE challans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_number VARCHAR(20) NOT NULL,
    owner_name VARCHAR(100) NOT NULL,
    violation_type VARCHAR(50) NOT NULL,
    location VARCHAR(150) NOT NULL,
    fine_amount DECIMAL(8, 2) NOT NULL,
    payment_status VARCHAR(30) DEFAULT 'PENDING',
    officer_name VARCHAR(100) NOT NULL,
    evidence_url VARCHAR(255) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 11: SIGNAL_PLANS (Dynamic Signal Timing Algorithms & Schedules)
-- ------------------------------------------------------------------------------
CREATE TABLE signal_plans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    junction_id BIGINT NOT NULL,
    plan_name VARCHAR(100) NOT NULL,
    north_south_green INT NOT NULL,
    north_south_red INT NOT NULL,
    east_west_green INT NOT NULL,
    east_west_red INT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    recommended_by_algo BOOLEAN DEFAULT FALSE,
    recommendation_reason VARCHAR(255),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (junction_id) REFERENCES junctions(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 12: SPECIAL_EVENTS (Festivals, Public Rallies, Sports Events)
-- ------------------------------------------------------------------------------
CREATE TABLE special_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(150) NOT NULL,
    location VARCHAR(150) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    expected_crowd INT DEFAULT 5000,
    affected_roads_json TEXT,
    diversion_plan TEXT,
    status VARCHAR(30) DEFAULT 'SCHEDULED'
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 13: TRAFFIC_NOTIFICATIONS (Real-Time Control Room Alert Stream)
-- ------------------------------------------------------------------------------
CREATE TABLE traffic_notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    message TEXT NOT NULL,
    alert_type VARCHAR(30) DEFAULT 'INFO',
    priority VARCHAR(30) DEFAULT 'NORMAL',
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Table 14: TRAFFIC_PREDICTIONS (Rule-Based Congestion Forecasts)
-- ------------------------------------------------------------------------------
CREATE TABLE traffic_predictions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    junction_id BIGINT NOT NULL,
    predicted_day VARCHAR(20) NOT NULL,
    predicted_hour VARCHAR(10) NOT NULL,
    congestion_level VARCHAR(30) NOT NULL,
    confidence_score DECIMAL(3, 2) NOT NULL,
    weather_factor VARCHAR(50) DEFAULT 'Clear',
    event_factor VARCHAR(100) DEFAULT 'None',
    recommended_action TEXT,
    FOREIGN KEY (junction_id) REFERENCES junctions(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ------------------------------------------------------------------------------
-- Indexes for High Performance
-- ------------------------------------------------------------------------------
CREATE INDEX idx_junction_status ON junctions(status);
CREATE INDEX idx_road_status ON roads(status);
CREATE INDEX idx_incidents_status ON incidents(status);
CREATE INDEX idx_challan_vehicle ON challans(vehicle_number);
CREATE INDEX idx_challan_status ON challans(payment_status);
CREATE INDEX idx_ev_status ON emergency_vehicles(status);
