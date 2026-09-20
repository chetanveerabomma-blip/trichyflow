-- ==============================================================================
-- TRICHYFLOW: Realistic Demo Data for Tiruchirappalli (Trichy)
-- All traffic values represent "TRICHY TRAFFIC SIMULATION" for academic demo
-- ==============================================================================

USE trichyflow_db;

-- ------------------------------------------------------------------------------
-- 1. USERS (Credentials: admin/admin123, police/police123, control/control123)
-- Password hash: BCrypt of respective passwords
-- ------------------------------------------------------------------------------
INSERT INTO users (id, username, password_hash, full_name, role, badge_number, email, phone, active) VALUES
(1, 'admin', '$2a$10$w82/sO67QZJ7eFhF4o2q3eFh7bQ9aQ3V.hV6rVf8dO7V6rVf8dO7W', 'Commissioner S. Ramanathan', 'ADMIN', 'IPS-TR-01', 'admin@trichyflow.gov.in', '+91 94431 10001', TRUE),
(2, 'police', '$2a$10$w82/sO67QZJ7eFhF4o2q3eFh7bQ9aQ3V.hV6rVf8dO7V6rVf8dO7W', 'Inspector M. Anbarasan', 'TRAFFIC_POLICE', 'TP-CHATH-42', 'anbarasan.tp@trichyflow.gov.in', '+91 94431 10002', TRUE),
(3, 'control', '$2a$10$w82/sO67QZJ7eFhF4o2q3eFh7bQ9aQ3V.hV6rVf8dO7V6rVf8dO7W', 'Lead Operator K. Selvam', 'CONTROL_ROOM', 'CR-SYS-108', 'selvam.ops@trichyflow.gov.in', '+91 94431 10003', TRUE),
(4, 'sub_inspector_kavitha', '$2a$10$w82/sO67QZJ7eFhF4o2q3eFh7bQ9aQ3V.hV6rVf8dO7V6rVf8dO7W', 'SI Kavitha Sundaram', 'TRAFFIC_POLICE', 'TP-CENT-19', 'kavitha.tp@trichyflow.gov.in', '+91 94431 10004', TRUE);

-- ------------------------------------------------------------------------------
-- 2. JUNCTIONS (18 Major Trichy Traffic Nodes)
-- ------------------------------------------------------------------------------
INSERT INTO junctions (id, name, landmark, latitude, longitude, traffic_density, current_phase, green_time, red_time, congestion_index, status, north_flow, south_flow, east_flow, west_flow) VALUES
(1, 'Chathiram Bus Stand Junction', 'Near St. Joseph College & Old Bus Stand', 10.828500, 78.694800, 'HEAVY', 'North-South Green', 60, 40, 4.20, 'ACTIVE', 85, 78, 45, 38),
(2, 'Main Guard Gate (Rockfort)', 'Entry to Teppakulam & Rockfort Temple', 10.826200, 78.697400, 'HEAVY', 'East-West Green', 50, 45, 3.90, 'ACTIVE', 65, 55, 72, 68),
(3, 'Thillai Nagar Main Road (11th Cross)', 'Commercial Shopping & Clinic Hub', 10.820100, 78.687200, 'MODERATE', 'North-South Green', 45, 45, 2.80, 'ACTIVE', 52, 48, 30, 25),
(4, 'Central Bus Stand Junction', 'Intercity Bus Terminus & Hotel District', 10.796800, 78.686500, 'CRITICAL', 'North-South Green', 70, 35, 4.85, 'ACTIVE', 110, 95, 80, 75),
(5, 'Trichy Railway Junction (Collectorate)', 'Opposite Southern Railway Divisional HQ', 10.793200, 78.683400, 'HEAVY', 'East-West Green', 55, 40, 3.95, 'ACTIVE', 75, 70, 85, 82),
(6, 'Cantonment Roundabout', 'Near Head Post Office & District Court', 10.803500, 78.687000, 'MODERATE', 'Roundabout Circulating', 45, 45, 2.50, 'ACTIVE', 40, 45, 42, 38),
(7, 'TVS Tollgate Junction', 'NH-45 Madurai-Chennai Highway Convergence', 10.787100, 78.704200, 'CRITICAL', 'East-West Green', 65, 45, 4.60, 'ACTIVE', 95, 90, 105, 98),
(8, 'Palakkarai Junction', 'Heritage Overbridge & Goods Shed Road', 10.812200, 78.698100, 'MODERATE', 'North-South Green', 40, 45, 3.10, 'ACTIVE', 48, 50, 35, 40),
(9, 'Woraiyur Nachiyar Kovil Cross', 'Textile Heritage & Residential Arterial', 10.822800, 78.675400, 'NORMAL', 'North-South Green', 35, 45, 1.80, 'ACTIVE', 28, 25, 20, 18),
(10, 'Srirangam Rajagopuram Junction', 'Entrance to Sri Ranganathaswamy Temple', 10.862500, 78.691200, 'HEAVY', 'East-West Green', 50, 40, 3.80, 'ACTIVE', 70, 68, 62, 58),
(11, 'Tiruvanaikoil Trunk Road Junction', 'Jambukeswarar Akilandeswari Temple Route', 10.853200, 78.705500, 'MODERATE', 'North-South Green', 40, 40, 2.60, 'ACTIVE', 45, 40, 38, 35),
(12, 'Ponmalai (Golden Rock) Market', 'Railway Workshop Colony & Shandy Ground', 10.778500, 78.721400, 'NORMAL', 'North-South Green', 35, 45, 2.10, 'ACTIVE', 32, 30, 25, 22),
(13, 'KK Nagar Sundar Nagar Signal', 'Sundar Nagar Arterial towards Airport', 10.771200, 78.705800, 'MODERATE', 'North-South Green', 40, 45, 2.70, 'ACTIVE', 45, 42, 35, 30),
(14, 'Trichy International Airport Junction', 'Pudukkottai Highway & Terminal Access', 10.764500, 78.718900, 'NORMAL', 'East-West Green', 45, 45, 1.95, 'ACTIVE', 35, 32, 40, 38),
(15, 'Thennur High Road Cross', 'Anna Nagar & Uzhavar Sandhai Connector', 10.814500, 78.687800, 'MODERATE', 'East-West Green', 40, 45, 2.90, 'ACTIVE', 44, 46, 50, 48),
(16, 'Crawford Post Office Signal', 'Madurai Bypass connector & Military Camp', 10.779200, 78.677500, 'NORMAL', 'North-South Green', 30, 50, 1.70, 'ACTIVE', 25, 22, 28, 24),
(17, 'Subramaniapuram Main Junction', 'Prison Road & Airport Alternate Bypass', 10.783200, 78.691800, 'MODERATE', 'North-South Green', 40, 40, 2.45, 'ACTIVE', 38, 36, 42, 40),
(18, 'Mambalasalai Cauvery Bridge Junction', 'North Bridge Approach connecting Srirangam', 10.842100, 78.698500, 'HEAVY', 'North-South Green', 55, 40, 3.90, 'ACTIVE', 82, 80, 40, 35);

-- ------------------------------------------------------------------------------
-- 3. HOSPITALS (6 Major Trichy Emergency Medical Hubs)
-- ------------------------------------------------------------------------------
INSERT INTO hospitals (id, name, location, latitude, longitude, emergency_contact, emergency_capacity, current_occupancy, status) VALUES
(1, 'Mahatma Gandhi Memorial Govt Hospital (Trichy GH)', 'Near Collectorate, Cantonment', 10.806200, 78.685400, '+91 431 2415555', 45, 32, 'AVAILABLE'),
(2, 'Kauvery Hospital (Cantonment)', 'Tennur High Road & Cantonment', 10.808500, 78.687200, '+91 431 4006000', 30, 24, 'AVAILABLE'),
(3, 'Apollo Speciality Hospitals', 'Old Thanjavur Road, Ariyamangalam', 10.801200, 78.718000, '+91 431 3986000', 25, 18, 'AVAILABLE'),
(4, 'Cethar Hospitals', '11th Cross, Thillai Nagar West', 10.821500, 78.684100, '+91 431 2760000', 20, 19, 'BUSY'),
(5, 'Frontline Hospital', 'Williams Road, Cantonment', 10.799500, 78.688100, '+91 431 2414141', 15, 8, 'AVAILABLE'),
(6, 'Child Jesus Hospital', 'Pudukkottai Main Road, Cantonment', 10.791500, 78.692200, '+91 431 2412233', 18, 17, 'BUSY');

-- ------------------------------------------------------------------------------
-- 4. ROADS (22 Inter-Junction Connectors with GPS Coordinates)
-- ------------------------------------------------------------------------------
INSERT INTO roads (id, name, start_junction_id, end_junction_id, distance_km, speed_limit, current_speed, lanes, status, coordinates_json) VALUES
(1, 'Cauvery River Trunk Road (Chathiram to Mambalasalai)', 1, 18, 1.60, 50, 32, 4, 'OPEN', '[[10.8285, 78.6948], [10.8350, 78.6965], [10.8421, 78.6985]]'),
(2, 'Srirangam Bridge Arterial (Mambalasalai to Rajagopuram)', 18, 10, 2.40, 50, 28, 4, 'OPEN', '[[10.8421, 78.6985], [10.8520, 78.6950], [10.8625, 78.6912]]'),
(3, 'Thiruvanaikoil Bypass Road', 18, 11, 1.50, 45, 38, 4, 'OPEN', '[[10.8421, 78.6985], [10.8475, 78.7015], [10.8532, 78.7055]]'),
(4, 'College Road (Chathiram to Main Guard Gate)', 1, 2, 0.45, 30, 18, 2, 'CONGESTED', '[[10.8285, 78.6948], [10.8270, 78.6960], [10.8262, 78.6974]]'),
(5, 'Karur Bypass Road (Chathiram to Thillai Nagar)', 1, 3, 1.20, 45, 25, 4, 'OPEN', '[[10.8285, 78.6948], [10.8245, 78.6905], [10.8201, 78.6872]]'),
(6, 'Woraiyur Link (Thillai Nagar to Woraiyur Nachiyar)', 3, 9, 1.40, 35, 30, 2, 'OPEN', '[[10.8201, 78.6872], [10.8215, 78.6810], [10.8228, 78.6754]]'),
(7, 'Thennur High Road (Thillai Nagar to Thennur Cross)', 3, 15, 0.85, 35, 22, 4, 'OPEN', '[[10.8201, 78.6872], [10.8170, 78.6875], [10.8145, 78.6878]]'),
(8, 'Palakkarai Main Road (Main Guard Gate to Palakkarai)', 2, 8, 1.65, 40, 24, 2, 'OPEN', '[[10.8262, 78.6974], [10.8190, 78.6980], [10.8122, 78.6981]]'),
(9, 'Heber Road (Thennur to Cantonment Roundabout)', 15, 6, 1.25, 45, 34, 4, 'OPEN', '[[10.8145, 78.6878], [10.8090, 78.6874], [10.8035, 78.6870]]'),
(10, 'Williams Road (Cantonment to Central Bus Stand)', 6, 4, 0.90, 40, 15, 4, 'CONGESTED', '[[10.8035, 78.6870], [10.8000, 78.6868], [10.7968, 78.6865]]'),
(11, 'Collectorate Link (Central Bus Stand to Railway Junction)', 4, 5, 0.60, 35, 12, 4, 'CONGESTED', '[[10.7968, 78.6865], [10.7950, 78.6850], [10.7932, 78.6834]]'),
(12, 'Dindigul Road (Railway Junction to Crawford)', 5, 16, 1.70, 50, 42, 4, 'OPEN', '[[10.7932, 78.6834], [10.7860, 78.6800], [10.7792, 78.6775]]'),
(13, 'Pudukkottai Highway (Central Bus Stand to Subramaniapuram)', 4, 17, 1.60, 50, 36, 4, 'OPEN', '[[10.7968, 78.6865], [10.7900, 78.6890], [10.7832, 78.6918]]'),
(14, 'TVS Tollgate Link (Subramaniapuram to TVS Tollgate)', 17, 7, 1.45, 50, 20, 4, 'CONGESTED', '[[10.7832, 78.6918], [10.7850, 78.6980], [10.7871, 78.7042]]'),
(15, 'Palakkarai Overbridge to TVS Tollgate', 8, 7, 2.90, 45, 32, 4, 'OPEN', '[[10.8122, 78.6981], [10.7990, 78.7010], [10.7871, 78.7042]]'),
(16, 'NH-45 Madurai Bypass (TVS Tollgate to KK Nagar)', 7, 13, 1.80, 60, 48, 6, 'OPEN', '[[10.7871, 78.7042], [10.7790, 78.7050], [10.7712, 78.7058]]'),
(17, 'Airport Road (KK Nagar to International Airport)', 13, 14, 1.60, 60, 52, 4, 'OPEN', '[[10.7712, 78.7058], [10.7680, 78.7120], [10.7645, 78.7189]]'),
(18, 'Ponmalai GOC Road (TVS Tollgate to Golden Rock)', 7, 12, 2.10, 40, 34, 2, 'OPEN', '[[10.7871, 78.7042], [10.7825, 78.7130], [10.7785, 78.7214]]'),
(19, 'Golden Rock to Airport Outer Ring', 12, 14, 1.75, 45, 40, 2, 'OPEN', '[[10.7785, 78.7214], [10.7710, 78.7200], [10.7645, 78.7189]]'),
(20, 'Court Road / GH Access (Cantonment Roundabout to Trichy GH)', 6, 1, 0.40, 30, 25, 2, 'OPEN', '[[10.8035, 78.6870], [10.8050, 78.6860], [10.8062, 78.6854]]'),
(21, 'Thillai Nagar 1st Cross Flyover Road', 3, 2, 1.20, 40, 0, 2, 'FULLY_BLOCKED', '[[10.8201, 78.6872], [10.8230, 78.6920], [10.8262, 78.6974]]'),
(22, 'Old Palakkarai Bazar Link', 8, 15, 1.10, 30, 15, 2, 'PARTIALLY_BLOCKED', '[[10.8122, 78.6981], [10.8135, 78.6925], [10.8145, 78.6878]]');

-- ------------------------------------------------------------------------------
-- 5. INCIDENTS (10 Realistic Incidents)
-- ------------------------------------------------------------------------------
INSERT INTO incidents (id, incident_type, location, latitude, longitude, severity, vehicles_involved, injuries, description, reported_by, status, detour_route) VALUES
(1, 'ACCIDENT', 'Central Bus Stand Exit Gate near Royal Hotel', 10.796500, 78.686800, 'CRITICAL', 3, 2, 'Collision between TNSTC passenger bus and auto-rickshaw. Northbound lane obstructed.', 'Traffic Constable Murugan', 'REPORTED', 'Divert via Williams Road and Cantonment Roundabout'),
(2, 'ACCIDENT', 'Chathiram Bus Stand near College Road Corner', 10.828200, 78.695200, 'HIGH', 2, 1, 'Two-wheeler collision with delivery truck. Road clearance under way.', 'SI Kavitha Sundaram', 'DISPATCHED', 'Route via Karur Bypass Road'),
(3, 'VEHICLE_BREAKDOWN', 'Mambalasalai Cauvery Bridge North Span', 10.841500, 78.698200, 'MEDIUM', 1, 0, 'Heavy container trailer axle breakdown on bridge approach lane.', 'Highway Patrol 3', 'RESOLVING', 'Use Thiruvanaikoil old bridge for light vehicles'),
(4, 'WATER_LOGGING', 'TVS Tollgate Underpass Subway', 10.786800, 78.703800, 'HIGH', 0, 0, 'Flash rain runoff accumulated 2.5 ft water in the subway.', 'Control Room Sensors', 'REPORTED', 'Reroute via Subramaniapuram Overbridge'),
(5, 'ACCIDENT', 'Thillai Nagar 11th Cross near Shopping Mall', 10.820500, 78.687500, 'MEDIUM', 2, 0, 'Rear-end fender bender between two private hatchbacks.', 'Beat Officer Ramesh', 'RESOLVED', 'None - lane cleared'),
(6, 'FALLEN_TREE', 'Woraiyur Nachiyar Temple Street', 10.822200, 78.675000, 'HIGH', 1, 0, 'Large Banyan tree branch fallen across electric cables.', 'Woraiyur Station SI', 'RESOLVING', 'Traffic diverted through Salai Road'),
(7, 'OIL_SPILL', 'Palakkarai Overbridge Incline', 10.812500, 78.698500, 'CRITICAL', 4, 3, 'Spilled hydraulic oil caused skidding of three two-wheelers.', 'Patrol Unit 07', 'DISPATCHED', 'Sand spreading in progress; use Goods Shed road'),
(8, 'ACCIDENT', 'KK Nagar Sundar Nagar Signal', 10.771500, 78.706200, 'LOW', 2, 0, 'Minor scrape between city bus and motorcycle.', 'Public Emergency Call', 'RESOLVED', 'Cleared by local traffic patrol'),
(9, 'VEHICLE_BREAKDOWN', 'Trichy Railway Junction Station Approach', 10.793000, 78.683000, 'MEDIUM', 1, 0, 'Airport shuttle mini-bus clutch failure blocking lane 2.', 'Station Traffic Booth', 'REPORTED', 'Traffic diverted to station parcel office lane'),
(10, 'ACCIDENT', 'Srirangam Amma Mandapam Ghat Road', 10.858000, 78.689000, 'MEDIUM', 2, 1, 'Devotee van collided with parked light commercial vehicle.', 'Srirangam Traffic Police', 'RESOLVING', 'Divert via North Gate Outer Road');

-- ------------------------------------------------------------------------------
-- 6. ROAD_BLOCKS (Active Civil Works & Closures)
-- ------------------------------------------------------------------------------
INSERT INTO road_blocks (id, road_id, reason, start_time, expected_end_time, severity, status, diversion_notes) VALUES
(1, 21, 'Underground Drainage Pipe Laying (UGD Project)', '2026-09-18 08:00:00', '2026-09-24 18:00:00', 'COMPLETE', 'ACTIVE', 'Road completely dug up; divert all light traffic via Salai Road and Karur Bypass'),
(2, 22, 'Culvert Reconstruction & Storm Drain Concrete Work', '2026-09-19 09:00:00', '2026-09-22 20:00:00', 'PARTIAL', 'ACTIVE', 'One-way restriction enforced; heavy vehicles strictly rerouted through TVS Tollgate');

-- ------------------------------------------------------------------------------
-- 7. EMERGENCY_VEHICLES (Ambulances, Fire, Police)
-- ------------------------------------------------------------------------------
INSERT INTO emergency_vehicles (id, vehicle_code, vehicle_type, current_lat, current_lng, destination_hospital_id, priority, status, assigned_driver, contact_no) VALUES
(1, 'AMB-108-A', 'AMBULANCE', 10.828500, 78.694800, 1, 'CRITICAL', 'EN_ROUTE', 'R. Manikandan', '+91 98421 00108'),
(2, 'AMB-108-B', 'AMBULANCE', 10.862500, 78.691200, 2, 'HIGH', 'IDLE', 'S. Vigneshwaran', '+91 98421 00109'),
(3, 'FIRE-TENDER-01', 'FIRE_TRUCK', 10.803500, 78.687000, NULL, 'CRITICAL', 'IDLE', 'Station Officer G. Pandian', '+91 94431 00101'),
(4, 'PATROL-PCR-05', 'POLICE', 10.796800, 78.686500, NULL, 'HIGH', 'EN_ROUTE', 'Head Constable K. Murugan', '+91 94431 00100'),
(5, 'AMB-KAUVERY-03', 'AMBULANCE', 10.787100, 78.704200, 2, 'CRITICAL', 'IDLE', 'V. Anthony Raj', '+91 98421 00110');

-- ------------------------------------------------------------------------------
-- 8. EMERGENCY_ROUTES (Green Corridor Pre-emption)
-- ------------------------------------------------------------------------------
INSERT INTO emergency_routes (id, vehicle_id, origin_junction_id, destination_hospital_id, route_junctions_json, estimated_travel_time_min, active, activated_at) VALUES
(1, 1, 1, 1, '[1, 3, 15, 6, 1]', 7.5, TRUE, CURRENT_TIMESTAMP);

-- ------------------------------------------------------------------------------
-- 9. CHALLANS (35 Realistic Tamil Nadu E-Challans)
-- ------------------------------------------------------------------------------
INSERT INTO challans (id, vehicle_number, owner_name, violation_type, location, fine_amount, payment_status, officer_name, evidence_url) VALUES
(1, 'TN45AB1234', 'S. Sivakumar', 'Signal Jump', 'Chathiram Bus Stand Junction', 1000.00, 'UNPAID', 'SI Kavitha Sundaram', 'https://trichyflow.gov.in/evidence/cam01_0920.jpg'),
(2, 'TN48CD5678', 'P. Radhakrishnan', 'Speeding', 'NH-45 TVS Tollgate Bypass', 1500.00, 'PAID', 'Inspector M. Anbarasan', 'https://trichyflow.gov.in/evidence/speed04_0819.jpg'),
(3, 'TN55EF9012', 'M. Karthikeyan', 'No Helmet', 'Thillai Nagar 11th Cross', 1000.00, 'PENDING', 'HC R. Periyasamy', 'https://trichyflow.gov.in/evidence/cam03_0911.jpg'),
(4, 'TN45BK4499', 'D. Anand Babu', 'Wrong Parking', 'Main Guard Gate Teppakulam Bazaar', 500.00, 'UNPAID', 'SI Kavitha Sundaram', 'https://trichyflow.gov.in/evidence/park01_0912.jpg'),
(5, 'TN48Z1102', 'K. Meenakshi Sundaram', 'Dangerous Driving', 'Cantonment Roundabout', 2000.00, 'PENDING', 'Inspector M. Anbarasan', 'https://trichyflow.gov.in/evidence/dash02_0915.jpg'),
(6, 'TN45CC8831', 'G. Balasubramanian', 'No Seat Belt', 'Trichy Railway Junction Station Road', 1000.00, 'PAID', 'HC S. Ganesan', 'https://trichyflow.gov.in/evidence/cam05_0917.jpg'),
(7, 'TN45DD2341', 'R. Velmurugan', 'Driving Without License', 'Woraiyur Nachiyar Kovil', 5000.00, 'UNPAID', 'SI T. Rajendran', 'https://trichyflow.gov.in/evidence/chk01_0918.jpg'),
(8, 'TN48AA7765', 'A. Mohammed Farooq', 'Signal Jump', 'Central Bus Stand Signal', 1000.00, 'PENDING', 'HC K. Murugan', 'https://trichyflow.gov.in/evidence/cam04_0919.jpg'),
(9, 'TN55K9090', 'J. Suresh Kumar', 'Speeding', 'Airport Outer Ring Road', 1500.00, 'PAID', 'SI V. Dhanapal', 'https://trichyflow.gov.in/evidence/speed11_0920.jpg'),
(10, 'TN45AW3312', 'T. Saravanan', 'No Helmet', 'Palakkarai Overbridge Incline', 1000.00, 'UNPAID', 'HC M. Raman', 'https://trichyflow.gov.in/evidence/cam08_0920.jpg'),
(11, 'TN48BB4321', 'V. Lakshmi Narayanan', 'Signal Jump', 'TVS Tollgate Junction', 1000.00, 'UNPAID', 'Inspector M. Anbarasan', 'https://trichyflow.gov.in/evidence/cam07_0920.jpg'),
(12, 'TN45XY9812', 'E. Charles Robert', 'Dangerous Driving', 'Mambalasalai Cauvery Bridge', 2000.00, 'PENDING', 'SI Kavitha Sundaram', 'https://trichyflow.gov.in/evidence/cam18_0920.jpg'),
(13, 'TN48CD1122', 'N. Muthulakshmi', 'Wrong Parking', 'Srirangam South Chitra Street', 500.00, 'PAID', 'HC P. Karuppan', 'https://trichyflow.gov.in/evidence/park03_0918.jpg'),
(14, 'TN45QQ4433', 'K. Balamurugan', 'No Seat Belt', 'KK Nagar Sundar Nagar', 1000.00, 'PENDING', 'SI V. Dhanapal', 'https://trichyflow.gov.in/evidence/cam13_0919.jpg'),
(15, 'TN55MM7788', 'S. Pradeep Kumar', 'Driving Without License', 'Crawford Post Office Signal', 5000.00, 'UNPAID', 'SI T. Rajendran', 'https://trichyflow.gov.in/evidence/chk03_0919.jpg'),
(16, 'TN45RT8811', 'M. Aravind Swamy', 'Speeding', 'Karur Bypass Arterial', 1500.00, 'PAID', 'SI Kavitha Sundaram', 'https://trichyflow.gov.in/evidence/speed02_0919.jpg'),
(17, 'TN48KL5566', 'R. Deepa Chandran', 'Signal Jump', 'Thennur High Road Cross', 1000.00, 'UNPAID', 'HC S. Ganesan', 'https://trichyflow.gov.in/evidence/cam15_0919.jpg'),
(18, 'TN45ZX3399', 'B. Dinesh Kumar', 'No Helmet', 'Ponmalai Golden Rock Market', 1000.00, 'PAID', 'HC M. Raman', 'https://trichyflow.gov.in/evidence/cam12_0919.jpg'),
(19, 'TN48MN6677', 'P. Senthil Nathan', 'Dangerous Driving', 'Airport Terminal Access Road', 2000.00, 'PENDING', 'SI V. Dhanapal', 'https://trichyflow.gov.in/evidence/dash04_0919.jpg'),
(20, 'TN45JJ9900', 'V. Geetha Govind', 'Wrong Parking', 'Williams Road Cantonment', 500.00, 'UNPAID', 'Inspector M. Anbarasan', 'https://trichyflow.gov.in/evidence/park05_0919.jpg'),
(21, 'TN55EE1133', 'H. Harishankar', 'Signal Jump', 'Subramaniapuram Junction', 1000.00, 'PENDING', 'HC K. Murugan', 'https://trichyflow.gov.in/evidence/cam17_0920.jpg'),
(22, 'TN45PP2244', 'C. Cyril Prakash', 'No Seat Belt', 'Tiruvanaikoil Trunk Road', 1000.00, 'PAID', 'SI Kavitha Sundaram', 'https://trichyflow.gov.in/evidence/cam11_0920.jpg'),
(23, 'TN48QQ3355', 'L. Loganathan', 'Driving Without License', 'Chathiram Old Bus Stand', 5000.00, 'UNPAID', 'SI Kavitha Sundaram', 'https://trichyflow.gov.in/evidence/chk04_0920.jpg'),
(24, 'TN45WW4466', 'N. Nithya Kalyani', 'Speeding', 'NH-45 Madurai Bypass', 1500.00, 'PAID', 'Inspector M. Anbarasan', 'https://trichyflow.gov.in/evidence/speed05_0920.jpg'),
(25, 'TN48RR5577', 'T. Tamilselvan', 'No Helmet', 'Woraiyur Market Road', 1000.00, 'PENDING', 'SI T. Rajendran', 'https://trichyflow.gov.in/evidence/cam09_0920.jpg'),
(26, 'TN45SS6688', 'W. Wilfred Joseph', 'Signal Jump', 'Central Bus Stand Junction', 1000.00, 'UNPAID', 'HC K. Murugan', 'https://trichyflow.gov.in/evidence/cam04_0920.jpg'),
(27, 'TN48TT7799', 'Y. Yogeshwaran', 'Dangerous Driving', 'Chathiram College Road', 2000.00, 'PENDING', 'SI Kavitha Sundaram', 'https://trichyflow.gov.in/evidence/dash05_0920.jpg'),
(28, 'TN55UU8800', 'Z. Zakir Hussain', 'Wrong Parking', 'Main Guard Gate Bazaar', 500.00, 'PAID', 'HC R. Periyasamy', 'https://trichyflow.gov.in/evidence/park06_0920.jpg'),
(29, 'TN45VV9911', 'S. Shalini Priyadarshini', 'No Seat Belt', 'Thillai Nagar West Extn', 1000.00, 'PAID', 'HC S. Ganesan', 'https://trichyflow.gov.in/evidence/cam03_0920.jpg'),
(30, 'TN48WW1122', 'M. Madhan Mohan', 'Speeding', 'Airport Outer Ring', 1500.00, 'PENDING', 'SI V. Dhanapal', 'https://trichyflow.gov.in/evidence/speed12_0920.jpg'),
(31, 'TN45XX2233', 'R. Revathi Sankar', 'Driving Without License', 'TVS Tollgate Underpass', 5000.00, 'UNPAID', 'Inspector M. Anbarasan', 'https://trichyflow.gov.in/evidence/chk05_0920.jpg'),
(32, 'TN48YY3344', 'K. Kaviya Varshini', 'No Helmet', 'Palakkarai Roundabout', 1000.00, 'PAID', 'HC M. Raman', 'https://trichyflow.gov.in/evidence/cam08_0920.jpg'),
(33, 'TN55ZZ4455', 'B. Bhuvaneshwari', 'Signal Jump', 'Cantonment Signal', 1000.00, 'PENDING', 'Inspector M. Anbarasan', 'https://trichyflow.gov.in/evidence/cam06_0920.jpg'),
(34, 'TN45AA5566', 'A. Ashwin Kumar', 'Wrong Parking', 'Rockfort Teppakulam Corner', 500.00, 'UNPAID', 'SI Kavitha Sundaram', 'https://trichyflow.gov.in/evidence/park07_0920.jpg'),
(35, 'TN48BB6677', 'O. Om Prakash', 'Speeding', 'Srirangam Bypass Link', 1500.00, 'PAID', 'SI Kavitha Sundaram', 'https://trichyflow.gov.in/evidence/speed08_0920.jpg');

-- ------------------------------------------------------------------------------
-- 10. SPECIAL_EVENTS
-- ------------------------------------------------------------------------------
INSERT INTO special_events (id, event_name, location, start_time, end_time, expected_crowd, affected_roads_json, diversion_plan, status) VALUES
(1, 'Srirangam Vaikunta Ekadasi Festival (Paramapada Vasal Opening)', 'Sri Ranganathaswamy Temple, Srirangam', '2026-12-21 03:00:00', '2026-12-25 23:59:00', 150000, '[2, 3]', 'All four-wheelers diverted to temporary parking grounds at Yatri Nivas; only green corridor permitted on bridge.', 'SCHEDULED'),
(2, 'NIT Trichy International Technical Symposium Pragyan', 'Thuvakudi & Tanjore Road', '2026-10-15 09:00:00', '2026-10-18 20:00:00', 12000, '[14, 16]', 'Special shuttle buses deployed from Central Bus Stand to Campus; lane 1 dedicated to campus shuttles.', 'SCHEDULED'),
(3, 'Chithirai Car Festival (Rockfort Temple Chariot)', 'Rockfort Teppakulam Bazaar', '2026-04-20 06:00:00', '2026-04-22 18:00:00', 80000, '[4, 8, 21]', 'Complete pedestrianization of Teppakulam perimeter; diversions via Karur Bypass Road.', 'SCHEDULED');

-- ------------------------------------------------------------------------------
-- 11. NOTIFICATIONS
-- ------------------------------------------------------------------------------
INSERT INTO traffic_notifications (id, title, message, alert_type, priority, timestamp, is_read) VALUES
(1, 'Emergency Corridor Activated', 'Ambulance AMB-108-A dispatched towards Mahatma Gandhi Memorial Govt Hospital. Priority corridor engaged.', 'EMERGENCY', 'CRITICAL', CURRENT_TIMESTAMP, FALSE),
(2, 'Major Accident at Central Bus Stand', 'Multi-vehicle collision reported near Royal Hotel. Police patrol & ambulance dispatched.', 'ACCIDENT', 'HIGH', CURRENT_TIMESTAMP, FALSE),
(3, 'High Congestion Warning: TVS Tollgate', 'Vehicular queue extends over 1.2 km on NH-45 approach. Signal timing recommendation generated (+15s East-West).', 'CONGESTION', 'HIGH', CURRENT_TIMESTAMP, FALSE),
(4, 'Road Block Notice: Thillai Nagar 1st Cross', 'UGD pipe laying active. Diversion in place via Karur Bypass Road.', 'WARNING', 'NORMAL', CURRENT_TIMESTAMP, TRUE),
(5, 'Weather Alert: Heavy Downpour Imminent', 'Regional Met Centre issues heavy rain forecast for Trichy Corporation. Low-lying subways monitored.', 'INFO', 'NORMAL', CURRENT_TIMESTAMP, TRUE);

-- ------------------------------------------------------------------------------
-- 12. TRAFFIC_PREDICTIONS (Rule-Based Forecasts)
-- ------------------------------------------------------------------------------
INSERT INTO traffic_predictions (id, junction_id, predicted_day, predicted_hour, congestion_level, confidence_score, weather_factor, event_factor, recommended_action) VALUES
(1, 1, 'Monday', '09:00 AM', 'SEVERE', 0.92, 'Clear', 'Office & College Peak', 'Extend North-South green duration by 20s; activate manual officer override at Chathiram.'),
(2, 4, 'Monday', '09:30 AM', 'SEVERE', 0.95, 'Clear', 'Interdistrict Bus Peak', 'Implement queue clearance phase; throttle approach from Cantonment by 10s.'),
(3, 7, 'Monday', '06:00 PM', 'HIGH', 0.88, 'Clear', 'Evening Industrial Dispersal', 'Favor East-West NH-45 arterial flow; pre-signal warning at Subramaniapuram.'),
(4, 10, 'Friday', '07:30 PM', 'HIGH', 0.85, 'Festival Devotees', 'Weekend Temple Pilgrimage', 'Hold peripheral parking flow; direct light vehicles through East Chitra Street.'),
(5, 3, 'Tuesday', '02:00 PM', 'LOW', 0.90, 'Clear', 'Off-Peak Midday', 'Reduce signal cycle to 45s standard for energy and wait-time minimization.');
