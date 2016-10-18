package zhou.com.fulicenter.net;

import android.content.Context;

import zhou.com.fulicenter.I;
import zhou.com.fulicenter.bean.NewGoodsBean;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NetDao {
    public static void downloadNewGoods(Context context, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(I.CAT_ID))
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_ID_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    public void temp(Context context) {
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.PAGE_SIZE, String.valueOf(20))
                .targetClass(NewGoodsBean.class)
                .execute(new OkHttpUtils.OnCompleteListener() {
                    @Override
                    public void onSuccess(Object result) {

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
