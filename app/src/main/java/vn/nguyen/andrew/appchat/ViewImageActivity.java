package vn.nguyen.andrew.appchat;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import vn.nguyen.andrew.appchat.image.TouchImageView;

public class ViewImageActivity extends AppCompatActivity implements LoadImage, View.OnClickListener{
    private TouchImageView imageView;
    private String imageBase64, username;
    private Utilities utilities;
    private Bitmap imagebitmap;
    private int totalImage, imageId;
    private ImageButton backButtonImageView;
    private TextView usernameImageView, image_info;
    private boolean isShowActionBar = true;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

//custom ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b000000")));
        LayoutInflater inflater = LayoutInflater.from(this);
        View mCustomView = inflater.inflate(R.layout.custom_actionbar_imageview, null);
        ActionBar.LayoutParams layoutParams = new  ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        actionBar.setCustomView(mCustomView, layoutParams);
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
//
        utilities = new Utilities();
        DisplayMetrics md = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(md);
        int width = md.widthPixels;
        int height = md.heightPixels;

        Bundle args = getIntent().getExtras();
        if(args != null){
            username = args.getString(LoginActivity.USERNAME);
            imageId = args.getInt(LoginActivity.IMAGEID);
            totalImage = args.getInt(LoginActivity.TOTALIMAGE);
            imageBase64 = args.getString(LoginActivity.AVATARBASE64STRING);
        }
//setCustomActionBar
        backButtonImageView = (ImageButton) mCustomView.findViewById(R.id.backButtonImageView);
        backButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        usernameImageView = (TextView) mCustomView.findViewById(R.id.usernameImageView);
        image_info = (TextView) mCustomView.findViewById(R.id.image_info);
        int tmp = (int) getResources().getDimension(R.dimen.actionbar_imageview);
        backButtonImageView.getLayoutParams().width = tmp;
        loadImage(backButtonImageView, R.drawable.back_icon_32px);
        usernameImageView.setText(username);
        String imageInfo = String.valueOf(imageId) + "/" + String.valueOf(totalImage);
        image_info.setText(imageInfo);

//
        if(!imageBase64.equals("")){
            imagebitmap = utilities.getBitmapFromBase64String(imageBase64);
        }

        imageView = (TouchImageView) findViewById(R.id.imageMain);

        imageView.setImageBitmap(imagebitmap);
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
        if(readBitmapInfo(resource) > MemUtils.megabytesFree()){
            subSampleImage(32, resource, image);
        }else{
            Bitmap resBitmap = BitmapFactory.decodeResource(getResources(), resource);
            image.setImageBitmap(resBitmap);
        }
    }

    @Override
    public void onClick(View v) {

    }


}
