CREATE DATABASE publichealthportal;

USE DATABASE publichealthportal;

CREATE TABLE  person_details{
	id integer,
	first_name varchar(50),
	last_name varchar(50),
	gender varchar(10),
	age integer,
	address varchar(255),
	panchayat varchar(50),
};