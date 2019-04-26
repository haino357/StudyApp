package nanoconnect.co.jp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /**
     * 「ライフサイクル　Activity」 onClickButton
     * @param view
     */
    public void MoveLifeCycleActivity(View view){
        Intent intent = new Intent(this,ActivityLifeCycleActivity.class);
        intent.putExtra("init",true);
        intent.putExtra("loop",false);
        startActivity(intent);
    }

    /**
     * 「ライフサイクル　Service」 onClickButton
     * @param view
     */
    public void MoveLifeCycleService(View view){
        Intent intent = new Intent(this,ActivityLifeCycleService.class);
        startActivity(intent);
    }

    /**
     * 「Broadcast」  onClickButton
     * @param view
     */
    public void MoveBroadcastCount(View view){
        Intent intent = new Intent(this,ActivityBroadcast.class);
        startActivity(intent);
    }

    /**
     * 「Listener」   onClickButton
     * @param view
     */
    public void MoveListenerTest(View view){
        Intent intent = new Intent(this,ActivityListenerTest.class);
        startActivity(intent);
    }

    /**
     * 「AidlJanken」 onClickButton
     * @param view
     */
    public void MoveAidlJanken(View view){
        Intent intent = new Intent(this,ActivityAidlJanken.class);
        intent.putExtra("Bind_Action","nanoconnect.co.jp.Action_Bind");
        intent.putExtra("Package_Name","nanoconnect.co.jp");
        startActivity(intent);
    }

    /**
     * 「AidlJankenOther」 onClickButton
     * @param view
     */
    public void MoveAidlJankenOther(View view){
        Intent intent = new Intent(this,ActivityAidlJankenOther.class);
        intent.putExtra("Bind_Action","co.nanoconnect.studyserviceapp.Action_Bind");
        intent.putExtra("Package_Name","co.nanoconnect.studyserviceapp");
        startActivity(intent);
    }

    /**
     * 「AidlJankenOther」 onClickButton
     * @param view
     */
    public void MoveParcelable(View view){
        Intent intent = new Intent(this,ActivityParcelAndRecipient.class);
        startActivity(intent);
    }


}
