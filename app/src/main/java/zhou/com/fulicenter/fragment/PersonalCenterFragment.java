package zhou.com.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhou.com.fulicenter.FuLiCenterApplication;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.activity.MainActivity;
import zhou.com.fulicenter.bean.UserAvatar;
import zhou.com.fulicenter.utils.ImageLoader;
import zhou.com.fulicenter.utils.L;
import zhou.com.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/24.
 */
public class PersonalCenterFragment extends BaseFragment {
    private static final String TAG = PersonalCenterFragment.class.getSimpleName();

    @BindView(R.id.ivUserAvatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tvUserName)
    TextView mTvUserName;

    MainActivity mContext;
    @BindView(R.id.iv2QR_Code)
    ImageView mIv2QRCode;
    @BindView(R.id.tvAccountManagement)
    TextView mTvAccountManagement;

    UserAvatar user = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        L.e(TAG, "user=" + user);
        if (user == null) {
            mTvUserName.setText(R.string.user_account_name_normal);
            mTvAccountManagement.setVisibility(View.INVISIBLE);
        } else {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
            mIv2QRCode.setVisibility(View.VISIBLE);
            mTvAccountManagement.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setListener() {
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }


/*
    @OnClick({R.id.tvCenterSetting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCenterSetting:
                break;

        }
    }*/


    @OnClick({R.id.tvCenterSetting, R.id.tvAccountManagement, R.id.ivUserAvatar, R.id.tvUserName})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCenterSetting:
                break;
            case R.id.tvAccountManagement:
                gotoAccountManager();
                break;
            case R.id.ivUserAvatar:
                if (user == null) {
                    gotoLogin();
                    return;
                }
                gotoAccountManager();
                break;
            case R.id.tvUserName:
                if (user != null) {
                    gotoAccountManager();
                } else {
                    gotoLogin();
                }
                break;
        }
    }

    public void gotoAccountManager() {
        MFGT.gotoAccountManagerActivity(mContext);
    }

    public void gotoLogin() {
        MFGT.gotoLoginActivity(mContext);
    }

/*    @OnClick({R.id.tvAccountManagement, R.id.ivUserAvatar, R.id.tvUserName})
    public void gotoAccountManager(View view) {
        MFGT.gotoAccountManagerActivity(mContext);
    }

    @OnClick({R.id.ivUserAvatar, R.id.tvUserName})
    public void gotoLogin(View view) {
        if (user == null) {
            MFGT.gotoLoginActivity(mContext);
        } else {
            MFGT.gotoAccountManagerActivity(mContext);
        }
    }*/
}