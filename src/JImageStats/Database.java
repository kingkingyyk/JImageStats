package JImageStats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Database {
	private static int dbCount=0;
	private int dbID;
	private LinkedList<Thread> threadQueue;
	
	
	public Database () {
		this.dbID=Database.dbCount++;
		this.threadQueue=new LinkedList<Thread>();
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
	
	public void getLock () {
		this.threadQueue.add(Thread.currentThread());
		if (this.threadQueue.size()>1) {
			while (true) {
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}
	
	public void releaseLock () {
		this.threadQueue.removeFirst();
		if (this.threadQueue.peekFirst()!=null) {
			this.threadQueue.peekFirst().interrupt();
		}
	}
	
	public void destroy () {
		this.executeSQL("SHUTDOWN");
	}
}
