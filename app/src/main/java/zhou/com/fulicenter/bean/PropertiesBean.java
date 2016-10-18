package zhou.com.fulicenter.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/10/13.
 */
public class PropertiesBean implements Serializable {


    /**
     * id : 731
     * goodsId : 6895
     * catId : 345
     * goodsName : IRM05W 幻彩蛹 变色鼠
     * goodsEnglishName : i-rocks 艾芮克
     * goodsBrief : 传说中IG战队用过的鼠标（啊，别问我IG是什么鬼，真的不知道！）。提供更高的精确度及细致度，USB高速侦测模组技术。3D多彩背光DC技术，可依你喜好设定所需颜色。即插即用，无需安装任何软件。
     * shopPrice : ￥399
     * currencyPrice : ￥414
     * promotePrice : ￥0
     * rankPrice : ￥414
     * isPromote : false
     * goodsThumb : 201508/thumb_img/6895_thumb_G_1439355137855.jpg
     * goodsImg : 201508/thumb_img/6895_thumb_G_1439355137855.jpg
     * addTime : 1442419200000
     * shareUrl : http://m.fulishe.com/item/6895
     * properties : [{"id":8514,"goodsId":0,"colorId":4,"colorName":"绿色","colorCode":"#59d85c","colorImg":"201309/1380064997570506166.jpg","colorUrl":"https://cn.shopbop.com/alexa-chung-loretta-romper-ag/vp/v=1/1573999972.htm?fm=search-shopbysize&os=false","albums":[{"pid":6936,"imgId":26104,"imgUrl":"201508/goods_img/6936_P_1439535131675.png","thumbUrl":"no_picture.gif"}]},{"id":8514,"goodsId":0,"colorId":4,"colorName":"绿色","colorCode":"#59d85c","colorImg":"201309/1380064997570506166.jpg","colorUrl":"https://cn.shopbop.com/alexa-chung-loretta-romper-ag/vp/v=1/1573999972.htm?fm=search-shopbysize&os=false","albums":[{"pid":6936,"imgId":26104,"imgUrl":"201508/goods_img/6936_P_1439535131675.png","thumbUrl":"no_picture.gif"}]}]
     * promote : false
     */
    private int id;
    private int goodsId;
    private int colorId;
    private String colorName;
    private String colorCode;
    private String colorImg;
    private String colorUrl;
    private AlbumsBean[] albums;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorImg() {
        return colorImg;
    }

    public void setColorImg(String colorImg) {
        this.colorImg = colorImg;
    }

    public String getColorUrl() {
        return colorUrl;
    }

    public void setColorUrl(String colorUrl) {
        this.colorUrl = colorUrl;
    }

    public AlbumsBean[] getAlbums() {
        return albums;
    }

    public void setAlbums(AlbumsBean[] albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        return "PropertiesBean{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", colorId=" + colorId +
                ", colorName='" + colorName + '\'' +
                ", colorCode='" + colorCode + '\'' +
                ", colorImg='" + colorImg + '\'' +
                ", colorUrl='" + colorUrl + '\'' +
                ", albums=" + Arrays.toString(albums) +
                '}';
    }
}
