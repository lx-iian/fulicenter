package zhou.com.fulicenter.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhou.com.fulicenter.I;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.utils.L;
import zhou.com.fulicenter.views.DisplayUtils;

public class OrderActivity extends BaseActivity {

    private static final String TAG = OrderActivity.class.getSimpleName();

    @BindView(R.id.rd_order_name)
    EditText rdOrderName;
    @BindView(R.id.rd_order_phone)
    EditText rdOrderPhone;
    @BindView(R.id.spin_order_province)
    Spinner spinOrderProvince;
    @BindView(R.id.rd_order_street)
    EditText rdOrderStreet;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_order_buy)
    TextView tvOrderBuy;
    OrderActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, getResources().getString(R.string.collect_title));
        String cartIds = getIntent().getStringExtra(I.Cart.ID);
        L.e(TAG, "cartIds= " + cartIds);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
