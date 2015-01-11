use maven_test_db;
create table if not exists t_user (
	 id int(10) auto_increment primary key,
	 username varchar(20),
	 password varchar(20)
)