package vn.nguyen.andrew.appchat.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.nguyen.andrew.appchat.LoginActivity;
import vn.nguyen.andrew.appchat.R;
import vn.nguyen.andrew.appchat.fragment.ListView.ProfileListView;
import vn.nguyen.andrew.appchat.image.MLRoundedImageView;

/**
 * Created by trunganh on 22/09/2016.
 */
public class ProfileAdapter extends ArrayAdapter<ProfileListView> {
    Context c;
    private List<ProfileListView> prList = new ArrayList<ProfileListView>();
    LayoutInflater inflater;

    public ProfileAdapter(Context context, List<ProfileListView> prList) {
        super(context, R.layout.profile_layout, prList);
        this.c = context;
        this.prList = prList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_view_profile, parent, false);
        }

        ProfileListView pr = prList.get(position);

        TextView text = (TextView) view.findViewById(R.id.username_profiletab);
        ImageView imageView = (ImageView) view.findViewById(R.id.avatar_profile_tab);
        text.setFocusable(false);
        imageView.setFocusable(false);

        MLRoundedImageView ml = new MLRoundedImageView(getContext());
        Bitmap bm = ml.getCroppedBitmap(pr.getBm(), 360);

        text.setText(pr.getText());
        imageView.getLayoutParams().height = LoginActivity.IMAGELVWANDH;
        imageView.getLayoutParams().width = LoginActivity.IMAGELVWANDH;
        imageView.setImageBitmap(bm);

        return view;
    }

}
