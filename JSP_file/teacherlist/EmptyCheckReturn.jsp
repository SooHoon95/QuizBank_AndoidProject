<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

    String wtitle = request.getParameter("wtitle");
    String qno = request.getParameter("qno");

	String url_mysql = "jdbc:mysql://localhost/questionbank?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "SELECT wtitle, quantity, qno, qimage, qanswer FROM workbook, question WHERE workbook.wid = question.workbook_id and wtitle = '"+ wtitle +"' and qno = " + qno;
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"question_info"  : [ 
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
			"quantity" : "<%=rs.getInt(2) %>",   
			"qno" : "<%=rs.getInt(3) %>",
            "qimage" : "<%=rs.getString(4) %>",
            "qanswer" : "<%=rs.getInt(5) %>"
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
