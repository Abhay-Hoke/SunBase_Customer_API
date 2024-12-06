
--tables to be created manually inside your database using following structure

--CREATE TABLE users (
--    id INT AUTO_INCREMENT PRIMARY KEY,
--    username VARCHAR(50) NOT NULL UNIQUE,
--    password VARCHAR(255) NOT NULL,
--    role ENUM('ADMIN', 'USER') NOT NULL
--);
--

--command to be run once on mysql databse server after first registration of user to make him admin
--Update users set role = 'ADMIN' where username ='write your username here';

--CREATE TABLE customers (
--    uuid CHAR(36) PRIMARY KEY DEFAULT (UUID()), -- UUID column
--    first_name VARCHAR(100) NOT NULL, 
--    last_name VARCHAR(100), 
--	  street VARCHAR(255),
--    address VARCHAR(255), 
--    city VARCHAR(50), 
--    state VARCHAR(50), 
--    email VARCHAR(100) UNIQUE NOT NULL, 
--    phone VARCHAR(15) NOT NULL
--);





