package com.aosproject.project_team6.Bean;

public class TeacherLoginBean {

    // Field
    private String tid;
    private String tpw;
    private String tname;
    private String taddress1;
    private String taadress2;
    private String tdeletedate;

    // Constructor
    public TeacherLoginBean(String tid, String tpw, String tname, String taddress1, String taadress2, String tdeletedate) {
        this.tid = tid;
        this.tpw = tpw;
        this.tname = tname;
        this.taddress1 = taddress1;
        this.taadress2 = taadress2;
        this.tdeletedate = tdeletedate;
    }

    // Getter N Setter
    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTpw() {
        return tpw;
    }

    public void setTpw(String tpw) {
        this.tpw = tpw;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTaddress1() {
        return taddress1;
    }

    public void setTaddress1(String taddress1) {
        this.taddress1 = taddress1;
    }

    public String getTaadress2() {
        return taadress2;
    }

    public void setTaadress2(String taadress2) {
        this.taadress2 = taadress2;
    }

    public String getTdeletedate() {
        return tdeletedate;
    }

    public void setTdeletedate(String tdeletedate) {
        this.tdeletedate = tdeletedate;
    }
}
