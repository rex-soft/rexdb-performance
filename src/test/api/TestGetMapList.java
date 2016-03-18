package test.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rex.DB;
import org.rex.RMap;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;

import test.Student;

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
	
	/**
	 * no prepared parameters
	 */
	public List<RMap> getMapList() throws Exception{
		List<RMap> list = DB.getMapList("select * from r_student");
		if(list.size() != 20)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * by ps
	 */
	public List<RMap> getMapListByPs() throws Exception{
		String sql = "select * from r_student where student_id > ? and readonly = ?";
		List<RMap> list = DB.getMapList(sql, new Ps(10, 1));
		if(list.size() != 20)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * by array
	 */
	public List<RMap> getMapListByArray() throws Exception{
		String sql = "select * from r_student where student_id > ? and readonly = ?";
		List<RMap> list = DB.getMapList(sql, new Object[]{10, 1});
		if(list.size() != 20)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * by java bean
	 */
	public List<RMap> getMapListByBean() throws Exception{
		String sql = "select * from r_student where student_id > #{studentId} and readonly = #{readonly}";
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
	public List<RMap> getMapListByMap() throws Exception{
		String sql = "select * from r_student where student_id > #{studentId} and readonly = #{readonly}";
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
	public List<RMap> getPagedMapList() throws Exception{
		List<RMap> list = DB.getMapList("select * from r_student", 5, 10);
		if(list.size() != 10)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * paged list by ps
	 */
	public List<RMap> getPagedMapListByPs() throws Exception{
		String sql = "select * from r_student where student_id > ? and readonly = ?";
		List<RMap> list = DB.getMapList(sql, new Ps(10, 1), 5, 10);
		if(list.size() != 10)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * paged list by array
	 */
	public List<RMap> getPagedMapListByArray() throws Exception{
		String sql = "select * from r_student where student_id > ? and readonly = ?";
		List<RMap> list = DB.getMapList(sql, new Object[]{10, 1}, 5, 10);
		if(list.size() != 10)
			throw new Exception("getMapList seems didn't work well.");
		
		return list;
	}
	
	/**
	 * paged list by java bean
	 */
	public List<RMap> getPagedMapListByBean() throws Exception{
		String sql = "select * from r_student where student_id > #{studentId} and readonly = #{readonly}";
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
	public List<RMap> getPagedMapListByMap() throws Exception{
		String sql = "select * from r_student where student_id > #{studentId} and readonly = #{readonly}";
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
		
		selectList.getMapList();
		selectList.getMapListByPs();
		selectList.getMapListByArray();
		selectList.getMapListByBean();
		selectList.getMapListByMap();
		
		selectList.getPagedMapList();
		selectList.getPagedMapListByPs();
		selectList.getPagedMapListByMap();
		selectList.getPagedMapListByBean();
		selectList.getPagedMapListByMap();
	}
}
