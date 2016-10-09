package vn.nguyen.andrew.appchat;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.nguyen.andrew.appchat.custom.CustomAdapterUserWall;
import vn.nguyen.andrew.appchat.fragment.ProfileFragment;
import vn.nguyen.andrew.appchat.image.MLRoundedImageView;

public class UserWallActivity extends AppCompatActivity implements LoadImage, ImageButton.OnClickListener {
    private String avatar, coverImage, username, user_target_name;
    private Utilities utilities;
    private Bitmap avatarBitmap, coverImageBitmap;
    private ImageButton avatarButton, backButton;
    private TextView usernameText;
    private LinearLayout coverImageLayout;
    private BitmapDrawable coverImageDrawable;
    private MLRoundedImageView ml;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Bundle ProfileFragmentArgs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall);
        utilities = new Utilities();
        ml = new MLRoundedImageView(getApplication());
        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty()){
            avatar = bundle.getString(LoginActivity.AVATARBASE64STRING);
            coverImage = bundle.getString(LoginActivity.COVERIMAGE64STRING);
            username = bundle.getString(LoginActivity.USERNAME);
            user_target_name = bundle.getString(LoginActivity.USERTARGETNAME);
            ProfileFragmentArgs = bundle.getBundle(LoginActivity.BUNDLEPROFILEFRAGMENT);
        }
        avatarButton = (ImageButton) findViewById(R.id.avatar);
        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        coverImageLayout = (LinearLayout) findViewById(R.id.coverImage);
        usernameText = (TextView) findViewById(R.id.username);
        if(!avatar.equals("")){
            avatarBitmap = utilities.getBitmapFromBase64String(avatar);
        }
        if(!coverImage.equals("")){
            coverImageBitmap = utilities.getBitmapFromBase64String(coverImage);
            coverImageDrawable = new BitmapDrawable(getResources(), coverImageBitmap);
        }
        setUserWallImage(avatarBitmap, coverImageDrawable, user_target_name, coverImageLayout, avatarButton, usernameText);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_userwall);
        viewPager = (ViewPager) findViewById(R.id.viewPager_userwall);
        CustomAdapterUserWall customAdapterUserWall = new CustomAdapterUserWall(getSupportFragmentManager(),
                username, user_target_name, getApplication());
        viewPager.setAdapter(customAdapterUserWall);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUserWallImage(Bitmap avatar, BitmapDrawable coverImage, String username,
                                  LinearLayout coverView, ImageButton avaView, TextView usernameText){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            coverView.setBackground(coverImage);
        }
        float radius = utilities.getRadiusOfAvatarImage(getResources());
        int radius_int = (int) radius;
        Bitmap tmp = ml.getCroppedBitmap(avatar, radius_int);
        avaView.getLayoutParams().width = radius_int;
        avaView.getLayoutParams().height = radius_int;
        avaView.setImageBitmap(tmp);
        usernameText.setText(username);
        loadImage(backButton, R.drawable.back_icon_32px);
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
        if(v.getId() == R.id.backButton){
            this.finish();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            ProfileFragment pf = new ProfileFragment();
            pf.setArguments(ProfileFragmentArgs);
            transaction.add(pf, "PF");
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ProfileFragment pf = new ProfileFragment();
        pf.setArguments(ProfileFragmentArgs);
        transaction.add(pf, "PF");
        transaction.commit();
    }

}
