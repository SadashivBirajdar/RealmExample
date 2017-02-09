package com.demo.simplerealmandroid.db.interfaces;

import com.demo.simplerealmandroid.model.University;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public interface UniversityRepository {

    void addUniversity(University university);

    void deleteUniversityById(String Id);

    void deleteUniversityByPosition(int position);

    void getAllUniversities();

    void getUniversityById(String id);
}
