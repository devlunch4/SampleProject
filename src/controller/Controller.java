package controller;

import java.util.Map;

import service.BoardService;
import service.UserService;
import util.ScanUtil;
import util.View;

public class Controller {

	public static void main(String[] args) {

		// 발표 순서 : 조 소개 > 주제 소개 > 주제 선정 배경 > 메뉴 구조 > 시연
		// 발표인원 : 발표자1명, ppt 및 시연 도우미 1명

		// //클래스
		// 1.Controller : 화면이동
		// 2.Service : 화면기능
		// 3.Dao : 쿼리 작성 (입력을 받거나 sql db 접속 등등)

		new Controller().start();
	}

	// 유저정보를 가져오기 위한 맵
	public static Map<String, Object> LoginUser;

	private UserService userService = UserService.getInstance();
	private BoardService boardService = BoardService.getInstance();

	private void start() { // 화면이동 역할
		int view = View.HOME; // 변수 지정

		while (true) {
			switch (view) {
			case View.HOME:
				view = home(); // 호출후 홈 메소드가 실행
				break;
			case View.LOGIN: // 1번을 선택하면 다음화면은 로그인 화면 등장
				view = userService.login();
				break;
			case View.JOIN: // 로그인을 가져오면 조인이 됨.
				view = userService.join();
				break;
			case View.BOARD_LIST:
				view = boardService.boardList();
				break;
			}
		}
	}

	private int home() {
		System.out.println("-------------------------------------");
		System.out.println("1.로그인\t2.회원가입\t0.프로그램종료");
		System.out.println("-------------------------------------");
		System.out.println("번호입력>>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.LOGIN;
		case 2:
			return View.JOIN;
		case 0:
			System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);
			break;
		}
		return View.HOME;
	}
}
