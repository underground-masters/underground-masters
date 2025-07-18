package util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import com.sun.rowset.CachedRowSetImpl;

public class DBUtil {
	private static final Properties prop = new Properties();

	static {
		try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("resource/db.properties")) {
			if (in == null)
				throw new IllegalStateException("DB.properties 파일을 찾을 수 없습니다.");
			prop.load(in);
		} catch (Exception e) {
			throw new RuntimeException("DB.properties 파일을 로드하는데 실패했습니다.", e);
		}
	}

	// DB 정보는 properties 파일에서 읽도록 변경
	private static final String JDBC_URL = prop.getProperty("db.url");
	private static final String DB_USER = prop.getProperty("db.user");
	private static final String DB_PASSWORD = prop.getProperty("db.password");
	private static final String JDBC_DRIVER = prop.getProperty("db.driver");

	// 커넥션을 사용할 때마다 얻는다
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		try {
			Class.forName(JDBC_DRIVER); // 0. oracle jdbc 드라이버 연결
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("JDBC Driver not found.", e);
		}
		return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
	}

	/**
	 * SELECT 쿼리 실행, CachedRowSetImpl로 리턴 (리소스 안전하게 반납)
	 */
	public static ResultSet dbExecuteQuery(String queryStmt, Object... params)
			throws SQLException, ClassNotFoundException {

		CachedRowSetImpl crs = new CachedRowSetImpl();

		try (Connection conn = DriverManager.getConnection(JDBC_URL);
				PreparedStatement pstmt = conn.prepareStatement(queryStmt);) {
			// 파라미터 바인딩
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}

			// ResultSet 데이터를 CachedRowSetImpl에 복사
			try (ResultSet rs = pstmt.executeQuery()) {
				crs.populate(rs);
			}
		}

		return crs; // Connection, Statement, ResultSet 다 닫혀도 CachedRowSetImpl은 안전
	}

	/**
	 * INSERT/UPDATE/DELETE 쿼리
	 */
	public static void dbExecuteUpdate(String procCall, Object... params) throws SQLException, ClassNotFoundException {
		try (Connection conn = getConnection(); CallableStatement cstmt = conn.prepareCall(procCall);) {
			// 파라미터 바인딩
			for (int i = 0; i < params.length; i++) {
				cstmt.setObject(i + 1, params[i]);
			}

			// 성공 여부
			int cnt = cstmt.executeUpdate();
			if (cnt > 0) {
				System.out.println(cnt + "개 행이 성공했습니다.");
			}
		}
	}
}
