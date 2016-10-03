package vn.nguyen.andrew.appchat.fragment.userwall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.nguyen.andrew.appchat.R;

/**
 * Created by trunganh on 30/09/2016.
 */
public class ImageUserWall extends Fragment {
    private String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_userwall_layout, container, false);
    }
}
