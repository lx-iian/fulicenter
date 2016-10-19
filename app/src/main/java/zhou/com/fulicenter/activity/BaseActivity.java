package zhou.com.fulicenter.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import zhou.com.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/19.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    protected abstract void initView();
    protected abstract void initData();
    protected abstract void setListener();

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
