package member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static common.JdbcTemplate.*;

import member.model.vo.Member;

public class MemberDao {

	public int insertMember(Connection conn, Member member) {
		String sql = "insert into member "
					+ "values(?, ?, ?, ?, ?, ?, default)";
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
		String sql = "select * from member where name like ?";
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
			e.printStackTrace();
		} finally {
			// 4. 자원반납(pstmt, rset)
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public Member selectOne(Connection conn, String id) {
		String sql = "select * "
					+ "from member "
					+ "where id = ?";
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
			e.printStackTrace();
		} finally {
			// 3. 자원반납(pstmt, rset)
			close(rset);
			close(pstmt);
		}
		
		return member;
	}

	public List<Member> selectAll(Connection conn) {
		String sql = "select * from member order by reg_date desc";
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
			e.printStackTrace();
		} finally {
			// 4. 자원반납
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int deleteMember(Connection conn, String id) {
		String sql = "delete from member where id = ?";
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			// 1. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			// 2. 실행	
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 3. 자원반납
			close(pstmt);
		}
		return result;
	}

	public int updateMemberName(Connection conn, Member member) {
		String sql = "update member set name = ? where id = ?";
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			// 1. PreparedStatement 객체 생성 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getId());
			// 2. 실행
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 3. 자원 반납
			close(pstmt);
		}
		 
		
		return result;
	}

	public int updateMemberBirthday(Connection conn, Member member) {
		String sql = "update member set birthday = ? where id = ?";
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			// 1. PreparedStatement 객체 생성 
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, member.getBirthday());
			pstmt.setString(2, member.getId());
			// 2. 실행
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 3. 자원 반납
			close(pstmt);
		}
		
		return result;
	}

	public int updateMemberEmail(Connection conn, Member member) {
		String sql = "update member set email = ? where id = ?";
		PreparedStatement pstmt = null;
		int result = 0;
	
		try {
			// 1. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getId());
			// 2. 실행
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 3. 자원 반납
			close(pstmt);
		}
		
		return result ;
	}

	public int updateMemberAddress(Connection conn, Member member) {
		String sql = "update member set address = ? where id = ?";
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			// 1. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getAddress());
			pstmt.setString(2, member.getId());
			// 2. 실행
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 3. 자원 반납
			close(pstmt);			
		}	
		return result;
	}

}
