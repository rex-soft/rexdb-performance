package org.rex.db.test.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rex.DB;
import org.rex.RMap;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;
import org.rex.db.test.Student;

/**
 * test <code>DB.getMapList(...)</code>
 */
public class TestGetMapList extends Base{

	public TestGetMapList() throws DBException{
		init();
	}
	
	@Override
	protected void init() throws DBException {
		super.deleteAll();
		super.initRows(20);
	}
	
	@Override
	public String getName() {
		return "DB.getMapList";
	}
	
	/**
	 * no prepared parameters
	 */
	public List<RMap> testGetMapList() throws Exception{
		List<RMap> list = DB.getMapList("select * from rexdb_test_student");
		if(list.size() != 20)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * by ps
	 */
	public List<RMap> testGetMapListByPs() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > ? and readonly = ?";
		List<RMap> list = DB.getMapList(sql, new Ps(10, 1));
		if(list.size() != 20)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * by array
	 */
	public List<RMap> testGetMapListByArray() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > ? and readonly = ?";
		List<RMap> list = DB.getMapList(sql, new Object[]{10, 1});
		if(list.size() != 20)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * by java bean
	 */
	public List<RMap> testGetMapListByBean() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > #{studentId} and readonly = #{readonly}";
		Student student = new Student();
		student.setStudentId(10);
		student.setReadonly(1);
		
		List<RMap> list = DB.getMapList(sql, student);
		if(list.size() != 20)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * by map
	 */
	public List<RMap> testGetMapListByMap() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > #{studentId} and readonly = #{readonly}";
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("studentId", 10);
		student.put("readonly", 1);
		
		List<RMap> list = DB.getMapList(sql, student);
		if(list.size() != 20)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * no prepared parameters
	 */
	public List<RMap> testGetPagedMapList() throws Exception{
		List<RMap> list = DB.getMapList("select * from rexdb_test_student", 5, 10);
		if(list.size() != 10)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * paged list by ps
	 */
	public List<RMap> testGetPagedMapListByPs() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > ? and readonly = ?";
		List<RMap> list = DB.getMapList(sql, new Ps(10, 1), 5, 10);
		if(list.size() != 10)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * paged list by array
	 */
	public List<RMap> testGetPagedMapListByArray() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > ? and readonly = ?";
		List<RMap> list = DB.getMapList(sql, new Object[]{10, 1}, 5, 10);
		if(list.size() != 10)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * paged list by java bean
	 */
	public List<RMap> testGetPagedMapListByBean() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > #{studentId} and readonly = #{readonly}";
		Student student = new Student();
		student.setStudentId(10);
		student.setReadonly(1);
		
		List<RMap> list = DB.getMapList(sql, student, 5, 10);
		if(list.size() != 10)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * paged list by map
	 */
	public List<RMap> testGetPagedMapListByMap() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > #{studentId} and readonly = #{readonly}";
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("studentId", 10);
		student.put("readonly", 1);
		
		List<RMap> list = DB.getMapList(sql, student, 5, 10);
		if(list.size() != 10)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	//--------------main
	public static void main(String[] args) throws Exception{
		TestGetMapList selectList = new TestGetMapList();
		
		selectList.testGetMapList();
		selectList.testGetMapListByPs();
		selectList.testGetMapListByArray();
		selectList.testGetMapListByBean();
		selectList.testGetMapListByMap();
		
		selectList.testGetPagedMapList();
		selectList.testGetPagedMapListByPs();
		selectList.testGetPagedMapListByMap();
		selectList.testGetPagedMapListByBean();
		selectList.testGetPagedMapListByMap();
	}
}
