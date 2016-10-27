package zhou.com.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhou.com.fulicenter.FuLiCenterApplication;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.activity.MainActivity;
import zhou.com.fulicenter.adapter.BoutiqueAdapter;
import zhou.com.fulicenter.adapter.CartAdapter;
import zhou.com.fulicenter.bean.BoutiqueBean;
import zhou.com.fulicenter.bean.CartBean;
import zhou.com.fulicenter.bean.UserAvatar;
import zhou.com.fulicenter.net.NetDao;
import zhou.com.fulicenter.net.OkHttpUtils;
import zhou.com.fulicenter.utils.CommonUtils;
import zhou.com.fulicenter.utils.ConvertUtils;
import zhou.com.fulicenter.utils.L;
import zhou.com.fulicenter.utils.MFGT;
import zhou.com.fulicenter.views.SpaceItemDecoration;

/**
 * Created by Administrator on 2016/10/19.
 */
public class CartFragment extends BaseFragment {
    private static String TAG = CartFragment.class.getCanonicalName();

    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    LinearLayoutManager llm;
    MainActivity mContent;
    CartAdapter mAdapter;
    ArrayList<CartBean> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        L.e("CartFragment");
        mContent = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new CartAdapter(mContent, mList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void setListener() {
        setPullDownListener();
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                downloadCart();
            }
        });
    }

    @Override
    protected void initData() {
        downloadCart();
    }

    private void downloadCart() {
        UserAvatar user = FuLiCenterApplication.getUser();
        if (user == null) {
            MFGT.gotoLoginActivity(mContent);
        }else {
            NetDao.downloadCart(mContent, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    L.e(TAG,"result="+result);
                    mSrl.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                    if (result != null && result.length > 0) {
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        mAdapter.initData(list);
                    }
                }

                @Override
                public void onError (String error){
                    mSrl.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                    CommonUtils.showShortToast(error);
                    L.e("error" + error);
                }
            });

        }
    }

    @Override
    protected void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green)
        );
        llm = new LinearLayoutManager(mContent);
        mRv.setLayoutManager(llm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
    }
}
