package org.rex.db.test.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rex.DB;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;
import org.rex.db.test.Student;

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
	
	public abstract String getName();

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
	
	protected static Student[] newStudents(int i){
		Student[] students = new Student[i];
		for (int j = 0; j < i; j++) {
			students[j] = newStudent();
		}
		return students;
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
	
	protected static Map[] newStudentMaps(int i){
		Map[] students = new Map[i];
		for (int j = 0; j < i; j++) {
			students[j] = newStudentMap();
		}
		return students;
	}
	
	/**
	 * new student
	 */
	protected static Ps newPs(){
		return new Ps(getId(), "Jim", 1, new Date(), new Date(), new Date(), 10000, null, null, 0);
	}
	
	protected static Ps[] newPss(int i){
		Ps[] students = new Ps[i];
		for (int j = 0; j < i; j++) {
			students[j] = newPs();
		}
		return students;
	}
	
	/**
	 * new student
	 */
	protected static Object[] newArray(){
		return new Object[]{getId(), "Jim", 1, new Date(), new Date(), new Date(), 10000, null, null, 0};
	}
	
	protected static Object[][] newArrays(int i){
		Object[][] students = new Object[i][];
		for (int j = 0; j < i; j++) {
			students[j] = newArray();
		}
		return students;
	}
	
	protected static List newList(int i){
		List<Student> students = new ArrayList();
		for (int j = 0; j < i; j++) {
			students.add(newStudent());
		}
		return students;
	}
	
	/**
	 * remove all rows
	 */
	protected int deleteAll() throws DBException{
		return DB.update("delete from rexdb_test_student");
	}
	
	/**
	 * init row
	 */
	protected int initRow() throws DBException{
		String sql = "INSERT INTO rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly)"
				+ " VALUES (#{studentId},#{name},#{sex},#{birthday},#{birthTime},#{enrollmentTime},#{major},#{photo},#{remark},#{readonly})";
		return DB.update(sql, newStudent());
	}
	
	/**
	 * init rows
	 */
	protected int[] initRows(int rows) throws DBException{
		String sql = "INSERT INTO rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly)"
				+ " VALUES (#{studentId},#{name},#{sex},#{birthday},#{birthTime},#{enrollmentTime},#{major},#{photo},#{remark},#{readonly})";
		Student[] students = new Student[rows];
		for (int i = 0; i < rows; i++) {
			students[i] = newStudent();
		}
		return DB.batchUpdate(sql, students);
	}
}
