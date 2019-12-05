package com.cpsc411.assignment3.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpsc411.assignment3.R;
import com.cpsc411.assignment3.StudentDetails;
import com.cpsc411.assignment3.model.Student;
import com.cpsc411.assignment3.model.StudentDB;

public class SummaryListAdapter extends BaseAdapter {

    StudentDB mStudentDB;

    public SummaryListAdapter (StudentDB studentDB) {
        mStudentDB = studentDB;
    }

    @Override
    public int getCount() {
        return mStudentDB.getStudentObjects().size();
    }

    @Override
    public Object getItem(int position) {
        return mStudentDB.getStudentObjects().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row_view = convertView;

        if(row_view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            row_view = inflater.inflate(R.layout.summary_list_row, parent, false);
        }

        Student s = mStudentDB.getStudentList().get(position);

        ((TextView) row_view.findViewById(R.id.first_name)).setText(s.getFirstName());
        ((TextView) row_view.findViewById(R.id.last_name)).setText(s.getLastName());
        ((TextView) row_view.findViewById(R.id.cwid)).setText(Integer.toString(s.getCWID()));
        row_view.setTag(new Integer(position));

        row_view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), StudentDetails.class);
                        intent.putExtra("StudentIndex", ((Integer)v.getTag()).intValue());
                        v.getContext().startActivity(intent);
                    }
                }
        );

        return row_view;
    }
}
