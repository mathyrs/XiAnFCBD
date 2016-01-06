package junit.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.inspur.util.DBTools;

public class ResultSetTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Connection conn = DBTools.getConnection();
			Statement st = conn.createStatement();
			String sql = "select * from TRight;";
			ResultSet rs = st.executeQuery(sql);
			rs.absolute(4);
			System.out.println(rs.getString(2));
			rs.next();
			System.out.println(rs.getString(2));
			
			DBTools.release(conn, st, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
