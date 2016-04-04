drop TABLE IF EXISTS rexdb_test_student;

$$

CREATE TABLE IF NOT EXISTS rexdb_test_student (
	student_id int(11) NOT NULL,
	name varchar(30) NOT NULL,
	sex tinyint(1) NOT NULL,
	birthday date NOT NULL,
	birth_time time NOT NULL,
	enrollment_time datetime NOT NULL,
	major smallint(6) NOT NULL,
	photo blob,
	remark clob,
	readonly tinyint(1) NOT NULL,
	PRIMARY KEY (student_id)
);

$$