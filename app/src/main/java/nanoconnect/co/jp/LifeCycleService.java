package nanoconnect.co.jp;

import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class LifeCycleService extends android.app.Service {

    static final String TAG="LocalService";

    //サービスに接続するためのBinder
    public class MyServiceLocalBinder extends Binder {
        //サービスの取得
        LifeCycleService getService() {
            return LifeCycleService.this;
        }
    }
    //Binderの生成
    private final IBinder mBinder = new MyServiceLocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        String addLog = "onBind:" + intent;
        showToast(addLog);  //トースト表示
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent){
        String addLog = "onRebind:" + intent;
        showToast(addLog);  //トースト表示
    }

    @Override
    public boolean onUnbind(Intent intent){
        String addLog = "onUnbind:" + intent;
        showToast(addLog);  //トースト表示

        //onUnbindをreturn trueでoverrideすると次回バインド時にonRebildが呼ばれる
        return true;
    }

    @Override
    public void onCreate() {
        String addLog = "onCreate";
        showToast(addLog);  //トースト表示
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand Received start id " + startId + ": " + intent);
        String addLog = "onStartCommand"+ startId + ": " + intent;
        showToast(addLog);  //トースト表示
        // 強制終了時、システムによる再起動を求める場合はSTART_STICKYを利用
        // 再起動が不要な場合はSTART_NOT_STICKYを利用する
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        String addLog = "onDestroy";
        showToast(addLog);  //トースト表示
    }

    /**
     * ライフサイクルを表示するトースト
     * @param text
     */
    private void showToast(String text){
        //Log.i(LOG_TAG, text);
        Toast ts = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        ts.setGravity(Gravity.BOTTOM , 0, 100);
        ts.show();
    }
}
