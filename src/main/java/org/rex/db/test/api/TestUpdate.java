package org.rex.db.test.api;

import org.rex.DB;
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
		super.deleteAll();
	}
	
	@Override
	public String getName() {
		return "DB.update";
	}
	
	
	/**
	 * by Ps
	 */
	public int testUpdateByPs() throws Exception{
		String sql = "insert into rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) values (?,?,?,?,?,?,?,?,?,?)";
		int i = DB.update(sql, super.newPs());
		if(i != 1)
			throw new Exception("update seems didn't work.");
		
		return i;
	}
	
	/**
	 * by Array
	 */
	public int testUpdateByObjectArray() throws Exception{
		String sql = "insert into rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) values (?,?,?,?,?,?,?,?,?,?)";
		int i = DB.update(sql, super.newArray());
		if(i != 1)
			throw new Exception("update seems didn't work.");
		
		return i;
	}
	
	/**
	 * by java bean
	 */
	public int testUpdateByBean() throws Exception{
		String sql = "insert into rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) "
				+ "values (#{studentId}, #{name}, #{sex}, #{birthday}, #{birthTime}, #{enrollmentTime}, #{major}, #{photo}, #{remark}, #{readonly})";
		int i = DB.update(sql, super.newStudent());
		if(i != 1)
			throw new Exception("update seems didn't work.");
		
		return i;
	}
	
	/**
	 * by Map
	 */
	public int testUpdateByMap() throws Exception{
		String sql = "insert into rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) "
				+ "values (#{studentId}, #{name}, #{sex}, #{birthday}, #{birthTime}, #{enrollmentTime}, #{major}, #{photo}, #{remark}, #{readonly})";
		int i = DB.update(sql, super.newStudentMap());
		if(i != 1)
			throw new Exception("update seems didn't work.");
		
		return i;
	}
	
	/**
	 * no prepared parameters
	 */
	public int testUpdate() throws Exception{
		testUpdateByMap();
		int i = DB.update("delete from rexdb_test_student");
		if(i == 0)
			throw new Exception("update seems didn't work.");
		
		return i;
	}

	//--------------main
	public static void main(String[] args) throws Exception{
		TestUpdate update = new TestUpdate();
		update.testUpdateByPs();
		update.testUpdateByObjectArray();
		update.testUpdateByBean();
		update.testUpdateByMap();
		update.testUpdate();
	}
}
