package member.controller;

import java.util.List;

import member.model.dao.MemberDao;
import member.model.vo.Member;

/**
 * controller 클래스
 * 	- 신호등 역할
 * 	- mvc 흐름에서 가장 중심이 되는 클래스
 * 	- service 단에 업무요청
 * @author jes
 *
 */
public class MemberController {

	private MemberDao memberDao = new MemberDao();
	/**
	 * 사용자의 회원가입 요청을 처리하는 메서드 
	 * 1. dao에 회원가입 요청 후 int 반환
	 * 2. menu에 int반환
	 * @param newMember
	 * @return
	 */
	public int insertMember(Member newMember) {
		int result = memberDao.insertMember(newMember);
		System.out.println("result@MemberController = " + result);
		return result;
	}
	
	public List<Member> selectAll() {
		List<Member> list = memberDao.selectAll();
		return list;
	}

	public Member selectOne(String id) {
		Member member = memberDao.selectOne(id);
		return member;
	}

	public List<Member> searchName(String name) {
		List<Member> list = memberDao.searchName(name);
		return list;
	}

	public int updateMember(Member changeMember) {
		int result = memberDao.updateMember(changeMember);
		return result;
	}

	public int deleteMember(Member deleteMember) {
		int result = memberDao.deleteMember(deleteMember);
		return result;
	}

}
