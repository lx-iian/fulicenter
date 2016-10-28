package zhou.com.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhou.com.fulicenter.FuLiCenterApplication;
import zhou.com.fulicenter.I;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.bean.CartBean;
import zhou.com.fulicenter.bean.UserAvatar;
import zhou.com.fulicenter.net.NetDao;
import zhou.com.fulicenter.net.OkHttpUtils;
import zhou.com.fulicenter.utils.L;
import zhou.com.fulicenter.utils.ResultUtils;
import zhou.com.fulicenter.views.DisplayUtils;

public class OrderActivity extends BaseActivity implements PaymentHandler {
    private static String URL = "http://218.244.151.190/demo/charge";

    private static final String TAG = OrderActivity.class.getSimpleName();

    @BindView(R.id.ed_order_name)
    EditText mEdOrderName;
    @BindView(R.id.ed_order_phone)
    EditText mEdOrderPhone;
    @BindView(R.id.spin_order_province)
    Spinner mSpinOrderProvince;
    @BindView(R.id.ed_order_street)
    EditText mEdOrderStreet;
    @BindView(R.id.tv_order_price)
    TextView mTvOrderPrice;
    @BindView(R.id.tv_order_buy)
    TextView mTvOrderBuy;
    OrderActivity mContext;
    String cartIds = "";
    UserAvatar user = null;
    ArrayList<CartBean> mList = null;
    String[] ids = new String[]{};
    float ranPrice = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        //设置需要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "bfb", "jdpay_wap"});

        // 提交数据的格式，默认格式为json
        // PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";

        PingppLog.DEBUG = true;
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, getResources().getString(R.string.confrim_order));
    }

    @Override
    protected void initData() {
        cartIds = getIntent().getStringExtra(I.Cart.ID);
        user = FuLiCenterApplication.getUser();
        L.e(TAG, "cartIds= " + cartIds);
        if (cartIds == null || cartIds.equals("") || user == null) {
            finish();
        }
        ids = cartIds.split(",");
        getOrderList();
    }

    private void getOrderList() {
        NetDao.downloadCart(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String string) {
                ArrayList<CartBean> list = ResultUtils.getCartFromJson(string);
                if (list == null || list.size() == 0) {
                    finish();
                } else {
                    mList.addAll(list);
                    sumPrice();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void sumPrice() {
        ranPrice = 0.0f;
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                L.e(TAG, "c.id=" + c.getId());
                for (String id : ids) {
                    if (id.equals(String.valueOf(c.getId()))) {
                        L.e(TAG, "order.id=" + id);
                        ranPrice += getPrice(c.getGoods().getRankPrice()) * c.getCount();
                    }
                }
            }
        }
        mTvOrderPrice.setText("合计：￥" + Float.valueOf(ranPrice));
    }

    private float getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Float.valueOf(price);
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.tv_order_buy)
    public void checkOrder() {
        String receiveName = mEdOrderName.getText().toString();
        if (TextUtils.isEmpty(receiveName)) {
            mEdOrderName.setError("收货人姓名为空");
            mEdOrderName.requestFocus();
            return;
        }
        String mobile = mEdOrderPhone.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            mEdOrderPhone.setError("手机号码为空");
            mEdOrderPhone.requestFocus();
            return;
        }
        if (!mobile.matches("[\\d]{11}")) {
            mEdOrderPhone.setText("手机号码格式错误");
            mEdOrderPhone.requestFocus();
            return;
        }
        String area = mSpinOrderProvince.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)) {
            Toast.makeText(OrderActivity.this, "收货地区为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = mEdOrderStreet.getText().toString();
        if (TextUtils.isEmpty(address)) {
            mEdOrderStreet.setError("街道地址为空");
            mEdOrderStreet.requestFocus();
            return;
        }
        gotoStatement();
    }

    private void gotoStatement() {
        L.e(TAG, "rankPrice" + ranPrice);

        // 产生个订单号
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());

        // 构建账单json对象
        JSONObject bill = new JSONObject();

        // 自定义的额外信息 选填
        JSONObject extras = new JSONObject();
        try {
            extras.put("extra1", "extra1");
            extras.put("extra2", "extra2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            bill.put("order_no", orderNo);
            bill.put("ranPrice", ranPrice);
            bill.put("extras", extras);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //壹收款: 创建支付通道的对话框
        PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), URL, this);
    }

    @Override
    public void handlePaymentResult(Intent data) {
        if (data != null) {
            /**
             * code：支付结果码  -2:服务端错误、 -1：失败、 0：取消、1：成功
             * error_msg：支付结果信息
             */

            int code = data.getExtras().getInt("code");
            String errorMsg = data.getExtras().getString("error_msg");
        }
    }
}