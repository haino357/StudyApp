package nanoconnect.co.jp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ActivityLifeCycleActivity extends AppCompatActivity {


    //View
    private TextView lifeCycleLog;
    private String log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);

        String addLog = "onCreate";
        showToast(addLog);  //トースト表示
        log =  addLog + "\n";

        //初回のみ初期化
        if(getIntent().getBooleanExtra("init", false)){
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edt = pref.edit();
            edt.putString("ActivityLog","");
            edt.apply();
        }

        //ループ時のみ前回のデータの読み込み
        if (getIntent().getBooleanExtra("loop", false)){
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            log = pref.getString("ActivityLog","") + log;
        }

        lifeCycleLog = findViewById(R.id.txtvLifeCycleActivityLog);
        lifeCycleLog.setMovementMethod(ScrollingMovementMethod.getInstance());
        lifeCycleLog.setText(log);
    }

    /**
     * 「再表示　ボタン」 onClickButton
     * @param view
     */
    public void ReShowLifeCycleActivity(View view){
        this.finish();
        Intent intent = new Intent(this, ActivityLifeCycleActivity.class);
        intent.putExtra("init",false);
        intent.putExtra("loop",true);
        startActivity(intent);

    }

    /**
     * 「ログ削除　ボタン」 onClickButton
     * @param view
     */
    public void LogDelLifeCycleActivity(View view){
        log = "";
        lifeCycleLog.setText(log);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edt = pref.edit();
        edt.putString("ActivityLog",log);
        edt.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
        String addLog = "onStart";
        showToast(addLog);  //トースト表示
        log += addLog + "\n";
        lifeCycleLog.setText(log);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String addLog = "onResume";
        showToast(addLog);  //トースト表示
        log += addLog + "\n";
        lifeCycleLog.setText(log);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        String addLog = "onRestart";
        showToast(addLog);  //トースト表示
        log += addLog + "\n";
        lifeCycleLog.setText(log);
    }


    @Override
    public void onPause() {
        super.onPause();
        String addLog = "onPause";
        showToast(addLog);  //トースト表示
        log += addLog + "\n";
        lifeCycleLog.setText(log);
    }

    @Override
    public void onStop() {
        super.onStop();
        String addLog = "onStop";
        showToast(addLog);  //トースト表示
        log += addLog + "\n";
        lifeCycleLog.setText(log);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        String addLog = "onDestroy";
        showToast(addLog);  //トースト表示
        log += addLog + "\n";
        lifeCycleLog.setText(log);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edt = pref.edit();
        edt.putString("ActivityLog",log);
        edt.apply();
    }

    /**
     * ライフサイクルを表示するトースト
     * @param text
     */
    private void showToast(String text){
        Log.i("ActivityLifeCycle", text);
        Toast ts = Toast.makeText(this, "Activity\n" + text, Toast.LENGTH_SHORT);
        ts.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 30, 50);
        ts.show();
    }
}
