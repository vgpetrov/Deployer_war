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
  profile varchar(60) NOT NULL,
  web_port INTEGER,
  admin_port INTEGER
);

CREATE TABLE IF NOT EXISTS Users (
  id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
  login varchar(60) NOT NULL,
  password varchar(40) NOT NULL
);

-- md5 password 123
insert into users(login, password) values('admin', '202cb962ac59075b964b07152d234b70');