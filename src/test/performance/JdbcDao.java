package test.performance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;

import test.Student;

public class JdbcDao extends Dao{
	
	BasicDataSource bds = null;
	
	public JdbcDao(){
		bds = new BasicDataSource();
		bds.setDriverClassName("org.apache.commons.dbcp.BasicDataSource");
		bds.setUrl("jdbc:mysql://localhost:3306/rexdb?rewriteBatchedStatements=true");
		bds.setUsername("root");
		bds.setPassword("12345678");
	}

	@Override
	public int insert() throws Exception {
		String sql = "INSERT INTO R_STUDENT(STUDENT_ID, NAME, SEX, BIRTHDAY, BIRTH_TIME, ENROLLMENT_TIME, MAJOR, PHOTO, REMARK, READONLY) VALUES (?,?,?,?,?,?,?,?,?,?)";
		Connection conn = bds.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, Dao.studentId++);
			ps.setString(2, "jim");
			ps.setObject(3, 1);
			ps.setObject(4, new Timestamp(System.currentTimeMillis()));
			ps.setObject(5, new Timestamp(System.currentTimeMillis()));
			ps.setObject(6, new Timestamp(System.currentTimeMillis()));
			ps.setObject(7, 10);
			ps.setObject(8, null);
			ps.setObject(9, null);
			ps.setObject(10, 1);
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
		ResultSet rs = null;
		try{
			conn = bds.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
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
			rs.close();
			ps.close();
			conn.close();
		}
		return list;
	}
	

	@Override
	public List getMapList() throws Exception {
		String sql = "SELECT * FROM R_STUDENT";
		List<Map> list = new ArrayList<Map>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = bds.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				Map student = new HashMap();
				student.put("studentId", rs.getInt("STUDENT_ID"));
				student.put("name", rs.getString("NAME"));
				student.put("sex", rs.getInt("SEX"));
				student.put("birthday", rs.getDate("BIRTHDAY"));
				student.put("birthTime", rs.getTime("BIRTH_TIME"));
				student.put("enrollmentTime", rs.getTimestamp("ENROLLMENT_TIME"));
				student.put("major", rs.getInt("MAJOR"));
				student.put("photo", rs.getBytes("PHOTO"));
				student.put("remark", rs.getString("REMARK"));
				student.put("readonly", rs.getInt("READONLY"));
				list.add(student);
			}
		}finally{
			rs.close();
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
		String sql = "INSERT INTO R_STUDENT(STUDENT_ID, NAME, SEX, BIRTHDAY, BIRTH_TIME, ENROLLMENT_TIME, MAJOR, PHOTO, REMARK, READONLY) VALUES (?,?,?,?,?,?,?,?,?,?)";
		Connection conn = bds.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int j = 0; j < rows; j++) {
				ps.setLong(1, Dao.studentId++);
				ps.setString(2, "jim");
				ps.setObject(3, 1);
				ps.setObject(4, new Timestamp(System.currentTimeMillis()));
				ps.setObject(5, new Timestamp(System.currentTimeMillis()));
				ps.setObject(6, new Timestamp(System.currentTimeMillis()));
				ps.setObject(7, 10);
				ps.setObject(8, null);
				ps.setObject(9, null);
				ps.setObject(10, 1);
				ps.addBatch();
			}
			return ps.executeBatch();
		}finally{
			ps.close();
			conn.close();
		}
	}


}
