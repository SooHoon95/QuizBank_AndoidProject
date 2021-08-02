<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String wtitle = request.getParameter("wtitle");
	String duedate = request.getParameter("duedate");
	String subject = request.getParameter("subject");
	String quantity = request.getParameter("quantity");
	String teacher_id = request.getParameter("teacher_id");
	String wid = request.getParameter("wid");
		
//------
	String url_mysql = "jdbc:mysql://localhost/questionbank?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력 확인 

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "UPDATE workbook SET ";
	    String B = "wtitle = ?, subject = ?, duedate = ?, quantity = "+quantity+" WHERE wid = "+wid;
	
	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, wtitle);
	    ps.setString(2, subject);
		ps.setString(3, duedate);

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

