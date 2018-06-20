package daoClasses;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.support.TransactionTemplate;

public abstract class MySqlDao {

	protected static final String url = "jdbc:mysql://localhost:3306/sistema_academico?autoReconnect=true&useSSL=false";
	protected static final String user = "root";
	protected static final String password = "1234";
	private static final String driverClassName = "com.mysql.cj.jdbc.Driver";

	private DataSource dataSource;
	protected JdbcTemplate jdbcTemplate;
	public TransactionTemplate transactionTemplate;

	public MySqlDao() {
		dataSource = getDataSource();
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		jdbcTemplate = new JdbcTemplate(transactionManager.getDataSource());
		transactionTemplate = new TransactionTemplate(transactionManager);
	}

	private static DriverManagerDataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		return dataSource;
	}

}
