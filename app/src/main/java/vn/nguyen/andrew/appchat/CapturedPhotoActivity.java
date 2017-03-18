package vn.nguyen.andrew.appchat;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;


import vn.nguyen.andrew.appchat.interfaces.LoadImage;

public class CapturedPhotoActivity extends Activity implements LoadImage {
    private ImageView takenPicture;
    private byte[] BytePicture;
    private Utilities utilities;
    private FrameLayout taken_photo_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captured_photo);

        utilities = new Utilities();
        BytePicture = getIntent().getByteArrayExtra(Camera_layout.PICTURE_BYTE);
        takenPicture = (ImageView) findViewById(R.id.taken_picture);
        taken_photo_layout = (FrameLayout) findViewById(R.id.taken_picture_layout);
        loadImageBitmap(taken_photo_layout, BytePicture);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            taken_photo_layout.setAlpha((float) 0.5);
        }
        loadImage(takenPicture, R.drawable.crop_image);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            takenPicture.setImageAlpha(255);
        }

    }

    @Override
    public float readBitmapInfo(int resource) {
        Resources res = this.getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resource, options);
        float imageHeight = options.outHeight;
        float imageWidth = options.outWidth;
        String imageMimeTypes = options.outMimeType;

        Log.d("Scale Before Load", "w,h,type : " + imageWidth + ", " + imageHeight + ", " + imageMimeTypes);

        return imageHeight * imageWidth * LoginActivity.BYTES_PER_PX / MemUtils.BYTES_IN_MB;
    }

    @Override
    public void subSampleImage(int powerOf2, int resource, ImageView image) {
        if(powerOf2 < 1 || powerOf2 > 32){
            Log.e("Scale Before Load", "Trying to apply upscale or excessive downscale" + powerOf2);
            return;
        }
        Resources res = this.getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = powerOf2;
        Bitmap bitmap = BitmapFactory.decodeResource(res, resource, options);
        image.setImageBitmap(bitmap);
    }

    @Override
    public void loadImage(ImageView image, int resource) {
        if(readBitmapInfo(R.drawable.crop_image) > MemUtils.megabytesFree()){
            subSampleImage(32, R.drawable.crop_image, takenPicture);
        }else{
            image.setImageResource(resource);
        }
    }

    public float readBitmapInfoFromByteArray(byte[] bytes){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(BytePicture, 0, BytePicture.length, options);
        float imageHeight = options.outHeight;
        float imageWidth = options.outWidth;

        String imageMimeTypes = options.outMimeType;

        Log.d("Scale Before Load", "w,h,type : " + imageHeight + ", " + imageHeight + ", " + imageMimeTypes);
        return imageHeight * imageWidth * LoginActivity.BYTES_PER_PX / MemUtils.BYTES_IN_MB;
    }

    public void subSampleImageByteArray(int powerOf2, byte[] bytes, FrameLayout imageLayout){
        if(powerOf2 < 1 || powerOf2 > 32){
            Log.e("Scale Before Load", "Trying to apply upscale or excessive downscale" + powerOf2);
            return;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = powerOf2;
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        BitmapDrawable drawableTmp = new BitmapDrawable(bitmap);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageLayout.setBackground(drawableTmp);
        }
    }

    public void loadImageBitmap(FrameLayout imageLayout, byte[] bytes){
        if(readBitmapInfoFromByteArray(bytes) > MemUtils.megabytesFree()){
            subSampleImageByteArray(32, bytes, imageLayout);
        }else{
            Bitmap tmp = utilities.getBitmapFromByteArray(bytes);
            BitmapDrawable drawableTmp = new BitmapDrawable(tmp);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                imageLayout.setBackground(drawableTmp);
            }
        }
    }

}
