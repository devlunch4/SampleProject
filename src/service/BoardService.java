package service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.BoardDao;

public class BoardService {

	private BoardService() {
	}

	private static BoardService instance;

	public static BoardService getInstance() {
		if (instance == null) {
			instance = new BoardService();
		}
		return instance;
	}

	private BoardDao boardDao = BoardDao.getInstance();
	

	public int boardList() {
		List<Map<String, Object>> boardList = boardDao.selectBoardList();
	
		System.out.println("===============");
		System.out.println("번호\t제목\t내용\t작성자\t작성일");
		System.out.println("---------------");
		for (int i = 0; i < boardList.size(); i++) {
			SimpleDateFormat format1 = new SimpleDateFormat("MM-dd");
			String today = format1.format(boardList.get(i).get("REG_DATE"));
			
			System.out.println(boardList.get(i).get("BOARD_NO") + "\t"
					+ boardList.get(i).get("TITLE") + "\t"
					+ boardList.get(i).get("CONTENT") + "\t"
					+ boardList.get(i).get("USER_NAME") + "\t"
					//+ boardList.get(i).get("REG_DATE"));
					+ today);
		}
		System.out.println("===============");
		System.out.println("1.조회\t2.등록\t0.로그아웃");
		System.out.println("입력>");
		int input = ScanUtil.nextInt();
		

		switch (input) {
		case 1: // 조회
			boardDao.selectBoard();
			break;
			
		case 2: // 등록
			boardDao.insertBoard();
			break;
		case 0:
			Controller.LoginUser = null;
			return View.HOME;
		}
		return View.BOARD_LIST;

	}
}
