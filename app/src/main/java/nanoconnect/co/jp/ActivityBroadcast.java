package nanoconnect.co.jp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityBroadcast extends AppCompatActivity {

    // 他とカブっていなければなんでもいい
    public static final String ACTION_CODE = "nanoconnect.co.jp.activitybroadcast";
    public static final String SEND_CODE = "SEND";

    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;
    private TextView tvText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        // Serviceを起動するためのIntent
        final Intent itService = new Intent(getApplication(), BroadcastService.class);

        // Serviceを起動するButton
        Button btService = (Button) findViewById(R.id.btnBroadcast);
        btService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(itService);
            }
        });

        // 結果を表示するTextView
        tvText =  findViewById(R.id.txtvCount);

        // BroadcastManagerの初期化（引数はContext）
        broadcastManager = LocalBroadcastManager.getInstance(this);
        // BroadcastRecieverの初期化　※BroadcastRecieverを継承した自作クラス
        broadcastReceiver = new testBroadcastReciever();

        // Action名を設定
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_CODE);

        // Recieverの登録
        broadcastManager.registerReceiver(broadcastReceiver, filter);
    }

    // Broadcastを受け取った際の処理
    public class testBroadcastReciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            // Intentから送られてきた値を取得
            String recieveText = intent.getStringExtra(SEND_CODE);

            // 結果を更新
            tvText.setText(recieveText);
            Log.d("ActivityBroadcast", recieveText);
        }
    }


}
