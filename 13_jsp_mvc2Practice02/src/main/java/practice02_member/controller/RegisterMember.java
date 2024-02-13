package practice02_member.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import practice02_member.dao.MemberDAO;
import practice02_member.dto.MemberDTO;


@WebServlet("/registerMember")
public class RegisterMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String profileRepositoryPath = FileConfig.PROFILE_REPSITORY_PATH;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("practice02_memberEx/mRegister.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		MultipartRequest multi = new MultipartRequest(request,profileRepositoryPath,1024 * 1024 * 30, "utf-8");
	
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(multi.getParameter("memberId"));
		memberDTO.setPasswd(multi.getParameter("passwd"));
		memberDTO.setMemberNm(multi.getParameter("memberNm"));
		memberDTO.setSex(multi.getParameter("sex"));
		memberDTO.setBirthAt(multi.getParameter("birthAt"));
		memberDTO.setHp(multi.getParameter("hp"));
		if(multi.getParameter("smsRecvAgreeYn") == null) memberDTO.setSmsRecvAgreeYn(multi.getParameter("N"));
		else 											 memberDTO.setSmsRecvAgreeYn(multi.getParameter("smsRecvAgreeYn"));
		if(multi.getParameter("emailRecvAgreeYn") == null) memberDTO.setEmailRecvAgreeYn(multi.getParameter("N"));
		else 											 memberDTO.setEmailRecvAgreeYn(multi.getParameter("emailRecvAgreeYn"));
		memberDTO.setZipcode(multi.getParameter("zipcode"));
		memberDTO.setRoadAddress(multi.getParameter("roadAddress"));
		memberDTO.setJibunAddress(multi.getParameter("jibunAddress"));
		memberDTO.setNamujiAddress(multi.getParameter("namujiAddress"));
		
		//파일 저장하기(원본파일과 UUID파일 모두 저장하기)
		Enumeration<?> files = multi.getFileNames();//file객체에서 파일 이름을 가지고오고 열거형으로 반환하고 files변수에 저장한다.
		String originalFileName = "";
		String profileUUID ="";
		if(files.hasMoreElements()) {
			String elementFile = (String) files.nextElement();
			originalFileName = multi.getOriginalFileName(elementFile);
		    profileUUID = UUID.randomUUID()+ originalFileName.substring(originalFileName.indexOf("."));
			
			memberDTO.setProfile(originalFileName);
			memberDTO.setProfileUUID(profileUUID);
		}
		// 원본파일 경로 찾아서 uuid로 리네임하여 경로저장하기
		File file =  new File(profileRepositoryPath + originalFileName);
		File renameFile = new File(profileRepositoryPath + profileUUID);
		file.renameTo(renameFile);
		
		MemberDAO.getInstance().registerMember(memberDTO);
		
		String jsScript = """
				<script>
					alert('회원가입 되었습니다.');
				  location.href='mainMember';
			    </script>""";
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
		
	}

}
