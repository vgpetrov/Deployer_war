CREATE TABLE Events (
		event_time DATE,
		event varchar(60),
		product_name varchar(120) NOT NULL,
		version varchar(10) NOT NULL,
		host_id int NOT NULL
	);

CREATE TABLE Hosts (
		id int not null,
		host_name varchar(60) NOT NULL,
		profile varchar(60) NOT NULL
	);

CREATE UNIQUE INDEX sqlite_autoindex_Hosts_1 ON Hosts (id ASC);

select * from events
	inner join hosts on events.host_id = hosts.id;