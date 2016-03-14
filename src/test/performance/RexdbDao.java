package test.performance;

import java.util.List;

import org.rex.DB;

import test.Student;

public class RexdbDao extends Dao {

	@Override
	public int insert() throws Exception {
		String sql = "INSERT INTO r_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly)"
				+ " VALUES (#{studentId},#{name},#{sex},#{birthday},#{birthTime},#{enrollmentTime},#{major},#{photo},#{remark},#{readonly})";
		return DB.update(sql, super.newStudent());
	}

	@Override
	public int[] batchInsert(int rows) throws Exception {
		String sql = "INSERT INTO r_student(student_id, name, sex, birthday, birth_time, enrollment_time, major, photo, remark, readonly)"
				+ " VALUES (#{studentId},#{name},#{sex},#{birthday},#{birthTime},#{enrollmentTime},#{major},#{photo},#{remark},#{readonly})";

		Student[] students = new Student[rows];
		for (int i = 0; i < rows; i++) {
			students[i] = super.newStudent();
		}
		return DB.batchUpdate(sql, students);
	}

	@Override
	public List getList() throws Exception {
		List list= DB.getList("SELECT * FROM r_student", Student.class);
		return list;
	}

	@Override
	public List getMapList() throws Exception {
		return DB.getMapList("SELECT * FROM r_student");
	}
	
	@Override
	public int delete() throws Exception {
		// TODO Auto-generated method stub
		return DB.update("DELETE FROM r_student");
	}

	// --------------MAIN TEST
	public static void main(String[] args) throws Exception {
		Dao dao = new RexdbDao();
		System.out.println(dao.insert());
		System.out.println(dao.getList());
		System.out.println(dao.delete());
	}

}
