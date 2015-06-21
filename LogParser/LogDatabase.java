import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class LogDatabase {
	
	static Connection conn;
	
	public LogDatabase(){
	}
	
	public void executeQuery(String query){
		commit(query);
	}

	private void commit(String query) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/summerpracticum", "root", "cs5389!SummerPracticum");

			Statement stmt = conn.createStatement();
			stmt.execute(query);
			stmt.close();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
}
