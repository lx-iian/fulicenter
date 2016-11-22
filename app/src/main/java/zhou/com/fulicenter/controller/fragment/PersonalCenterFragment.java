package zhou.com.fulicenter.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhou.com.fulicenter.FuLiCenterApplication;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.controller.activity.MainActivity;
import zhou.com.fulicenter.bean.Result;
import zhou.com.fulicenter.bean.UserAvatar;
import zhou.com.fulicenter.model.dao.UserDao;
import zhou.com.fulicenter.model.net.NetDao;
import zhou.com.fulicenter.model.utils.OkHttpUtils;
import zhou.com.fulicenter.model.utils.CommonUtils;
import zhou.com.fulicenter.model.utils.ImageLoader;
import zhou.com.fulicenter.model.utils.L;
import zhou.com.fulicenter.model.utils.MFGT;
import zhou.com.fulicenter.model.utils.ResultUtils;

/**
 * Created by Administrator on 2016/10/24.
 */
public class PersonalCenterFragment extends BaseFragment implements AdapterView.OnItemClickListener {
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
    @BindView(R.id.center_user_order_lis)
    GridView mCenterUserOrderLis;
    SimpleAdapter mAdapter;

    List<Map<String, Object>> dataList;
    int[] icon = {
            R.mipmap.bg_collect_out, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.drawable.center_card_coupon1, R.drawable.center_card_coupon2, R.drawable.center_card_coupon3
    };

    String[] iconName = {"我的收藏","店铺关注","足迹","生活券","网店劵","会员卡"
 /*           getResources().getString(R.string.my_collects),
            getResources().getString(R.string.my_shop_of_collects),
            getResources().getString(R.string.my_footmark),
            getResources().getString(R.string.my_live_coupon),
            getResources().getString(R.string.my_online_store_coupon),
            getResources().getString(R.string.my_vip_card),*/
    };

           /* getResources().getString(R.string.my_collects), getResources().getString(R.string.my_shop_of_collect),
            getResources().getString(R.string.my_footmark), getResources().getString(R.string.my_live_coupon),
            getResources().getString(R.string.my_online_store_coupon), getResources().getString(R.string.my_vip_card)*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getActivity();
        dataList = new ArrayList<>();
        mAdapter = new SimpleAdapter(mContext, getData(), R.layout.item_my_tools, new String[]{"image", "text"},
                new int[]{R.id.iv_tools, R.id.tv_tools});
        mCenterUserOrderLis.setAdapter(mAdapter);
        mCenterUserOrderLis.setOnItemClickListener(this);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            dataList.add(map);
        }
        return dataList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
       /* if (getString(R.string.my_collects).equals(iconName[position])) {
            L.e(TAG, "position=" + position);
            MFGT.gotoCollectsActivity(mContext);
        }*/
        if (user == null) {
            //finish();
            CommonUtils.showLongToast(R.string.login_first);
            MFGT.gotoLoginActivity(mContext);
        }else {
            MFGT.gotoCollectsActivity(mContext);
        }
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

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        user = FuLiCenterApplication.getUser();
        L.e(TAG, "user=" + user);
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
            mIv2QRCode.setVisibility(View.VISIBLE);
            mTvAccountManagement.setVisibility(View.VISIBLE);
            syncUserInfo();
            // 老版本的收藏
            // syncCollectCount();
        }
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

    private void syncUserInfo() {
        NetDao.syncUserInfo(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String string) {
                Result result = ResultUtils.getResultFromJson(string, UserAvatar.class);
                if (result != null) {
                    UserAvatar u = (UserAvatar) result.getRetData();
                    if (!user.equals(u)) {
                        UserDao dao = new UserDao(mContext);
                        boolean b = dao.saveUser(u);
                        if (b) {
                            FuLiCenterApplication.setUser(u);
                            user = u;
                            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvUserAvatar);
                            mTvUserName.setText(user.getMuserNick());
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    // 老版本的收藏
/*    private void syncCollectsCount() {
        NetDao.getCollectsCount(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    mTvUserName.setText(result.getMsg());
                } else {
                    mTvUserName.setText(String.valueOf(0));
                }
            }

            @Override
            public void onError(String error) {
                L.e(TAG, "error= " + error);
            }
        });
    }*/
}