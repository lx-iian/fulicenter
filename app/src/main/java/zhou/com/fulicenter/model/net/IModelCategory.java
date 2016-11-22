package zhou.com.fulicenter.model.net;

import android.content.Context;

import zhou.com.fulicenter.bean.CategoryChildBean;
import zhou.com.fulicenter.bean.CategoryGroupBean;
import zhou.com.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/11/22.
 */

public interface IModelCategory {
    void downloadCategoryGroup(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener);

    void downloadCategoryChild(Context context, int parentId, OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener);
}
