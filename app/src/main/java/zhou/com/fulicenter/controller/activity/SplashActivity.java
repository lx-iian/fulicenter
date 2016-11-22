package zhou.com.fulicenter.controller.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zhou.com.fulicenter.FuLiCenterApplication;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.bean.UserAvatar;
import zhou.com.fulicenter.model.dao.SharePreferenceUtils;
import zhou.com.fulicenter.model.dao.UserDao;
import zhou.com.fulicenter.model.utils.L;
import zhou.com.fulicenter.model.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private final long SPLASH_TIME = 2000;
    SplashActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserAvatar user = FuLiCenterApplication.getUser();
                L.e(TAG,"fulicenter, user=" + user);
                String username = SharePreferenceUtils.getInstance(mContext).getUser();
                L.e(TAG,"fulicenter, username=" + username);
                if (user == null && username != null) {
                    UserDao dao = new UserDao(mContext);
                    user = dao.getUser(username);
                    L.e(TAG, "DATABASE, user=" + user);
                    if(user != null){
                        FuLiCenterApplication.setUser(user);
                    }
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
                //MFGT.finish(SplashActivity.this);
            }
        }, SPLASH_TIME);

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
