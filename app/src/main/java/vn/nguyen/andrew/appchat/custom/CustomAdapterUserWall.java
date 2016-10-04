package vn.nguyen.andrew.appchat.custom;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import vn.nguyen.andrew.appchat.LoginActivity;
import vn.nguyen.andrew.appchat.R;
import vn.nguyen.andrew.appchat.fragment.userwall.ImageUserWall;
import vn.nguyen.andrew.appchat.fragment.userwall.ProfileUserWallFragment;

/**
 * Created by trunganh on 30/09/2016.
 */
public class CustomAdapterUserWall extends FragmentPagerAdapter{
    private String username, user_target_name;
    private Bundle bundle;
    private String[] titles;
    private Context context;

    public CustomAdapterUserWall(FragmentManager fm, String username, String user_target_name, Context context) {
        super(fm);
        this.username = username;
        this.user_target_name = user_target_name;
        this.context = context;
        titles = new String[]{context.getResources().getString(R.string.profile),
                context.getResources().getString(R.string.image)};
        bundle = new Bundle();
        bundle.putString(LoginActivity.USERTARGETNAME, user_target_name);
        bundle.putString(LoginActivity.USERNAME, username);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                ProfileUserWallFragment pr = new ProfileUserWallFragment();
                pr.setArguments(bundle);
                return pr;
            case 1 :
                ImageUserWall image = new ImageUserWall();
                image.setArguments(bundle);
                return image;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
