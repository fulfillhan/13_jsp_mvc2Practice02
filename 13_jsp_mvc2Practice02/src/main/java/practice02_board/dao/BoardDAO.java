package practice02_board.dao;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.PStmtKey;

import com.mysql.cj.exceptions.RSAException;

import practice02_board.dto.BoardDTO;

public class BoardDAO {
	
	private BoardDAO () {}
	private  static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance () {
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

	public void insertBoard(BoardDTO boardDTO) {
		try {
			getConnection();

			// 전달받은 데이터를 데이터베이스에 저장하기
			String sql = """
					INSERT INTO BOARD(WRITER, SUBJECT, EMAIL, PASSWORD, CONTENT, READ_CNT, ENROLL_DT)
					VALUES(?,?,?,?,?,0,NOW());
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardDTO.getWriter());
			pstmt.setString(2, boardDTO.getSubject());
			pstmt.setString(3, boardDTO.getEmail());
			pstmt.setString(4, boardDTO.getPassword());
			pstmt.setString(5, boardDTO.getContent());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
	}

	public ArrayList<BoardDTO> getBoardList() {
		
	ArrayList<BoardDTO> boardList = new ArrayList<>();
		
		try {
			getConnection();
			
			String sql = """
					SELECT * FROM BOARD
					""";
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 
			 while(rs.next()) {
			BoardDTO boardDTO = new BoardDTO();
			boardDTO.setBoardId(rs.getLong("BOARD_ID"));
			boardDTO.setSubject(rs.getString("SUBJECT"));
			boardDTO.setWriter(rs.getString("WRITER"));
			boardDTO.setEnrollDt(rs.getDate("ENROLL_DT"));
			boardDTO.setReadCnt(rs.getLong("READ_CNT"));
			boardList.add(boardDTO);
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			getClose();
		}
		return boardList; 
	}
	
	public BoardDTO getBoardDetail(long boardId) {
		BoardDTO boardDTO = new BoardDTO();

		try {
			getConnection();
			
			pstmt = conn.prepareStatement("UPDATE BOARD SET READ_CNT = READ_CNT +1 WHERE BOARD_ID=?");
			pstmt.setLong(1, boardId);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE BOARD_ID = ?");
			pstmt.setLong(1, boardId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				boardDTO.setBoardId(boardId);//boardId 꼭 가져오기!
				boardDTO.setReadCnt(rs.getLong("READ_CNT"));
				boardDTO.setWriter(rs.getString("WRITER"));
				boardDTO.setEnrollDt(rs.getDate("ENROLL_DT"));
				boardDTO.setEmail(rs.getString("EMAIL"));
				boardDTO.setSubject(rs.getString("SUBJECT"));
				boardDTO.setContent(rs.getString("CONTENT"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

		return boardDTO;
	}
	
	
	public boolean checkAuthorizedUser(BoardDTO boardDTO) {
		boolean isAuthorizedUser = false;
		
		try {
			getConnection();
			String sql = """
					SELECT * FROM BOARD WHERE BOARD_ID=? AND PASSWORD=?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardDTO.getBoardId());
			pstmt.setString(2, boardDTO.getPassword());
			
			 rs = pstmt.executeQuery();
			 
			 if(rs.next()) {
				 isAuthorizedUser=true;
			 }
			
		} catch (Exception e) {
		e.printStackTrace();
		}finally {
			getClose();
		}
		//System.out.println(isAuthorizedUser);
		return isAuthorizedUser;
	}

	public void deleteBoard(long boardId){
		try {
			getConnection();
			 pstmt = conn.prepareStatement("DELETE FROM BOARD WHERE BOARD_ID=?");
			pstmt.setLong(1, boardId);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			getClose();
		}
	}

	public void UpdateBoard(BoardDTO boardDTO) {

		try {
			getConnection();

			pstmt = conn.prepareStatement("UPDATE BOARD SET SUBJECT=?, CONTENT=? WHERE BOARD_ID=?");
			pstmt.setString(1, boardDTO.getSubject());
			pstmt.setString(2, boardDTO.getContent());
			pstmt.setLong(3, boardDTO.getBoardId());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

	}
	
}
