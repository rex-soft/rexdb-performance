package test.performance;

import java.util.List;

import org.rex.DB;

import test.Student;

public class RexdbDao extends Dao{

	@Override
	public int insert() throws Exception {
		String sql = "INSERT INTO R_STUDENT(NAME, SEX, BIRTHDAY, BIRTH_TIME, ENROLLMENT_TIME, MAJOR, PHOTO, REMARK, READONLY)"
				+ " VALUES (#{name},#{sex},#{birthday},#{birthTime},#{enrollmentTime},#{major},#{photo},#{remark},#{readonly})";
		return DB.update(sql, super.newStudent());
	}

	@Override
	public List getList() throws Exception {
		return DB.getList("SELECT * FROM R_STUDENT", Student.class);
	}

	@Override
	public int delete() throws Exception {
		// TODO Auto-generated method stub
		return DB.update("DELETE FROM R_STUDENT");
	}

}
