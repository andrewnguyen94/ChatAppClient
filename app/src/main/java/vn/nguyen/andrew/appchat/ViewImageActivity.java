package vn.nguyen.andrew.appchat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

//import vn.nguyen.andrew.appchat.image.PinchZoomImageView;
import vn.nguyen.andrew.appchat.image.TouchImageView;

public class ViewImageActivity extends Activity implements LoadImage{
    private TouchImageView imageView;
    private String imageBase64;
    private Utilities utilities;
    private Bitmap imagebitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        utilities = new Utilities();
        DisplayMetrics md = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(md);
        int width = md.widthPixels;
        int height = md.heightPixels;

        Bundle args = getIntent().getExtras();
        if(args != null){
            imageBase64 = args.getString(LoginActivity.AVATARBASE64STRING);
        }
        if(!imageBase64.equals("")){
            imagebitmap = utilities.getBitmapFromBase64String(imageBase64);
        }

        imageView = (TouchImageView) findViewById(R.id.imageMain);
//        imageView.getLayoutParams().width = width;

        imageView.setImageBitmap(imagebitmap);
    }

    @Override
    public float readBitmapInfo(int resource) {
        return 0;
    }

    @Override
    public void subSampleImage(int powerOf2, int resource, ImageView image) {

    }

    @Override
    public void loadImage(ImageView image, int resource) {

    }
}
