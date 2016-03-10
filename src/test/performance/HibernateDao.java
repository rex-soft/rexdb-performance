package test.performance;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateDao extends Dao{
	
	static SessionFactory cf = null;
	
	static Session getSession(){
		if(cf == null)
			cf = new Configuration().configure().buildSessionFactory();
		return cf.openSession();
	}

	@Override
	public int insert() throws Exception {
		Session session = getSession();
		try {
			session.beginTransaction();
			Serializable key = session.save(super.newStudent());
			session.getTransaction().commit();
			return key == null ? 0 : 1;
		} finally {
			session.close();
		}
	}
	
	@Override
	public int[] batchInsert(int rows) throws Exception {
		int[] c = new int[rows];
		Session session = getSession();
		try {
			session.beginTransaction();
			for (int i=0; i<rows; i++ ) {
				Serializable key = session.save(super.newStudent());
				c[i] = key == null ? 0 : 1;
			}
			session.flush();  
            session.clear();
			session.getTransaction().commit();
			return c;
		} finally {
			session.close();
		}
	}

	@Override
	public List getList() throws Exception {
		Session session = getSession();
		try {
			Query query = session.createQuery("from Student");
			return query.list();
		} finally {
			session.close();
		}
	}

	@Override
	public int delete() throws Exception {
		Session session = getSession();
		try {
			return session.createQuery("delete Student").executeUpdate();
		} finally {
			session.close();
		}
	}

	//--------------MAIN TEST
	public static void main(String[] args) throws Exception{
		Dao dao = new HibernateDao();
		System.out.println(dao.insert());
		System.out.println(dao.getList());
		System.out.println(dao.delete());
	}

}
