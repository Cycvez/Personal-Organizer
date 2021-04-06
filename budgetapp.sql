drop database if exists budgetapp;

create database budgetapp;

use budgetapp;

use budgetapp;

create table Users(
	Id int primary key auto_increment,
    firstname varchar(50) not null,
    lastname varchar(50) not null
);

create table income(
	Id int primary key auto_increment,
	`name` varchar(50) not null,
    `Description` varchar(255),
    amount Decimal(10,2) not null,
    `user` int not null,
    foreign key (`user`) references Users(Id)
);

create table expenses(
	Id int primary key auto_increment,
	name varchar(50) not null,
    `Description` varchar(255),
    amount Decimal(10,2) not null,
    `user` int not null,
    foreign key (`user`) references Users(Id)
);

create table timecard(
	Id int primary key auto_increment,
	punch_in time,
    punch_out time,
    hoursworked int,
    `Description` varchar(255),
    `user` int not null,
     foreign key (`user`) references Users(Id)
);

create table todo(
	Id int primary key auto_increment,
    name varchar(50) not null,
    `Description` varchar(255),
    completed tinyint not null,
    `user` int not null,
     foreign key (`user`) references Users(Id)
);