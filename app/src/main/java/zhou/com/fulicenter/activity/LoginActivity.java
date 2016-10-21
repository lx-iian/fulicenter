package zhou.com.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhou.com.fulicenter.I;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.bean.Result;
import zhou.com.fulicenter.net.NetDao;
import zhou.com.fulicenter.net.OkHttpUtils;
import zhou.com.fulicenter.utils.CommonUtils;
import zhou.com.fulicenter.utils.L;
import zhou.com.fulicenter.utils.MFGT;
import zhou.com.fulicenter.views.DisplayUtils;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.backClickArea)
    LinearLayout mBackClickArea;
    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.register)
    TextView mRegister;

    String username;
    String password;
    LoginActivity mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContent = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this, "用户登陆");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.btn_login, R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkInput();
                break;
            case R.id.register:
                MFGT.gotoRegisterActivity(this);
                break;
        }
    }

    private void checkInput() {
        username = mUsername.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            CommonUtils.showLongToast(R.string.user_name_connot_be_empty);
            mUsername.requestFocus();
            return;
        } else if (TextUtils.isEmpty(password)) {
            CommonUtils.showLongToast(R.string.password_connot_be_empty);
            mPassword.requestFocus();
            return;
        }
        login();
    }

    private void login() {
        final ProgressDialog pd = new ProgressDialog(mContent);
        pd.setMessage(getResources().getString(R.string.login));
        pd.show();
        L.e(TAG, "username=" + username + ", password=" + password);
        NetDao.login(mContent, username, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                L.e(TAG, "result=" + result);
                if (result == null) {
                    CommonUtils.showLongToast(R.string.login_fail);
                } else {
                    if (result.isRetMsg()) {
                        I.User user = (I.User) result.getRetData();
                        L.e(TAG, "user" + user);
                        MFGT.finish(mContent);
                    } else {
                        if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                            CommonUtils.showLongToast(R.string.login_fail_unknow_user);
                        } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                            CommonUtils.showLongToast(R.string.login_fail_unknow_password);
                        } else {
                            CommonUtils.showLongToast(R.string.login_fail);
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(error);
                L.e(TAG, "error=" + error);
            }
        });
    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if (resultCode == RESULT_OK && resultCode == I.REQUEST_COOD_REGISTER) {
            String name = data.getStringExtra(I.User.USER_NAME);
            mUsername.setText(name);
        }
    }
}