package org.rex.db.test.performance;

import java.util.List;

import org.rex.DB;
import org.rex.db.Ps;
import org.rex.db.test.Student;

public class RexdbDao extends Dao {

	@Override
	public int insert() throws Exception {
		String sql = "INSERT INTO rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly)"
				+ " VALUES (#{studentId},#{name},#{sex},#{birthday},#{birthTime},#{enrollmentTime},#{major},#{photo},#{remark},#{readonly})";
		return DB.update(sql, super.newStudent());
	}

	@Override
	public int insertPs() throws Exception {
		String sql = "INSERT INTO rexdb_test_student(STUDENT_ID, NAME, SEX, BIRTHDAY, BIRTH_TIME, ENROLLMENT_TIME, MAJOR, PHOTO, REMARK, READONLY) VALUES (?,?,?,?,?,?,?,?,?,?)";
		return DB.update(sql, super.newPs());
	}
	
	@Override
	public int[] batchInsert(int rows) throws Exception {
		String sql = "INSERT INTO rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly)"
				+ " VALUES (#{studentId},#{name},#{sex},#{birthday},#{birthTime},#{enrollmentTime},#{major},#{photo},#{remark},#{readonly})";

		Student[] students = new Student[rows];
		for (int i = 0; i < rows; i++) {
			students[i] = super.newStudent();
		}
		return DB.batchUpdate(sql, students);
	}
	
	@Override
	public int[] batchInsertPs(int rows) throws Exception {
		String sql = "INSERT INTO rexdb_test_student(STUDENT_ID, NAME, SEX, BIRTHDAY, BIRTH_TIME, ENROLLMENT_TIME, MAJOR, PHOTO, REMARK, READONLY) VALUES (?,?,?,?,?,?,?,?,?,?)";
		Ps[] pss = new Ps[rows];
		for (int i = 0; i < rows; i++) {
			pss[i] = super.newPs();
		}
		return DB.batchUpdate(sql, pss);
	}
	
	@Override
	public List getList() throws Exception {
		List list= DB.getList("SELECT * FROM rexdb_test_student", Student.class);
		return list;
	}

	@Override
	public List getMapList() throws Exception {
		return DB.getMapList("SELECT * FROM rexdb_test_student");
	}
	
	@Override
	public int delete() throws Exception {
		// TODO Auto-generated method stub
		return DB.update("DELETE FROM rexdb_test_student");
	}
	
	@Override
	public String getName() throws Exception {
		return "rexdb";
	}

	// --------------MAIN TEST
	public static void main(String[] args) throws Exception {
		Dao dao = new RexdbDao();
		System.out.println(dao.insert());
		System.out.println(dao.getList());
		System.out.println(dao.delete());
	}

}
