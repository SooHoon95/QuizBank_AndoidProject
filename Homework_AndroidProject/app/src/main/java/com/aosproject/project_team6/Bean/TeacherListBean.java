package com.aosproject.project_team6.Bean;

public class TeacherListBean {

    // Field
    private int wid;
    private String wtitle;
    private String subject;
    private String duedate;
    private int quantity;

    // Constructor
    public TeacherListBean(int wid, String wtitle, String subject, String duedate, int quantity) {
        this.wid = wid;
        this.wtitle = wtitle;
        this.subject = subject;
        this.duedate = duedate;
        this.quantity = quantity;
    }


    // Getter N Setter
    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getWtitle() {
        return wtitle;
    }

    public void setWtitle(String wtitle) {
        this.wtitle = wtitle;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
