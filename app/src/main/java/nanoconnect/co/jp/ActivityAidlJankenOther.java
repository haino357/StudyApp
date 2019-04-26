package nanoconnect.co.jp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
//Aidlのimport
import co.nanoconnect.studyserviceapp.IOtherAIDLJankenService;

public class ActivityAidlJankenOther extends AppCompatActivity {

    // IAIDLSampleServiceのインスタンス
    private IOtherAIDLJankenService aidlJankenServiceIf;

    // UIパーツのインスタンス
    private ImageButton btnGu, btnChoki, btnPa;
    private TextView myHandView, enmHandView, resultView;

    // デバッグ用
    private final String TAG = "AIDLSample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_janken_other);

        Intent intent = getIntent();
        String Bind_Action = intent.getStringExtra("Bind_Action");
        String Package_Name = intent.getStringExtra("Package_Name");

        // IAIDLSampleServiceをバインド
        //Intent intent = new Intent(AIDLJankenService.class.getName());
        //Intent intent = new Intent("nanoconnect.co.jp.Action_Bind");
        intent = new Intent(Bind_Action);
        //intent.setPackage("nanoconnect.co.jp.IAIDLJankenService"); // Android 5.0 Lolipop以降で必須の設定
        intent.setPackage(Package_Name); // Android 5.0 Lolipop以降で必須の設定
        bindService(intent, aidlJankenServiceConn, BIND_AUTO_CREATE);
        //bindService(new Intent(AIDLJankenService.class.getName()), aidlJankenServiceConn, BIND_AUTO_CREATE);


        // UIパーツ取得
        btnGu = findViewById(R.id.imgBtnGuOther);
        btnChoki = findViewById(R.id.imgBtnChokiOther);
        btnPa = findViewById(R.id.imgBtnPaOther);
        myHandView = (TextView) findViewById(R.id.txtvMyHandOther);
        enmHandView = (TextView) findViewById(R.id.txtvComHandOther);
        resultView = (TextView) findViewById(R.id.txtvWin_LossOther);

        // ボタンにクリックリスナーを登録
        btnGu.setOnClickListener(new myBtnClickListener());
        btnChoki.setOnClickListener(new myBtnClickListener());
        btnPa.setOnClickListener(new myBtnClickListener());
    }
    @Override
    protected void onDestroy() {
        // IAIDLSampleServiceをunbind
        unbindService(aidlJankenServiceConn);

        super.onDestroy();
    }
    /**
     * サービスと接続・切断された時の処理
     */
    private ServiceConnection aidlJankenServiceConn = new ServiceConnection() {

        /**
         * サービスに接続された
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // サービスのインターフェースを取得する
            aidlJankenServiceIf = IOtherAIDLJankenService.Stub.asInterface(service);
            Log.d(TAG, "インターフェース取得！");
        }

        /**
         * サービスと切断された
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            aidlJankenServiceIf = null;
            Log.d(TAG, "サービスと切断！");
        }
    };
    /**
     * クリックリスナー実装クラス
     */
    private class myBtnClickListener implements View.OnClickListener {
        // 手の定数
        private final int ROCK = 0, SCISSORS = 1, PAPER = 2;
        // プレイヤの手を持っておく
        int inMyHand;
        /**
         * クリックされた時の挙動
         */
        @Override
        public void onClick(View view) {
            Log.d(TAG, "クリックされたよ！");
            // 自分の手を数値化する
            switch (view.getId()) {
                case R.id.imgBtnGuOther:
                    inMyHand = ROCK;
                    break;
                case R.id.imgBtnChokiOther:
                    inMyHand = SCISSORS;
                    break;
                case R.id.imgBtnPaOther:
                    inMyHand = PAPER;
                    break;
                default:
                    // なんかエラー
                    inMyHand = -1;
            }

            // サービスインターフェースを叩く
            try {
                myHandView.setText(aidlJankenServiceIf.getStrMyHand(inMyHand));
                enmHandView.setText(aidlJankenServiceIf.getStrEnmHand());
                resultView.setText(aidlJankenServiceIf.getStrResult());
            } catch (RemoteException e) {
                // やばいエラー
                Log.d(TAG, "RemoteException");
            } catch (NullPointerException e) {
                // サービスが死んでるとぬるぽが返る
                Log.d(TAG, "ぬるぽ(´・ω・｀)");
            }
        }
    }
}
