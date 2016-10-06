package vn.nguyen.andrew.appchat.custom;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.nguyen.andrew.appchat.R;
import vn.nguyen.andrew.appchat.Utilities;
import vn.nguyen.andrew.appchat.fragment.ListView.ProfileListViewUserWallDefault;
import vn.nguyen.andrew.appchat.textview.UnderlinedTextView;

/**
 * Created by trunganh on 30/09/2016.
 */
public class ProfileUserWallAdapterDefault extends ArrayAdapter<ProfileListViewUserWallDefault>{
    private Utilities utilities;
    private Context c;
    LayoutInflater inflater;
    private List<ProfileListViewUserWallDefault> pr = new ArrayList<ProfileListViewUserWallDefault>();

    public ProfileUserWallAdapterDefault(Context context, List<ProfileListViewUserWallDefault> pr) {
        super(context, R.layout.item_view_profile_userwall_default, pr);
        this.c = context;
        this.pr = pr;
        utilities = new Utilities();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_view_profile_userwall_default, parent, false);
        }
        ProfileListViewUserWallDefault profileListViewUserWall = pr.get(position);

        TextView option = (TextView) view.findViewById(R.id.option_profile_userwall_default);
        UnderlinedTextView value = (UnderlinedTextView) view.findViewById(R.id.value_profile_userwall_default);

        SpannableString optionString = new SpannableString(profileListViewUserWall.getOption());
        option.setText(optionString);
        value.setText(profileListViewUserWall.getValue());

        return view;
    }
}
