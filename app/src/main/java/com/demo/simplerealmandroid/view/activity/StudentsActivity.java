package com.demo.simplerealmandroid.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.demo.simplerealmandroid.R;
import com.demo.simplerealmandroid.adapter.StudentsAdapter;
import com.demo.simplerealmandroid.model.Student;
import com.demo.simplerealmandroid.presenter.BasePresenter;
import com.demo.simplerealmandroid.presenter.StudentPresenter;
import com.demo.simplerealmandroid.utils.Constants;
import com.demo.simplerealmandroid.view.dialogs.AddStudentDialog;

import io.realm.RealmList;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public class StudentsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = StudentsActivity.class.getSimpleName();
    private StudentPresenter mStudentPresenter;
    private RecyclerView mRvStudents;
    private StudentsAdapter mStudentsAdapter;
    private RealmList<Student> mStudents;
    private String mUniversityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        mStudentPresenter = new StudentPresenter(this);
        mUniversityId = getIntent().getStringExtra(Constants.ID);
        initComponents();
    }

    @Override
    protected void initComponents() {
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(
                R.id.fab_add_student);
        actionButton.setOnClickListener(this);
        initRecyclerListener();
    }

    @Override
    protected BasePresenter getPresenter() {
        return mStudentPresenter;
    }

    public void updateToolbarTittle(String tittle) {
        getSupportActionBar().setTitle(getString(R.string.students) + " - " + tittle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStudentPresenter.getUniversityById(mUniversityId);
        mStudentPresenter.getAllStudentsByUniversityId(mUniversityId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_student: {
                showAddStudentDialog();
                break;
            }
        }
    }

    private void initRecyclerListener() {
        mRvStudents = (RecyclerView) findViewById(R.id.rv_students);
        mRvStudents.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRvStudents.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override

                    public boolean onMove(RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        mStudentPresenter.deleteStudentById(
                                mStudents.get(viewHolder.getAdapterPosition()).getId());
                    //    mStudentsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                });
        swipeToDismissTouchHelper.attachToRecyclerView(mRvStudents);
    }

    private void showAddStudentDialog() {
        final AddStudentDialog dialog = new AddStudentDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        dialog.setListener(new AddStudentDialog.OnAddStudentClickListener() {
            @Override
            public void onAddStudentClickListener(Student student) {
                dialog.dismiss();
                mStudentPresenter.addStudentByUniversityId(student, mUniversityId);
                mStudentPresenter.getAllStudentsByUniversityId(mUniversityId);
            }
        });
    }

    public void showStudents(RealmList<Student> students) {
        this.mStudents = students;
        mStudentsAdapter = new StudentsAdapter(students);
        mRvStudents.setAdapter(mStudentsAdapter);
    }
}
