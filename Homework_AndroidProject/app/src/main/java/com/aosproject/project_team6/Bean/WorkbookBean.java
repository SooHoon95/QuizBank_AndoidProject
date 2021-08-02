package com.aosproject.project_team6.Bean;

public class WorkbookBean {

    /////////////////////////////////////////////////////////
    //                                                     //
    //    문제집을 클릭했을때, 그 안에 있는 문제들 값을 불러오는 Bean   //
    //                                                     //
    /////////////////////////////////////////////////////////


    // * * Field * *
    private String wtitle;
    private int quantity;
    private int qno;
    private String qimage;
    private int qanswer;

    // * * Constructor * *

    // 문제집을 클릭했을 때, 해당하는 문제들 불러오기 위한 Con
    public WorkbookBean(String wtitle, int quantity, int qno, String qimage, int qanswer) {
        this.wtitle = wtitle;
        this.quantity = quantity;
        this.qno = qno;
        this.qimage = qimage;
        this.qanswer = qanswer;
    }

    // * * Getter N Setter * *
    public String getWtitle() {
        return wtitle;
    }

    public void setWtitle(String wtitle) {
        this.wtitle = wtitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQno() {
        return qno;
    }

    public void setQno(int qno) {
        this.qno = qno;
    }

    public String getQimage() {
        return qimage;
    }

    public void setQimage(String qimage) {
        this.qimage = qimage;
    }

    public int getQanswer() {
        return qanswer;
    }

    public void setQanswer(int qanswer) {
        this.qanswer = qanswer;
    }
}
