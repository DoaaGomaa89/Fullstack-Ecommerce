-- Align with docker-compose env and avoid plugin directives
CREATE USER IF NOT EXISTS 'admin' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON `full-stack-ecommerce`.* TO 'admin';
FLUSH PRIVILEGES;
