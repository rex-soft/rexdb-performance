package org.rex.db.test.api;

import java.util.List;

import org.rex.DB;
import org.rex.RMap;
import org.rex.db.Ps;

public class TestCall extends Base{
	
	public TestCall() throws Exception{
		init();
	}
	
	@Override
	protected void init() throws Exception {
		super.deleteAll();
		super.setId(200000);
		super.initRows(10);
	}

	public static void main(String[] args) throws Exception {
		TestCall call = new TestCall();
		call.testCallInParamter();
		call.testCallOutParamter();
		call.testCallInOutParamter();
		call.testCallInoutParamter();
		call.testCallReturnParamter();
		call.testCallReturnRsParamter();
	}

	/**
	 * 输入参数
	 */
	public void testCallInParamter() throws Exception {
		String sql = "{call rexdb_test_proc_in(?)}";
		
		RMap result = DB.call(sql, new Ps(200000));
		if(result.get("return_1") == null)
			throw new Exception("call seems didn't work well.");
	}

	/**
	 * 输出参数
	 */
	public void testCallOutParamter() throws Exception {
		String sql = "{call rexdb_test_proc_out(?)}";
		Ps ps = new Ps();
		ps.addOutInt("age");

		RMap result = DB.call(sql, ps);
		int outDefault = result.getInt("out_1"), outRenamed = result.getInt("age");
		if(outDefault != 10 || outRenamed != 10)
			throw new Exception("call seems didn't work well.");
	}

	/**
	 * 输入、输出参数同时存在
	 */
	public void testCallInOutParamter() throws Exception {
		String sql = "{call rexdb_test_proc_in_out(?,?)}";

		Ps ps = new Ps();
		ps.add(200000);
		ps.addOutInt("major");
		
		RMap result = DB.call(sql, ps);
		if(result.getInt("major") != 10)
			throw new Exception("call seems didn't work well.");
	}

	/**
	 * 即是输入参数，也是输出
	 */
	public void testCallInoutParamter() throws Exception {
		String sql = "{call rexdb_test_proc_inout(?)}";

		// 输出参数按照序号命名
		Ps ps = new Ps();
		ps.addInOut("count", 10);

		RMap result = DB.call(sql, ps);
		if(result.getInt("count") != 20)
			throw new Exception("call seems didn't work well.");
	}

	/**
	 * 带有返回值
	 */
	public void testCallReturnParamter() throws Exception {
		String sql = "{call rexdb_test_proc_return()}";

		RMap result = DB.call(sql);
		List<RMap> return1 = result.getList("return_1");
		if(return1 ==null || return1.size() != 1 || return1.get(0).getInt("c") != 10)
			throw new Exception("call seems didn't work well.");
	}

	/**
	 * 返回值是ResultSet
	 */
	public void testCallReturnRsParamter() throws Exception {
		String sql = "{call rexdb_test_proc_return_rs()}";

		RMap result = DB.call(sql);
		List<RMap> return1 = result.getList("return_1");
		List<RMap> return2 = result.getList("return_2");
		
		if(return1 == null || return1.size() != 5 || return2 == null || return2.size() != 5)
			throw new Exception("call seems didn't work well.");
	}


	@Override
	public String getName() {
		return "DB.call";
	}
}
