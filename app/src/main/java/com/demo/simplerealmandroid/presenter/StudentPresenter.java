package com.demo.simplerealmandroid.presenter;

import android.util.Log;

import com.demo.simplerealmandroid.db.dao.StudentDao;
import com.demo.simplerealmandroid.db.dao.UniversityDao;
import com.demo.simplerealmandroid.model.Student;
import com.demo.simplerealmandroid.utils.Constants;
import com.demo.simplerealmandroid.view.activity.StudentsActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public class StudentPresenter extends BasePresenter {

    private static final String TAG = StudentPresenter.class.getSimpleName();
    private StudentDao mStudentDao;
    private UniversityDao mUniversityDao;
    private StudentsActivity mView;

    public StudentPresenter(StudentsActivity activity) {
        mStudentDao = new StudentDao();
        mUniversityDao = new UniversityDao();
        mView=activity;
    }

    public void addStudent(Student student) {
        mStudentDao.addStudent(student);
    }

    public void addStudentByUniversityId(Student student, String universityId) {
        mStudentDao.addStudentByUniversityId(student, universityId);
    }

    public void deleteStudentByPosition(int position) {
        mStudentDao.deleteStudentByPosition(position);
    }

    public void deleteStudentById(String studentId) {
        mStudentDao.deleteStudentById(studentId);
    }

    public void getAllStudents() {
        mStudentDao.getAllStudents();
    }

    public void getAllStudentsByUniversityId(String id) {
        mStudentDao.getAllStudentsByUniversityId(id);
    }

    public void getStudentById(String id) {
        mStudentDao.getStudentById(id);
    }

    public void getUniversityById(String id) {
        mUniversityDao.getUniversityById(id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onActionFinished(StudentDao.FinishedEvent event) {
        if(!event.isSuccess()){
            Log.d(TAG, "onActionFinished: ");
        }else {
            switch (event.getAction()){
                case Constants.ADD:
                    mView.showMessage("Added");
                    break;
                case Constants.DELETE:
                    mView.showMessage("Deleted");
                    break;
                case Constants.READ:
                    break;
                case Constants.READ_ALL:
                    mView.showStudents(event.getStudents());
                    break;
            }
        }
    }
}
