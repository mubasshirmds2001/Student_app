package com.example.login_activity_student;

public class Marks {

    private String subject1;
    private String student_USN;
    private String subject2;
    private String subject3;
    private String subject4;
    private String subject5;
    private String subject6;
    private String subject7;
    private String subject8;
    private String subject9;

    public Marks(){
    }

    public Marks(String stud_USN, String subject1Marks, String subject2Marks, String subject3Marks, String subject4Marks, String subject5Marks, String subject6Marks, String subject7Marks, String subject8Marks,String subject9Marks) {
        this.student_USN = stud_USN;
        this.subject1 = subject1Marks;
        this.subject2 = subject2Marks;
        this.subject3 = subject3Marks;
        this.subject4 = subject4Marks;
        this.subject5 = subject5Marks;
        this.subject6 = subject6Marks;
        this.subject7 = subject7Marks;
        this.subject8 = subject8Marks;
        this.subject9 = subject9Marks;
    }


    public String getStudent_USN() {
        return student_USN;
    }

    public void setStudent_USN(String student_USN) {
        this.student_USN = student_USN;
    }

    public String getSubject1() {
        return subject1;
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject2() {
        return subject2;
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
    }

    public String getSubject3() {
        return subject3;
    }

    public void setSubject3(String subject3) {
        this.subject3 = subject3;
    }

    public String getSubject4() {
        return subject4;
    }

    public void setSubject4(String subject4) {
        this.subject4 = subject4;
    }

    public String getSubject5() {
        return subject5;
    }

    public void setSubject5(String subject5) {
        this.subject5 = subject5;
    }

    public String getSubject6() {
        return subject6;
    }

    public void setSubject6(String subject6) {
        this.subject6 = subject6;
    }

    public String getSubject7() {
        return subject7;
    }

    public void setSubject7(String subject7) {
        this.subject7 = subject7;
    }

    public String getSubject8() {
        return subject8;
    }

    public void setSubject8(String subject8) {
        this.subject8 = subject8;
    }

    public String getSubject9() {
        return subject9;
    }

    public void setSubject9(String subject9) {
        this.subject9 = subject9;
    }
}
