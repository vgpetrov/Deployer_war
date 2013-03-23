CREATE TABLE IF NOT EXISTS Events (
		event_time DATE NOT NULL,
		event varchar(60) NOT NULL,
		product_name varchar(120) NOT NULL,
		version varchar(10) NOT NULL,
		revision varchar(10) NOT NULL,
		host_id int NOT NULL
	);

CREATE TABLE IF NOT EXISTS Hosts (
  id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
  host_name varchar(60) NOT NULL,
  profile varchar(60) NOT NULL
);