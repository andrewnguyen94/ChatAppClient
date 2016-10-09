package vn.nguyen.andrew.appchat.fragment.ListView;

import java.io.Serializable;

/**
 * Created by trunganh on 09/10/2016.
 */
public class PopupListView implements Serializable {
    private String content;

    public PopupListView(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
