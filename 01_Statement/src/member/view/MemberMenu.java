package member.view;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import member.controller.MemberController;
import member.model.vo.Member;

/**
 * view패키지 클래스 
 * 	- 사용자가 보게 될 화면을 담당하는 클래스
 * 	- 메뉴, 사용자 입력값처리, 요청한 데이터 출력
 */
public class MemberMenu {
	private Scanner sc = new Scanner(System.in);
	private MemberController controller = new MemberController();
	
	public void mainMenu() {
		String menu = "===== 회원관리 =====\n"
					+ "1. 회원 전체 조회\n"
					+ "2. 회원 아이디 조회\n"
					+ "3. 회원 이름 검색\n"
					+ "4. 회원 가입\n"
					+ "5. 회원 정보 변경\n"
					+ "6. 회원 탈퇴\n"
					+ "0. 프로그램 종료\n"
					+ "------------------\n"
					+ "선택 : ";
		
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			
			List<Member> list = null;
			Member newMember = null;
			int result = 0;
			String id = null;
			String name = null;
			Member member = null;
			Member changeMember = null;
			Member deleteMember = null;
			
			switch(choice) {
			case "1": 
				list = controller.selectAll();
				printMemberList(list);
				break;
			case "2": 
				id = inputId();
				member = controller.selectOne(id);
				printMember(member);
				break;
			case "3": 
				// 3. 이름 검색시 일부만 입력해도 검색이 되어야 한다. (like연산자)
				name = inputName();
				list = controller.searchName(name);
				printMemberList(list);
				break;
			case "4": 
				newMember = inputMember();
				result = controller.insertMember(newMember); // DML이라서 처리된 행의 수(int)가 리턴될 것. 
				System.out.println(result > 0 ? "> 회원 가입 성공!" : "회원 가입 실패!");
				break;
			case "5": 
				// 5.  정보변경은 이름, 생일, 이메일, 주소만 일괄 변경한다.
				id = inputId();
				changeMember = controller.selectOne(id);
				if(changeMember == null) {
					System.out.println("조회된 회원이 없습니다.");
					continue;
				}
				else {
					changeMember = inputUpdateMember(changeMember);
					result = controller.updateMember(changeMember);					
					System.out.println(result > 0 ? "> 회원 정보 변경 성공!" : "회원 정보 변경 실패!");
				}
				break;				
			case "6": 
				id = inputId();
				deleteMember = controller.selectOne(id);
				if(deleteMember == null) {
					System.out.println("조회된 회원이 없습니다.");
					continue;
				}
				else {
					result = controller.deleteMember(deleteMember);
					System.out.println(result > 0 ? "회원 탈퇴 성공!" : "회원 탈퇴 실패!");
				}
				break;
			case "0": return;
			default:
				System.out.println("잘못 입력하셨습니다.");
			}
		}
	}
	
	/**
	 * 멤버의 변경할 정보를 받는 메서드 
	 * @return 
	 * @return 
	 * @return
	 */
	private Member inputUpdateMember(Member changeMember) {
		System.out.println("---- 회원 정보 변경 ----");
		System.out.println(">변경할 정보 입력<");
		
		System.out.print("이름: ");
		changeMember.setName(sc.next());
		
		System.out.print("생일(예: 19990909) : ");
		String _birthday = sc.next();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date birthday = null;
		try {
			birthday = new Date(sdf.parse(_birthday).getTime());  // java.util.Date를 java.sql.Date로 변환한 것.
			changeMember.setBirthday(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.print("이메일: ");
		changeMember.setEmail(sc.next());
		sc.nextLine();
		
		System.out.print("주소: ");
		changeMember.setAddress(sc.nextLine());
		
		return changeMember;
		
	}

	/**
	 * 조회할 이름을 입력하는 메서드
	 * @return
	 */
	private String inputName() {
		System.out.println("----- 조회할 이름 입력 ------");
		System.out.print("이름 : ");
		return sc.next();
	}

	/**
	 * 회원 1명 출력 메서드
	 * 	- 조회한 id가 문제가 있으면 null 리턴, 문제가 없다면 member 객체 1개 리턴
	 * @param member
	 */
	private void printMember(Member member) {
		if(member == null) {
			System.out.println("> 조회된 회원이 없습니다.");	
		}
		else {
			System.out.println("============================");
			System.out.printf("id : %s%n", member.getId());
			System.out.printf("name : %s%n", member.getName());
			System.out.printf("gender : %s%n", member.getGender());
			System.out.printf("birthday : %s%n", member.getBirthday());
			System.out.printf("email : %s%n", member.getEmail());
			System.out.printf("address : %s%n", member.getAddress());
			System.out.printf("regDate : %s%n", member.getRegDate());
			System.out.println("---------------------------------");
			System.out.println();
		}
		
	}

	/**
	 * 조회할 id를 입력하는 메서드
	 * @return
	 */
	private String inputId() {
		System.out.println("----- 조회할 id 입력 -----");
		System.out.print("id : ");
		return sc.next();
	}

	/**
	 * 여러행 조회결과를 출력하는 메서드
	 * (list가 null인 경우 없음)
	 * - 0행
	 * - n행
	 */
	public void printMemberList(List<Member> list) {
		if(list.isEmpty()) {
			System.out.println("> 조회된 행이 없습니다.");
		}
		else {
			System.out.println("> 조회결과");
			System.out.println("=======================================================");
			System.out.printf("%15s%15s%15s%15s%15s%15s%15s%n", 
								"id", "name", "gender", "birthday", "email", "address", "regDate");
			System.out.println("------------------------------------------------------");
			for(Member member : list) {
				System.out.printf("%15s%15s%15s%15s%15s%15s%15s%n", 
								  member.getId(),
								  member.getName(),
								  member.getGender(),
								  member.getBirthday(),
								  member.getEmail(),
								  member.getAddress(),
								  member.getRegDate());
			}
			System.out.println("------------------------------------------");
			System.out.println();
		}
	}

	/**
	 * 사용자 입력값으로 member객체 생성 메서드
	 */
	private Member inputMember() {
		System.out.println("----- 새 회원 정보 입력 -----");
		System.out.print("아이디 : ");
		String id = sc.next();
		System.out.print("이름 : ");
		String name = sc.next();
		System.out.print("성별(M/F) : ");
		String gender = String.valueOf(sc.next().toUpperCase().charAt(0));
		System.out.print("생일(예: 19990909) : ");
		String _birthday = sc.next();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date birthday = null;
		try {
			birthday = new Date(sdf.parse(_birthday).getTime());  // java.util.Date를 java.sql.Date로 변환한 것.
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.print("이메일 : ");
		String email = sc.next();
		sc.nextLine(); // 개행문자 날리기
		System.out.print("주소 : ");
		String address = sc.nextLine();

		return new Member(id, name, gender, birthday, email, address, null);
		
	}

}
