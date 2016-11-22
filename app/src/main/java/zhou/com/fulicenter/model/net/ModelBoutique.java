package zhou.com.fulicenter.model.net;

import android.content.Context;

import zhou.com.fulicenter.I;
import zhou.com.fulicenter.bean.BoutiqueBean;
import zhou.com.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/11/22.
 */

public class ModelBoutique implements IModelBoutique {

    @Override
    public void downloadBoutique(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
}
