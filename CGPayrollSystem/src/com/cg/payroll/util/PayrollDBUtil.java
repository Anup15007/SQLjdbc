package com.cg.payroll.util;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.cg.payroll.beans.Associate;

public class PayrollDBUtil {
	private static Connection con=null;
	public static Connection getDBConnection() {
		if(con==null) {
			Properties payrollProperties=new Properties();
			try {
				payrollProperties.load(new FileInputStream(new File(".//resources//payroll.properties")));
				String driver=payrollProperties.getProperty("driver");
				String url=payrollProperties.getProperty("url");
				String user=payrollProperties.getProperty("user");
				String password=payrollProperties.getProperty("password");
				//first load driver
				Class.forName(driver);
				//2nd open connection
				con=DriverManager.getConnection(url,user,password);
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return con;
	}
}
