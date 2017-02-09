package com.demo.simplerealmandroid.db.dao;

import com.demo.simplerealmandroid.db.interfaces.StudentRepository;
import com.demo.simplerealmandroid.model.Student;
import com.demo.simplerealmandroid.model.University;
import com.demo.simplerealmandroid.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by roma on 16.10.15.
 */
public class StudentDao implements StudentRepository {

    @Override
    public void addStudent(Student student) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Student realmStudent = realm.createObject(Student.class,UUID.randomUUID().toString());
        realmStudent.setName(student.getName());
        realmStudent.setBirthday(student.getBirthday());
        realmStudent.setEmail(student.getEmail());
        realm.commitTransaction();
        EventBus.getDefault().post(new FinishedEvent(true, Constants.ADD));
    }

    @Override
    public void addStudentByUniversityId(Student student, String universityId) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Student realmStudent = realm.createObject(Student.class,UUID.randomUUID().toString());
        realmStudent.setName(student.getName());
        realmStudent.setEmail(student.getEmail());
        realmStudent.setBirthday(student.getBirthday());
        University university = realm.where(University.class).equalTo(Constants.ID, universityId).findFirst();
        university.getStudents().add(realmStudent);
        realm.commitTransaction();
        EventBus.getDefault().post(new FinishedEvent(true, Constants.ADD));

    }

    @Override
    public void deleteStudentById(String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Student result = realm.where(Student.class).equalTo(Constants.ID, id).findFirst();
        result.deleteFromRealm();
        realm.commitTransaction();
        EventBus.getDefault().post(new FinishedEvent(true, Constants.DELETE));
    }

    @Override
    public void deleteStudentByPosition(int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery<Student> query = realm.where(Student.class);
        RealmResults<Student> results = query.findAll();
        Student student = results.get(position);
        student.deleteFromRealm();
        realm.commitTransaction();
        EventBus.getDefault().post(new FinishedEvent(true, Constants.DELETE));
    }

    @Override
    public void getAllStudents() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Student> results = realm.where(Student.class).findAll();
        RealmList <Student> studentRealmList = new RealmList<Student>();
        studentRealmList.addAll(results);
        EventBus.getDefault().post(new FinishedEvent(true, Constants.READ_ALL, studentRealmList));
    }

    @Override
    public void getAllStudentsByUniversityId(String id) {
        Realm realm = Realm.getDefaultInstance();
        University university = realm.where(University.class).equalTo(Constants.ID, id).findFirst();
        RealmList<Student> students = university.getStudents();
        EventBus.getDefault().post(new FinishedEvent(true, Constants.READ_ALL, students));

    }

    @Override
    public void getStudentById(String id) {
        Realm realm = Realm.getDefaultInstance();
        Student student = realm.where(Student.class).equalTo(Constants.ID, id).findFirst();
        EventBus.getDefault().post(new FinishedEvent(true, Constants.READ, student));
    }

    public static class FinishedEvent {

        Student mStudent;
        RealmList<Student> mStudents;
        boolean isSuccess;
        String mAction;

        public FinishedEvent(boolean isSuccess, String action) {
            this.isSuccess=isSuccess;
            this.mAction=action;
        }

        public FinishedEvent(boolean isSuccess, String action, Student student) {
            this.isSuccess=isSuccess;
            this.mAction=action;
            this.mStudent = student;
        }

        public FinishedEvent(boolean isSuccess, String action, RealmList<Student> students) {
            this.isSuccess=isSuccess;
            this.mAction=action;
            this.mStudents=students;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getAction() {
            return mAction;
        }

        public Student getStudent() {
            return mStudent;
        }

        public RealmList<Student> getStudents() {
            return mStudents;
        }
    }
}
