package org.rex.db.test.api;

import java.util.Arrays;
import java.util.Date;

import org.rex.DB;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;

/**
 * test <code>DB.batchUpdate(...)</code>
 */
public class TestBatchUpdate extends Base{
	
	public TestBatchUpdate() throws DBException{
		init();
	}
	
	@Override
	protected void init() throws DBException {
		super.deleteAll();
	}
	
	@Override
	public String getName() {
		return "DB.batchUpdate";
	}
	
	/**
	 * by Ps
	 */
	public int[] testBatchUpdateByPs() throws Exception{
		String sql = "insert into rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) values (?,?,?,?,?,?,?,?,?,?)";
		int[] i = DB.batchUpdate(sql, super.newPss(3));
		if(!Arrays.equals(i, new int[]{1, 1, 1}))
			throw new Exception("update seems didn't work.");
		
		return i;
	}
	
	/**
	 * by Array
	 */
	public int[] testBatchUpdateByObjectArray() throws Exception{
		try{
		String sql = "insert into rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) values (?,?,?,?,?,?,?,?,?,?)";
		int[] i = DB.batchUpdate(sql, super.newArrays(3));
		if(!Arrays.equals(i, new int[]{1, 1, 1}))
			throw new Exception("update seems didn't work.");
		
		return i;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * by java bean
	 */
	public int[] testBatchUpdateByBean() throws Exception{
		String sql = "insert into rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) "
				+ "values (#{studentId}, #{name}, #{sex}, #{birthday}, #{birthTime}, #{enrollmentTime}, #{major}, #{photo}, #{remark}, #{readonly})";
		int[] i = DB.batchUpdate(sql, super.newStudents(3));
		if(!Arrays.equals(i, new int[]{1, 1, 1}))
			throw new Exception("update seems didn't work.");
		
		return i;
	}
	
	/**
	 * by Map
	 */
	public int[] testBatchUpdateByMap() throws Exception{
		String sql = "insert into rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) "
				+ "values (#{studentId}, #{name}, #{sex}, #{birthday}, #{birthTime}, #{enrollmentTime}, #{major}, #{photo}, #{remark}, #{readonly})";
		int[] i = DB.batchUpdate(sql, super.newStudentMaps(3));
		if(!Arrays.equals(i, new int[]{1, 1, 1}))
			throw new Exception("update seems didn't work.");
		
		return i;
	}
	
	/**
	 * no prepared parameters
	 */
	public int[] testBatchUpdate() throws Exception{
		super.deleteAll();
		testBatchUpdateByMap();
		int[] i = DB.batchUpdate(new String[]{"delete from rexdb_test_student", "delete from rexdb_test_student"});
		if(!Arrays.equals(i, new int[]{3, 0}))
			throw new Exception("update seems didn't work.");
		
		return i;
	}

	//--------------main
	public static void main(String[] args) throws Exception{
		TestBatchUpdate update = new TestBatchUpdate();
		update.testBatchUpdateByPs();
		update.testBatchUpdateByObjectArray();
		update.testBatchUpdateByBean();
		update.testBatchUpdateByMap();
		update.testBatchUpdate();
	}


}
