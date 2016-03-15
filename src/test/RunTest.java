package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.rex.DB;
import org.rex.db.configuration.Configuration;
import org.rex.db.dialect.Dialect;
import org.rex.db.exception.DBException;

import com.alibaba.fastjson.JSON;

import test.performance.Dao;
import test.performance.HibernateDao;
import test.performance.JdbcDao;
import test.performance.MybatisDao;
import test.performance.RexdbDao;

public class RunTest {
	
	//--operation
	public static final int OPER_INSERT = 0;
	public static final int OPER_QUERY_LIST = 1;
	public static final int OPER_QUERY_MAPLIST = 2;
	public static final int OPER_BATCH = 3;
	
	//--daos
	Dao hibernateDao;
	Dao mybatisDao;
	Dao rexdbDao;
	Dao jdbcDao;
	
	//--framework enabled
	boolean hibernateEnabled, mybatisEnabled, rexdbEnabled, jdbcEnabled;
	
	public RunTest() throws Exception{
		hibernateDao = new HibernateDao();
		mybatisDao = new MybatisDao();
		rexdbDao = new RexdbDao();
		jdbcDao = new JdbcDao();
		
		rebuildTable();
		testFramework();
	}
	
	//--recreate table
	public void rebuildTable() throws Exception{
		System.out.println("================== creating table r_student ==================");
		
		Dialect dialect = DB.getDialect();
		if(dialect == null)
			throw new Exception("database not support, dialect required.");
		String name = dialect.getName();
		
		InputStream is = this.getClass().getResourceAsStream("/create/"+name.toLowerCase()+".sql");
		if(is == null)
			throw new Exception("file "+ "create/"+name.toLowerCase()+".sql" +" not exist.");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while((line = in.readLine())!=null){
			if(!line.startsWith("--"))
				sb.append(line);
		}
		
		String[] sqls = sb.toString().split(";");
		
		DB.beginTransaction();
		try{
			for (int i = 0; i < sqls.length; i++) {
				DB.update(sqls[i]);
				System.out.println("--- execute: "+sqls[i]);
			}
			DB.commit();
		}catch(Exception e){
			DB.rollback();
			throw e;
		}
	}
	
	//test frameworks
	public void testFramework() throws Exception{
		System.out.println("================== testing frameworks ==================");
		
		try{
			hibernateDao.delete();
			
			hibernateDao.insert();
			hibernateDao.batchInsert(1);
			hibernateDao.getList();
			hibernateDao.getMapList();
			hibernateDao.delete();
			
			hibernateEnabled = true;
		}catch(Exception e){
			System.out.println("-- hibernate error: " + e.getMessage());
			hibernateEnabled = false;
		}
		
		try{
			mybatisDao.insert();
			mybatisDao.batchInsert(1);
			mybatisDao.getList();
			mybatisDao.getMapList();
			mybatisDao.delete();
			
			mybatisEnabled = true;
		}catch(Exception e){
			System.out.println("-- mybatis error: " + e.getMessage());
			mybatisEnabled = false;
		}
		
		try{
			rexdbDao.insert();
			rexdbDao.batchInsert(1);
			rexdbDao.getList();
			rexdbDao.getMapList();
			rexdbDao.delete();
			
			rexdbEnabled = true;
		}catch(Exception e){
			System.out.println("-- rexdb error: " + e.getMessage());
			e.printStackTrace();
			rexdbEnabled = false;
		}
		
		try{
			jdbcDao.insert();
			jdbcDao.batchInsert(1);
			jdbcDao.getList();
			jdbcDao.getMapList();
			jdbcDao.delete();
			
			jdbcEnabled = true;
		}catch(Exception e){
			System.out.println("-- jdbc error: " + e.getMessage());
			jdbcEnabled = false;
		}
		
		System.out.println("--- hibernateEnabled: "+hibernateEnabled);
		System.out.println("--- mybatisEnabled: "+mybatisEnabled);
		System.out.println("--- rexdbEnabled: "+rexdbEnabled);
		System.out.println("--- jdbcEnabled: "+jdbcEnabled);
	}
	
	//remove all rows
	public void deleteRows() throws Exception{
		System.out.println("================== deleting all rows ==================");
		
		rexdbDao.delete();
	}
	
	//insert rows for test
	public void initRows(int rows) throws Exception{
		System.out.println("================== batch insert " + rows + " rows ==================");
		
		rexdbDao.batchInsert(rows);
	}
	
	public long oper(int operation, Dao dao, int rows) throws Exception{
		long start = System.currentTimeMillis();
		
		if(OPER_BATCH == operation){
			dao.batchInsert(rows);
		}else{
			for (int i = 0; i < rows; i++) {
				if(OPER_INSERT == operation){
					dao.insert();
				}else if(OPER_QUERY_LIST == operation){
					dao.getList();
				}else if(OPER_QUERY_MAPLIST == operation){
					dao.getMapList();
				}
			}
		}
		
		return System.currentTimeMillis() - start;
	}
	
	//test insert performance
	public long[] opers(String testName, int operation, int loop, int rows) throws Exception{
		System.out.println("--------------- testing "+testName+" ---------------");
		System.out.println("|      |   hibernate   |   mybatis   |    jdbc    |   rexdb  |");
		System.out.println("| ---- | ------------- | ----------- | ---------- | -------- |");
		
		long sumH = 0, sumM = 0, sumR = 0, sumJ = 0;
		for (int i = 0; i < loop; i++) {
			long h = 0, m = 0, r = 0, j = 0;
			
			if(hibernateEnabled) h = oper(operation, hibernateDao, rows);
			if(mybatisEnabled) m = oper(operation, mybatisDao, rows);
			if(jdbcEnabled) j = oper(operation, jdbcDao, rows);
			if(rexdbEnabled) r = oper(operation, rexdbDao, rows);
			
			sumH += h;
			sumM += m;
			sumJ += j;
			sumR += r;
			
			System.out.println("|   " + (i + 1) + "   |     " +h + "     |     " + m + "     |   " + j + "   |   " + r + "   |");
		}
		
		System.out.println("|  AVG   |     " + sumH/loop + "     |     " + sumM/loop + "     |   " + sumJ/loop + "   |   " + sumR/loop + "   |");
		return new long[]{sumH/loop, sumM/loop, sumJ/loop, sumR/loop};
	}
	
	//set rexdb dynamicClass setting
	public void  setRexdbDynamicClass(boolean dynamicClass) throws DBException{
		Configuration.getCurrentConfiguration().setDynamicClass(dynamicClass);
	}
	
	
	//----------START TESTING
	public static void main(String[] args) throws Exception {
		RunTest test = new RunTest();
		
		boolean fast = false;
		for (int i = 0; i < args.length; i++) {
			if("fast".equals(args[i]))
				fast = true;
		}
		
		Map<String, long[]> results = new LinkedHashMap<String, long[]>();
		
		//--------fast test
		test.deleteRows();
		if(fast){
			int fastLoop = 30;
			System.out.println("================== running fast test ==================");
			
			//test insert
//			results.put("insert-50", test.opers("insert-50", OPER_INSERT, fastLoop, 50));
			test.deleteRows();
			
			//test batch insert
			results.put("batchInsert-10k", test.opers("batchInsert-10k", OPER_BATCH, fastLoop, 10000));
			test.deleteRows();
			
			//test get list
			test.initRows(10000);
			results.put("getList-10k", test.opers("getList-10k", OPER_QUERY_LIST, fastLoop, 1));
			test.deleteRows();

		//-------fully test
		}else{
			int loop = 1;
			System.out.println("================== running fully test ==================");
			
			//test insert
			results.put("insert-100", test.opers("insert-100", OPER_INSERT, loop, 100));
			results.put("insert-200", test.opers("insert-200", OPER_INSERT, loop, 200));
			results.put("insert-500", test.opers("insert-500", OPER_INSERT, loop, 500));
			test.deleteRows();
			
//			//test batch insert
			results.put("batchInsert-10k", test.opers("batchInsert-10k", OPER_BATCH, loop, 10000));
			results.put("batchInsert-50k", test.opers("batchInsert-50k", OPER_BATCH, loop, 50000));
			results.put("batchInsert-100k", test.opers("batchInsert-100k", OPER_BATCH, loop, 100000));
			test.deleteRows();
			
			//test get list
			test.initRows(10000);
			results.put("getList-10k", test.opers("getList-10k", OPER_QUERY_LIST, loop, 1));
			results.put("getMapList-10k", test.opers("getMapList-10k", OPER_QUERY_MAPLIST, loop, 1));
			
			test.setRexdbDynamicClass(false);
			results.put("getList-disableDynamic-10k", test.opers("getList-10k", OPER_QUERY_LIST, loop, 1));
			test.setRexdbDynamicClass(true);
		
			test.deleteRows();
			test.initRows(50000);
			results.put("getList-50k", test.opers("getList-50k", OPER_QUERY_LIST, loop, 1));	
			results.put("getMapList-50k", test.opers("getMapList-50k", OPER_QUERY_MAPLIST, loop, 1));
			
			test.setRexdbDynamicClass(false);
			results.put("getList-disableDynamic-50k", test.opers("getList-50k", OPER_QUERY_LIST, loop, 1));
			test.setRexdbDynamicClass(true);
			
			test.deleteRows();
			test.initRows(100000);
			results.put("getList-100k", test.opers("getList-100k", OPER_QUERY_LIST, loop, 1));
			results.put("getMapList-100k", test.opers("getMapList-100k", OPER_QUERY_MAPLIST, loop, 1));
			
			test.setRexdbDynamicClass(false);
			results.put("getList-disableDynamic-100k", test.opers("getList-100k", OPER_QUERY_LIST, loop, 1));
			test.setRexdbDynamicClass(true);
			
			test.deleteRows();
		}
		
		
		//------print results
		printResult(results);
		printJson(results);
	}
	
	//print result
	public static void printResult(Map<String, long[]> result){
		System.out.println("================== printing result ==================");
		System.out.println("|   OPER/COSTS(ms)   |   hibernate   |   mybatis   |    jdbc    |   rexdb  |");
		System.out.println("| ------------------ | ------------- | ----------- | ---------- | -------- |");
		
		for (Iterator<Map.Entry<String, long[]>> iterator = result.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, long[]> entry = iterator.next();
			String key = entry.getKey();
			long[] values = entry.getValue();
			
			System.out.println("|   " + key + "   |     " + values[0] + "     |     " + values[1] + "     |   " + values[2] + "   |   " + values[3] + "   |");
		}
	}
	
	//print json
	public static void printJson(Map<String, long[]> result){
		System.out.println("================== printing json result ==================");
		
		Map datas = new LinkedHashMap();
		for (Iterator<Map.Entry<String, long[]>> iterator = result.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, long[]> entry = iterator.next();
			String key = entry.getKey();
			long[] values = entry.getValue();
			Map costs = new LinkedHashMap();
			costs.put("hibernate", values[0]);
			costs.put("mybatis", values[1]);
			costs.put("jdbc", values[2]);
			costs.put("rexdb", values[3]);
			
			datas.put(key, costs);
		}
		
		System.out.println(JSON.toJSONString(datas));
	}
}
