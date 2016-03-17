package test.performance;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisDao extends Dao{
	
	static final String MYBATIS_CONFIG = "mybatis.xml";
	
	static SqlSessionFactory sessionFactory = null;
	
	static SqlSession getSession() throws IOException{
		return getSession(false);
	}

	static SqlSession getSession(boolean isBatch) throws IOException{
		if(sessionFactory == null){
			sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(MYBATIS_CONFIG));
		}
		
		if(isBatch)
			return sessionFactory.openSession(ExecutorType.BATCH, true);
		else
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
	public int insertPs() throws Exception {
		return insert();
	}

	@Override
	public int[] batchInsert(int rows) throws Exception {
		int c = 0;
		SqlSession session = getSession(true);
		try{
			for (int i = 0; i < rows; i++) {
				session.insert("insert", newStudent());
			}
			session.flushStatements();
		}finally{
			session.close();
		}
		return new int[]{c};
	}
	
	@Override
	public int[] batchInsertPs(int rows) throws Exception {
		return batchInsert(rows);
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
	
	@Override
	public String getName() throws Exception {
		return "mybatis";
	}

	//--------------MAIN TEST
	public static void main(String[] args) throws Exception{
		Dao dao = new MybatisDao();
		System.out.println(dao.insert());
		System.out.println(dao.getList());
		System.out.println(dao.delete());
	}


}
