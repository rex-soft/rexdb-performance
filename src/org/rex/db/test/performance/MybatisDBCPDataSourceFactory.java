package org.rex.db.test.performance;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;

public class MybatisDBCPDataSourceFactory implements DataSourceFactory {
	
    private BasicDataSource datasource = null;
    
    public MybatisDBCPDataSourceFactory(){
        this.datasource = new BasicDataSource();
    }
    
    public DataSource getDataSource() {
        return datasource;
    }

    public void setProperties(Properties ps) {
        datasource.setDriverClassName( ps.getProperty("driverclassname"));
        datasource.setUsername( ps.getProperty("username"));
        datasource.setUrl( ps.getProperty("url"));
        datasource.setPassword( ps.getProperty("password"));
    }
}
