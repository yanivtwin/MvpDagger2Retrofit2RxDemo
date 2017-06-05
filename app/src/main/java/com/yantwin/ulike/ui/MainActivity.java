package com.yantwin.ulike.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.yantwin.ulike.R;
import com.yantwin.ulike.application.ButtonApplication;
import com.yantwin.ulike.base.ButtonPresenter;
import com.yantwin.ulike.model.ButtonAdapter;
import com.yantwin.ulike.model.ButtonResponse;
import com.yantwin.ulike.service.ButtonService;
import com.yantwin.ulike.service.ButtonViewInterface;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

public class MainActivity extends AppCompatActivity implements ButtonViewInterface, ButtonAdapter.ButtonClickListener {

    @Inject
    ButtonService mService;

    private ButtonPresenter mPresenter;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ButtonAdapter mAdapter;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolveDependency();

        ButterKnife.bind(MainActivity.this);

        configViews();
        mPresenter = new ButtonPresenter(MainActivity.this);
        mPresenter.onCreate();
    }

    private void resolveDependency() {
        ((ButtonApplication) getApplication())
                .getApiComponent()
                .inject(MainActivity.this);
    }

    private void configViews() {
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ButtonAdapter(this, getLayoutInflater());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
        mPresenter.fetchButtons();
        mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setIndeterminate(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setTitle("Downloading List");
        mDialog.setMessage("Please wait...");
        mDialog.show();
    }

    @Override
    public void onCompleted() {
        mDialog.dismiss();
    }

    @Override
    public void onError(String message) {
        mDialog.dismiss();
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.error);
        mp.start();
        Toast.makeText(this,message,
                Toast.LENGTH_SHORT).show();    }

    @Override
    public void onButtons(List<ButtonResponse> ButtonResponses) {
        mAdapter.addButtons(ButtonResponses);
    }

    @Override
    public Observable<List<ButtonResponse>> getButtons() {
        return mService.getButtons();
    }

    @Override
    public void onNonSelected() {

    }

    @Override
    public void onSelected(String type) {

    }


    @Override
    public void onClick(int position, String name) {
        Toast.makeText(getApplicationContext(), "You clicked on " + name, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this,SingleButtonActivity.class));

    }
}
