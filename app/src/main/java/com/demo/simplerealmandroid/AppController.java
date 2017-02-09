package com.demo.simplerealmandroid;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public class AppController extends Application {

    private static AppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name(
                "sample.realm").schemaVersion(0).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static AppController getInstance() {
        return instance;
    }
}
