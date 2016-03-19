package org.rex.db.test.api;

import java.util.HashMap;
import java.util.Map;

import org.rex.DB;
import org.rex.RMap;
import org.rex.db.Ps;
import org.rex.db.test.Student;

/**
 * test <code>DB.getMap(...)</code>
 */
public class TestGetMap extends Base{

	public TestGetMap() throws Exception{
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
		return "DB.getMap";
	}
	
	/**
	 * no prepared parameters
	 */
	public RMap testGetMap() throws Exception{
		RMap result = DB.getMap("select * from rexdb_test_student");
		if(result == null)
			throw new Exception("getMap seems didn't work well.");
		
		return result;
	}
	
	/**
	 * by ps
	 */
	public RMap testGetMapByPs() throws Exception{
		String sql = "select * from rexdb_test_student where student_id = ? and readonly = ?";
		RMap result = DB.getMap(sql, new Ps(100001, 1));
		if(result == null)
			throw new Exception("getMap seems didn't work well.");
		
		return result;
	}
	
	/**
	 * by array
	 */
	public RMap testGetMapByArray() throws Exception{
		String sql = "select * from rexdb_test_student where student_id = ? and readonly = ?";
		RMap result = DB.getMap(sql, new Object[]{100001, 1});
		if(result == null)
			throw new Exception("getMap seems didn't work well.");
		
		return result;
	}
	
	/**
	 * by java bean
	 */
	public RMap testGetMapByBean() throws Exception{
		String sql = "select * from rexdb_test_student where student_id = #{studentId} and readonly = #{readonly}";
		Student student = new Student();
		student.setStudentId(100001);
		student.setReadonly(1);
		
		RMap result = DB.getMap(sql, student);
		if(result == null)
			throw new Exception("getMap seems didn't work well.");
		
		return result;
	}
	
	/**
	 * by map
	 */
	public RMap testGetMapByMap() throws Exception{
		String sql = "select * from rexdb_test_student where student_id = #{studentId} and readonly = #{readonly}";
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("studentId", 100001);
		student.put("readonly", 1);
		
		RMap result = DB.getMap(sql, student);
		if(result == null)
			throw new Exception("getMap seems didn't work well.");
		
		return result;
	}
	
	//--------------main
	public static void main(String[] args) throws Exception{
		TestGetMap selectList = new TestGetMap();
		
		selectList.testGetMap();
		selectList.testGetMapByPs();
		selectList.testGetMapByArray();
		selectList.testGetMapByBean();
		selectList.testGetMapByMap();
		
	}
}
