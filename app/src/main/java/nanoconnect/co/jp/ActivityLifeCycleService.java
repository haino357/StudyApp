package nanoconnect.co.jp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class ActivityLifeCycleService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_service);
    }

    public void StartService(View view){
        Intent intent = new Intent(getApplication(), LifeCycleService.class);
        startService(intent);
    }

    public void StopService(View view){
        Intent intent = new Intent(getApplication(), LifeCycleService.class);
        // Serviceの停止
        stopService(intent);
    }

    public void BindService(View view){
        doBindService();
    }

    public void UnbindService(View view){
        doUnbindService();
    }

    //取得したServiceの保存
    private LifeCycleService mBoundService;
    private boolean mIsBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {

            // サービスとの接続確立時に呼び出される
            String addLog = "Activity:onServiceConnected";
            showToast(addLog);

            // サービスにはIBinder経由で#getService()してダイレクトにアクセス可能
            mBoundService = ((LifeCycleService.MyServiceLocalBinder)service).getService();

            //必要であればmBoundServiceを使ってバインドしたサービスへの制御を行う
        }

        public void onServiceDisconnected(ComponentName className) {
            // サービスとの切断(異常系処理)
            // プロセスのクラッシュなど意図しないサービスの切断が発生した場合に呼ばれる。
            mBoundService = null;
            String addLog = "Activity:onServiceDisconnected";
            showToast(addLog);
        }
    };

    void doBindService() {
        //サービスとの接続を確立する。明示的にServiceを指定
        //(特定のサービスを指定する必要がある。他のアプリケーションから知ることができない = ローカルサービス)
        bindService(new Intent(ActivityLifeCycleService.this,
                LifeCycleService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            // コネクションの解除
            unbindService(mConnection);
            mIsBound = false;
        }
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
