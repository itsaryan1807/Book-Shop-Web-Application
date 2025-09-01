-- schema.sql for WebBookApp
CREATE DATABASE IF NOT EXISTS books;
USE books;

CREATE TABLE IF NOT EXISTS booksdata (
  id INT PRIMARY KEY AUTO_INCREMENT,
  bookname   VARCHAR(100) NOT NULL,
  bookedition VARCHAR(50) NOT NULL,
  bookprice  DECIMAL(10,2) NOT NULL
);