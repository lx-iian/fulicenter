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
        UserAvatar user = FuLiCenterApplication.getUser();
        L.e(TAG, "user=" + user);
        if (user == null) {
            MFGT.gotoLoginActivity(mContext);
        } else {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
        }
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.tvCenterSetting)
    public void onClick() {
    }
}
