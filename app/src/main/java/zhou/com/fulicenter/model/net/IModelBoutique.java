package zhou.com.fulicenter.model.net;

import android.content.Context;

import zhou.com.fulicenter.bean.BoutiqueBean;
import zhou.com.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/11/22.
 */

public interface IModelBoutique {
    void downloadBoutique(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener);
}