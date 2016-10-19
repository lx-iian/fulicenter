package zhou.com.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhou.com.fulicenter.I;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.bean.NewGoodsBean;
import zhou.com.fulicenter.utils.ImageLoader;
import zhou.com.fulicenter.utils.MFGT;
import zhou.com.fulicenter.views.FooterViewHolder;

/**
 * Created by Administrator on 2016/10/17.
 */
public class GoodsAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<NewGoodsBean> mList;
    boolean isMore;

    public GoodsAdapter(Context mContext, List<NewGoodsBean> list) {
        this.mContext = mContext;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder gvh = (FooterViewHolder) holder;
            gvh.tvFooter.setText(getFootString());
        } else {
            GoodsViewHolder gvh = (GoodsViewHolder) holder;
            NewGoodsBean goods = mList.get(position);
            ImageLoader.downloadImg(mContext, gvh.ivGoodsThumb, goods.getGoodsThumb());
            gvh.tvGoodsName.setText(goods.getGoodsName());
            gvh.tvgGoodsPrice.setText(goods.getCurrencyPrice());
            gvh.layoutGoods.setTag(goods.getGoodsId());
        }
    }

    private int getFootString() {

        return isMore ? R.string.load_more : R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvgGoodsPrice)
        TextView tvgGoodsPrice;
        @BindView(R.id.layout_goods)
        LinearLayout layoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick() {
            int goodsId = (int) layoutGoods.getTag();
            MFGT.gotoGoodsDetailsActivity(mContext, goodsId);
        }
    }

}
