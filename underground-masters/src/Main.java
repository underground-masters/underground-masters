import java.sql.Connection;
import java.sql.SQLException;

import util.DBUtil;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection connection =DBUtil.getConnection();
		System.out.println("연결성공!" + connection);
	}

}
