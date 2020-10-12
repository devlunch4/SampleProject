package service;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import dao.UserDao;
import util.ScanUtil;
import util.View;

public class UserService {

	private UserService() {
	}

	private static UserService instance;

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	private UserDao userDao = UserDao.getInstance();

	// 회원가입 메소드
	public int join() {
		System.out.println("=======회원가입========");
		System.out.println("아이디>>");
		String userId = ScanUtil.nextLine();
		System.out.println("비밀번호>>");
		String password = ScanUtil.nextLine();
		System.out.println("이름>>");
		String username = ScanUtil.nextLine();

		// 아이디 중복 확인 생략
		// 비미번호 확인 생략
		// 정규표현식(유효성검사)생략

		Map<String, Object> param = new HashMap<>();
		param.put("USER_ID", userId);
		param.put("PASSWORD", password);
		param.put("USER_NAME", username);

		// 입력값을 입력 메소드 클래스로 지정
		int result = userDao.insertUser(param);

		if (0 < result) {
			System.out.println("회원가입 성공");
		} else {
			System.out.println("회원가입 실패");
		}
		return View.HOME;
	}

	// 회원가입으로 형성된 사용자 정보를 통한 로그인 메소드
	public int login() {
		System.out.println("========로그인========");
		System.out.println("아이디>");
		String userId = ScanUtil.nextLine();
		System.out.println("비밀번호>");
		String password = ScanUtil.nextLine();

		// db 테이블과 비교하기
		// user한명 조회
		Map<String, Object> user = userDao.selectUser(userId, password);

		if (user == null) {
			System.out.println("아이디 혹은 비밀번호를 잘못 입력했습니다");
			System.out.println("홈화면으로 이동합니다.");
		} else {
			System.out.println("로그인 성공");
			// 컨트롤러에 해당 사용자 정보 저장.
			Controller.LoginUser = user;

			return View.BOARD_LIST;
			// 게시판 리스트를 보기위해 컨트롤러 클래스에서 게시판리스트 호출을 위한 리턴
		}
		return View.HOME;

	}
}
