package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;
import util.ScanUtil;

public class BoardDao {

	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "PC20";
	String password = "java";

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	private BoardDao() {
	}

	private static BoardDao instance;

	public static BoardDao getInstance() {
		if (instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();

	public List<Map<String, Object>> selectBoardList() {
		String sql = "SELECT A.BOARD_NO, A.TITLE, A.CONTENT, B.USER_NAME, A.REG_DATE " + "FROM TB_JDBC_BOARD A "
				+ "LEFT OUTER JOIN TB_JDBC_USER B " + "ON A.USER_ID = B.USER_ID " + "ORDER BY A.BOARD_NO ASC";

		return jdbc.selectList(sql);
	}

	public void selectBoard() {
		System.out.print("조회할 게시물 번호>");
		int boardNo = ScanUtil.nextInt();

		try {
			con = DriverManager.getConnection(url, user, password);

			String sql = "select board_no, title, content, user_id, reg_date " + "from tb_jdbc_board "
					+ "where board_no = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, boardNo);
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println("번호\t : " + rs.getInt("board_no"));
				System.out.println("작성자\t : " + rs.getString("user_id"));
				System.out.println("작성일\t : " + rs.getString("reg_date"));
				System.out.println("제목\t : " + rs.getString("title"));
				System.out.println("내용\t : " + rs.getString("content"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}

		System.out.println("1.수정\t2.삭제\t3.목록");
		System.out.print("입력>");
		int input = ScanUtil.nextInt();

		switch (input) {
		case 1: // 수정
			updateBoard(boardNo);
			break;

		case 2: // 삭제
			deleteBoard(boardNo);
			break;
		}
	}

	public void updateBoard(int boardNo) {
		System.out.print("제목>");
		String title = ScanUtil.nextLine();
		System.out.print("내용>");
		String content = ScanUtil.nextLine();

		try {
			con = DriverManager.getConnection(url, user, password);

			String sql = "update tb_jdbc_board " + "set title = ? " + ", content = ? " + "where board_no = ?";

			ps = con.prepareStatement(sql);
			ps.setString(1, title);
			ps.setString(2, content);
			ps.setInt(3, boardNo);

			int result = ps.executeUpdate();
			if (0 < result) {
				System.out.println("수정이 완료되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	public void deleteBoard(int boardNo) {
		try {
			con = DriverManager.getConnection(url, user, password);

			String sql = "delete from tb_jdbc_board " + "where board_no = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, boardNo);

			int result = ps.executeUpdate();
			if (0 < result) {
				System.out.println("삭제가 완료되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	public void insertBoard() {
		System.out.print("제목>");
		String title = ScanUtil.nextLine();
		System.out.print("내용>");
		String content = ScanUtil.nextLine();
		String userId = (String) Controller.LoginUser.get("USER_ID");

		try {
			con = DriverManager.getConnection(url, user, password);

			String sql = "insert into tb_jdbc_board values(" + "(select nvl(max(board_no), 0) + 1 from tb_jdbc_board)"
					+ ", ?, ?, ?, sysdate" + ")";
			ps = con.prepareStatement(sql);
			ps.setString(1, title);
			ps.setString(2, content);
			ps.setString(3, userId);

			int result = ps.executeUpdate();
			if (0 < result) {
				System.out.println("등록이 완료되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

}
