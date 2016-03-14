package test.performance;

import java.util.Date;
import java.util.List;

import test.Student;

public abstract class Dao {
	
	/**
	 * insert 1 row
	 */
	public abstract int insert() throws Exception;

	/**
	 * select all rows
	 */
	public abstract List getList() throws Exception;
	
	/**
	 * select all rows
	 */
	public abstract List getMapList() throws Exception;
	
	/**
	 * delete all rows
	 */
	public abstract int delete() throws Exception;
	
	/**
	 * insert rows batch
	 */
	public abstract int[] batchInsert(int i) throws Exception;

	
	public static volatile long studentId = 0;
	/**
	 * new student
	 */
	protected static Student newStudent(){
		Student student = new Student();
		student.setStudentId(studentId++);
		student.setName("Jim");
		student.setSex(1);
		student.setBirthday(new Date());
		student.setBirthTime(new Date());
		student.setEnrollmentTime(new Date());
		student.setMajor(10);
		student.setReadonly(1);
		return student;
	}
}
