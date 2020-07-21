drop database if exists budgetapp;

create database budgetapp;

use budgetapp;

create table Users(
	Id int primary key auto_increment,
    firstname varchar(50) not null,
    lastname varchar(50) not null
);

create table income(
	Id int primary key auto_increment,
	name varchar(50) not null,
    `Description` varchar(255),
    amount Decimal(10,2) not null,
    users int not null,
    foreign key (users) references Users(Id)
);

create table expenses(
	Id int primary key auto_increment,
	name varchar(50) not null,
    `Description` varchar(255),
    amount Decimal(10,2) not null,
    users int not null,
    foreign key (users) references Users(Id)
);