package com.cpsc411.assignment3.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CourseEnrollment extends PersistentObject {

    protected String mCourseID;
    protected String mGrade;
    protected int mCWID;

    public CourseEnrollment(String courseID, String grade, int CWID) {
        mCourseID = courseID;
        mGrade = grade;
        mCWID = CWID;
    }

    public CourseEnrollment() {

    }

    public String getCourseID() {
        return mCourseID;
    }

    public void setCourseID(String courseID) {
        mCourseID = courseID;
    }

    public String getGrade() {
        return mGrade;
    }

    public void setGrade(String grade) {
        mGrade = grade;
    }

    public int getCWID() {
        return mCWID;
    }

    public void setCWID(int cwid) {
        mCWID = cwid;
    }

    @Override
    public void insert(SQLiteDatabase db) {
        ContentValues vals = new ContentValues();
        vals.put("CourseID", mCourseID);
        vals.put("Grade", mGrade);
        vals.put("CWID", mCWID);
        db.insert("CourseEnrollment", null, vals);
    }

    @Override
    public void initFrom(SQLiteDatabase db, Cursor cursor) {
        mCourseID = cursor.getString(cursor.getColumnIndex("CourseID"));
        mGrade = cursor.getString(cursor.getColumnIndex("Grade"));
        mCWID = cursor.getInt(cursor.getColumnIndex("CWID"));
    }
}
