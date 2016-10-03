package vn.nguyen.andrew.appchat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.nguyen.andrew.appchat.custom.CustomAdapterUserWall;
import vn.nguyen.andrew.appchat.image.MLRoundedImageView;

public class UserWallActivity extends AppCompatActivity {
    private String avatar, coverImage, username, user_target_name;
    private Utilities utilities;
    private Bitmap avatarBitmap, coverImageBitmap;
    private ImageButton avatarButton;
    private TextView usernameText;
    private LinearLayout coverImageLayout;
    private BitmapDrawable coverImageDrawable;
    private MLRoundedImageView ml;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String[] tabLayout_Titles;

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
        }
        avatarButton = (ImageButton) findViewById(R.id.avatar);
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
        viewPager = (ViewPager) findViewById(R.id.viewPager_userwall);
        CustomAdapterUserWall customAdapterUserWall = new CustomAdapterUserWall(getSupportFragmentManager(), username, user_target_name);
        viewPager.setAdapter(customAdapterUserWall);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_userwall);
        tabLayout_Titles = new String[]{getResources().getString(R.string.profile), getResources().getString(R.string.image)};
        for(int i = 0; i < 2; i++){
            tabLayout.getTabAt(i).setText(tabLayout_Titles[i]);
        }


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
    }
}
