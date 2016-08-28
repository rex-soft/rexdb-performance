drop TABLE IF EXISTS rexdb_test_student;

$$

CREATE TABLE IF NOT EXISTS rexdb_test_student (
	student_id integer NOT NULL,
	name varchar(30) NOT NULL,
	sex smallint NOT NULL,
	birthday date NOT NULL,
	birth_time time NOT NULL,
	enrollment_time timestamp NOT NULL,
	major smallint NOT NULL,
	photo bytea,
	remark text,
	readonly smallint NOT NULL,
	PRIMARY KEY (student_id)
);

$$