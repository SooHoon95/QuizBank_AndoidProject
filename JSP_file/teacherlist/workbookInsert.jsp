<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String wtitle = request.getParameter("wtitle");
	String duedate = request.getParameter("duedate");
	String subject = request.getParameter("subject");
	String quantity_str = request.getParameter("quantity");	
	int quantity = Integer.parseInt(quantity_str);
	String teacher_id = request.getParameter("teacher_id");	


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
	
	    String A = "insert into workbook (wtitle, duedate, subject, attenddate, quantity, teacher_id";
	    String B = ") values (?,?,?,curdate(),?,?)";
	
	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, wtitle);
	    ps.setString(2, duedate);
	    ps.setString(3, subject);
	    ps.setInt(4, quantity);
		ps.setString(5, teacher_id);
	    

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

