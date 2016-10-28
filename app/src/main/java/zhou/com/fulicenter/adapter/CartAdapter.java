package zhou.com.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhou.com.fulicenter.I;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.bean.CartBean;
import zhou.com.fulicenter.bean.GoodsDetailsBean;
import zhou.com.fulicenter.bean.MessageBean;
import zhou.com.fulicenter.net.NetDao;
import zhou.com.fulicenter.net.OkHttpUtils;
import zhou.com.fulicenter.utils.ImageLoader;
import zhou.com.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/19.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context mContext, ArrayList<CartBean> list) {
        this.mContext = mContext;
        mList = list;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_cart, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        final CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext, holder.mIvCartThumb, goods.getGoodsThumb());
            holder.mTvCartGoodName.setText(goods.getGoodsName());
            holder.mTvCartPrice.setText(goods.getCurrencyPrice());
        }
        holder.mTvCartCount.setText(String.valueOf(cartBean.getCount()));
        holder.mCbCartSelect.setChecked(cartBean.isChecked());
        holder.mCbCartSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cartBean.setChecked(b);
                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
            }
        });
        holder.mIvCartAdd.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<CartBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_cart_selected)
        CheckBox mCbCartSelect;
        @BindView(R.id.iv_cart_thumb)
        ImageView mIvCartThumb;
        @BindView(R.id.tv_cart_good_name)
        TextView mTvCartGoodName;
        @BindView(R.id.tv_cart_price)
        TextView mTvCartPrice;
        @BindView(R.id.iv_cart_del)
        ImageView mIvCartDel;
        @BindView(R.id.tv_cart_count)
        TextView mTvCartCount;
        @BindView(R.id.iv_cart_add)
        ImageView mIvCartAdd;
        @BindView(R.id.layout_cart_detail)
        RelativeLayout mLayoutCartDetail;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.iv_cart_thumb, R.id.tv_cart_good_name, R.id.tv_cart_price})
        public void gotoDetail() {
            final int position = (int) mIvCartAdd.getTag();
            CartBean cart = mList.get(position);
            MFGT.gotoGoodsDetailsActivity(mContext, cart.getGoodsId());
        }

        @OnClick(R.id.iv_cart_add)
        public void addCart() {
            final int position = (int) mIvCartAdd.getTag();
            CartBean cart = mList.get(position);
            NetDao.updateCart(mContext, cart.getId(), cart.getCount() + 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        mList.get(position).setCount(mList.get(position).getCount() + 1);
                        mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                        mTvCartCount.setText(String.valueOf(mList.get(position).getCount()));
                    }

                }

                @Override
                public void onError(String error) {

                }
            });
        }

        @OnClick(R.id.iv_cart_del)
        public void delCart() {
            final int position = (int) mIvCartAdd.getTag();
            CartBean cart = mList.get(position);
            if (cart.getCount() > 1) {
                NetDao.updateCart(mContext, cart.getId(), cart.getCount() - 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            mList.get(position).setCount(mList.get(position).getCount() - 1);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                            mTvCartCount.setText(String.valueOf(mList.get(position).getCount()));
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            } else {
                NetDao.deleteCart(mContext, cart.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            mList.remove(position);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }
    }
}
