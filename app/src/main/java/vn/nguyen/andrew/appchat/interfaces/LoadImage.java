package vn.nguyen.andrew.appchat.interfaces;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.util.Set;

/**
 * Created by trunganh on 11/09/2016.
 */
public interface LoadImage {

    public float readBitmapInfo(int resource);
    public void subSampleImage(int powerOf2, int resource, ImageView image);
    public void loadImage(ImageView image, int resource);
}
