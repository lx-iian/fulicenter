package zhou.com.fulicenter.model.net;

import android.content.Context;

import zhou.com.fulicenter.I;
import zhou.com.fulicenter.bean.CategoryChildBean;
import zhou.com.fulicenter.bean.CategoryGroupBean;
import zhou.com.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/11/22.
 */

public class ModelCategory implements IModelCategory {
    @Override
    public void downloadCategoryGroup(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener) {
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    @Override
    public void downloadCategoryChild(Context context, int parentId, OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener) {
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID, String.valueOf(parentId))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }
}
