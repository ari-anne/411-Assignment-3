package com.cpsc411.assignment3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.cpsc411.assignment3.R;
import com.cpsc411.assignment3.model.CourseEnrollment;
import com.cpsc411.assignment3.model.Student;

public class CourseListAdapter extends BaseAdapter {

    Student student;

    public CourseListAdapter(Student student) {
        this.student = student;
    }

    @Override
    public int getCount() {
        return student.getCourses().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.course_row, parent, false);
        }

        CourseEnrollment course = student.getCourses().get(position);

        EditText courseID = view.findViewById(R.id.course_id);
        courseID.setText(course.getCourseID());
        courseID.setEnabled(false);

        EditText grade = view.findViewById(R.id.grade);
        grade.setText(course.getGrade());
        grade.setEnabled(false);

        return view;
    }
}
