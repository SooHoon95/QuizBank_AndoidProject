<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String date = request.getParameter("date");
	String title = request.getParameter("title");
	String detail = request.getParameter("detail");
	String status = request.getParameter("status");
		
//------
	String url_mysql = "jdbc:mysql://localhost/diary?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력 확인 

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "UPDATE mydiary SET ";
	    String B = "title = ?, detail = ? WHERE date = ?";
	
	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, title);
	    ps.setString(2, detail);
		ps.setString(3, date);
		
		result = ps.executeUpdate();
%>
		{
			"result" : "<%=result%>"
		}

<%		
	    conn_mysql.close();
	} 
	catch (Exception e){
%>
		{
			"result" : "<%=result%>"
		}
<%		
	    e.printStackTrace();
	} 
	
%>

