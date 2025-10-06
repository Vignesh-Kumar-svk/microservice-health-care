INSERT INTO patients (patient_id, name, age, email, gender, contact, medical_history, registered_date) VALUES
(gen_random_uuid(), 'John Doe', 30, 'john.doe@example.com', 'Male', '9876543210', 'None', '2025-10-04'),
(gen_random_uuid(), 'Alice Smith', 25, 'alice.smith@example.com', 'Female', '9876543211', 'Asthma', '2025-09-30'),
(gen_random_uuid(), 'Bob Johnson', 40, 'bob.johnson@example.com', 'Male', '9876543212', 'Diabetes', '2025-09-15'),
(gen_random_uuid(), 'Carol White', 35, 'carol.white@example.com', 'Female', '9876543213', 'Hypertension', '2025-08-20'),
(gen_random_uuid(), 'David Brown', 28, 'david.brown@example.com', 'Male', '9876543214', 'None', '2025-10-01'),
(gen_random_uuid(), 'Eva Green', 32, 'eva.green@example.com', 'Female', '9876543215', 'Allergy', '2025-09-25'),
(gen_random_uuid(), 'Frank Black', 45, 'frank.black@example.com', 'Male', '9876543216', 'Cardiac Issues', '2025-08-30');
