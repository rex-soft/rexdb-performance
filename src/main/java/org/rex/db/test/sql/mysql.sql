-- PROCEDURE
DROP PROCEDURE IF EXISTS rexdb_test_proc_in;$$
DROP PROCEDURE IF EXISTS rexdb_test_proc_inout;$$
DROP PROCEDURE IF EXISTS rexdb_test_proc_in_out;$$
DROP PROCEDURE IF EXISTS rexdb_test_proc_out;$$
DROP PROCEDURE IF EXISTS rexdb_test_proc_return;$$
DROP PROCEDURE IF EXISTS rexdb_test_proc_return_rs;$$
drop TABLE IF EXISTS rexdb_test_student;$$


CREATE PROCEDURE rexdb_test_proc_in (IN id INT)  BEGIN
	select * from rexdb_test_student where student_id = id;
END

$$

CREATE PROCEDURE rexdb_test_proc_out (OUT s INT)  BEGIN 
	SELECT COUNT(*) INTO s FROM rexdb_test_student ;  
END

$$

CREATE PROCEDURE rexdb_test_proc_in_out (IN id INT, OUT m INT)  BEGIN 
	SELECT major INTO m FROM rexdb_test_student where student_id = id;  
END

$$

CREATE PROCEDURE rexdb_test_proc_inout (INOUT c INT)  BEGIN 
	SELECT COUNT(*) + c INTO c FROM rexdb_test_student;
END

$$

CREATE PROCEDURE rexdb_test_proc_return ()  BEGIN
	SELECT count(*) as c FROM rexdb_test_student ; 
END

$$

CREATE PROCEDURE rexdb_test_proc_return_rs ()  BEGIN
	SELECT * FROM rexdb_test_student limit 0, 5; 
    SELECT * FROM rexdb_test_student limit 5, 9; 
END

$$

--TABLE
CREATE TABLE IF NOT EXISTS rexdb_test_student (
	student_id int(11) AUTO_INCREMENT NOT NULL,
	name varchar(30) NOT NULL,
	sex tinyint(1) NOT NULL,
	birthday date NOT NULL,
	birth_time time NOT NULL,
	enrollment_time datetime NOT NULL,
	major smallint(6) NOT NULL,
	photo blob,
	remark text,
	readonly tinyint(1) NOT NULL,
	PRIMARY KEY (student_id)
);

$$