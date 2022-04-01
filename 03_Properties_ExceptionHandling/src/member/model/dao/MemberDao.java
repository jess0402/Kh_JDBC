package member.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static common.JdbcTemplate.*;

import member.model.exception.MemberException;
import member.model.vo.Member;

public class MemberDao {

	Properties prop = new Properties();
	
	public MemberDao() {
		// member.query.properties의 내용 불러오기
		try {
			prop.load(new FileReader("resources/member.query.properties"));
			System.out.println("> member.query.properties 로드 완료!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public int insertMember(Connection conn, Member member) {
		String sql = prop.getProperty("insertMember");
		int result = 0;
		PreparedStatement pstmt = null;

		try {
			// 1. PreparedStatement 생성 (미완성 sql & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getAddress());
			// 2. 실행
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// 예외를 잡고 다시 던진다.
			// 대신 체크드예외는 처리하기가 불편하니까 언체크드 예외로 변환해서 던진다. 
			throw new RuntimeException(e);  
			// 원래 발생한 예외를 감싸서(전환) 다시 던지기
			// -> service에서 트랜잭션 처리를 할 수 있도록. 
		} finally {
			// 3. 자원반납(pstmt) - conn 반환하지 XX (트랜잭션처리 전이기 때문)
			close(pstmt);
		}
		return result;
	}

	public List<Member> findMemberByName(Connection conn, String name) {
		String sql = prop.getProperty("findMemberByName");
		List<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			// 1. PreparedStatement 객체 생성(미완성 sql 전달 및 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + name + "%"); // where name like '%길동%'
			
			// 2. 실행
			rset = pstmt.executeQuery();
			
			// 3. ResultSet처리 -> 한 행 한 행을 Member객체로 변환 
			while(rset.next()) {
				Member member = new Member();
				member.setId(rset.getString("id"));
				member.setName(rset.getString("name"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setAddress(rset.getString("address"));
				member.setRegDate(rset.getTimestamp("reg_date"));
				
				list.add(member);
			}
			
		} catch (SQLException e) {
			throw new MemberException("findMemberByName 조회오류", e);
		} finally {
			// 4. 자원반납(pstmt, rset)
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public Member selectOne(Connection conn, String id) {
		String sql = prop.getProperty("selectOne");
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;		
		
		try {
			// 1. PreparedStatement 객체 생성(미완성 sql 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 2. 실행 (DQL인 경우: 및 ResultSet처리)
			rset = pstmt.executeQuery();
			while(rset.next()) {
				member = new Member();
				member.setId(rset.getString("id"));
				member.setName(rset.getString("name"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setAddress(rset.getString("address"));
				member.setRegDate(rset.getTimestamp("reg_date"));
			}
			
		} catch (SQLException e) {
			throw new MemberException("selectOne 조회오류", e);
		} finally {
			// 3. 자원반납(pstmt, rset)
			close(rset);
			close(pstmt);
		}
		
		return member;
	}

	public List<Member> selectAll(Connection conn) {
		String sql = prop.getProperty("selectAll");
		List<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			// 1. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// 2. 실행
			rset = pstmt.executeQuery();
			// 3. ResultSet 처리
			while(rset.next()) {
				Member member = new Member();
				member.setId(rset.getString("id"));
				member.setName(rset.getString("name"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setAddress(rset.getString("address"));
				member.setRegDate(rset.getTimestamp("reg_date"));
				
				list.add(member);
			}
		} catch (SQLException e) {	
			throw new MemberException("selectAll 조회오류", e);
		} finally {
			// 4. 자원반납
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int deleteMember(Connection conn, String id) {
		String sql = prop.getProperty("deleteMember");
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			// 1. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			// 2. 실행	
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("deleMember 예외", e);
		} finally {
			// 3. 자원반납
			close(pstmt);
		}
		return result;
	}

	/**
	 * 컬럼명을 동적으로 변경하는 기능은 PreparedStatement에서 지원하지 않는다.
	 * 
	 * @param conn
	 * @param id
	 * @param colName
	 * @param newValue
	 * @return
	 */
	public int updateMember(Connection conn, String id, String colName, Object newValue) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateMember");
			sql = sql.replace("#", colName);
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, newValue); // 상응하는 db타입값으로 자동 대입
			// setObejct로는 String이든 Date형식이든 다 받을 수 있음
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("updateMember 예외", e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}
