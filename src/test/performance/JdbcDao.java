package test.performance;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

import test.Student;

public class JdbcDao extends Dao{
	
	BasicDataSource bds = null;
	
	public JdbcDao(){
		Properties prop = loadProperties("/conn.properties");
		
		bds = new BasicDataSource();
		bds.setDriverClassName(prop.getProperty("driverClassName"));
		bds.setUrl(prop.getProperty("url"));
		bds.setUsername(prop.getProperty("username"));
		bds.setPassword(prop.getProperty("password"));
	}

	Properties loadProperties(String resources) {
		InputStream inputstream = this.getClass().getResourceAsStream(resources);
		if(inputstream == null)
			throw new RuntimeException("could not find properties "+resources);
		Properties properties = new Properties();
		try {
			properties.load(inputstream);
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				inputstream.close();
			} catch (IOException e) {
			}
		}
	}

	@Override
	public int insert() throws Exception {
		String sql = "INSERT INTO R_STUDENT(STUDENT_ID, NAME, SEX, BIRTHDAY, BIRTH_TIME, ENROLLMENT_TIME, MAJOR, PHOTO, REMARK, READONLY) VALUES (?,?,?,?,?,?,?,?,?,?)";
		Connection conn = bds.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			
			Student stu  = super.newStudent();
			ps.setLong(1, stu.getStudentId());
			ps.setString(2, stu.getName());
			ps.setObject(3, stu.getSex());
			ps.setObject(4, stu.getBirthday());
			ps.setObject(5, stu.getBirthTime());
			ps.setObject(6, stu.getEnrollmentTime());
			ps.setObject(7, stu.getMajor());
			ps.setObject(8, stu.getPhoto());
			ps.setObject(9, stu.getRemark());
			ps.setObject(10, stu.getReadonly());
			
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
				
				Student stu  = super.newStudent();
				ps.setLong(1, stu.getStudentId());
				ps.setString(2, stu.getName());
				ps.setObject(3, stu.getSex());
				ps.setObject(4, stu.getBirthday());
				ps.setObject(5, stu.getBirthTime());
				ps.setObject(6, stu.getEnrollmentTime());
				ps.setObject(7, stu.getMajor());
				ps.setObject(8, stu.getPhoto());
				ps.setObject(9, stu.getRemark());
				ps.setObject(10, stu.getReadonly());
				
				ps.addBatch();
			}
			return ps.executeBatch();
		}finally{
			ps.close();
			conn.close();
		}
	}


}
