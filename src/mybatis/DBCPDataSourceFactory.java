package mybatis;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;

public class DBCPDataSourceFactory implements DataSourceFactory {
	
    private BasicDataSource datasource = null;
    
    public DBCPDataSourceFactory(){
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
        datasource.setDefaultAutoCommit( ps.getProperty("defaultautocommit","0").equals("1") );
        datasource.setInitialSize( Integer.parseInt(ps.getProperty("initialsize","2")) );
        datasource.setMaxActive( Integer.parseInt(ps.getProperty("maxactive","20")));
        datasource.setMaxIdle( Integer.parseInt(ps.getProperty("maxidle","0")));
        datasource.setMaxWait( Long.parseLong(ps.getProperty("maxwait","0")));        
    }
}
