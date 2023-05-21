drop table if exists product;

create table product (
	id bigint not null auto_increment,
	name varchar(100) not null,
	price double precision not null,
	primary key (id)
);