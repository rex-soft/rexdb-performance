package test.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rex.DB;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;

import test.Student;

/**
 * test <code>DB.getList(...)</code>
 */
public class TestGetList extends Base{
	
	public TestGetList() throws DBException{
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
	public List<Student> getList() throws DBException{
		return DB.getList("select * from r_student", Student.class);
	}
	
	/**
	 * by ps
	 */
	public List<Student> getListByPs() throws DBException{
		String sql = "select * from r_student where student_id > ? and readonly = ?";
		return DB.getList(sql, new Ps(10, 1), Student.class);
	}
	
	/**
	 * by array
	 */
	public List<Student> getListByArray() throws DBException{
		String sql = "select * from r_student where student_id > ? and readonly = ?";
		return DB.getList(sql, new Object[]{10, 1}, Student.class);
	}
	
	/**
	 * by java bean
	 */
	public List<Student> getListByBean() throws DBException{
		String sql = "select * from r_student where student_id > #{studentId} and readonly = #{readonly}";
		Student student = new Student();
		student.setStudentId(10);
		student.setReadonly(1);
		
		return DB.getList(sql, student, Student.class);
	}
	
	/**
	 * by map
	 */
	public List<Student> getListByMap() throws DBException{
		String sql = "select * from r_student where student_id > #{studentId} and readonly = #{readonly}";
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("studentId", 10);
		student.put("readonly", 1);
		
		return DB.getList(sql, student, Student.class);
	}
	
	/**
	 * no prepared parameters
	 */
	public List<Student> getPagedList() throws DBException{
		return DB.getList("select * from r_student", Student.class, 0, 9);
	}
	
	/**
	 * paged list by ps
	 */
	public List<Student> getPagedListByPs() throws DBException{
		String sql = "select * from r_student where student_id > ? and readonly = ?";
		return DB.getList(sql, new Ps(10, 1), Student.class, 0, 9);
	}
	
	/**
	 * paged list by array
	 */
	public List<Student> getPagedListByArray() throws DBException{
		String sql = "select * from r_student where student_id > ? and readonly = ?";
		return DB.getList(sql, new Object[]{10, 1}, Student.class, 0, 9);
	}
	
	/**
	 * paged list by java bean
	 */
	public List<Student> getPagedListByBean() throws DBException{
		String sql = "select * from r_student where student_id > #{studentId} and readonly = #{readonly}";
		Student student = new Student();
		student.setStudentId(10);
		student.setReadonly(1);
		
		return DB.getList(sql, student, Student.class, 0, 9);
	}
	
	/**
	 * paged list by map
	 */
	public List<Student> getPagedListByMap() throws DBException{
		String sql = "select * from r_student where student_id > #{studentId} and readonly = #{readonly}";
		Map<String, Object> student = new HashMap<String, Object>();
		student.put("studentId", 10);
		student.put("readonly", 1);
		
		return DB.getList(sql, student, Student.class, 0, 9);
	}
	
	//--------------main
	public static void main(String[] args) throws Exception{
		TestGetList selectList = new TestGetList();
		
		selectList.getList();
		selectList.getListByPs();
		selectList.getListByArray();
		selectList.getListByBean();
		selectList.getListByMap();
		
		selectList.getPagedList();
		selectList.getPagedListByPs();
		selectList.getPagedListByMap();
		selectList.getPagedListByBean();
		selectList.getPagedListByMap();
	}
}
