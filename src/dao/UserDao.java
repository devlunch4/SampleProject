package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class UserDao {

	private UserDao() {
	}

	private static UserDao instance;

	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}
		return instance;
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();

	// 사용자 추가(회원등록)
	public int insertUser(Map<String, Object> p) {
		String sql = "INSERT INTO TB_JDBC_USER VALUES (?,?,?)";

		List<Object> param = new ArrayList<>();
		param.add(p.get("USER_ID"));
		param.add(p.get("PASSWORD"));
		param.add(p.get("USER_NAME"));

		// jdbc에 해당 쿼리 전송하여 회원가입 정보 테이블에 작성 그리고 콘트롤러 조인메소드 완료 브레이크
		return jdbc.update(sql, param);
	}

	// 로그인 조회시 사용자 조회
	public Map<String, Object> selectUser(String userId, String password) {
		String sql = "SELECT USER_ID, USER_NAME FROM TB_JDBC_USER " + "WHERE USER_ID = ? " + "AND PASSWORD = ?";
		List<Object> param = new ArrayList<>();
		param.add(userId);
		param.add(password);

		// 해당 값 출력을 위해 jdbc 사용
		return jdbc.selectOne(sql, param);
	}
}
