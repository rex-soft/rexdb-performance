package test.api;

import java.util.HashMap;
import java.util.Map;

import org.rex.DB;
import org.rex.RMap;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;

import test.Student;

/**
 * test <code>DB.getMap(...)</code>
 */
public class TestGetMap extends Base{

	public TestGetMap() throws DBException{
		init();
	}
	
	@Override
	protected void init() throws DBException {
		super.deleteAll();
		super.setId(100001);
		super.initRows(1);
	}
	
	/**
	 * no prepared parameters
	 */
	public RMap getMap() throws DBException{
		return DB.getMap("select * from r_student");
	}
	
	/**
	 * by ps
	 */
	public RMap getMapByPs() throws DBException{
		String sql = "select * from r_student where student_id > ? and readonly = ?";
		return DB.getMap(sql, new Ps(10, 1));
	}
	
	/**
	 * by array
	 */
	public RMap getMapByArray() throws DBException{
		String sql = "select * from r_student where student_id > ? and readonly = ?";
		return DB.getMap(sql, new Object[]{10, 1});
	}
	
	/**
	 * by java bean
	 */
	public RMap getMapByBean() throws DBException{
		String sql = "select * from r_student where student_id > #{studentId} and readonly = #{readonly}";
		Student student = new Student();
		student.setStudentId(10);
		student.setReadonly(1);
		
		return DB.getMap(sql, student);
	}
	
	/**
	 * by map
	 */
	public RMap getMapByMap() throws DBException{
		String sql = "select * from r_student where student_id > #{studentId} and readonly = #{readonly}";
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("studentId", 10);
		student.put("readonly", 1);
		
		return DB.getMap(sql, student);
	}
	
	//--------------main
	public static void main(String[] args) throws Exception{
		TestGetMap selectList = new TestGetMap();
		
		selectList.getMap();
		selectList.getMapByPs();
		selectList.getMapByArray();
		selectList.getMapByBean();
		selectList.getMapByMap();
		
	}
}
