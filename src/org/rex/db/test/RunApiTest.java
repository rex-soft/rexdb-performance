package org.rex.db.test;

import java.lang.reflect.Method;

import org.rex.DB;
import org.rex.db.dialect.Dialect;
import org.rex.db.test.api.Base;
import org.rex.db.test.api.TestBatchUpdate;
import org.rex.db.test.api.TestCall;
import org.rex.db.test.api.TestGet;
import org.rex.db.test.api.TestGetList;
import org.rex.db.test.api.TestGetMap;
import org.rex.db.test.api.TestGetMapList;
import org.rex.db.test.api.TestTransaction;
import org.rex.db.test.api.TestUpdate;

public class RunApiTest implements Runner{
	
	/**
	 * test a method
	 */
	public void testMethods(Base test){
		System.out.println("--------------------- testing "+test.getName()+" ------------------");
		
		Method[] methods = test.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String methodName = method.getName();
			if(methodName.startsWith("test")){
				String api = methodName.substring(4, methodName.length());
				api = api.substring(0, 1).toLowerCase() + api.substring(1, api.length());
				
				try{
					method.invoke(test, null);
					System.out.println("--" + api + ": passed");
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("--" + api + ": faild, message: " + e.getCause().getMessage());
				}
			}
		}
	}
	
	@Override
	public void run() throws Exception {
		Dialect dialect = DB.getDialect();
		if(dialect == null)
			System.out.println("Does not support your database.");
		
		String dbName = dialect.getName();
		System.out.println("================== running rexdb API test for dbName ===================");
		
		testMethods(new TestGet());
		testMethods(new TestGetMap());
		testMethods(new TestGetList());
		testMethods(new TestGetMapList());
		testMethods(new TestUpdate());
		testMethods(new TestBatchUpdate());
		testMethods(new TestTransaction());
		
		if(!"MYSQL".equals(dbName)){
			System.out.println("--------------------- ignore DB.call test ------------------");
		}else
			testMethods(new TestCall());
	}
	
	
	public static void main(String[] args) throws Exception {

		new RunApiTest().run();
	}
}
