package org.rex.db.test.performance;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.rex.DB;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;
import org.rex.db.test.Student;

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
		student.setPhoto(new byte[]{1,2,3});
		student.setRemark("This is Jim's infomation.");
		
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
		ps.add(new byte[]{1,2,3});
		ps.add("This is Jim's infomation.");
		ps.add(1);
		return ps;
	}
	
	protected static Properties loadConnProperties(String resources) {
		InputStream inputstream = Dao.class.getResourceAsStream(resources);
		if(inputstream == null)
			throw new RuntimeException("could not find properties "+resources);
		Properties properties = new Properties();
		try {
			properties.load(inputstream);
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				inputstream.close();
			} catch (IOException e) {
			}
		}
	}
	
	static int isPostgreSql = -1;
	static int isH2 = -1;
	protected static boolean isPostgreSql(){
		if(isPostgreSql == -1){
			try {
				if("POSTGRESQL".equals(DB.getDialect().getName()))
					isPostgreSql=1;
				else
					isPostgreSql=0;
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		
		return isPostgreSql == 1;
	}
	
	protected static boolean isH2(){
		if(isH2 == -1){
			try {
				if("H2".equals(DB.getDialect().getName()))
					isH2=1;
				else
					isH2=0;
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		
		return isH2 == 1;
	}
}
