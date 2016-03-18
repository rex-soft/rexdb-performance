package test.api;

import java.util.Date;

import org.rex.DB;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;

/**
 * test <code>DB.update(...)</code>
 */
public class TestUpdate extends Base{
	
	public TestUpdate() throws DBException{
		init();
	}
	
	@Override
	protected void init() throws DBException {
		super.initRows(10);
	}
	
	/**
	 * by Ps
	 */
	public int updateByPs() throws Exception{
		String sql = "insert into r_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) values (?,?,?,?,?,?,?,?,?,?)";
		return DB.update(sql, new Ps(super.getId(), "Jim", 1, new Date(), new Date(), new Date(), 10000, null, null, 0));
	}
	
	/**
	 * by Array
	 */
	public int updateByObjectArray() throws Exception{
		String sql = "insert into r_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) values (?,?,?,?,?,?,?,?,?,?)";
		return DB.update(sql, new Object[]{super.getId(), "Jim", 1, new Date(), new Date(), new Date(), 10000, null, null, 0});
	}
	
	/**
	 * by java bean
	 */
	public int updateByBean() throws Exception{
		String sql = "insert into r_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) "
				+ "values (#{studentId}, #{name}, #{sex}, #{birthday}, #{birthTime}, #{enrollmentTime}, #{major}, #{photo}, #{remark}, #{readonly})";
		return DB.update(sql, super.newStudent());
	}
	
	/**
	 * by Map
	 */
	public int updateByMap() throws Exception{
		String sql = "insert into r_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) "
				+ "values (#{studentId}, #{name}, #{sex}, #{birthday}, #{birthTime}, #{enrollmentTime}, #{major}, #{photo}, #{remark}, #{readonly})";
		return DB.update(sql, super.newStudentMap());
	}
	
	/**
	 * no prepared parameters
	 */
	public int update() throws Exception{
		return DB.update("delete from r_student");
	}

	//--------------main
	public static void main(String[] args) throws Exception{
		TestUpdate update = new TestUpdate();
		update.updateByPs();
		update.updateByObjectArray();
		update.updateByBean();
		update.updateByMap();
		update.update();
	}
}
