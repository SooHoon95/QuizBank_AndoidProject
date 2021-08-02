package com.aosproject.project_team6.Bean;

public class StudentLoginbean {

    // Field
    private String sid;
    private String sname;
    private String spw;
    private String sdivision;
    private String sdeletedate;

    // Constructor
    public StudentLoginbean(String sid, String spw, String sname, String sdivision, String sdeletedate) {
        this.sid = sid;
        this.sname = sname;
        this.spw = spw;
        this.sdivision = sdivision;
        this.sdeletedate = sdeletedate;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSpw() {
        return spw;
    }

    public void setSpw(String spw) {
        this.spw = spw;
    }

    public String getSdivision() {
        return sdivision;
    }

    public void setSdivision(String sdivision) {
        this.sdivision = sdivision;
    }

    public String getSdeletedate() {
        return sdeletedate;
    }

    public void setSdeletedate(String sdeletedate) {
        this.sdeletedate = sdeletedate;
    }
}
