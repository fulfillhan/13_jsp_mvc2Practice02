package practice02_board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import practice02_board.dao.BoardDAO;
import practice02_board.dto.BoardDTO;


@WebServlet("/bDetail")
public class DetailBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 long boardId = Long.parseLong(request.getParameter("boardId"));
		  BoardDTO boardDTO= BoardDAO.getInstance().getBoardDetail(boardId);
		  request.setAttribute("boardDTO", boardDTO);
		  
		  RequestDispatcher dis = request.getRequestDispatcher("practice02_boardEx/bDetail.jsp");
		  dis.forward(request, response);;
	}

}
