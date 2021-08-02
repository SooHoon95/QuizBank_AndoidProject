package com.aosproject.project_team6.Bean;

public class StudentListBean {

    private int wid;
    private String student_id;
    private String wtitle;
    private String subject;
    private String duedate;
    private int seq_no;
    private String qno;
    private String qimage;
    private String qanswer;
    private int compareResult;
    private String dDayuseDuedate;
    private String wbDday;



//Constructor
    public StudentListBean(String str){
        this.dDayuseDuedate = str;
        this.wbDday = str;
    }

    public StudentListBean(int seq_no, String qno, String qimage){
        this.seq_no = seq_no;
        this.qno = qno;
        this.qimage = qimage;

    }

    public StudentListBean(String wtitle, String subject, String duedate, int wid) {
        this.wtitle = wtitle;
        this.subject = subject;
        this.duedate = duedate;
        this.wid = wid;
    }

    public StudentListBean(String wtitle, String subject, String duedate) {
        this.wtitle = wtitle;
        this.subject = subject;
        this.duedate = duedate;
    }

    public StudentListBean(int seq_no, String qno, String qimage, String qanswer) {
        this.seq_no = seq_no;
        this.qno = qno;
        this.qimage = qimage;
        this.qanswer = qanswer;
    }
    //G/s


    public String getdDayuseDuedate() {
        return dDayuseDuedate;
    }

    public void setdDayuseDuedate(String dDayuseDuedate) {
        this.dDayuseDuedate = dDayuseDuedate;
    }

    public String getWbDday() {
        return wbDday;
    }

    public void setWbDday(String wbDday) {
        this.wbDday = wbDday;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
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

    public int getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(int seq_no) {
        this.seq_no = seq_no;
    }

    public String getQno() {
        return qno;
    }

    public void setQno(String qno) {
        this.qno = qno;
    }

    public String getQimage() {
        return qimage;
    }

    public void setQimage(String qimage) {
        this.qimage = qimage;
    }

    public String getQanswer() {
        return qanswer;
    }

    public void setQanswer(String qanswer) {
        this.qanswer = qanswer;
    }
}
