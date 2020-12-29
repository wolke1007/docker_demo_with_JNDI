package com.mitake.java.docker_demo_project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.stereotype.Repository;

@Repository
public class DemoDao {

	final JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
	private DataSource dataSource;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void setDataSource() {
		dataSourceLookup.setResourceRef(true);
		try {
			this.dataSource = dataSourceLookup.getDataSource("jdbc/test");
	    } catch (DataSourceLookupFailureException e) {
	        log.error("DataSource not found.");
	    }
	}

	public int insert(String value) {
		setDataSource();
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			result = stmt.executeUpdate("INSERT INTO NewTable (Column1) VALUES('" + value + "')");
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	public String find() {
		setDataSource();
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		log.info("test");
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				    ResultSet.CONCUR_READ_ONLY);
			set = stmt.executeQuery("SELECT Column1 FROM NewTable");
			set.last();
			log.info("test1");
			return set.getString("Column1");
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("test2");
			return null;
		} finally {
			try {
				stmt.close();
				conn.close();
				set.close();
				log.info("test3");
			} catch (SQLException e) {
				e.printStackTrace();
				log.info("test4");
				return null;
			}
		}
	}
}
