package com.cpsc411.assignment3.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;

public class StudentDB {

    protected ArrayList<Student> mStudentList = new ArrayList<Student>();
    protected SQLiteDatabase mSQLiteDatabase;

    public StudentDB(Context context) {
        File dbFile = context.getDatabasePath("student.db");
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);

        mSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Student (FirstName Text, LastName Text, CWID Integer)");
        mSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS CourseEnrollment (CourseID Text, Grade Text, CWID Integer)");

//        reset database
        makeStudents();
    }

    public ArrayList<Student> getStudentList() {
        return mStudentList;
    }

    public void setStudentList(ArrayList<Student> studentList) {
        mStudentList = studentList;
    }

//    reset database
    protected void makeStudents() {
        mSQLiteDatabase.delete("Student", null, null);
        mSQLiteDatabase.delete("CourseEnrollment", null, null);
        mSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Student (FirstName Text, LastName Text, CWID Integer)");
        mSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS CourseEnrollment (CourseID Text, Grade Text, CWID Integer)");

        Student student = new Student("John", "Smith", 123456789);
        ArrayList<CourseEnrollment> courses = new ArrayList<CourseEnrollment>();
        courses.add(new CourseEnrollment("CPSC349", "A", student.getCWID()));
        courses.add(new CourseEnrollment("CPSC452", "B", student.getCWID()));
        courses.add(new CourseEnrollment("CPSC471", "C", student.getCWID()));
        courses.add(new CourseEnrollment("CPSC476", "A", student.getCWID()));
        student.setCourses(courses);
        student.insert(mSQLiteDatabase);

        student = new Student("Kevin", "Nguyen", 987654321);
        courses = new ArrayList<CourseEnrollment>();
        courses.add(new CourseEnrollment("CPSC240", "A", student.getCWID()));
        courses.add(new CourseEnrollment("CPSC311", "B", student.getCWID()));
        courses.add(new CourseEnrollment("CPSC335", "B", student.getCWID()));
        courses.add(new CourseEnrollment("CPSC481", "A", student.getCWID()));
        student.setCourses(courses);
        student.insert(mSQLiteDatabase);
    }

    public void addStudent(Student student) {
        student.insert(mSQLiteDatabase);
    }

    public ArrayList<Student> getStudentObjects() {
        mStudentList = new ArrayList<Student>();
        Cursor c = mSQLiteDatabase.query("Student", null, null, null, null, null, null);

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                Student student = new Student();
                student.initFrom(mSQLiteDatabase, c);
                mStudentList.add(student);
            }
        }

        return mStudentList;
    }
}
