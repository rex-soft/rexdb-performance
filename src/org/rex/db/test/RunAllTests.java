package org.rex.db.test;

/**
 * RUN ALL TESTS
 */
public class RunAllTests {

	public static void main(String[] args) throws Exception {
		boolean fast = false;
		for (int i = 0; i < args.length; i++) {
			if("fast".equals(args[i]))
				fast = true;
		}
		
		new RunSQLScript().run();
		new RunApiTest().run();
		new RunPerformanceTest(fast).run();
	}
}
