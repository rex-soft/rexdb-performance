package org.rex.db.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.rex.DB;
import org.rex.db.dialect.Dialect;

public class RunSQLScript implements Runner{
	
	static final String DELIMITER = "\\$\\$";

	//--recreate table
	public void run() throws Exception{
		System.out.println("================== running script ==================");
		
		Dialect dialect = DB.getDialect();
		if(dialect == null)
			throw new Exception("database not support, dialect required.");
		String name = dialect.getName();
		
		InputStream is = this.getClass().getResourceAsStream("/org/rex/db/test/sql/"+name.toLowerCase()+".sql");
		if(is == null)
			throw new Exception("file "+ "create/"+name.toLowerCase()+".sql" +" not exist.");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while((line = in.readLine())!=null){
			if(line.startsWith("--")) continue;
			
				sb.append(line);
		}
		
		String[] sqls = sb.toString().split(DELIMITER);
		
		for (int i = 0; i < sqls.length; i++) {
			String sql = sqls[i].trim();
			if(sql.endsWith(";"))
				sql = sql.substring(0, sql.length() - 1);
			
			try{
				DB.update(sql);
				System.out.println("--- executed: "+sql);
			}catch(Exception e){
				System.out.println("--- execute error: "+sql+". error message: "+e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		new RunSQLScript().run();
	}
}
