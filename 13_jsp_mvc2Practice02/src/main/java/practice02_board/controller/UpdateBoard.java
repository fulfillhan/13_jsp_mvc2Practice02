package practice02_board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import practice02_board.dao.BoardDAO;
import practice02_board.dto.BoardDTO;


@WebServlet("/bUpdate")
public class UpdateBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long boardId = Long.parseLong(request.getParameter("boardId"));
		BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(boardId);
		request.setAttribute("boardDTO", boardDTO);
		
		RequestDispatcher dis = request.getRequestDispatcher("practice02_boardEx/bUpdate.jsp");
		dis.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//수정하기subject content
		
		request.setCharacterEncoding("utf-8");
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoardId(Long.parseLong(request.getParameter("boardId")));
		boardDTO.setSubject(request.getParameter("subject"));
		boardDTO.setContent(request.getParameter("content"));
	
		BoardDAO.getInstance().UpdateBoard(boardDTO);
		System.out.println(boardDTO);
		
		String jsScript = """
				<script>
					alert('수정이 완료되었습니다');
					location.href='bList';
				</script>
				""";
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsScript);
	}

}
