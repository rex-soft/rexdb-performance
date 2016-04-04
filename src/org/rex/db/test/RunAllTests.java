package org.rex.db.test;

/**
 * RUN ALL TESTS
 */
public class RunAllTests {

	//fast = 10
	public static void main(String[] args) throws Exception {
		int speed = 1, loop = 50;
		
		for (int i = 0; i < args.length; i++) {
			
			String[] p = args[i].split("=");
			
			String arg0 = p[0].trim().toLowerCase();
			int arg1 = -1;
			try{
				arg1 = Integer.parseInt(p[1]);
				if(arg1 <= 0)
					System.out.println("argument '"+arg0+"' must be greater than 0.");
			}catch(Exception e){
				System.out.println("argument '"+arg0+"' is not a number.");
			}
			

			if("speed".equals(arg0) && arg1 > 0){
				speed = arg1;
			}else if("loop".equals(arg0) && arg1 > 0){
				loop = arg1;
			}
		}
		
		
		System.out.println("================== starting test ==================");
		System.out.println("--speed: "+speed);
		System.out.println("--loop: "+loop);
		
		
		new RunSQLScript().run();
		new RunApiTest().run();
		new RunPerformanceTest(speed, loop).run();
	}
}
