package com.cpsc411.assignment3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cpsc411.assignment3.adapter.CourseListAdapter;
import com.cpsc411.assignment3.model.CourseEnrollment;
import com.cpsc411.assignment3.model.Student;
import com.cpsc411.assignment3.model.StudentDB;

import java.util.ArrayList;

public class StudentDetails extends AppCompatActivity {

    Button mButton;
    Student student = new Student();
    ArrayList<CourseEnrollment> courses;
    ArrayList<EditText> courseIDs = new ArrayList<EditText>();
    ArrayList<EditText> grades = new ArrayList<EditText>();
    ArrayList<LinearLayout> rows = new ArrayList<LinearLayout>();
    LinearLayout courseList;
    StudentDB mStudentDB;
    boolean studentExists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_student);

        mStudentDB = new StudentDB(this);
        mButton = findViewById(R.id.add_course);

        int index = getIntent().getIntExtra("StudentIndex", -1);
        studentExists = index >= 0;

        if (studentExists) {
            mStudentDB.getStudentObjects();
            student = mStudentDB.getStudentList().get(index);

//            fill in student info
            EditText editText = findViewById(R.id.first_name);
            editText.setText(student.getFirstName());
            editText.setEnabled(false);
            editText = findViewById(R.id.last_name);
            editText.setText(student.getLastName());
            editText.setEnabled(false);
            editText = findViewById(R.id.cwid);
            editText.setText(Integer.toString(student.getCWID()));
            editText.setEnabled(false);
            courses = student.getCourses();

//            disable add courses button
            mButton.setVisibility(View.GONE);
        }
        else {
//            initialize empty course list
            courses = new ArrayList<CourseEnrollment>();
            student.setCourses(courses);
        }

//        create adapter for course list
        ListView courseList = findViewById(R.id.course_list_view);
        CourseListAdapter adapter = new CourseListAdapter(student);
        courseList.setAdapter(adapter);
    }

    public void addCourse(View v) {
        courseList = findViewById(R.id.course_list);

//        add rows to listview
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout row = (LinearLayout) inflater.inflate(R.layout.course_row, courseList, false);
        rows.add(row);
        courseList.addView(row);

//        save EditTexts for referencing
        EditText course = row.findViewById(R.id.course_id);
        EditText grade = row.findViewById(R.id.grade);
        courseIDs.add(course);
        grades.add(grade);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!studentExists) {
            getMenuInflater().inflate(R.menu.detail_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.save) {
//            get text from input fields and create student object
            String firstName = ((EditText)(findViewById(R.id.first_name))).getText().toString();
            String lastName = ((EditText)(findViewById(R.id.last_name))).getText().toString();
            int cwid = Integer.parseInt(((EditText)(findViewById(R.id.cwid))).getText().toString());
            Student student = new Student(firstName, lastName, cwid);

//            adds new courses to student
            for(int i = 0; i < rows.size(); i++) {
                String courseID = courseIDs.get(i).getText().toString();
                String grade = grades.get(i).getText().toString();
                courses.add(new CourseEnrollment(courseID, grade, cwid));
                courseList.removeView(rows.get(i));
            }
            courseIDs.clear();
            grades.clear();
            rows.clear();
            student.setCourses(courses);

//            creates new student in student list
            mStudentDB.addStudent(student);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
