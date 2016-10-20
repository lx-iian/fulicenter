package zhou.com.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhou.com.fulicenter.I;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.activity.MainActivity;
import zhou.com.fulicenter.adapter.CategoryAdapter;
import zhou.com.fulicenter.bean.CategoryChildBean;
import zhou.com.fulicenter.bean.CategoryGroupBean;
import zhou.com.fulicenter.net.NetDao;
import zhou.com.fulicenter.net.OkHttpUtils;
import zhou.com.fulicenter.utils.ConvertUtils;
import zhou.com.fulicenter.utils.L;

/**
 * Created by Administrator on 2016/10/20.
 */
public class CategoryFragment extends BaseFragment {
    @BindView(R.id.elv_category)
    ExpandableListView mElvCategory;

    CategoryAdapter mAdapter;
    MainActivity mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    int groupCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        mAdapter = new CategoryAdapter(mContext, mGroupList, mChildList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {
        mElvCategory.setGroupIndicator(null);
        mElvCategory.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        downloadGroup();
    }

    private void downloadGroup() {
        NetDao.downloadCategoryGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                L.e("downloadGroup, result=" + result);
                if (result != null && result.length > 0) {
                    ArrayList<CategoryGroupBean> groupList = ConvertUtils.array2List(result);
                    L.e("groupList" + groupList.size());
                    mGroupList.addAll(groupList);
                    for (int i=0;i<groupList.size();i++) {
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        CategoryGroupBean g = groupList.get(i);
                        downloadChild(g.getId(),i);
                    }

                }
            }

            @Override
            public void onError(String error) {
                L.e("error=" + error);
            }
        });

    }

    private void downloadChild(int id,final int index) {
        NetDao.downloadCategoryChild(mContext, id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                L.e("downloadChild,");
                if (result != null && result.length > 0) {
                    ArrayList<CategoryChildBean> childList = ConvertUtils.array2List(result);
                    L.e("groupList" + childList.size());
                    mChildList.set(index,childList);
                    if (groupCount == mGroupList.size()) {
                        mAdapter.initData(mGroupList, mChildList);
                    }
                    if (groupCount == mGroupList.size()) {
                        mAdapter.initData(mGroupList,mChildList);
                    }

                }
            }

            @Override
            public void onError(String error) {
                L.e("error=" + error);
            }
        });
    }

    @Override
    protected void setListener() {

    }
}
