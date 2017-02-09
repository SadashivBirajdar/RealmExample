package com.demo.simplerealmandroid.view.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.demo.simplerealmandroid.R;

public class AddUniversityDialog extends DialogFragment implements View.OnClickListener {

    private EditText mEtUniversityName;
    private OnAddUniversityClickListener mOnAddUniversityClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_university, container);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        mEtUniversityName = (EditText) view.findViewById(R.id.et_university_name);
        mEtUniversityName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btAddUniversity = (Button) view.findViewById(R.id.bt_add_university);
        btAddUniversity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_university: {
                if (isUniversityInfoValid()) {
                    mOnAddUniversityClickListener.onAddUniversityClickListener(
                            mEtUniversityName.getText().toString());
                }
                break;
            }
        }
    }

    private boolean isUniversityInfoValid() {
        return !mEtUniversityName.getText().toString().isEmpty();
    }

    public void setOnAddUniversityClickListener(
            OnAddUniversityClickListener onAddUniversityClickListener) {
        this.mOnAddUniversityClickListener = onAddUniversityClickListener;
    }

    public interface OnAddUniversityClickListener {
        void onAddUniversityClickListener(String universityName);
    }
}
