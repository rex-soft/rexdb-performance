package test.performance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import test.Student;

public class JdbcDao extends Dao{
	
	BasicDataSource bds = null;
	
	public JdbcDao(){
		bds = new BasicDataSource();
		bds.setDriverClassName("org.apache.commons.dbcp.BasicDataSource");
		bds.setUrl("jdbc:mysql://localhost:3306/rexdb");
		bds.setUsername("root");
		bds.setPassword("12345678");
	}

	@Override
	public int insert() throws Exception {
		String sql = "INSERT INTO R_STUDENT(NAME, SEX, BIRTHDAY, BIRTH_TIME, ENROLLMENT_TIME, MAJOR, PHOTO, REMARK, READONLY) VALUES (?,?,?,?,?,?,?,?,?)";
		Connection conn = bds.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "jim");
			ps.setObject(2, 1);
			ps.setObject(3, new Timestamp(System.currentTimeMillis()));
			ps.setObject(4, new Timestamp(System.currentTimeMillis()));
			ps.setObject(5, new Timestamp(System.currentTimeMillis()));
			ps.setObject(6, 10);
			ps.setObject(7, null);
			ps.setObject(8, null);
			ps.setObject(9, 1);
			
			return ps.executeUpdate();
		}finally{
			ps.close();
			conn.close();
		}
	}

	@Override
	public List getList() throws Exception {
		String sql = "SELECT * FROM R_STUDENT";
		
		List<Student> list = new ArrayList<Student>();
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = bds.getConnection();
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Student student = new Student();
				student.setStudentId(rs.getInt("STUDENT_ID"));
				student.setName(rs.getString("NAME"));
				student.setSex(rs.getInt("SEX"));
				student.setBirthday(rs.getDate("BIRTHDAY"));
				student.setBirthTime(rs.getTime("BIRTH_TIME"));
				student.setEnrollmentTime(rs.getTimestamp("ENROLLMENT_TIME"));
				student.setMajor(rs.getInt("MAJOR"));
				student.setPhoto(rs.getBytes("PHOTO"));
				student.setRemark(rs.getString("REMARK"));
				student.setReadonly(rs.getInt("READONLY"));
				list.add(student);
			}
		}finally{
			ps.close();
			conn.close();
		}
		
		return list;
	}

	@Override
	public int delete() throws Exception {
		String sql = "DELETE FROM R_STUDENT";
		Connection conn = bds.getConnection();
		Statement statement = conn.createStatement();
		try {
			return statement.executeUpdate(sql);
		}finally{
			statement.close();
			conn.close();
		}
	}

	@Override
	public int[] batchInsert(int rows) throws Exception {
		String sql = "INSERT INTO R_STUDENT(NAME, SEX, BIRTHDAY, BIRTH_TIME, ENROLLMENT_TIME, MAJOR, PHOTO, REMARK, READONLY) VALUES (?,?,?,?,?,?,?,?,?)";
		Connection conn = bds.getConnection();
		PreparedStatement ps = null;
		try {
			for (int j = 0; j < rows; j++) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, "jim");
				ps.setObject(2, 1);
				ps.setObject(3, new Timestamp(System.currentTimeMillis()));
				ps.setObject(4, new Timestamp(System.currentTimeMillis()));
				ps.setObject(5, new Timestamp(System.currentTimeMillis()));
				ps.setObject(6, 10);
				ps.setObject(7, null);
				ps.setObject(8, null);
				ps.setObject(9, 1);
				ps.addBatch();
			}
			return ps.executeBatch();
		}finally{
			ps.close();
			conn.close();
		}
	}

}
