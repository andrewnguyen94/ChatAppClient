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
import vn.nguyen.andrew.appchat.fragment.ListView.ProfileListViewUserWallCustom;

/**
 * Created by trunganh on 02/10/2016.
 */
public class ProfileUserWallAdapterCustom extends ArrayAdapter<ProfileListViewUserWallCustom> implements LoadImage{
    private Context c;
    LayoutInflater inflater;
    private List<ProfileListViewUserWallCustom> pr = new ArrayList<ProfileListViewUserWallCustom>();

    public ProfileUserWallAdapterCustom(Context context, List<ProfileListViewUserWallCustom> pr) {
        super(context, R.layout.item_view_profile_userwall_custom, pr);
        this.c = context;
        this.pr = pr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_view_profile_userwall_custom, parent, false);
        }

        ProfileListViewUserWallCustom prCustom = pr.get(position);

        TextView option = (TextView) view.findViewById(R.id.option_profile_userwall_custom);
        TextView value = (TextView) view.findViewById(R.id.value_profile_username_custom);
        ImageView imageView = (ImageView) view.findViewById(R.id.edit_icon_userwall);


        return view;
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
