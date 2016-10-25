package zhou.com.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhou.com.fulicenter.FuLiCenterApplication;
import zhou.com.fulicenter.I;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.bean.UserAvatar;
import zhou.com.fulicenter.dao.SharePreferenceUtils;
import zhou.com.fulicenter.utils.CommonUtils;
import zhou.com.fulicenter.utils.ImageLoader;
import zhou.com.fulicenter.utils.L;
import zhou.com.fulicenter.utils.MFGT;
import zhou.com.fulicenter.views.DisplayUtils;

public class AccountManagerActivity extends BaseActivity {

    private static final String TAG = AccountManagerActivity.class.getSimpleName();

    @BindView(R.id.im_user_account_avatar)
    ImageView mIvUserAccountAvatar;
    @BindView(R.id.tv_user_account_name)
    TextView mTvUserAccountName;
    @BindView(R.id.tv_user_account_nick)
    TextView mTvUserAccountNick;

    AccountManagerActivity mContext;
    UserAvatar user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_account_manager);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, getResources().getString(R.string.user_account_manager));
    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            finish();
        }
        showInfo();
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.layout_manager_UserAvatar, R.id.layout_manager_UserName, R.id.layout_manager_Nick, R.id.btnLogout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_manager_UserAvatar:

                break;
            case R.id.layout_manager_UserName:
                CommonUtils.showLongToast(R.string.user_name_connot_be_empty);
                break;
            case R.id.layout_manager_Nick:
                MFGT.gotoUpdateNickActivity(mContext);
                break;
            case R.id.btnLogout:
                logout();
                break;
        }
    }

    private void logout() {
        if (user != null) {
            SharePreferenceUtils.getInstance(mContext).removeUser();
            FuLiCenterApplication.setUser(null);
            L.e(TAG, "logout" + user);
            MFGT.gotoLoginActivity(mContext);
        }
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_NICK) {
            CommonUtils.showLongToast(R.string.update_user_nick_success);
        }
    }

    private void showInfo() {
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvUserAccountAvatar);
            mTvUserAccountName.setText(user.getMuserName());
            mTvUserAccountNick.setText(user.getMuserNick());
        }
    }
}