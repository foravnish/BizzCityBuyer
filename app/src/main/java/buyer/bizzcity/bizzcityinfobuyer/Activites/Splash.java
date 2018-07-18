package buyer.bizzcity.bizzcityinfobuyer.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Thread background = new Thread() {
            public void run() {
                try {

                    sleep(3*1000);

//                    Intent intent = new Intent(Splash.this, Login.class);
//                    startActivity(intent);
//                    finish();

                    if (MyPrefrences.getUserLogin(Splash.this)==true){
                        Intent intent=new Intent(Splash.this,HomeAct.class);
                        intent.putExtra("type","0");
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(Splash.this, Registration.class);
                        startActivity(intent);
                        finish();
                    }



                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
}
