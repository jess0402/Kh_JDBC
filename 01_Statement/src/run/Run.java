package run;

import member.view.MemberMenu;

/**
 * main메서드를 가진 실행클래스 
 *
 */
public class Run {

	public static void main(String[] args) {
		new MemberMenu().mainMenu();
		System.out.println("----- 프로그램을 종료합니다.-----");
	}

}
