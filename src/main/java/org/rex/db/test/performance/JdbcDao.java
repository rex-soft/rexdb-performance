package org.rex.db.test.performance;

import java.sql.Blob;
import java.sql.Clob;
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
import org.rex.db.test.Student;

public class JdbcDao extends Dao{
	
	BasicDataSource bds = null;
	
	public JdbcDao(){
		Properties prop = loadConnProperties("conn.properties");
		
		bds = new BasicDataSource();
		bds.setDriverClassName(prop.getProperty("driverClassName"));
		bds.setUrl(prop.getProperty("url"));
		bds.setUsername(prop.getProperty("username"));
		bds.setPassword(prop.getProperty("password"));
	}
	
	@Override
	public int insert() throws Exception {
		String sql = "INSERT INTO rexdb_test_student(STUDENT_ID, NAME, SEX, BIRTHDAY, BIRTH_TIME, ENROLLMENT_TIME, MAJOR, PHOTO, REMARK, READONLY) VALUES (?,?,?,?,?,?,?,?,?,?)";
		Connection conn = bds.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			
			Student stu  = newStudent();
			ps.setLong(1, stu.getStudentId());
			ps.setString(2, stu.getName());
			ps.setInt(3, stu.getSex());
			ps.setTimestamp(4, new java.sql.Timestamp(stu.getBirthday().getTime()));
			ps.setTimestamp(5, new java.sql.Timestamp(stu.getBirthTime().getTime()));
			ps.setTimestamp(6, new java.sql.Timestamp(stu.getEnrollmentTime().getTime()));
			ps.setInt(7, stu.getMajor());
			ps.setBytes(8, stu.getPhoto());
			ps.setString(9, stu.getRemark());
			ps.setInt(10, stu.getReadonly());
			
			return ps.executeUpdate();
		}finally{
			ps.close();
			conn.close();
		}
	}
	
	@Override
	public int insertPs() throws Exception {
		return insert();
	}

	@Override
	public List getList() throws Exception {
		String sql = "SELECT * FROM rexdb_test_student";
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
				student.setStudentId(rs.getLong("STUDENT_ID"));
				student.setName(rs.getString("NAME"));
				student.setSex(rs.getInt("SEX"));
				student.setBirthday(rs.getDate("BIRTHDAY"));
				student.setBirthTime(rs.getTime("BIRTH_TIME"));
				student.setEnrollmentTime(rs.getTimestamp("ENROLLMENT_TIME"));
				student.setMajor(rs.getInt("MAJOR"));
				
				if(super.isPostgreSql()){
					student.setPhoto(rs.getBytes("PHOTO"));
					student.setRemark(rs.getString("REMARK"));
				}else{
				    Blob blob = rs.getBlob("PHOTO");
				    byte[] photo = null;
				    if (null != blob) {
				    	photo = blob.getBytes(1, (int) blob.length());
				    }
				    
				    String remark = null;
				    Clob clob = rs.getClob("REMARK");
				    if (clob != null) {
				      int size = (int) clob.length();
				      remark = clob.getSubString(1, size);
				    }
					student.setPhoto(photo);
					student.setRemark(remark);
				}
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
		String sql = "SELECT * FROM rexdb_test_student";
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
				student.put("studentId", rs.getLong("STUDENT_ID"));
				student.put("name", rs.getString("NAME"));
				student.put("sex", rs.getInt("SEX"));
				student.put("birthday", rs.getDate("BIRTHDAY"));
				student.put("birthTime", rs.getTime("BIRTH_TIME"));
				student.put("enrollmentTime", rs.getTimestamp("ENROLLMENT_TIME"));
				student.put("major", rs.getInt("MAJOR"));
				
				if(super.isPostgreSql()){
					student.put("photo", rs.getBytes("PHOTO"));
					student.put("remark", rs.getString("REMARK"));
				}else{
				    Blob blob = rs.getBlob("PHOTO");
				    byte[] photo = null;
				    if (null != blob) {
				    	photo = blob.getBytes(1, (int) blob.length());
				    }
				    
				    String remark = null;
				    Clob clob = rs.getClob("REMARK");
				    if (clob != null) {
				      int size = (int) clob.length();
				      remark = clob.getSubString(1, size);
				    }
					student.put("photo", photo);
					student.put("remark", remark);
				}
				
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
		String sql = "DELETE FROM rexdb_test_student";
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
		String sql = "INSERT INTO rexdb_test_student(STUDENT_ID, NAME, SEX, BIRTHDAY, BIRTH_TIME, ENROLLMENT_TIME, MAJOR, PHOTO, REMARK, READONLY) VALUES (?,?,?,?,?,?,?,?,?,?)";
		Student[] students = new Student[rows];
		for (int i = 0; i < rows; i++) {
			students[i] = super.newStudent();
		}
		
		Connection conn = bds.getConnection();
		PreparedStatement ps = null;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for (int j = 0; j < students.length; j++) {
				
				Student stu  = students[j];

				ps.setLong(1, stu.getStudentId());
				ps.setString(2, stu.getName());
				ps.setObject(3, stu.getSex());
				ps.setObject(4, new java.sql.Timestamp(stu.getBirthday().getTime()));
				ps.setObject(5, new java.sql.Timestamp(stu.getBirthTime().getTime()));
				ps.setObject(6, new java.sql.Timestamp(stu.getEnrollmentTime().getTime()));
				ps.setObject(7, stu.getMajor());
				ps.setObject(8, stu.getPhoto());
				ps.setObject(9, stu.getRemark());
				ps.setObject(10, stu.getReadonly());
				
				ps.addBatch();
			}
			int[] r = ps.executeBatch();
			conn.commit();
			return r;
		}catch(Exception e){
			conn.rollback();
			throw e;
		}finally{
			ps.close();
			conn.close();
		}
	}
	

	@Override
	public int[] batchInsertPs(int rows) throws Exception {
		return batchInsert(rows);
	}

	@Override
	public String getName() throws Exception {
		return "jdbc";
	}

}
