package practice02_board.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import practice02_board.dto.BoardDTO_02;

public class BoardDAO_02 {
	
	private BoardDAO_02 () {}
	private  static BoardDAO_02 instance = new BoardDAO_02();
	public static BoardDAO_02 getInstance () {
		return instance;
	}
	//연동 객체 생성
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	// 연결 메서드
	private void getConnection() {
		try {
			
			Context initctx = new InitialContext();						 // 데이터베이스와 연결하기 위한 init객체 생성
			Context envctx = (Context) initctx.lookup("java:comp/env");  // lookup 메서드를 통해 context.xml 파일에 접근하여 자바환경 코드를 검색    
			DataSource ds = (DataSource) envctx.lookup("jdbc/board"); 	 // <Context>태그안의 <Resource> 환경설정의 name이 jdbc/board인 것을 검색(SQL하고 다른거 연결할 때 바꿔줘야함)	  
			conn = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//해지 메서드
	private void getClose() {
		if(rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getInsertBoard(BoardDTO_02 boardDTO_02) {
		try {
			getConnection();

			// O, NOW() : 디폴트 값
			String sql = """
					INSERT INTO BOARD(WRITER, SUBJECT, EMAIL, PASSWORD, CONTENT, READ_CNT, ENROLL_DT)
					VALUE(?,?,?,?,?, 0, NOW());
					""";
			// 가져온 데이터를 데이터베이스에 set하기
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardDTO_02.getWriter());
			pstmt.setString(2, boardDTO_02.getSubject());
			pstmt.setString(3, boardDTO_02.getEmail());
			pstmt.setString(3, boardDTO_02.getPassword());
			pstmt.setString(3, boardDTO_02.getContent());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		//데이터 리스트 가지고오기 메서드
//	public ArrayList<BoardDTO_02> getBoardList(){
//		ArrayList<BoardDTO_02> boardList = new ArrayList<BoardDTO_02>();
//		
//		try {
//			getConnection();
//			
//			pstmt = conn.prepareStatement("SELECT * FROM BOARD");//데이터 쿼리 가지고오기
//			 rs = pstmt.executeQuery();//데이터 쿼리 실행하기
//			
//			 if(rs != null) {
//				 //가지고온 데이터를DTO형태로 만들어서 배열화에 저장하기
//				 BoardDTO_02 boardDTO02 = new BoardDTO_02();
//				 boardDTO02.setBoardId(rs.getLong("BOARD_ID"));
//				 boardDTO02.setSubject(rs.getString("SUBJECT"));     //.getString("WRITER");
//				 boardDTO02.setWriter(rs.getString("WRITER"));     //.getString("WRITER");
//				 boardDTO02.setEnrollDt(rs.getDate("ENROLL_DT"));     //.getString("WRITER");
//				 boardDTO02.setReadCnt(rs.getLong("READ_CNT"));     //.getString("WRITER");
//				 boardList.add(boardDTO02);
//			 }
//		} catch (Exception e) {
//		e.printStackTrace();
//		}finally {
//			getClose();
//		}
//		
//		return boardList;
//	}
		

	}
	
	
	
	

}
