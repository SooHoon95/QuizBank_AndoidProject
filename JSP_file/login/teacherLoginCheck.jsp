<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String tid = request.getParameter("tid");

	String url_mysql = "jdbc:mysql://localhost/questionbank?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select tid, tpw, tname, taddress1, taddress2, tdeletedate from teacher where tid = '"+tid+"' and tdeletedate is null";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"login_info"  : [ 
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
			"tid" : "<%=rs.getString(1) %>", 
			"tpw" : "<%=rs.getString(2) %>",   
			"tname" : "<%=rs.getString(3) %>",  
			"taddress1" : "<%=rs.getString(4) %>",
            "taddress2" : "<%=rs.getString(5) %>",
            "tdeletedate" : "<%=rs.getString(6) %>"
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
