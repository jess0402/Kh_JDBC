package common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * jdbc api 사용간에 공통된 처리를 담당하는 static method 모음 클래스
 *
 */
public class JdbcTemplate {
	
    static String driverClass; // 드라이버클래스명
    static String url; // 접속할 db서버주소 (db접속프로토콜@ip:port:sid)
    static String user;
    static String password;
    
	static {
		// datasource.properties에서 설정정보 가져오기(최초 1회)
		Properties prop = new Properties();
		try {
			prop.load(new FileReader("resources/datasource.properties"));
			driverClass = prop.getProperty("driverClass");
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			System.out.println("> 설정정보 로드 완료");
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		// 프로그램 구동시 최초 1회 사용
		
		try {
			// 1. driver class 등록 - 프로그램 실행시 최초 1회만!
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// 2. Connection 생성(url, user, password) - setAutoCommit(false)
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);  // DQL일때도 이렇게 처리해도 상관없음 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null && !(conn.isClosed()))
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null && !(pstmt.isClosed()))
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !(rset.isClosed()))
				rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void commit(Connection conn) {
		try {
			if(conn != null && !(conn.isClosed()))
				conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !(conn.isClosed()))
				conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
