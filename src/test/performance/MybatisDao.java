package test.performance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import test.Student;

public class MybatisDao extends Dao{
	
	static final String MYBATIS_CONFIG = "mybatis.xml";
	
	static SqlSessionFactory sessionFactory = null;
	
	static SqlSession getSession() throws IOException{
		if(sessionFactory == null){
			sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(MYBATIS_CONFIG));
		}
		return sessionFactory.openSession(true);
	}

	@Override
	public int insert() throws Exception {
		SqlSession session = getSession();
		try{
			return session.insert("insert", newStudent());
		}finally{
			session.close();
		}
	}
	
	@Override
	public int[] batchInsert(int rows) throws Exception {
		List list = new ArrayList();
		for (int i = 0; i < rows; i++) {
			list.add(super.newStudent());
		}
		
		SqlSession session = getSession();
		try{
			int c = session.insert("batch", list);
			return new int[]{c};
		}finally{
			session.close();
		}
	}
	
	@Override
	public List getList() throws Exception{
		SqlSession session = getSession();
		try{
			return session.selectList("getList");
		}finally{
			session.close();
		}
	}
	
	@Override
	public List getMapList() throws Exception {
		SqlSession session = getSession();
		try{
			List list = session.selectList("getMapList");
			return list;
		}finally{
			session.close();
		}
	}
	
	@Override
	public int delete() throws Exception {
		SqlSession session = getSession();
		try{
			return getSession().delete("delete");
		}finally{
			session.close();
		}
	}

	//--------------MAIN TEST
	public static void main(String[] args) throws Exception{
		Dao dao = new MybatisDao();
		System.out.println(dao.insert());
		System.out.println(dao.getList());
		System.out.println(dao.delete());
	}


}
