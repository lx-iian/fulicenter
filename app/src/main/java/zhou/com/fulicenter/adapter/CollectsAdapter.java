package zhou.com.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhou.com.fulicenter.FuLiCenterApplication;
import zhou.com.fulicenter.I;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.bean.CollectBean;
import zhou.com.fulicenter.bean.MessageBean;
import zhou.com.fulicenter.bean.NewGoodsBean;
import zhou.com.fulicenter.net.NetDao;
import zhou.com.fulicenter.net.OkHttpUtils;
import zhou.com.fulicenter.utils.CommonUtils;
import zhou.com.fulicenter.utils.ImageLoader;
import zhou.com.fulicenter.utils.L;
import zhou.com.fulicenter.utils.MFGT;
import zhou.com.fulicenter.views.FooterViewHolder;

/**
 * Created by Administrator on 2016/10/17.
 */
public class CollectsAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<CollectBean> mList;
    boolean isMore;

    public CollectsAdapter(Context mContext, List<CollectBean> list) {
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
            holder = new CollectsViewHolder(View.inflate(mContext, R.layout.item_collects, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder gvh = (FooterViewHolder) holder;
            gvh.tvFooter.setText(getFootString());
        } else {
            CollectsViewHolder gvh = (CollectsViewHolder) holder;
            CollectBean collects = mList.get(position);
            ImageLoader.downloadImg(mContext, gvh.ivGoodsThumb, collects.getGoodsThumb());
            gvh.tvGoodsName.setText(collects.getGoodsName());
            gvh.layoutGoods.setTag(collects);
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

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class CollectsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvgGoodsPrice;
        @BindView(R.id.iv_collect_delete)
        ImageView ivCollectDelete;
        @BindView(R.id.layout_goods)
        RelativeLayout layoutGoods;

        CollectsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick() {
            CollectBean collects = (CollectBean) layoutGoods.getTag();
            MFGT.gotoGoodsDetailsActivity(mContext, collects.getId());
        }

        @OnClick(R.id.iv_collect_delete)
        public void deleleCollect() {
            final CollectBean collects = (CollectBean) layoutGoods.getTag();
            String username = FuLiCenterApplication.getUser().getMuserName();
            NetDao.deleteCollect(mContext, username, collects.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        mList.remove(collects);
                        notifyDataSetChanged();
                    } else {
                        CommonUtils.showLongToast(result != null ? result.getMsg() : mContext.getResources().getString(R.string.delete_collect_fail));
                    }
                }

                @Override
                public void onError(String error) {
                    L.e("error=" + error);
                    CommonUtils.showLongToast(mContext.getResources().getString(R.string.delete_collect_fail));
                }
            });
        }
    }

}
