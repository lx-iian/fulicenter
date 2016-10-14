package zhou.com.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class CategoryGroup {
    /**
     * id : 344
     * name : 最IN
     * imageUrl : muying/2.jpg
     */

    private int id;
    private String name;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CategoryGroup() {
        super();
    }

    @Override
    public String toString() {
        return "CategoryGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
