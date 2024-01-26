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


@WebServlet("/bAuthentication")
public class AuthenticationBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		//long boardId =Long.parseLong(request.getParameter("boardId"));
		 BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(Long.parseLong(request.getParameter("boardId")));
		 request.setAttribute("boardDTO", boardDTO);
		 
		 request.setAttribute("menu", request.getParameter("menu"));
		 
		 RequestDispatcher dis = request.getRequestDispatcher("practice02_boardEx/bAuthentication.jsp");
			dis.forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//패스워드가 맞는지 확인하는 로직
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoardId(Long.parseLong(request.getParameter("boardId")));// Long.parseLong(request.getParameter("boardId"));
		boardDTO.setPassword(request.getParameter("password"));
		
		 String menu = request.getParameter("menu");
		 
		 
		 boolean result =  BoardDAO.getInstance().checkAuthorizedUser(boardDTO);
		 String jsScript = "";
		 if(result) {
			 //삭제 수정 모두 가능
			 if(menu.equals("delete")) {
				 jsScript = "<script>";
				 jsScript += "location.href='bDelete?boardId="+ boardDTO.getBoardId()+"';" ;
				 jsScript += "alert('ok');";
				 jsScript +=" </script>";
			 }
			 else if (menu.equals("update")) {
				 jsScript = "<script>";
				 jsScript += "location.href='bUpdate?boardId="+ boardDTO.getBoardId()+"';" ;
				 jsScript += "alert('ok');";
				 jsScript +=" </script>";
			}
		 }
		 else {
			 jsScript = "<script>";
			 jsScript += "alert('패스워드를 확인하세요.');";
			 jsScript += "history.go(-1)";
			 jsScript += "<script>";
		 }
		 response.setContentType("text/html; charset=UTF-8");
		 PrintWriter out = response.getWriter();
		 out.print(jsScript);
		 
	}
}
