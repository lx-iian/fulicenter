package zhou.com.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhou.com.fulicenter.FuLiCenterApplication;
import zhou.com.fulicenter.I;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.bean.Result;
import zhou.com.fulicenter.bean.UserAvatar;
import zhou.com.fulicenter.dao.SharePreferenceUtils;
import zhou.com.fulicenter.net.NetDao;
import zhou.com.fulicenter.net.OkHttpUtils;
import zhou.com.fulicenter.utils.CommonUtils;
import zhou.com.fulicenter.utils.ImageLoader;
import zhou.com.fulicenter.utils.L;
import zhou.com.fulicenter.utils.MFGT;
import zhou.com.fulicenter.utils.OnSetAvatarListener;
import zhou.com.fulicenter.utils.ResultUtils;
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
    OnSetAvatarListener mOnSetAvatarListener;

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
                mOnSetAvatarListener = new OnSetAvatarListener(mContext, R.id.layout_update_avatar,
                        user.getMuserName(), I.AVATAR_TYPE_USER_PATH);
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
        if (resultCode != RESULT_OK) {
            return;
        }
        mOnSetAvatarListener.setAvatar(requestCode, data, mIvUserAccountAvatar);
        if (requestCode == I.REQUEST_CODE_NICK) {
            CommonUtils.showLongToast(R.string.update_user_nick_success);
        }
        if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            updateAvatar();
        }
    }

    private void updateAvatar() {
        File file = new File(OnSetAvatarListener.getAvatarPath(mContext,
                user.getMavatarPath() + "/" + user.getMuserName()
                        + I.AVATAR_SUFFIX_JPG));
        L.e("file=" + file.exists());
        L.e("file=" + file.getAbsolutePath());
        NetDao.updateAvatar(mContext, user.getMuserName(), file, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String string) {
                L.e("s=" + string);
                Result result = ResultUtils.getResultFromJson(string, UserAvatar.class);
                L.e("result=" + result);
                final ProgressDialog pd = new ProgressDialog(mContext);
                pd.setMessage(getResources().getString(R.string.update_user_avatar));
                pd.show();
                if (result == null) {
                    CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                } else {
                    UserAvatar u = (UserAvatar) result.getRetData();
                    if (result.isRetMsg()) {
                        FuLiCenterApplication.setUser(u);
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(u), mContext, mIvUserAccountAvatar);
                        CommonUtils.showLongToast(R.string.update_user_avatar_success);
                    } else {
                        CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                L.e("error=" + error);
            }
        });
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