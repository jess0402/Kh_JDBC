package member.model.service;

import java.sql.Connection;
import java.util.List;

import static common.JdbcTemplate.*;

import common.JdbcTemplate;
import member.model.dao.MemberDao;
import member.model.vo.Member;

/**
 * service 클래스
 * ---------------------------------------- Service 시작
 * 1. 드라이버 클래스 등록
 * 2. Connection 객체 생성(setAutoCommit(false))
 * ---------------------------------------- Dao 시작
 * 		3. PreparedStatement 객체 생성(미완성 sql 값대입)
 * 		4. 실행 (DQL인 경우: 및 ResultSet처리)
 * 		5. 자원반납(pstmt, rset)
 * ---------------------------------------- Dao 끝
 * 6. 트랜잭션 처리
 * 7. 자원반납 (conn) 
 * ---------------------------------------- Service 끝
 */
public class MemberService {
	
	private MemberDao memberDao = new MemberDao();

	public int insertMember(Member member) {		
		int result = 0;
		// 1. Connection 생성하는 메서드 불러서 Connection 가져오기
		Connection conn = JdbcTemplate.getConnection();
		try {
			// 2. dao 요청
			result = memberDao.insertMember(conn, member);
			
			// 3. 트랜잭션 처리
			commit(conn);
			// import static common.JdbcTemplate.*을 했기 때문에 
			// JdbcTemplate.commit(conn); <- 이렇게 적지 않아도 됨. 
			
		} catch (Exception e) {
			rollback(conn);
//			e.printStackTrace();
			throw e;
		} finally {
			// 4. 자원반납 
			close(conn);  
			// import static common.JdbcTemplate.*을 했기 때문에 
			// JdbcTemplate.close(conn); <- 이렇게 적지 않아도 됨. 			
		}
		return result;
	}
	
	public List<Member> findMemberByName(String name) {
		// 1. Conncetion 생성
		Connection conn = getConnection();  // static field나 static method는 이탤릭채로 나옴
		// 2. dao 요청
		List<Member> list = memberDao.findMemberByName(conn, name);
		// 3. 자원반납
		close(conn);
		
		return list;
	}

	public Member selectOne(String id) {
		// 1. Connection 객체 생성
		Connection conn = getConnection();
		
		// 2. dao 요청
		Member member = memberDao.selectOne(conn, id);
		
		// 3. 자원반납
		close(conn);
		
		return member;
	}

	public List<Member> selectAll() {
		
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. dao 요청
		List<Member> list = memberDao.selectAll(conn);
		
		// 3. 자원반납
		close(conn);

		return list;
	}

	public int deleteMember(String id) {
		int result = 0;
		// 1. Connection 객체 생성
		Connection conn = getConnection();

		try {			
			// 2. dao 요청
			result = memberDao.deleteMember(conn, id);
			// 3-1. 트랜잭션 처리
			commit(conn);
		} catch (Exception e) {
//			e.printStackTrace();
			// 3-2. 트랜잭션 처리
			rollback(conn);
			throw e; 
		} finally {
			// 4. 자원 반납
			close(conn);
		}
		return result;
	}
	
	public int updateMember(String id, String colName, Object newValue) {
		int result = 0;
		
		// 1. Connection 객체 생성
		Connection conn = getConnection();

		try {
			result = memberDao.updateMember(conn, id, colName, newValue);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e; // controller에서 분기처리 할 수 있도록 예외를 controller로 다시 던져줌
		} finally {
			close(conn);			
		}
		return result;
	}
	
//	public int insertMember(Member member) {
//		Connection conn = null;
//		int result = 0;
//		try {
//			// 1. driver class 등록
//			Class.forName(driverClass);
//			
//			// 2. Connection 생성(setAutoCommit(false))
//			conn = DriverManager.getConnection(url, user, password);
//			conn.setAutoCommit(false);  // 내가 직접 commit 하고 rollback할테니, 오토커밋을 false로 해라
//			
//			// 3. dao 요청 및 리턴
//			result = memberDao.insertMember(conn, member);
//			
//			// 4. 트랜잭션 처리
//			conn.commit();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		} finally {
//			// 5. 자원반납
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		
//		return result;
//	}
	
}
