package nanoconnect.co.jp;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityListenerTest extends AppCompatActivity implements Cat.CatListener {

    private Cat cat;
    private String log;

    //View
    ImageView imgCat;
    TextView txtvLog;

    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener_test);

        cat = new Cat();
        // listenerをセット
        cat.setListener(this);
        log = "";

        //View
        imgCat = findViewById(R.id.imgCatListener);
        txtvLog = findViewById(R.id.txtvCatLog);
        txtvLog.setMovementMethod(ScrollingMovementMethod.getInstance());

        //handler = new Handler();

    }

    /**
     * 「ごはんを準備する」　onClickButton
     * @param view
     */
    public void makeRice(View view){
        String status = "ごはんを準備する";
        imgCat.setImageResource(R.drawable.pet_esa_sara_full);
        log = log + "onClickButton" + "\n";
        int image = R.drawable.run_cat_left;
        //スレッドを生成して起動します。
        showToast(status);
        catThread thread = new catThread(log , image, 2000);
        thread.setListener(catViewListener());
        thread.execute( 0);
        //thread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);
        //猫が来る
        //handler.postDelayed(new CatChangeRunnable(log,image),2000);
        cat.eatCat();

    }

    /**
     * 「ログ削除」 onClickButton
     * @param view
     */
    public void catLogDel(View view){
        log = "";
        txtvLog.setText(log);
    }

    @Override
    public void onEatCatListener(String result, int s) {
        String status = "猫が来ました";
        log = log + result;
        //猫が食べている
        int image = R.drawable.cat_pet_esa;
        //スレッドを生成して起動します。
        showToast(status);
        catThread thread = new catThread(log , image, s);
        thread.setListener(catViewListener());
        thread.execute( 0);
        //thread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);
        //handler.postDelayed(new CatChangeRunnable(log,image),s);
        cat.backCat();
    }

    @Override
    public void onBackCatListener(String result, int s) {
        String status = "ご飯を食べています";
        log = log + result;
        //猫が去る
        int image = R.drawable.run_cat_right;
        //スレッドを生成して起動します。
        showToast(status);
        catThread thread = new catThread(log , image, s);
        thread.setListener(catViewListener());
        thread.execute( 0);
        //thread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);
        //handler.postDelayed(new CatChangeRunnable(log,image),s);
        cat.noCat();
    }

    @Override
    public void onNoCatListener(String result, int s) {
        String status = "去っていきました";
        log = log + result;
        //猫は居ない
        //int image = 0;
        int image = R.drawable.pet_esa_sara_empty;
        //スレッドを生成して起動します。
        showToast(status);
        catThread thread = new catThread(log , image, s);
        thread.setListener(catViewListener());
        thread.execute( 0);
        //thread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);
        //handler.postDelayed(new CatChangeRunnable(log,image),s);
    }

    private catThread.Listener catViewListener(){
        return new catThread.Listener(){
            @Override
            public void onSuccess(String log, int image, int s) {
                txtvLog.setText(log);
                if(image == 0){
                    imgCat.setImageDrawable(null);
                } else {
                    imgCat.setImageResource(image);
                }
                //showToast(status);
            }
        };
    }
    /**
     * ライフサイクルを表示するトースト
     * @param text
     */
    private void showToast(String text){
        //Log.i(LOG_TAG, text);
        Toast ts = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        ts.setGravity(Gravity.BOTTOM | Gravity.LEFT , 0, 100);
        ts.show();
    }

    /*class CatChangeRunnable implements Runnable{
        String log;
        int imageResource;

        CatChangeRunnable(String log , int image){
            this.log = log;
            this.imageResource = image;
        }

        @Override
        public void run() {
            txtvLog.setText(log);
            imgCat.setImageResource(imageResource);
        }
    }*/

    static class catThread extends AsyncTask<Integer,Integer,Integer> {
        String log;
        int imageResource;
        int s;
        private Listener listener;

        catThread(String log , int image , int s){
            this.log = log;
            this.imageResource = image;
            this.s = s;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            //時間のかかる処理実行します。
            try {
                //停止します。
                Thread.sleep(s);
            } catch (InterruptedException e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (listener != null) {
                listener.onSuccess(log,imageResource,s);
            }
        }
        void setListener(Listener listener) {
            this.listener = listener;
        }

        interface Listener {
            void onSuccess(String log , int image , int s);
        }
    }
}

class Cat {

    private CatListener listener;

    // interface 設定
    interface CatListener {
        void onEatCatListener(String result , int s);
        void onBackCatListener(String result , int s);
        void onNoCatListener(String result , int s);
    }
    // listener
    void setListener(CatListener listener) {
        this.listener = listener;
    }

    void eatCat() {
        if(listener != null) {
            // listenerに渡す
            listener.onEatCatListener("EatCatListener" + "\n" , 2000);
        }
    }
    void backCat(){
        if(listener != null) {
            // listenerに渡す
            listener.onBackCatListener("BackCatListener" + "\n" , 2000);
        }
    }
    void noCat(){
        if(listener != null) {
            // listenerに渡す
            listener.onNoCatListener("NoCatListener" + "\n\n" , 2000);
        }
    }

}