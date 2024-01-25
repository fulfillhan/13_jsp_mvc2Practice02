package practice02_board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import practice02_board.dao.BoardDAO_02;
import practice02_board.dto.BoardDTO_02;


@WebServlet("/bWrite02")
public class WriteBoard02 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	//화면 보여주기
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("practice02_boardEx/bWrite02.jsp");
		dis.forward(request, response);
	}

	//작성된 글 처리하기
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//dto형태로 셋팅 하기
		BoardDTO_02 boardDTO02 = new BoardDTO_02();
		boardDTO02.setWriter(request.getParameter("wrtier"));
		boardDTO02.setSubject(request.getParameter("subject"));
		boardDTO02.setEmail(request.getParameter("email"));
		boardDTO02.setPassword(request.getParameter("password"));
		boardDTO02.setContent(request.getParameter("content"));
		
		//작성된 데이터를 dao로 보내기 반환값은 > 데이터를 INSERT만 하면 되니깐 반환값은 없다
		BoardDAO_02.getInstance().getInsertBoard(boardDTO02);
		
		//작성되면 알람 표시하기
		response.setContentType("text/html; charset=UTF-8");
		String jsScript = """
				<script>
				 alert("게시글이 업로드 되었습니다.");
				 location.href="bList02";
				 </script>
				""";
		PrintWriter out = response.getWriter();
		out.print(jsScript);
		
	}

}
