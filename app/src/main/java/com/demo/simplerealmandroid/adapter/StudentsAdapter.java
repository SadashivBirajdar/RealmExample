package com.demo.simplerealmandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.simplerealmandroid.R;
import com.demo.simplerealmandroid.AppController;
import com.demo.simplerealmandroid.model.Student;
import com.demo.simplerealmandroid.utils.Utils;

import io.realm.RealmList;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

    private RealmList<Student> students;

    public StudentsAdapter(RealmList<Student> students) {
        this.students = students;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        holder.tvName.setText(students.get(position).getName());
        String birthday = AppController.getInstance().getString(R.string.birthday) + " " + Utils.convertDateToString(students.get(position).getBirthday());
        holder.tvBirthday.setText(birthday);
        String email = AppController.getInstance().getString(R.string.email) + " " + students.get(position).getEmail();
        holder.tvEmail.setText(email);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvBirthday;
        private TextView tvEmail;

        StudentViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_student_name);
            tvBirthday = (TextView) itemView.findViewById(R.id.tv_birthday);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
        }
    }

}
