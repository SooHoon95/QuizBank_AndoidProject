package com.aosproject.project_team6.Bean;

public class SubscribeBean {

    // Field
    private String paydate;
    private String teacher_id;

    // Constructor
    public SubscribeBean(String paydate, String teacher_id) {
        this.paydate = paydate;
        this.teacher_id = teacher_id;
    }

    // Getter N Setter
    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }
}
