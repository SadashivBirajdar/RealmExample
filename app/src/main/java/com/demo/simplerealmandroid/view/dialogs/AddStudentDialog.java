package com.demo.simplerealmandroid.view.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.demo.simplerealmandroid.R;
import com.demo.simplerealmandroid.model.Student;
import com.demo.simplerealmandroid.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class AddStudentDialog extends DialogFragment implements View.OnClickListener {

    private EditText mEtName, mEtEmail, mEtBirthday;
    private Date mDate;

    private OnAddStudentClickListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_student, container);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        mEtName = (EditText) view.findViewById(R.id.et_name);
        mEtEmail = (EditText) view.findViewById(R.id.et_email);
        mEtBirthday = (EditText) view.findViewById(R.id.et_birthday);
        Button btAdd = (Button) view.findViewById(R.id.bt_add);
        mEtName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btAdd.setOnClickListener(this);
        mEtBirthday.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add: {
                if (isUserInfoValidate()) {
                    Student student = new Student();
                    student.setName(mEtName.getText().toString());
                    student.setEmail(mEtEmail.getText().toString());
                    student.setBirthday(mDate);
                    listener.onAddStudentClickListener(student);
                }
                break;
            }
            case R.id.et_birthday: {
                Calendar now = Calendar.getInstance();
                final DatePickerDialog d = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int year,
                                    int monthOfYear, int dayOfMonth) {
                                Calendar checkedCalendar = Calendar.getInstance();
                                checkedCalendar.set(year, monthOfYear, dayOfMonth);
                                mDate = checkedCalendar.getTime();
                                mEtBirthday.setText(Utils.convertDateToString(mDate));
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                d.setMaxDate(now);
                d.show((getActivity()).getFragmentManager(), this.getClass().getName());
                break;
            }
        }
    }

    private boolean isUserInfoValidate() {
        return !mEtName.getText().toString().isEmpty() &&
                !mEtEmail.getText().toString().isEmpty() &&
                !mEtBirthday.getText().toString().isEmpty();
    }

    public void setListener(OnAddStudentClickListener listener) {
        this.listener = listener;
    }

    public interface OnAddStudentClickListener {
        void onAddStudentClickListener(Student student);
    }
}
