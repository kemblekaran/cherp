use forms;

show tables;

create table area(id int auto_increment,code int,status int,state varchar(255),city varchar(255),name varchar(255),type varchar(255),primary key(id));
 
 create table bank(id int auto_increment,bankName varchar(255),branchName varchar(255),accType varchar(255),
accNo long,ifscCode varchar(255),opBal long,address varchar(255),status int,primary key(id));

create table city(id int auto_increment,cityName varchar(255),stateName varchar(255),status int,primary key(id));

 create table cleaner(id int auto_increment,fname varchar(255),lname varchar(255),curAdd varchar(255),perAdd varchar(255),state varchar(255),city varchar(255),mobile long,phone long,drLisence long,pan_no varchar(255),adhaarNo long,photo varchar(255),status int,primary key(id));
 
 create table company(id int auto_increment,name varchar(255),preAdd varchar(255),secAdd varchar(255),mobile long, phone long, 
state varchar(255),city varchar(255) , pinCode long, ownName varchar(255), panNo long, opBal long, status int,primary key(id));

create table customer(id int auto_increment,fname varchar(255),lname varchar(255),shopName varchar(255), curAdd varchar(255),
perAdd varchar(255), state varchar(255), city varchar(255), area varchar(255), mobile long,
phone long, opBal long,status int,primary key(id));

create table driver(id int auto_increment,fname varchar(255),lname varchar(255),curAdd varchar(255),
perAdd varchar(255), state varchar(255), city varchar(255), mobile long, phone long, adhaarNo long, panNo long,
drLiscense long, photo varchar(255),status int,primary key(id));

create table expense(id int auto_increment,description varchar(1000),status int,primary key(id));

create table location(id int auto_increment,location varchar(255),status int,primary key(id));

create table product(id int auto_increment,prodName varchar(255),prodType varchar(255),
status int,primary key(id));

create table state(id int auto_increment,stateName varchar(255) ,status int,primary key(id));

create table user(id int auto_increment,name varchar(255) , pwd varchar(255), status int,primary key(id));

create table van(id int auto_increment,vanNumber varchar(255),companyName varchar(255),VanModel varchar(255),ownerName varchar(255),fitness int,vanCapacity int,insuranceNo int,insStartDate varchar(255),
insEndDate varchar(255),permitNo int,permitStartDate varchar(255),permitEndDate varchar(255),status
int,primary key(id));
