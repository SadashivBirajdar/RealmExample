package com.demo.simplerealmandroid.db.dao;

import android.util.Log;

import com.demo.simplerealmandroid.db.interfaces.UniversityRepository;
import com.demo.simplerealmandroid.model.University;
import com.demo.simplerealmandroid.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public class UniversityDao implements UniversityRepository {

    private static final String TAG= UniversityDao.class.getSimpleName();

    @Override
    public void addUniversity(University university) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        University u = realm.createObject(University.class,UUID.randomUUID().toString());
     //   u.setId(UUID.randomUUID().toString());
        u.setName(university.getName());
        realm.commitTransaction();
        EventBus.getDefault().post(new FinishedEvent(true, Constants.ADD));
    }

    @Override
    public void deleteUniversityById(String Id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        University university = realm.where(University.class).equalTo(Constants.ID, Id).findFirst();
        university.deleteFromRealm();
        realm.commitTransaction();
        EventBus.getDefault().post(new FinishedEvent(true, Constants.DELETE));
    }

    @Override
    public void deleteUniversityByPosition(int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery<University> query = realm.where(University.class);
        RealmResults<University> results = query.findAll();
        University university = results.get(position);
        university.deleteFromRealm();
        realm.commitTransaction();
        EventBus.getDefault().post(new FinishedEvent(true, Constants.DELETE));
    }

    @Override
    public void getUniversityById(String id) {
        Realm realm = Realm.getDefaultInstance();
        University result = realm.where(University.class).equalTo(Constants.ID, id).findFirst();
        EventBus.getDefault().post(new FinishedEvent(true, Constants.READ, result));

    }

    @Override
    public void getAllUniversities() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<University> query = realm.where(University.class);
        RealmResults<University> results = query.findAll();

        RealmList <University> universityRealmList = new RealmList<University>();
        universityRealmList.addAll(results);
        EventBus.getDefault().post(new FinishedEvent(true, Constants.READ_ALL, universityRealmList));
        Log.d(TAG, "getAllUniversities: ");
    }

    public static class FinishedEvent {

        University mUniversity;
        RealmList<University> mUniversities;
        boolean isSuccess;
        String mAction;

        public FinishedEvent(boolean isSuccess, String action) {
            this.isSuccess=isSuccess;
            this.mAction=action;
        }

        public FinishedEvent(boolean isSuccess, String action, University university) {
            this.isSuccess=isSuccess;
            this.mAction=action;
            this.mUniversity = university;
        }

        public FinishedEvent(boolean isSuccess, String action, RealmList<University> universities) {
            this.isSuccess=isSuccess;
            this.mAction=action;
            this.mUniversities =universities;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getAction() {
            return mAction;
        }

        public University getUniversity() {
            return mUniversity;
        }

        public RealmList<University> getUniversities() {
            return mUniversities;
        }
    }

}
