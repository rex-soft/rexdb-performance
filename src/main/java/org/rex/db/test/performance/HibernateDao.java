package org.rex.db.test.performance;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateDao extends Dao{
	
	static SessionFactory cf = null;
	
	static Session getSession() throws Exception {
		if (cf == null) {

			Properties conn = loadConnProperties("conn.properties");
			Properties prop = loadConnProperties("hibernate.properties");
			
			for (Iterator iterator = prop.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String value = ((String)entry.getValue()).trim();
				String key = (String)entry.getKey();
				
				if("${driverClassName}".equals(value)){
					prop.put(key, conn.getProperty("driverClassName"));
				}else if("${url}".equals(value)){
					prop.put(key, conn.getProperty("url"));
				}else if("${username}".equals(value)){
					prop.put(key, conn.getProperty("username"));
				}else if("${password}".equals(value)){
					prop.put(key, conn.getProperty("password"));
				}
			}
			
			Configuration configuration = new Configuration().addProperties(prop);
			configuration.addInputStream(HibernateDao.class.getResourceAsStream("/org/rex/db/test/Student.hbm.xml"));
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			cf = configuration.buildSessionFactory(serviceRegistry);
		}

		return cf.openSession();
	}
	
	//-----------
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
	public int insertPs() throws Exception {
		return insert();
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
	public int[] batchInsertPs(int rows) throws Exception {
		return batchInsert(rows);
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
		HibernateDao dao = new HibernateDao();
		System.out.println(dao.insert());
		System.out.println(dao.getList());
		System.out.println(dao.delete());
		
	}

}
