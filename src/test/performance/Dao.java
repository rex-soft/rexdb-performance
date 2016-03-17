package test.performance;

import java.util.Date;
import java.util.List;

import org.rex.db.Ps;

import test.Student;

public abstract class Dao {
	
	/**
	 * insert 1 row
	 */
	public abstract int insert() throws Exception;
	
	/**
	 * insert 1 row
	 */
	public abstract int insertPs() throws Exception;

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
	
	/**
	 * insert rows batch
	 */
	public abstract int[] batchInsertPs(int i) throws Exception;
	
	/**
	 * get DAO name
	 */
	public abstract String getName() throws Exception;
	
	public static volatile long studentId = 100000;
	
	static synchronized long getId(){
		return studentId++;
	}
	
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
	 * new student ps
	 */
	protected static Ps newPs(){
		Ps ps = new Ps();
		ps.add(getId());
		ps.add("Jim");
		ps.add(1);
		ps.add(new Date());
		ps.add(new Date());
		ps.add(new Date());
		ps.add(10);
		ps.addNull();
		ps.addNull();
		ps.add(1);
		return ps;
	}
}
