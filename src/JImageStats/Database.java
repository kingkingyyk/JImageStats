package JImageStats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private static int dbCount=0;
	private int dbID;
	
	public Database () {
		this.dbID=Database.dbCount++;
	}

	public ResultSet executeSQL (String sqlStatement) {
		try {
			Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:"+this.dbID, "sa", "");
			if (c!=null) {
				Statement st=c.createStatement();
				ResultSet rs=st.executeQuery(sqlStatement);
				return rs;
			}
		} catch (SQLException e) {
			System.out.println(sqlStatement);
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}
	
	public void destroy () {
		this.executeSQL("SHUTDOWN");
	}
}
