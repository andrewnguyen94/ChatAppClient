package vn.nguyen.andrew.appchat.fragment.ListView;

/**
 * Created by trunganh on 30/09/2016.
 */
public class ProfileListViewUserWallCustom {
    private String option, value;
    private int imageresource;
    public ProfileListViewUserWallCustom(String option, String value, int imageresource){
        this.option = option;
        this.value = value;
        this.imageresource = imageresource;
    }

    public int getImageresource() {
        return imageresource;
    }

    public String getOption() {
        return option;
    }

    public String getValue() {
        return value;
    }
}
