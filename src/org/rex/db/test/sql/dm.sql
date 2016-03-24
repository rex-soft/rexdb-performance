drop TABLE rexdb_test_student;

$$

CREATE TABLE rexdb_test_student (
	student_id int NOT NULL,
	name varchar(30) NOT NULL,
	sex tinyint NOT NULL,
	birthday date NOT NULL,
	birth_time time NOT NULL,
	enrollment_time datetime NOT NULL,
	major smallint NOT NULL,
	photo blob,
	remark clob,
	readonly tinyint NOT NULL,
	PRIMARY KEY (student_id)
);

$$