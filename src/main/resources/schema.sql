DROP TABLE IF EXISTS criteria;
DROP TABLE IF EXISTS filter;

-- Create the 'filter' table
CREATE TABLE filter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    filter_name VARCHAR(255) NOT NULL,
    selection VARCHAR(20) NOT NULL
);

-- Create the 'criteria' table with single table inheritance
CREATE TABLE criteria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    filter_id BIGINT,
    field VARCHAR(31) NOT NULL,
    amount_condition VARCHAR(20),
    amount_value VARCHAR(255),
    date_condition VARCHAR(20),
    date_value VARCHAR(255),
    title_condition VARCHAR(20),
    title_value VARCHAR(255),
    CONSTRAINT fk_filter FOREIGN KEY (filter_id) REFERENCES filter(id)
);

-- Insert example data into the 'filter' table
INSERT INTO filter (filter_name, selection) VALUES ('Filter 1', 'SELECT_1');
INSERT INTO filter (filter_name, selection) VALUES ('Filter 2', 'SELECT_2');

-- Insert example data into the 'criteria' table
INSERT INTO criteria (filter_id, field, amount_condition, amount_value) VALUES (1, 'AMOUNT', 'MORE', '1000');
INSERT INTO criteria (filter_id, field, date_condition, date_value) VALUES (1, 'DATE', 'FROM', '2024-01-01');
INSERT INTO criteria (filter_id, field, title_condition, title_value) VALUES (2, 'TITLE', 'STARTS_WITH', 'Test');
