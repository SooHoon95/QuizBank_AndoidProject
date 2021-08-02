<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String student_id = request.getParameter("student_id");


	String url_mysql = "jdbc:mysql://localhost/questionbank?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = " SELECT DISTINCT wtitle, duedate, subject, wid FROM auth, workbook WHERE auth.workbook_id = workbook.wid AND student_id ='" + student_id + "' AND workbook.duedate >= curdate()";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"studentworkbook"  : [ 
<%
        while (rs.next()) {
            if (count == 0) {

            }else{
%>
            , 
<%
            }
%>            
			{
			"wtitle" : "<%=rs.getString(1) %>",  
			"duedate" : "<%=rs.getString(2) %>",
            "subject" : "<%=rs.getString(3) %>",
            "wid" : "<%=rs.getInt(4) %>"
			}

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
