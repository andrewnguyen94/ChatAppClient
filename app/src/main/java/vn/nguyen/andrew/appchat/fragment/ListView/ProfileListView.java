package vn.nguyen.andrew.appchat.fragment.ListView;

import android.graphics.Bitmap;

/**
 * Created by trunganh on 22/09/2016.
 */
public class ProfileListView {
    private String text;
    private Bitmap bm;

    public ProfileListView( String text, Bitmap bm){
        this.text = text;
        this.bm = bm;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public int getImage() {
//        return image;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }
}
