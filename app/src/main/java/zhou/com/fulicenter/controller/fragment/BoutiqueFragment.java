package zhou.com.fulicenter.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.bean.BoutiqueBean;
import zhou.com.fulicenter.controller.activity.MainActivity;
import zhou.com.fulicenter.controller.adapter.BoutiqueAdapter;
import zhou.com.fulicenter.model.net.IModelBoutique;
import zhou.com.fulicenter.model.net.ModelBoutique;
import zhou.com.fulicenter.model.net.OnCompleteListener;
import zhou.com.fulicenter.model.utils.CommonUtils;
import zhou.com.fulicenter.model.utils.ConvertUtils;
import zhou.com.fulicenter.model.utils.L;
import zhou.com.fulicenter.view.SpaceItemDecoration;

/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueFragment extends BaseFragment {
    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    LinearLayoutManager llm;
    MainActivity mContent;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;

    IModelBoutique mModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mModel = new ModelBoutique();
        L.e("BoutiqueFragment");
        mContent = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new BoutiqueAdapter(mContent, mList);
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
                downloadBoutique();
            }
        });
    }

    @Override
    protected void initData() {
        downloadBoutique();
    }

    private void downloadBoutique() {
        mModel.downloadBoutique(mContent, new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                L.e("result=" + result);
                if (result != null && result.length > 0) {
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    mAdapter.initData(list);
                }
            }

            @Override
            public void onError(String error) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                CommonUtils.showShortToast(error);
                L.e("error" + error);
            }
        });
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
