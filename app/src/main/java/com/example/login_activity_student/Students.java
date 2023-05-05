package com.example.login_activity_student;

public class Students {

    private String student_USN;
    private String student_name;
    private String student_email;
    private String student_dept;
    private String student_year;
    private String student_section;

    public Students(String USN, String name, String email, String dept, String year, String section)
    {

    }

    public String getStudent_USN() {
        return student_USN;
    }

    public void setStudent_USN(String student_USN) {
        this.student_USN = student_USN;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public String getStudent_dept() {
        return student_dept;
    }

    public void setStudent_dept(String student_dept) {
        this.student_dept = student_dept;
    }

    public String getStudent_year() {
        return student_year;
    }

    public void setStudent_year(String student_year) {
        this.student_year = student_year;
    }

    public String getStudent_section() {
        return student_section;
    }

    public void setStudent_section(String student_section) {
        this.student_section = student_section;
    }
}
