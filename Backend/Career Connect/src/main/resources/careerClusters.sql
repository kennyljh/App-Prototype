-- Create the table
CREATE TABLE IF NOT EXISTS career_clusters (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Insert data into the table
INSERT IGNORE INTO career_clusters (id, name)
VALUES
    (1, 'Agriculture, Food & Natural Resources'),
    (2, 'Architecture & Construction'),
    (3, 'Arts, Audio/Video Technology & Communications'),
    (4, 'Business Management & Administration'),
    (5, 'Education & Training'),
    (6, 'Finance'),
    (7, 'Government & Public Administration'),
    (8, 'Health Science'),
    (9, 'Hospitality & Tourism'),
    (10, 'Human Services'),
    (11, 'Information Technology'),
    (12, 'Law, Public Safety, Corrections & Security'),
    (13, 'Manufacturing'),
    (14, 'Marketing'),
    (15, 'Science, Technology, Engineering & Mathematics'),
    (16, 'Transportation, Distribution & Logistics');