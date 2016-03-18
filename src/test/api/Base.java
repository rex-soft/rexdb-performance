package test.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.rex.DB;
import org.rex.db.exception.DBException;

import test.Student;

public abstract class Base {
	
	public static volatile long studentId = 100000;
	
	static synchronized long getId(){
		return studentId++;
	}
	
	static synchronized void setId(long id){
		studentId = id;
	}
	
	/**
	 * init
	 */
	protected abstract void init() throws Exception;

	/**
	 * new student
	 */
	protected static Student newStudent(){
		Student student = new Student();
		student.setStudentId(getId());
		student.setName("Jim");
		student.setSex(1);
		student.setBirthday(new Date());
		student.setBirthTime(new Date());
		student.setEnrollmentTime(new Date());
		student.setMajor(10);
		student.setReadonly(1);
		return student;
	}
	
	/**
	 * new student
	 */
	protected static Map<String, Object> newStudentMap(){
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("studentId", getId());
		student.put("name", "Jim");
		student.put("sex", 1);
		student.put("birthday", new Date());
		student.put("birthTime", new Date());
		student.put("enrollmentTime", new Date());
		student.put("major", 10);
		student.put("readonly", 1);
		
		return student;
	}
	
	/**
	 * remove all rows
	 */
	protected int deleteAll() throws DBException{
		return DB.update("delete from r_student");
	}
	
	/**
	 * init rows
	 */
	protected int[] initRows(int rows) throws DBException{
		String sql = "INSERT INTO r_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly)"
				+ " VALUES (#{studentId},#{name},#{sex},#{birthday},#{birthTime},#{enrollmentTime},#{major},#{photo},#{remark},#{readonly})";
		Student[] students = new Student[rows];
		for (int i = 0; i < rows; i++) {
			students[i] = newStudent();
		}
		return DB.batchUpdate(sql, students);
	}
}
