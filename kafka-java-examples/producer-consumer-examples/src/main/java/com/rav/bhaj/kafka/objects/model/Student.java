package com.rav.bhaj.kafka.objects.model;

public class Student implements java.io.Serializable {
    private int studentId;
    private String studentName;
    private String studentSubject;

    public Student(int studentId, String studentName, String studentSubject) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentSubject = studentSubject;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSubject() {
        return studentSubject;
    }

    public void setStudentSubject(String studentSubject) {
        this.studentSubject = studentSubject;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
