package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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
import test.performance.SpringDao;

public class RunTest {
	
	static DecimalFormat df =new DecimalFormat("#.00");  
	
	//--operation
	public static final int OPER_INSERT = 0;
	public static final int OPER_INSERT_PS = 1;
	public static final int OPER_BATCH = 2;
	public static final int OPER_BATCH_PS = 3;
	public static final int OPER_QUERY_LIST = 4;
	public static final int OPER_QUERY_MAPLIST = 5;

	
	//--daos
	Dao hibernateDao;
	Dao mybatisDao;
	Dao springDao;
	Dao rexdbDao;
	Dao jdbcDao;
	
	//--framework enabled
	boolean hibernateEnabled, mybatisEnabled, springEnabled, rexdbEnabled, jdbcEnabled;
	
	public RunTest() throws Exception{
		hibernateDao = new HibernateDao();
		mybatisDao = new MybatisDao();
		springDao = SpringDao.getDao();
		rexdbDao = new RexdbDao();
		jdbcDao = new JdbcDao();
		
		rebuildTable();
		testFrameworks();
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
			System.out.println("--- error: "+e.getMessage());
		}
	}

	//test framework
	public void testFrameworks() throws Exception{
		System.out.println("================== testing frameworks ==================");
		
		hibernateEnabled = testFramework(hibernateDao);
		mybatisEnabled = testFramework(mybatisDao);
		springEnabled = testFramework(springDao);
		rexdbEnabled = testFramework(rexdbDao);
		jdbcEnabled = testFramework(jdbcDao);
	}
	
	private boolean testFramework(Dao dao) throws Exception{
		boolean enabled = false;
		try{
			dao.delete();
			dao.insert();
			dao.batchInsert(1);
			dao.getList();
			dao.getMapList();
			dao.delete();
			
			enabled = true;
		}catch(Exception e){
			System.out.println("-- "+dao.getName()+" error: " + e.getMessage());
			e.printStackTrace();
			enabled = false;
		}
		System.out.println("--- "+dao.getName()+"Enabled: "+ enabled);
		
		return enabled;
	}
	
	//remove all rows
	public void deleteRows() throws Exception{
		rexdbDao.delete();
	}
	
	//insert rows for test
	public void initRows(int rows) throws Exception{
		System.out.println("------------------------- init "+rows+" rows --------------------------");
		rexdbDao.batchInsert(rows);
	}
	
	public long oper(int operation, Dao dao, int rows) throws Exception{
		long start = System.currentTimeMillis();
		
		if(OPER_BATCH == operation){
			dao.batchInsert(rows);
		}else if(OPER_BATCH_PS == operation){
			dao.batchInsertPs(rows);
		}else if(OPER_QUERY_LIST == operation){
			dao.getList();
		}else if(OPER_QUERY_MAPLIST == operation){
			dao.getMapList();
		}else if(OPER_INSERT == operation){
			for (int i = 0; i < rows; i++) {
				dao.insert();
			}
		}else if(OPER_INSERT_PS == operation){
			for (int i = 0; i < rows; i++) {
				dao.insertPs();
			}
		}
		
		return System.currentTimeMillis() - start;
	}
	
	//test insert performance
	public double[] opers(String testName, int operation, int loop, int rows) throws Exception{
		System.out.println("-------------- testing "+testName+" (Affected Rows per second) ------------");
		System.out.println("|      |     rexdb     |     jdbc     |    hibernate    |  mybatis   |  spring   |");
		System.out.println("| ---- | ------------- | ------------ | --------------- | ---------- | --------- |");
		
		List<Double> timeRs = new ArrayList<Double>(),
					timeJs = new ArrayList<Double>(),
					timeHs = new ArrayList<Double>(),
					timeMs = new ArrayList<Double>(),
					timeSs = new ArrayList<Double>();
		
		for (int i = 0; i < loop; i++) {
			double h = 0, m = 0, r = 0, j = 0, s = 0;
			double timeH, timeM, timeJ, timeR, timeS;
			
			if(rexdbEnabled) r = oper(operation, rexdbDao, rows);
			if(jdbcEnabled) j = oper(operation, jdbcDao, rows);
			if(hibernateEnabled) h = oper(operation, hibernateDao, rows);
			if(mybatisEnabled) m = oper(operation, mybatisDao, rows);
			if(springEnabled) s = oper(operation, springDao, rows);
			
			timeR = r == 0 ? 0 : rows/(r/1000);
			timeJ = j == 0 ? 0 : rows/(j/1000);
			timeH = h == 0 ? 0 : rows/(h/1000);
			timeM = m == 0 ? 0 : rows/(m/1000);
			timeS = s == 0 ? 0 : rows/(s/1000);
			
			timeRs.add(timeR);
			timeJs.add(timeJ);
			timeHs.add(timeH);
			timeMs.add(timeM);
			timeSs.add(timeS);
			
			System.out.println("|   " + (i + 1) + "  |     " + df.format(timeR) + "     |    " + df.format(timeJ) + "     |      " + 
						df.format(timeH) + "      |   " + df.format(timeM) + "    |   " + df.format(timeS) + "    |");
		}
		
		System.out.println("| AVG  |     " + avg(timeRs, loop) + "     |    " + avg(timeJs, loop) + "     |      " + 
				avg(timeHs, loop) + "      |   " + avg(timeMs, loop) + "    |" + avg(timeSs, loop) + "    |");
		
		return new double[]{new Double(avg(timeRs, loop)), new Double(avg(timeJs, loop)), new Double(avg(timeHs, loop)), 
				new Double(avg(timeMs, loop)), new Double(avg(timeSs, loop))};
	}
	
	private static String avg(List<Double> times, int loop){
		Collections.sort(times);
		double count = 0;
		for (int i = 0; i < times.size(); i++) {
			count += times.get(i);
		}
		
		if(count == 0) return "0";
		
		return df.format(count/loop);
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
		
		Map<String, double[]> results = new LinkedHashMap<String, double[]>();
		
		//--------fast test
		test.deleteRows();
		int loop = fast ? 10 : 50;
		int der = fast ? 5 : 1;
			
		System.out.println("===================== running test ======================");
		
		//test insert
		results.put("insert", test.opers("insert", OPER_INSERT, loop, 500/der));
		test.deleteRows();
		
		//test insert Ps
		results.put("insertPs", test.opers("insertPs", OPER_INSERT_PS, loop, 500/der));
		test.deleteRows();
		
		//test batch insert
		results.put("batchInsert", test.opers("batchInsert", OPER_BATCH, loop, 50000/der));
		test.deleteRows();
		
		//test batch insert Ps
		results.put("batchInsertPs", test.opers("batchInsertPs", OPER_BATCH_PS, loop, 50000/der));
		test.deleteRows();
		
		//test get list
		test.initRows(50000/der);
		results.put("getList", test.opers("getList", OPER_QUERY_LIST, loop, 50000/der));
		test.setRexdbDynamicClass(false);
		results.put("getList-disableDynamicClass", test.opers("getList-disableDynamic", OPER_QUERY_LIST, loop, 50000/der));
		test.setRexdbDynamicClass(true);
		results.put("getMapList", test.opers("getMapList", OPER_QUERY_MAPLIST, loop, 50000/der));
		
		test.deleteRows();
		
		//------print results
		printResult(results);
		printJson(results);
	}
	
	//print result
	public static void printResult(Map<String, double[]> result){
		System.out.println("================== printing result ==================");
		System.out.println("|   OPER/COSTS(ms)   |     rexdb     |     jdbc    |  hibernate |  mybatis |  spring |");
		System.out.println("| ------------------ | ------------- | ----------- | ---------- | -------- | ------- |");
		
		for (Iterator<Map.Entry<String, double[]>> iterator = result.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, double[]> entry = iterator.next();
			String key = entry.getKey();
			double[] values = entry.getValue();
			
			System.out.println("|   " + key + "   |     " + values[0] + "     |     " + values[1] + "     |   " + 
					values[2] + "   |   " + values[3] + "   |" + values[4] + "   |");
		}
	}
	
	//print json
	public static void printJson(Map<String, double[]> result){
		System.out.println("================== printing json result ==================");
		
		Map datas = new LinkedHashMap();
		for (Iterator<Map.Entry<String, double[]>> iterator = result.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, double[]> entry = iterator.next();
			String key = entry.getKey();
			double[] values = entry.getValue();
			Map costs = new LinkedHashMap();
			costs.put("rexdb", values[0]);
			costs.put("jdbc", values[1]);
			costs.put("hibernate", values[2]);
			costs.put("mybatis", values[3]);
			costs.put("spring", values[4]);
			
			datas.put(key, costs);
		}
		
		System.out.println(JSON.toJSONString(datas));
	}
}
