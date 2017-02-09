package com.demo.simplerealmandroid.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.demo.simplerealmandroid.R;
import com.demo.simplerealmandroid.adapter.UniversityAdapter;
import com.demo.simplerealmandroid.model.University;
import com.demo.simplerealmandroid.presenter.BasePresenter;
import com.demo.simplerealmandroid.presenter.UniversityPresenter;
import com.demo.simplerealmandroid.view.dialogs.AddUniversityDialog;

import io.realm.RealmList;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public class UniversityActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = UniversityActivity.class.getSimpleName();
    private RecyclerView mRvUniversities;
    private UniversityAdapter mUniversityAdapter;
    private UniversityPresenter mUniversityPresenter;
    private RealmList<University> mUniversities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);
        mUniversityPresenter = new UniversityPresenter(this);
        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUniversities();
    }

    public void getUniversities() {
        mUniversityPresenter.getAllUniversities();
    }

    @Override
    protected void initComponents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.universities);
        setSupportActionBar(toolbar);
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(
                R.id.fab_add_university);
        actionButton.setOnClickListener(this);
        initRecyclerListener();
    }

    @Override
    protected BasePresenter getPresenter() {
        return mUniversityPresenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_university: {
                showAddUniversityDialog();
                break;
            }
        }
    }

    private void initRecyclerListener() {
        mRvUniversities = (RecyclerView) findViewById(R.id.rv_universities);
        mRvUniversities.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRvUniversities.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mUniversityPresenter.deleteUniversityById(mUniversities.get(viewHolder.getAdapterPosition()).getId());
        //        mUniversityAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(mRvUniversities);
    }

    public void showUniversities(RealmList<University> universities) {
        this.mUniversities = universities;
        mUniversityAdapter = new UniversityAdapter(universities);
        mRvUniversities.setAdapter(mUniversityAdapter);
    }

    private void showAddUniversityDialog() {
        final AddUniversityDialog dialog = new AddUniversityDialog();
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
        dialog.setOnAddUniversityClickListener(new AddUniversityDialog.OnAddUniversityClickListener() {
            @Override
            public void onAddUniversityClickListener(String universityName) {
                dialog.dismiss();
                mUniversityPresenter.addUniversity(universityName);
            }
        });
    }
}
