create table user
(
	id int auto_increment,
	username varchar(20) not null,
	password varchar(20) not null,
	type INTEGER not null,
	account varchar(50) not null,
	constraint user_pk
		primary key (id)
);