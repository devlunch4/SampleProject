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

		int result = userDao.insertUser(param);

		if (0 < result) {
			System.out.println("회원가입 성공");
		} else {
			System.out.println("회원가입 실패");
		}
		return View.HOME;
	}

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
		} else {
			System.out.println("로그인 성공");
			// 컨트롤러에 해당 사용자 정보 저장.
			Controller.LoginUser = user;

			return View.BOARD_LIST;
		}
		return View.LOGIN;

	}
}
