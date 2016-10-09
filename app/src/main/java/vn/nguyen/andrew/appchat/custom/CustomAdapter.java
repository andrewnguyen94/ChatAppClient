package vn.nguyen.andrew.appchat.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import vn.nguyen.andrew.appchat.LoginActivity;
import vn.nguyen.andrew.appchat.fragment.ChatFragment;
import vn.nguyen.andrew.appchat.fragment.ContactFragment;
import vn.nguyen.andrew.appchat.fragment.ProfileFragment;
import vn.nguyen.andrew.appchat.fragment.RoomFragment;

/**
 * Created by trunganh on 16/09/2016.
 */
public class CustomAdapter extends FragmentPagerAdapter {

    private String avatarBase64;
    private String username;
    private String coverImageBase64;
    private FragmentManager manager;

    public CustomAdapter(FragmentManager fm, Context applicationContext, String avatarBase64,
                         String username, String coverImageBase64) {
        super(fm);
        this.avatarBase64 = avatarBase64;
        this.username = username;
        this.coverImageBase64 = coverImageBase64;
        this.manager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new ChatFragment();
            case 1 : return new ContactFragment();
            case 2 : return new RoomFragment();
            case 3 :
                Bundle bundle = new Bundle();
                bundle.putString(LoginActivity.AVATARBITMAP, avatarBase64);
                bundle.putString(LoginActivity.USERNAME, username);
                bundle.putString(LoginActivity.COVERIMAGE64STRING, coverImageBase64);
                ProfileFragment pf = new ProfileFragment();
                pf.setArguments(bundle);
                return pf;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
