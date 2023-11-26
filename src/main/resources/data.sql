-- Create table structure (if it's not already defined in a schema.sql file or by JPA)
CREATE TABLE IF NOT EXISTS location (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255), -- Added a column for the name of the location
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL
);

-- Insert sample data with location names

