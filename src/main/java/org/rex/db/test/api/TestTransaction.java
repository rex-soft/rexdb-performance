package org.rex.db.test.api;

import java.util.Date;
import java.util.List;

import org.rex.DB;
import org.rex.RMap;
import org.rex.db.Ps;
import org.rex.db.exception.DBException;

/**
 * test transaction
 */
public class TestTransaction extends Base {

	@Override
	protected void init() throws Exception {
		super.deleteAll();
	}
	
	@Override
	public String getName() {
		return "DB.transaction";
	}
	
	public void testRollback() throws Exception {
		super.deleteAll();
		
		DB.beginTransaction();
		try {
			insert();
			
			DB.rollback();
		} catch (Exception e) {
			DB.rollback();
			throw e;
		}
		
		if(getMapList().size() != 0)
			throw new Exception("Transaction rollback seems didn't work.");
		
		super.deleteAll();
	}
	
	
	public void testCommit() throws Exception {
		DB.beginTransaction();
		try {
			super.deleteAll();
			insert();
			
			DB.commit();
		} catch (Exception e) {
			DB.rollback();
			throw e;
		}
		
		if(getMapList().size() != 1)
			throw new Exception("Transaction commit seems didn't work.");
	}
	
	//----private
	public int insert() throws Exception{
		String sql = "insert into rexdb_test_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly) values (?,?,?,?,?,?,?,?,?,?)";
		return DB.update(sql, new Ps(super.getId(), "Jim", 1, new Date(), new Date(), new Date(), 10000, null, null, 0));
	}
	
	public List<RMap> getMapList() throws DBException{
		return DB.getMapList("select * from rexdb_test_student");
	}
	
	// --------------
	public static void main(String[] args) throws Exception {
		TestTransaction transaction = new TestTransaction();
		transaction.testCommit();
		transaction.testRollback();
	}

}
