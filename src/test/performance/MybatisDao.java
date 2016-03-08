package test.performance;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import test.Student;

public class MybatisDao extends Dao{
	
	static final String MYBATIS_CONFIG = "mybatis.xml";
	
	static SqlSession getSession() throws IOException{
		SqlSessionFactory sessionFactory = null;
		sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(MYBATIS_CONFIG));
		return sessionFactory.openSession(true);
	}

	@Override
	public int insert() throws Exception {
		Student student = newStudent();
		return getSession().insert("insert", student);
	}
	
	@Override
	public List getList() throws Exception{
		return getSession().selectList("getList");
	}
	
	@Override
	public int delete() throws Exception {
		return getSession().delete("delete");
	}

	//--------------MAIN TEST
	public static void main(String[] args) throws Exception{
		Dao dao = new MybatisDao();
		System.out.println(dao.insert());
		System.out.println(dao.getList());
		System.out.println(dao.delete());
	}

}
