CREATE DATABASE publichealthportal;

USE publichealthportal;

CREATE TABLE  person_details(
	id integer primary key,
	first_name varchar(50),
	last_name varchar(50),
	gender varchar(10),
	age integer,
	address varchar(255),
	panchayat varchar(50)
);