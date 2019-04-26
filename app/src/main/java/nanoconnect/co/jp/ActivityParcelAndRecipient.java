package nanoconnect.co.jp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.nanoconnect.studyserviceapp.ParcelableRecipientTestService;

public class ActivityParcelAndRecipient extends AppCompatActivity {

    private IBinder mBinder;
    // Aidlのインスタンス
    private ParcelableRecipientTestService parceRecipiService;

    private final String Bind_Action = "co.nanoconnect.studyserviceapp.Parcel_Test";
    private final String Package_Name = "co.nanoconnect.studyserviceapp";

    //View
    Button btnBind,btnUnBind,btnSend;
    EditText edtUser,edtMail,edtURL;
    TextView txtvUser,txtvMail,txtvURL,txtvUrlContent;

    private Boolean mIsBound;

    // デバッグ用
    private final String LOG_TAG = "AIDL&Parcelable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_and_recipient);
        //init
        mIsBound = false;

        //View
        btnBind = findViewById(R.id.btnParceBind);
        btnUnBind = findViewById(R.id.btnParceUnBind);
        //btnUnBind.setEnabled(false);
        btnSend = findViewById(R.id.btnParceSend);
        //btnSend.setEnabled(false);
        //EditText
        edtUser = findViewById(R.id.edtUserName);
        edtMail = findViewById(R.id.edtMail);
        edtURL = findViewById(R.id.edtURL);
        //TextView
        txtvUser = findViewById(R.id.txtvUserName);
        txtvMail = findViewById(R.id.txtvMail);
        txtvURL = findViewById(R.id.txtcURL);
        txtvUrlContent = findViewById(R.id.txtvUrlContent);

    }

    @Override
    protected void onDestroy() {
        if (mIsBound) {
            // コネクションの解除
            unbindService(mServiceConect);
            mIsBound = false;
        }
        super.onDestroy();
    }



    /**
     * 「Bind Service」のOnClickButton
     * @param view
     */
    public void ParcelBindService(View view){
        showToast("サービスとの接続を実行");
        Intent intent = new Intent(Bind_Action);
        //intent.setPackage("nanoconnect.co.jp.IAIDLJankenService"); // Android 5.0 Lolipop以降で必須の設定
        intent.setPackage(Package_Name); // Android 5.0 Lolipop以降で必須の設定
        bindService(intent, mServiceConect, BIND_AUTO_CREATE);
        mIsBound = true;
    }

    /**
     * 「UnBind Service」のOnClickButton
     * @param view
     */
    public void ParcelUnBindService(View view){
        if (mIsBound) {
            showToast("サービスとの接続解除を実行");
            // コネクションの解除
            try {
                unbindService(mServiceConect);
                parceRecipiService = null;
            } catch (IllegalStateException e) {
                showToast("IllegalStateException");
                e.printStackTrace();
            }
            mIsBound = false;
        }
    }

    /**
     * 「送信」のOnClickButton
     * @param view
     */
    public void ParcelSend(View view) throws RemoteException {
        //Bindで設定するかも？
        //向こうの関数を実行してデータを渡す？
        String name = edtUser.getText().toString();
        String mail = edtMail.getText().toString();
        String URL = edtURL.getText().toString();

        PersonURL personURL = new PersonURL(name,mail,URL);
        Bundle bundleData = new Bundle();
        bundleData.putParcelable("personData",personURL);

        // サービスインターフェースを叩く
        try {
            txtvUser.setText(parceRecipiService.getParceUser(name));
            txtvMail.setText(parceRecipiService.getParceMail(mail));
            txtvURL.setText(parceRecipiService.getParceURL(URL));
            txtvUrlContent.setText(parceRecipiService.getParceUrlContent(URL));

        } catch (RemoteException e) {
            // やばいエラー
            Log.d(LOG_TAG, "RemoteException");
        } catch (NullPointerException e) {
            // サービスが死んでるとぬるぽが返る
            showToast("サービスと接続されていません");
            Log.d(LOG_TAG, "ぬるぽ(´・ω・｀)");
        }


    }

    /**
     * プロセスの死亡の確認
     */
    IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBinder != null)
                mBinder.unlinkToDeath(this, 0);
            //showToast("mDeathRecipient:binderDied");
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast ts = Toast.makeText(getApplicationContext(), "DeathRecipient : binderDied", Toast.LENGTH_SHORT);
                    ts.setGravity(Gravity.BOTTOM, 0, 100);
                    ts.show();
                }
            });
        }
    };

    /**
     * サービスと接続・切断された時の処理
     */
    private ServiceConnection mServiceConect = new ServiceConnection() {

        /**
         * サービスに接続された
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = service;
            try {
                mBinder.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            // サービスのインターフェースを取得する
            parceRecipiService = ParcelableRecipientTestService.Stub.asInterface(service);
            /*btnBind.setEnabled(false);
            btnUnBind.setEnabled(true);
            btnSend.setEnabled(true);
            */
            Log.d(LOG_TAG, "onServiceConnected\nインターフェース取得！");
        }

        /**
         * サービスが強制切断された(意図せず切断された場合のみ実行)
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            parceRecipiService = null;
            /*btnBind.setEnabled(true);
            btnUnBind.setEnabled(false);
            btnSend.setEnabled(false);*/
            Log.d(LOG_TAG, "onServiceDisconnected\nサービスと強制切断");
        }
    };

    /**
     * トースト表示用
     * @param text
     */
    private void showToast(String text){
        //Log.i(LOG_TAG, text);
        Toast ts = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        ts.setGravity(Gravity.BOTTOM , 0, 100);
        ts.show();
    }
}
