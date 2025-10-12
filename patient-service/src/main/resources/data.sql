-- Create table if not exists
CREATE TABLE IF NOT EXISTS patients (
    patient_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    email VARCHAR(100) UNIQUE,
    gender VARCHAR(10) NOT NULL,
    contact VARCHAR(15) UNIQUE NOT NULL,
    medical_history VARCHAR(255),
    registered_date DATE NOT NULL
);

-- Insert sample data
INSERT INTO patients (patient_id, name, age, email, gender, contact, medical_history, registered_date) VALUES
('P-A1B2C', 'John Doe', 30, 'john.doe@example.com', 'Male', '9876543210', 'None', '2025-10-04'),
('P-D3E4F', 'Alice Smith', 25, 'alice.smith@example.com', 'Female', '9876543211', 'Asthma', '2025-09-30'),
('P-G5H6I', 'Bob Johnson', 40, 'bob.johnson@example.com', 'Male', '9876543212', 'Diabetes', '2025-09-15'),
('P-J7K8L', 'Carol White', 35, 'carol.white@example.com', 'Female', '9876543213', 'Hypertension', '2025-08-20'),
('P-M9N0O', 'David Brown', 28, 'david.brown@example.com', 'Male', '9876543214', 'None', '2025-10-01'),
('P-P1Q2R', 'Eva Green', 32, 'eva.green@example.com', 'Female', '9876543215', 'Allergy', '2025-09-25'),
('P-S3T4U', 'Frank Black', 45, 'frank.black@example.com', 'Male', '9876543216', 'Cardiac Issues', '2025-08-30'),
('P-V5W6X', 'Grace Lee', 29, 'grace.lee@example.com', 'Female', '9876543217', 'None', '2025-09-10'),
('P-Y7Z8A', 'Henry King', 38, 'henry.king@example.com', 'Male', '9876543218', 'Diabetes', '2025-08-28'),
('P-B9C0D', 'Ivy Scott', 31, 'ivy.scott@example.com', 'Female', '9876543219', 'Asthma', '2025-10-02'),
('P-E1F2G', 'Jack Turner', 42, 'jack.turner@example.com', 'Male', '9876543220', 'Hypertension', '2025-09-12'),
('P-H3I4J', 'Karen Hill', 27, 'karen.hill@example.com', 'Female', '9876543221', 'Allergy', '2025-09-18'),
('P-K5L6M', 'Leo Adams', 36, 'leo.adams@example.com', 'Male', '9876543222', 'None', '2025-08-25'),
('P-N7O8P', 'Mia Clark', 33, 'mia.clark@example.com', 'Female', '9876543223', 'Cardiac Issues', '2025-09-05'),
('P-Q9R0S', 'Nathan Hall', 39, 'nathan.hall@example.com', 'Male', '9876543224', 'Diabetes', '2025-09-22');
