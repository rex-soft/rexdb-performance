package test.api;

import java.util.HashMap;
import java.util.Map;

import org.rex.DB;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;

import test.Student;

/**
 * test <code>DB.get(...)</code>
 */
public class TestGet extends Base{
	
	public TestGet() throws DBException{
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
	public Student get() throws DBException{
		return DB.get("select * from r_student", Student.class);
	}
	
	/**
	 * by ps
	 */
	public Student getByPs() throws DBException{
		String sql = "select * from r_student where student_id = ? and readonly = ?";
		return DB.get(sql, new Ps(100001, 1), Student.class);
	}
	
	/**
	 * by array
	 */
	public Student getByArray() throws DBException{
		String sql = "select * from r_student where student_id = ? and readonly = ?";
		return DB.get(sql, new Object[]{100001, 1}, Student.class);
	}
	
	/**
	 * by java bean
	 */
	public Student getByBean() throws DBException{
		String sql = "select * from r_student where student_id = #{studentId} and readonly = #{readonly}";
		Student student = new Student();
		student.setStudentId(100001);
		student.setReadonly(1);
		
		return DB.get(sql, student, Student.class);
	}
	
	/**
	 * by map
	 */
	public Student getByMap() throws DBException{
		String sql = "select * from r_student where student_id = #{studentId} and readonly = #{readonly}";
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("studentId", 100001);
		student.put("readonly", 1);
		
		return DB.get(sql, student, Student.class);
	}
	
	
	//--------------main
	public static void main(String[] args) throws Exception{
		TestGet selectList = new TestGet();
		
		selectList.get();
		selectList.getByPs();
		selectList.getByArray();
		selectList.getByBean();
		selectList.getByMap();
	}
}
