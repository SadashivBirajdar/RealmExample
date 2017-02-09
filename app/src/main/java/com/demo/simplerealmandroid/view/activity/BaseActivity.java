package com.demo.simplerealmandroid.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.demo.simplerealmandroid.presenter.BasePresenter;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public abstract class BaseActivity extends AppCompatActivity {

    abstract protected void initComponents();

    abstract protected BasePresenter getPresenter();

    public void showMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        resumePresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyPresenter();
    }

    private void resumePresenter() {
        if (getPresenter() != null) {
            getPresenter().onResume();
        }
    }

    private void destroyPresenter() {
        if (getPresenter() != null) {
            getPresenter().onDestroy();
        }
    }
}

