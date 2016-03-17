package test.performance;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
			Transaction tx = session.beginTransaction();
			Serializable key = session.save(super.newStudent());
			tx.commit();
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
			Transaction tx = session.beginTransaction();
			for (int i=0; i<rows; i++ ) {
				Serializable key = session.save(super.newStudent());
				c[i] = key == null ? 0 : 1;
			}
			session.flush(); 
            session.clear();
            tx.commit();
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
	public List getMapList() throws Exception {
		Session session = getSession();
		try {
			Query query = session.createQuery("select new map(s.studentId as studentId, s.name as name, s.sex as sex,"
					+ "s.birthday as birthday, s.birthTime as birthTime, s.enrollmentTime as enrollmentTime,"
					+ "s.major as major, s.photo as photo, s.remark as remark,s.readonly as readonly) from Student s");
			List list = query.list();
			return list;
		} finally {
			session.close();
		}
	}

	@Override
	public int delete() throws Exception {
		Session session = getSession();
		try {
			Transaction tx = session.beginTransaction();
			int i = session.createQuery("delete Student").executeUpdate();
			tx.commit();
			return i;
		} finally {
			session.close();
		}
	}
	
	@Override
	public String getName() throws Exception {
		return "hibernate";
	}

	//--------------MAIN TEST
	public static void main(String[] args) throws Exception{
		Dao dao = new HibernateDao();
		System.out.println(dao.insert());
		System.out.println(dao.getList());
		System.out.println(dao.delete());
	}



}
