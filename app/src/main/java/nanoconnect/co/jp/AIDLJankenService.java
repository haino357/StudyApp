package nanoconnect.co.jp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Random;

public class AIDLJankenService extends Service {

    // じゃんけんの手の配列
    private final String[] HAND = { "グー", "チョキ", "パー" };
    // じゃんけんの結果の配列
    private final String[] RESULT = { "あいこ！", "あなたの負け！", "あなたの勝ち！" };
    // デバッグ用
    private final String TAG = "AIDLSampleService";

    /**
     * サービスがバインドされた時にコールバックされる
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "バインドされた！");
        showToast("バインドされた");
        // インテントが本物か確認（不要かも？）
        /*if (IAIDLJankenService.class.getName().equals(intent.getAction())) {
            // IADLSampleServiceインターフェースを実装したインスタンスを返す
            return aidlJankenServiceIf;
        }
        return null;*/
        return aidlJankenServiceIf;
    }
    // IAIDLSampleServiceの実装
    IAIDLJankenService.Stub aidlJankenServiceIf = new IAIDLJankenService.Stub() {
        // プレイヤの手とコンピュータの手を持っておく
        int inMyHand, inEnmHand;

        /**
         * 結果を文字列で返す
         */
        @Override
        public String getStrResult() throws RemoteException {
            Log.d(TAG, "結果を返すよ！");
            showToast("結果：" + RESULT[(inMyHand - inEnmHand + 3) % 3]);
            return RESULT[(inMyHand - inEnmHand + 3) % 3];
        }

        /**
         * プレイヤの手の文字列を返す
         */
        @Override
        public String getStrMyHand(int inMyHand) throws RemoteException {
            Log.d(TAG, "プレイヤの手を返すよ！");
            this.inMyHand = inMyHand;
            showToast("プレイヤの手：" + HAND[inMyHand]);
            return HAND[inMyHand];
        }

        /**
         * コンピュータの手を生成し、文字列で返す
         */
        @Override
        public String getStrEnmHand() throws RemoteException {
            Log.d(TAG, "コンピュータの手を返すよ！");
            inEnmHand = new Random().nextInt(2);
            showToast("コンピュータの手：" + HAND[inEnmHand]);
            return HAND[inEnmHand];
        }
    };

    private void showToast(String text){
        //Log.i(LOG_TAG, text);
        Toast ts = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        ts.setGravity(Gravity.BOTTOM , 0, 100);
        ts.show();
    }
}
