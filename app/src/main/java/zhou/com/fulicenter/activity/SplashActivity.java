package zhou.com.fulicenter.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zhou.com.fulicenter.R;
import zhou.com.fulicenter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private final long SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
                //MFGT.finish(SplashActivity.this);
            }
        },SPLASH_TIME);

      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                long costTime = System.currentTimeMillis() - start;
                if (SPLASH_TIME - costTime > 0) {
                    try {
                        Thread.sleep(SPLASH_TIME - costTime );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                // finish会先执行完，以至于会出现桌面
                MFGT.finish(SplashActivity.this);
            }
        }).start();*/

    }
}
