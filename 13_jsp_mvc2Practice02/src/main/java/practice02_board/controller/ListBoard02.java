package practice02_board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import practice02_board.dao.BoardDAO_02;
import practice02_board.dto.BoardDTO_02;


@WebServlet("/bList02")
public class ListBoard02 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   //dao에서 데이터 가져오기
		//BoardDAO_02.getInstance().getBoardList();
		
	}
	
	


}
