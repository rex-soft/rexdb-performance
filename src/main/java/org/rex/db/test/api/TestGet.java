package org.rex.db.test.api;

import java.util.HashMap;
import java.util.Map;

import org.rex.DB;
import org.rex.db.Ps;
import org.rex.db.test.Student;

/**
 * test <code>DB.get(...)</code>
 */
public class TestGet extends Base{
	
	public TestGet() throws Exception{
		init();
	}

	@Override
	protected void init() throws Exception {
		super.deleteAll();
		super.setId(100001);
		super.initRows(1);
	}
	
	@Override
	public String getName() {
		return "DB.get";
	}
	
	/**
	 * no prepared parameters
	 */
	public Student testGet() throws Exception{
		Student result = DB.get("select * from rexdb_test_student", Student.class);
		if(result == null)
			throw new Exception("get seems didn't work well.");
		
		return result;
	}
	
	/**
	 * by ps
	 */
	public Student testGetByPs() throws Exception{
		String sql = "select * from rexdb_test_student where student_id = ? and readonly = ?";
		Student result = DB.get(sql, new Ps(100001, 1), Student.class);
		if(result == null)
			throw new Exception("get seems didn't work well.");
		
		return result;
	}
	
	/**
	 * by array
	 */
	public Student testGetByArray() throws Exception{
		String sql = "select * from rexdb_test_student where student_id = ? and readonly = ?";
		Student result = DB.get(sql, new Object[]{100001, 1}, Student.class);
		if(result == null)
			throw new Exception("get seems didn't work well.");
		
		return result;
	}
	
	/**
	 * by java bean
	 */
	public Student testGetByBean() throws Exception{
		String sql = "select * from rexdb_test_student where student_id = #{studentId} and readonly = #{readonly}";
		Student student = new Student();
		student.setStudentId(100001);
		student.setReadonly(1);
		
		Student result = DB.get(sql, student, Student.class);
		if(result == null)
			throw new Exception("get seems didn't work well.");
		
		return result;
	}
	
	/**
	 * by map
	 */
	public Student testGetByMap() throws Exception{
		String sql = "select * from rexdb_test_student where student_id = #{studentId} and readonly = #{readonly}";
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("studentId", 100001);
		student.put("readonly", 1);
		
		Student result = DB.get(sql, student, Student.class);
		if(result == null)
			throw new Exception("get seems didn't work well.");
		
		return result;
	}
	
	
	//--------------main
	public static void main(String[] args) throws Exception{
		TestGet selectList = new TestGet();
		
		selectList.testGet();
		selectList.testGetByPs();
		selectList.testGetByArray();
		selectList.testGetByBean();
		selectList.testGetByMap();
	}
}
