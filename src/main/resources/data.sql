INSERT INTO employee_table (employee_name, salary, email, gender)
    VALUES
           ('Josh', 7500.0, 'josh@gmail.com', 'male'),
           ('Maria', 8000.0, 'maria@gmail.com', 'female'),
           ('Max', 6300.0, 'max@gmail.com', 'male'),
           ('Maximilian', 5705.0, 'maximilian@gmail.com', 'male'),
           ('Sarah', 7700.0, 'sarah@gmail.com', 'female');

INSERT INTO customers (email)
    VALUES
           ('fox@gmail.com'),
           ('joshua@gmail.com'),
           ('dmitriy@gmail.com'),
           ('rocketman@gmail.com');

INSERT INTO orders(uuid, customer_id)
    VALUES
           ('uuid1', 1),
           ('uuid2', 1),
           ('uuid3', 2),
           ('uuid4', 3);
