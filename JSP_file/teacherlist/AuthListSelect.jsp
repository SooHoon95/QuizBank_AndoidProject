<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
​
<%
	String workbookId = request.getParameter("workbookId");
​
	String url_mysql = "jdbc:mysql://localhost/questionbank?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "SELECT DISTINCT student_id FROM questionbank.auth WHERE auth.workbook_id=" + workbookId;
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();
​
        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"Auth_studentId"  : [ 
<%
        while (rs.next()) {
            if (count == 0) {
​
            }else{
%>
            , 
<%
            }
%>            
			{
			"student_id" : "<%=rs.getString(1) %>"
			}
​
<%		
        count++;
        }
%>
		  ] 
		} 
<%		
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
%>