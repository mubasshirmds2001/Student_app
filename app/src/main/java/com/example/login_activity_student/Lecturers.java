package com.example.login_activity_student;

public class Lecturers {
    private String Lecturer_name;
    private String Lecturer_email;

    public Lecturers(String name, String email)
    {

    }

    public String getLecturer_name() {
        return Lecturer_name;
    }

    public String getLecturer_email() {
        return Lecturer_email;
    }

    public void setLecturer_name(String lecturer_name) {
        Lecturer_name = lecturer_name;
    }

    public void setLecturer_email(String lecturer_email) {
        Lecturer_email = lecturer_email;
    }
}
