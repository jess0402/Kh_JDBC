package member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import member.model.vo.Member;

public class MemberDao {
	
	String driverClass = "oracle.jdbc.OracleDriver"; // 드라이버 클래스명
	String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속할 db서버주소 db접속프로토콜@ip:port:sid
	String user = "student";
	String password = "student";

	/**
	 * db에 접속, 쿼리를 실행하는 메서드
	 * 
	 * @param newMember
	 * @return
	 */
	public int insertMember(Member newMember) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
//		Statement stmt = null;
//		String sql = "insert into member values('yoogs', '유관순', 'F', to_date('19990909'), 'yoogs@gmail.com', '서울시 강남구', default)";
		// Statement는 보안적으로나 성능적으로나 떨어져서 안씀. 대신 PrepareStatement를 사용한다.
		String sql = "insert into member values(?, ?, ?, ?, ?, ?, default)"; // 미완성 sql문
		
		try {
			// 1. driver class 등록
			Class.forName(driverClass);
			
			// 2. Connection 객체 생성(setAutoCommit(false))
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			
			// 3. PreparedStatement 객체 생성(미완성 sql전달 & 값대입)
			// 물음표에 실제 값 쓰기작업 
			pstmt = conn.prepareStatement(sql);
			// db의 인덱스는 항상 1부터 시작.
			pstmt.setString(1, newMember.getId());  // 첫 번째 물음표
			pstmt.setString(2, newMember.getName());  // 두 번째 물음표
			pstmt.setString(3, newMember.getGender()); // 세 번째 물음표
			pstmt.setDate(4, newMember.getBirthday()); // 네 번째 물음표
			pstmt.setString(5, newMember.getEmail()); // 다섯 번째 물음표
			pstmt.setString(6, newMember.getAddress()); // 여섯 번째 물음표

			// 4. Statement 실행 및 int(처리행수) 반환
			result = pstmt.executeUpdate();
			// dml이라면 insert든 update든 delete든 모두 executeUpdate()사용
			// select일때만 executeQuery()
			
			// 5.1 트랜잭션 처리
			conn.commit();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			
			// 5.2 트랜잭션 처리
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			// 6. 자원반납후 종료
			// 객체생성의 역순으로 종료. 
			// 즉 Connection만들고 PreparedStatement 만들었기 때문에,
			// PreparedStatement를 먼저 반납하고 그 뒤에 Connection을 반납한다.
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/**
	 * member테이블의 모든 행을 리턴하는 메서드
	 * @return
	 */
	public List<Member> selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member order by reg_date desc";
		List<Member> list = new ArrayList<>();
		
		try {
			// 1. driver class 등록
			Class.forName(driverClass);

			// 2. Connection 객체 생성
			conn = DriverManager.getConnection(url, user, password);
			
			// 3. PreparedStatement 객체 생성(미완성 sql 전달 & 값 대입) 
			pstmt = conn.prepareStatement(sql);
			
			// 4. PreparedStatement 실행 및 ResultSet 반환
			// PrepareStatement는 executeQuery() <- 인자 없는 것! 
			rset = pstmt.executeQuery();
			
			// 5. ResultSet을 한 행식 fetch. Member객체로 전환 후 list 추가
			// 한 행 씩 접근한다!!
			while(rset.next()) {
				String id = rset.getString("id");
				String name = rset.getString("name");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String address = rset.getString("address");
				Timestamp regDate = rset.getTimestamp("reg_date");				
				Member member = new Member(id, name, gender, birthday, email, address, regDate);
				// 리스트에 추가
				list.add(member);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 자원반납
			try {
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return list;
	}

	public Member selectOne(String id) {
		String sql = "select * from member where id = ?";
//		String sql = "select * from member where id = " + id;
		// 위 두 번째 sql은 가능은 하지만, db입장에서는 caching이 되지 않아서 좋지 않음. 
		// 하지만 첫 번째 sql은 재사용이 가능하므로 첫번째처럼 쓰기
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member member = null;
		
		// jdbc DQL 레시피
        try {
        	// 1. driver class 등록
			Class.forName(driverClass);
			// 2. Connection 객체 생성
			conn = DriverManager.getConnection(url, user, password);
			// 3. PreparedStatement 객체 생성(미완성 sql전달 & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); // sql문의 첫 번째 물음표에 전달받은 id를 대입해라
			// 4. PreparedStatement 실행 및 ResultSet 반환
			rset = pstmt.executeQuery();
			// 5. ResultSet 한행씩 fetch. Member객체 전환 후 list에 추가
			while(rset.next()) {
				String name = rset.getString("name");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String address = rset.getString("address");
				Timestamp regDate = rset.getTimestamp("reg_date");				
				member = new Member(id, name, gender, birthday, email, address, regDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 자원반납
			try {
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}        
		return member;
	}

	public List<Member> searchName(String name) {
		String sql = "select * from member where name like '%'||?||'%'";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member member = null;
		List<Member> list = new ArrayList<>();
		
		try {
			// 1. driver class 등록
			Class.forName(driverClass);
			// 2. Connection 객체 생성
			conn = DriverManager.getConnection(url, user, password);
			// 3. PreparedStatement 객체 생성(미완성 sql전달 & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			// 4. PreparedStatement 실행 및 ResultSet 반환
			rset = pstmt.executeQuery();
			// 5. ResultSet 한행씩 fetch. Member객체 전환 후 list에 추가
			while(rset.next()) {
				String id = rset.getString("id");
				String fullname = rset.getString("name");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String address = rset.getString("address");
				Timestamp regDate = rset.getTimestamp("reg_date");
				member = new Member(id, fullname, gender, birthday, email, address, regDate);
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 자원반납
			try {
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

		return list;
	}

	public int updateMember(Member changeMember) {
		
		String sql = "update member set name = ?, birthday = ?, email = ?, address = ? where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			// 1. driver class 등록
			Class.forName(driverClass);
			// 2. Connection 객체 생성(setAutoCommit(false))
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			
			// 3. PreparedStatement 객체 생성(미완성 sql전달 & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, changeMember.getName());
			pstmt.setDate(2, changeMember.getBirthday());
			pstmt.setString(3, changeMember.getEmail());
			pstmt.setString(4, changeMember.getAddress());
			pstmt.setString(5, changeMember.getId());
			
			// 4. PreparedStatement실행 및 int(처리행수) 반환
			result = pstmt.executeUpdate();
			
			// 5-1. 트랜잭션처리
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			// 6. 자원반납
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}		
		return result;
	}

	public int deleteMember(Member deleteMember) {
		String sql = "delete from member where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			// 1. driver class 등록
			Class.forName(driverClass);
			// 2. Connection 객체 생성(setAutoCommit(false))
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			// 3. PreparedStatement 객체 생성(미완성 sql전달 & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deleteMember.getId());
			
			// 4. Statement실행 및 int(처리행수) 반환
			result = pstmt.executeUpdate();
			
			// 5-1. 트랜잭션처리
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			// 6. 자원반납
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}

}
