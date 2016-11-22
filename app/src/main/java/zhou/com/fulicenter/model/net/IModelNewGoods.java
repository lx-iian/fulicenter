package zhou.com.fulicenter.model.net;

import android.content.Context;

import zhou.com.fulicenter.bean.NewGoodsBean;
import zhou.com.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/11/22.
 */

public interface IModelNewGoods {
    void downloadNewGoods(Context context, int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener);
}
