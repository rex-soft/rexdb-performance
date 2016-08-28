package org.rex.db.test.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rex.DB;
import org.rex.db.Ps;
import org.rex.db.test.Student;

/**
 * test <code>DB.getList(...)</code>
 */
public class TestGetList extends Base{
	
	public TestGetList() throws Exception{
		init();
	}

	@Override
	protected void init() throws Exception {
		super.deleteAll();
		super.initRows(20);
	}
	
	@Override
	public String getName() {
		return "DB.getList";
	}
	
	/**
	 * no prepared parameters
	 */
	public List<Student> testGetList() throws Exception{
		List<Student> list = DB.getList("select * from rexdb_test_student", Student.class);
		if(list.size() != 20)
			throw new Exception("getList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * by ps
	 */
	public List<Student> testGetListByPs() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > ? and readonly = ?";
		List<Student> list = DB.getList(sql, new Ps(10, 1), Student.class);
		if(list.size() != 20)
			throw new Exception("getList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * by array
	 */
	public List<Student> testGetListByArray() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > ? and readonly = ?";
		List<Student> list = DB.getList(sql, new Object[]{10, 1}, Student.class);
		if(list.size() != 20)
			throw new Exception("getList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * by java bean
	 */
	public List<Student> testGetListByBean() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > #{studentId} and readonly = #{readonly}";
		Student student = new Student();
		student.setStudentId(10);
		student.setReadonly(1);
		
		List<Student> list = DB.getList(sql, student, Student.class);
		if(list.size() != 20)
			throw new Exception("getList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * by map
	 */
	public List<Student> testGetListByMap() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > #{studentId} and readonly = #{readonly}";
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("studentId", 10);
		student.put("readonly", 1);
		
		List<Student> list = DB.getList(sql, student, Student.class);
		if(list.size() != 20)
			throw new Exception("getList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * no prepared parameters
	 */
	public List<Student> testGetPagedList() throws Exception{
		List<Student> list = DB.getList("select * from rexdb_test_student", Student.class, 5, 10);
		if(list.size() != 10)
			throw new Exception("getList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * paged list by ps
	 */
	public List<Student> testGetPagedListByPs() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > ? and readonly = ?";
		List<Student> list = DB.getList(sql, new Ps(10, 1), Student.class, 5, 10);
		if(list.size() != 10)
			throw new Exception("getList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * paged list by array
	 */
	public List<Student> testGetPagedListByArray() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > ? and readonly = ?";
		List<Student> list = DB.getList(sql, new Object[]{10, 1}, Student.class, 5, 10);
		if(list.size() != 10)
			throw new Exception("getList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * paged list by java bean
	 */
	public List<Student> testGetPagedListByBean() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > #{studentId} and readonly = #{readonly}";
		Student student = new Student();
		student.setStudentId(10);
		student.setReadonly(1);
		
		List<Student> list = DB.getList(sql, student, Student.class, 5, 10);
		if(list.size() != 10)
			throw new Exception("getList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * paged list by map
	 */
	public List<Student> testGetPagedListByMap() throws Exception{
		String sql = "select * from rexdb_test_student where student_id > #{studentId} and readonly = #{readonly}";
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("studentId", 10);
		student.put("readonly", 1);
		
		List<Student> list = DB.getList(sql, student, Student.class, 5, 10);
		if(list.size() != 10)
			throw new Exception("getList seems didn't work well.");
		
		return list;
	}
	
	//--------------main
	public static void main(String[] args) throws Exception{
		TestGetList selectList = new TestGetList();
		
		selectList.testGetList();
		selectList.testGetListByPs();
		selectList.testGetListByArray();
		selectList.testGetListByBean();
		selectList.testGetListByMap();
		
		selectList.testGetPagedList();
		selectList.testGetPagedListByPs();
		selectList.testGetPagedListByMap();
		selectList.testGetPagedListByBean();
		selectList.testGetPagedListByMap();
	}
}
