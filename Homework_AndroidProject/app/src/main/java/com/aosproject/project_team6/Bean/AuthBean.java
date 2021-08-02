package com.aosproject.project_team6.Bean;

public class AuthBean {

    // Field
    int auth_no;
    String student_id;
    int workbook_id;
    String sid = "";

    // Constructor
    public AuthBean(String sid){
        this.sid =sid;
    }


    public AuthBean(int auth_no, String student_id, int workbook_id) {
        this.auth_no = auth_no;
        this.student_id = student_id;
        this.workbook_id = workbook_id;
    }


    // Getters and Setters


    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getAuth_no() {
        return auth_no;
    }

    public void setAuth_no(int auth_no) {
        this.auth_no = auth_no;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public int getWorkbook_id() {
        return workbook_id;
    }

    public void setWorkbook_id(int workbook_id) {
        this.workbook_id = workbook_id;
    }
}
