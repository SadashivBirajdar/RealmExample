package com.demo.simplerealmandroid.db.interfaces;

import com.demo.simplerealmandroid.model.Student;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public interface StudentRepository {

    void addStudent(Student student);

    void addStudentByUniversityId(Student student, String universityId);

    void deleteStudentById(String id);

    void deleteStudentByPosition(int position);

    void getAllStudents();

    void getAllStudentsByUniversityId(String id);

    void getStudentById(String id);

}
