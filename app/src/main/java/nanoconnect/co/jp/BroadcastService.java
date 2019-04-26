package nanoconnect.co.jp;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class BroadcastService extends android.app.Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private Thread thread;
    private Handler handler;
    private Runnable runnable;
    private Intent intent;
    private int count = 0;

    // 送信用のBoradcastManager
    private LocalBroadcastManager broadcastManager;

    @Override
    public void onCreate () {
        super.onCreate();
        String addLog = "onCreate";
        showToast(addLog);  //トースト表示

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                count ++;
                if (count > 10) {

                    stopService();
                    return;
                }

                // Broadcastを送信
                sendBroadcast(count);
                Log.d("Service Log.", String.valueOf(count));

                handler.postDelayed(runnable, 1000);
            }
        };

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                handler.post(runnable);
            }
        });

        // LocalBroadcastManagerの初期化（引数はContext）
        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand (Intent intent, int flag, int id) {
        super.onStartCommand(intent, flag, id);
        String addLog = "onStartCommand";
        showToast(addLog);  //トースト表示

        this.intent = intent;
        thread.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        String addLog = "onDestroy";
        showToast(addLog);  //トースト表示
    }

    private void stopService () {

        stopService(this.intent);
    }

    // Broadcastを送信
    private void sendBroadcast (int count) {

        // 送信するAction名を指定
        Intent intent = new Intent(ActivityBroadcast.ACTION_CODE);
        // Intentに送信する変数を格納
        intent.putExtra(ActivityBroadcast.SEND_CODE, String.valueOf(count));

        // Broadcastを送信
        broadcastManager.sendBroadcast(intent);
    }

    /**
     * ライフサイクルを表示するトースト
     * @param text
     */
    private void showToast(String text){
        //Log.i(LOG_TAG, text);
        Toast ts = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        ts.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 30, 50);
        ts.show();
    }


}
