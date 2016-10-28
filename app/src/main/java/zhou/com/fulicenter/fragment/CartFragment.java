package zhou.com.fulicenter.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhou.com.fulicenter.FuLiCenterApplication;
import zhou.com.fulicenter.I;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.activity.MainActivity;
import zhou.com.fulicenter.adapter.CartAdapter;
import zhou.com.fulicenter.bean.CartBean;
import zhou.com.fulicenter.bean.UserAvatar;
import zhou.com.fulicenter.net.NetDao;
import zhou.com.fulicenter.net.OkHttpUtils;
import zhou.com.fulicenter.utils.CommonUtils;
import zhou.com.fulicenter.utils.L;
import zhou.com.fulicenter.utils.MFGT;
import zhou.com.fulicenter.utils.ResultUtils;
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

    @BindView(R.id.tv_nothing)
    TextView mTvNothing;
    @BindView(R.id.tv_cart_sum_price)
    TextView mTvCartSumPrice;
    @BindView(R.id.tv_cart_save_price)
    TextView mTvCartSavePrice;
    @BindView(R.id.layout_cart)
    RelativeLayout mLayoutCart;

    updateCartReceiver mReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
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
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATE_CART);
        mReceiver = new updateCartReceiver();
        mContent.registerReceiver(mReceiver, filter);
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
        } else {
            NetDao.downloadCart(mContent, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                    L.e(TAG, "result=" + list);
                    mSrl.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                    if (list != null && list.size() > 0) {
                        L.e(TAG, "list[0]=" + list.get(0));
                        mList.clear();
                        mList.addAll(list);
                        mAdapter.initData(mList);
                        setCartLayout(true);
                    } else {
                        setCartLayout(false);
                    }
                }

                @Override
                public void onError(String error) {
                    setCartLayout(false);
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
        setCartLayout(false);
    }

    @OnClick(R.id.tv_cart_buy)
    public void onClick() {
    }

    public void setCartLayout(boolean hasCart) {
        mLayoutCart.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        mTvNothing.setVisibility(hasCart ? View.GONE : View.VISIBLE);
        mRv.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        sumPrice();
    }

    private void sumPrice() {
        float sumPrice = 0.0f;
        float ranPrice = 0.0f;
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                if (c.isChecked()) {
                    sumPrice += getPrice(c.getGoods().getCurrencyPrice()) * c.getCount();
                    ranPrice += getPrice(c.getGoods().getRankPrice()) * c.getCount();
                }
            }
            mTvCartSumPrice.setText("合计：￥ " + Float.valueOf(ranPrice));
            mTvCartSavePrice.setText("节省：￥ " + Float.valueOf(sumPrice - ranPrice));
        } else {
            mTvCartSavePrice.setText("合计：￥ 0.00");
            mTvCartSavePrice.setText("节省：￥ 0.00 ");
        }
    }

    private float getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Float.valueOf(price);
    }

    class updateCartReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            L.e(TAG, "updateCartReceiver..");
            sumPrice();
            setCartLayout(mList != null && mList.size() > 0);
        }
    }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (mReceiver != null) {
                mContent.unregisterReceiver(mReceiver);
            }
    }
}