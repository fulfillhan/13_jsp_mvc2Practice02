package practice02_board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.LongBinaryOperator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import practice02_board.dao.BoardDAO;


@WebServlet("/bDelete")
public class DeleteBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 long boardId= Long.parseLong(	request.getParameter("boardId"));
		request.setAttribute("boardId", boardId );
		
		RequestDispatcher dis = request.getRequestDispatcher("practice02_boardEx/bDelete.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 삭제하는 로직처리
		BoardDAO.getInstance().deleteBoard(Long.parseLong(request.getParameter("boardId")));
		
		String jsScript="""
				<script>
					alert("삭제 되었습니다.");
					location.href="bList";
				</script>
				""";
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsScript);
		
	}

}
