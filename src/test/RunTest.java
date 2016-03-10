package test;

import test.performance.Dao;
import test.performance.HibernateDao;
import test.performance.JdbcDao;
import test.performance.MybatisDao;
import test.performance.RexdbDao;

public class RunTest {
	
	public static final int OPER_INSERT = 0;
	public static final int OPER_QUERY_LIST = 1;

	Dao hibernateDao;
	Dao mybatisDao;
	Dao rexdbDao;
	Dao jdbcDao;
	
	public RunTest(){
		hibernateDao = new HibernateDao();
		mybatisDao = new MybatisDao();
		rexdbDao = new RexdbDao();
		jdbcDao = new JdbcDao();
	}
	
	//warm up frameworks, to avoid affecting test results
	void warmUp() throws Exception{
		System.out.println("preparing.");
		
		hibernateDao.insert();
		hibernateDao.getList();
//		hibernateDao.delete();
		
		mybatisDao.insert();
		mybatisDao.getList();
//		mybatisDao.delete();
		
		rexdbDao.insert();
		rexdbDao.getList();
//		rexdbDao.delete();
		
		jdbcDao.insert();
		jdbcDao.getList();
//		jdbcDao.delete();
	}
	
	//remove all rows
	void deleteRows() throws Exception{
		rexdbDao.delete();
	}
	
	//insert rows for test
	void initRows(int rows) throws Exception{
		rexdbDao.batchInsert(rows);
	}
	
	private long oper(int operation, Dao dao, int rows) throws Exception{
		long start = System.currentTimeMillis();
		for (int i = 0; i < rows; i++) {
			if(OPER_INSERT == operation){
				dao.insert();
			}else if(OPER_QUERY_LIST == operation){
				dao.getList();
			}
		}
		return System.currentTimeMillis() - start;
	}
	
	//test insert performance
	private long[] opers(int operation, int loop, int rows) throws Exception{
		System.out.println("starting " + operation + ".");
		System.out.println("|      |   hibernate   |   mybatis   |   rexdb   |   jdbc   |");
		System.out.println("| ----- | ------------- | ----------- | --------- | -------- |");
		
		long sumH = 0, sumM = 0, sumR = 0, sumJ = 0;
		for (int i = 0; i < loop; i++) {
			long h = 0, m = 0, r = 0, j = 0;
			
			h = oper(operation, hibernateDao, rows);
			m = oper(operation, mybatisDao, rows);
			r = oper(operation, rexdbDao, rows);
			j = oper(operation, jdbcDao, rows);	
			
			sumH += h;
			sumM += m;
			sumR += r;
			sumJ += j;
			
			System.out.println("|   " + (i + 1) + "   |     " +h + "     |     " + m + "     |   " + r + "   |   " + j + "   |");
		}
		
		System.out.println("|  AVG   |     " + sumH/loop + "     |     " + sumM/loop + "     |   " + sumR/loop + "   |   " + sumJ/loop + "   |");
		return new long[]{sumH/loop, sumM/loop, sumR/loop, sumJ/loop};
	}
	
	
	//----------START TESTING
	public static void main(String[] args) throws Exception {
		
		RunTest test = new RunTest();
//		test.warmUp();
		
//		//insert
//		test.opers(OPER_INSERT, 50, 50);
//
//		System.out.println("clear and inserting 10000 rows.");
//		test.deleteRows();
//		test.initRows(1000);
//		System.out.println("1000 rows inited.");
		
		//get list
		test.opers(OPER_QUERY_LIST, 10, 50);
	}
}
