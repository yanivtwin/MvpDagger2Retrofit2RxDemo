package com.yantwin.ulike.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.florent37.viewanimator.ViewAnimator;
import com.yantwin.ulike.R;
import com.yantwin.ulike.application.ButtonApplication;
import com.yantwin.ulike.base.ButtonPresenter;
import com.yantwin.ulike.base.FilterPresentor;
import com.yantwin.ulike.model.ButtonResponse;
import com.yantwin.ulike.service.ButtonService;
import com.yantwin.ulike.service.ButtonViewInterface;
import com.yantwin.ulike.service.FilterService;
import com.yantwin.ulike.service.NavigationCommand;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class SingleButtonActivity extends AppCompatActivity implements  ButtonViewInterface ,NavigationCommand{
    @Inject
    ButtonService mService;
    FilterService mFilService;
    private ButtonPresenter mPresenter;
    private FilterPresentor mFilterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_button);
        resolveDependency();

        ButterKnife.bind(SingleButtonActivity.this);
        mPresenter = new ButtonPresenter(SingleButtonActivity.this);
        mFilterPresenter = new FilterPresentor(SingleButtonActivity.this);
        mPresenter.onCreate();
        mFilterPresenter.onCreate();

    }
    public boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
        mFilterPresenter.onResume();

    }

    @Override
    public void onCompleted() {
    }
    @OnClick(R.id.single_button)
    public void OnClickSingle() {
        mPresenter.fetchButtons();

    }
    @OnClick(R.id.goto_button)
    public void onClicGoTo() {
        navigate(new Intent(SingleButtonActivity.this,MainActivity.class));

    }
    @Override
    public void onError(String message) {
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.error);
        mp.start();
        Toast.makeText(this,message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtons(List<ButtonResponse> ButtonResponses) {
        mFilterPresenter.getDoFilter(ButtonResponses , isInternetAvailable());

    }

    @Override
    public Observable<List<ButtonResponse>> getButtons() {


        return mService.getButtons();

    }



    @Override
    public void navigate(Intent intent) {
        startActivity(intent);
    }
    private void resolveDependency() {
        ((ButtonApplication) getApplication())
                .getApiComponent()
                .inject(SingleButtonActivity.this);
    }



    @Override
    public void onNonSelected() {
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.error);
        mp.start();
        Toast.makeText(this,"Everything failed , no action selected...",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSelected(String type) {
        switch (type){
            case "toast":
                Toast.makeText(this,"Current Action is Toast",
                        Toast.LENGTH_SHORT).show();
                break;
            case "call":
                navigate(new Intent().setAction("android.intent.action.DIAL"));
                break;
            case "notification":
                PendingIntent contentIntent =
                        PendingIntent.getActivity(this, 0, new Intent().setAction("android.intent.action.DIAL"), 0);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this)
                                .setContentTitle("Current action is Notification").setContentIntent(contentIntent)
                                .setSmallIcon(R.mipmap.ic_launcher)
                        ;
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                // Will display the notification in the notification bar
                notificationManager.notify(1, mBuilder.build());
                break;
            case "animation":

                ViewAnimator viewAnimator = ViewAnimator
                        .animate(findViewById(R.id.single_button))
                        .rotation(360)

                        .start();
                break;
        }
    }
}
