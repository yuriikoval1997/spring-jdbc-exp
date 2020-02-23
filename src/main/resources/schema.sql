DROP TABLE IF EXISTS employee_table;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;

CREATE TABLE IF NOT EXISTS employee_table (
    employee_id SERIAL NOT NULL,
    employee_name VARCHAR(45) NOT NULL,
    salary NUMERIC DEFAULT NULL,
    email VARCHAR(45) UNIQUE NOT NULL,
    gender VARCHAR(10) default NULL,
    PRIMARY KEY (employee_id)
);

CREATE TABLE IF NOT EXISTS customers (
    id SERIAL NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orders (
    id SERIAL NOT NULL,
    uuid VARCHAR(255) UNIQUE NOT NULL,
    customer_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);
