package vn.nguyen.andrew.appchat.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.nguyen.andrew.appchat.LoadImage;
import vn.nguyen.andrew.appchat.R;
import vn.nguyen.andrew.appchat.fragment.ListView.ProfileListViewUserWallDefault;

/**
 * Created by trunganh on 30/09/2016.
 */
public class ProfileUserWallAdapterDefault extends ArrayAdapter<ProfileListViewUserWallDefault> implements LoadImage{
    private Context c;
    LayoutInflater inflater;
    private List<ProfileListViewUserWallDefault> pr = new ArrayList<ProfileListViewUserWallDefault>();

    public ProfileUserWallAdapterDefault(Context context, List<ProfileListViewUserWallDefault> pr) {
        super(context, R.layout.item_view_profile_userwall_default, pr);
        this.c = context;
        this.pr = pr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_view_profile_userwall_default, parent, false);
        }
        ProfileListViewUserWallDefault profileListViewUserWall = pr.get(position);

        TextView otpion = (TextView) view.findViewById(R.id.option_profile_userwall_default);
        TextView value = (TextView) view.findViewById(R.id.value_profile_username_default);


        return null;
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