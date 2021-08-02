<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%
    request.setCharacterEncoding("utf-8");
    String tid = request.getParameter("tid");
    String taddress1 = request.getParameter("taddress1");
    String taddress2 = request.getParameter("taddress2");
   
        
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
        
        
        String A = "UPDATE questionbank.teacher SET ";
        String B = "taddress1 = ?, taddress2 = ? WHERE tid = ?";
    
        ps = conn_mysql.prepareStatement(A+B);
        ps.setString(1, taddress1);
        ps.setString(2, taddress2);
        ps.setString(3, tid);
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

