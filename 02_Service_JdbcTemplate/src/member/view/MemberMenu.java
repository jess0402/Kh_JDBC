package member.view;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import member.controller.MemberController;
import member.model.vo.Member;

/**
 * view단 클래스
 * - 메뉴 노출, 사용자 입력값처리, 결과값 출력
 */
public class MemberMenu {
	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();
	
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
			
			Member member = null;
			int result = 0;
			String name = null;
			List<Member> list = null;
			String id = null;
			
			switch(choice) {
			case "1" : 
				list = memberController.selectAll();
				printMemberList(list);
				break;
			case "2" : 
				id = inputId();
				member = memberController.selectOne(id);
				printMember(member);
				break;
			case "3" : 
				name = inputMemberName();
				list = memberController.findMemberByName(name);
				printMemberList(list);				
				break;
			case "4" : 
				member = inputMember();
				result = memberController.insertMember(member);
				printResultMsg(result, "회원 가입 성공!", "회원 가입 실패!");
				break;
			case "5" : 
				id = inputId();
				member = memberController.selectOne(id);
				if(member == null) {
					System.out.println("조회된 회원이 없습니다.");
					continue;
				}
				else {
					printMember(member);
					updateMemberMenu(member);
				}
				break;
			case "6" : 
				id = inputId();
				result = memberController.deleteMember(id);
				printResultMsg(result, "회원 삭제 성공!", "회원 삭제 실패!");
				break;
			case "0" : return;
			default : 
				System.out.println("잘못 입력하셨습니다.");
			}
		}
	}
		
	private void updateMemberMenu(Member member) {
		String updateMenu = "****** 회원 정보 변경 메뉴******\n"
						  + "1. 이름 변경\n"
						  + "2. 생일 변경\n"
						  + "3. 이메일 변경\n"
						  + "4. 주소 변경\n"
						  + "9. 메인메뉴 돌아가기\n"
						  + "------------------------------\n"
						  + "> 선택: ";
		while(true) {
			System.out.print(updateMenu);
			String choice = sc.next();
			String id = null;
			Member updateMember = null;
			String name = null;
			int result = 0;
			
			switch(choice) {
			case "1" :
				System.out.print("수정할 이름을 입력하세요: ");
				member.setName(sc.next());
				result = memberController.updateMemberName(member);
				printResultMsg(result, "이름 변경 성공!", "이름 변경 실패!");
				printMember(member);
				break;
			case "2" : 
				System.out.print("수정할 생일을 입력하세요(예: 19990402): ");
				String _birthday = sc.next();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date birthday = null;
				try {
					long millis = sdf.parse(_birthday).getTime();
					birthday = new Date(millis);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				member.setBirthday(birthday);
				result = memberController.updateMemberBirthday(member);
				printResultMsg(result, "생일 변경 성공!", "생일 변경 실패");
				printMember(member);
				break;
			case "3" : 
				System.out.print("수정할 이메일을 입력하세요: ");
				member.setEmail(sc.next());
				result = memberController.updateMemberEmail(member);
				printResultMsg(result, "이메일 변경 성공!", "이메일 변경 실패");
				printMember(member);
				break;
			case "4" :
				sc.nextLine();
				System.out.print("변경할 주소를 입력하세요: ");
				member.setAddress(sc.nextLine());
				result = memberController.updateMemberAddress(member);
				printResultMsg(result, "주소 변경 성공!", "주소 변경 실패");
				printMember(member);
				break;
			case "9" : return;
			default :
				System.out.println("잘못 입력하셨습니다.");
			}
		}
		
	}

	/**
	 * id 입력받는 메서드
	 * @return
	 */
	private String inputId() {
		System.out.println("-----------------------");
		System.out.print("아이디: ");
		return sc.next();
	}

	/**
	 * 회원 1명 출력 메서드
	 * @param member
	 */
	private void printMember(Member member) {
		if(member == null) {
			System.out.println("조회된 회원이 없습니다.");
			System.out.println();
		}
		else {
			System.out.println("==========================");
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
     * 여러행 조회결과 출력 메소드
     * (list가 null인 경우 없음)
     * - 0행
     * - n행
     * @param list
     */
    public void printMemberList(List<Member> list) {
        if(list == null || list.isEmpty()) {
            System.out.println("> 조회된 행이 없습니다.");
        }
        else {
            System.out.println("> 조회결과");
            System.out.println("=====================================================");
            System.out.printf("%15s%15s%15s%15s%15s%15s%15s%n", 
                                "id", "name", "gender", "birthday", "email", "address", "regDate");
            System.out.println("-----------------------------------------------------");
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
            System.out.println("-----------------------------------------------------");
            System.out.println();
        }
    }

	/**
	 * 조회할 회원명 입력메서드
	 * @return
	 */
	private String inputMemberName() {
		System.out.println("> 이름을 입력하세요. ");
		System.out.print("이름: ");
		return sc.next();
	}

	/**
	 * 신규회원정보를 입력받는 메서드
	 * @return
	 */
	private Member inputMember() {
		Member member = new Member();
		
		System.out.println("> 신규회원정보를 입력하세요. <");
		System.out.print("아이디: ");
		member.setId(sc.next());
		System.out.print("이름: ");
		member.setName(sc.next());
		System.out.print("성별(M/F): ");
		member.setGender(String.valueOf(sc.next().toUpperCase().charAt(0)));
		System.out.print("생일(예: 19990402): ");
		String _birthday = sc.next();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date birthday = null;
		try {
			long millis = sdf.parse(_birthday).getTime();
			birthday = new Date(millis);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		member.setBirthday(birthday);

		System.out.print("이메일: ");
		member.setEmail(sc.next());
		sc.nextLine(); // 개행문자 처리 
		
		System.out.print("주소: ");
		member.setAddress(sc.nextLine());
		
		return member;
	}

	/**
	 * DML 처리 결과를 출력하는 메서드
	 * @param result
	 * @param successMsg
	 * @param failureMsg
	 */
	private void printResultMsg(int result, String successMsg, String failureMsg) {
		if(result > 0)
			System.out.println("> " + successMsg);
		else
			System.out.println("> " + failureMsg);
	}

}
