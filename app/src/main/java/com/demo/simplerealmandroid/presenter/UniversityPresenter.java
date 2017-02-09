package com.demo.simplerealmandroid.presenter;

import android.util.Log;

import com.demo.simplerealmandroid.db.dao.UniversityDao;
import com.demo.simplerealmandroid.model.University;
import com.demo.simplerealmandroid.utils.Constants;
import com.demo.simplerealmandroid.view.activity.UniversityActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public class UniversityPresenter extends BasePresenter {

    private static final String TAG = UniversityPresenter.class.getSimpleName();
    private UniversityDao mUniversityDao;
    private UniversityActivity mView;

    public UniversityPresenter(UniversityActivity universityActivity) {
        mUniversityDao = new UniversityDao();
        mView=universityActivity;
    }

    public void getAllUniversities() {
        mUniversityDao.getAllUniversities();
    }

    public void addUniversity(String universityName) {
        University university = new University(universityName);
        mUniversityDao.addUniversity(university);
    }

    public void getUniversityById(String id) {
        mUniversityDao.getUniversityById(id);
    }

    public void deleteUniversity(int position) {
        mUniversityDao.deleteUniversityByPosition(position);
    }

    public void deleteUniversityById(String Id) {
        mUniversityDao.deleteUniversityById(Id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onActionFinished(UniversityDao.FinishedEvent event) {
        if(!event.isSuccess()){
            Log.d(TAG, "onActionFinished: ");
        }else {
            switch (event.getAction()){
                case Constants.ADD:
                    mView.showMessage("Added");
                    mView.getUniversities();
                    break;
                case Constants.DELETE:
                    mView.showMessage("Deleted");
                    break;
                case Constants.READ:
                    break;
                case Constants.READ_ALL:
                    mView.showUniversities(event.getUniversities());
                    break;
            }
        }
    }
}
