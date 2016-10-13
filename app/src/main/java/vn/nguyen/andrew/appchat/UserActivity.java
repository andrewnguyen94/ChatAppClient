package vn.nguyen.andrew.appchat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import vn.nguyen.andrew.appchat.custom.CustomAdapter;

public class UserActivity extends AppCompatActivity {
    private String avatarBase64 = "";
    private Utilities utilities;
    private String coverImageBase64 = "";
    private int[] btnIcons = {
            R.drawable.btn_chat, R.drawable.btn_contact, R.drawable.btn_room, R.drawable.btn_profile
    };

    private String username;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Bundle ProfileFragmentArgs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user);
        utilities = new Utilities();
        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty()){
            avatarBase64 = bundle.getString(LoginActivity.AVATARBASE64STRING);
            username = bundle.getString(LoginActivity.USERNAME);
            coverImageBase64 = bundle.getString(LoginActivity.COVERIMAGE64STRING);
        }
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        CustomAdapter customAdapter = new CustomAdapter(getSupportFragmentManager(), getApplication(),
                avatarBase64, username, coverImageBase64);
        viewPager.setAdapter(customAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        for(int i = 0; i < 4; i++){
            tabLayout.getTabAt(i).setIcon(btnIcons[i]);
        }
    }


}
