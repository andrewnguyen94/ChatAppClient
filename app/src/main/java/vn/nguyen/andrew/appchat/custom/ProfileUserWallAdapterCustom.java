package vn.nguyen.andrew.appchat.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.nguyen.andrew.appchat.interfaces.LoadImage;
import vn.nguyen.andrew.appchat.LoginActivity;
import vn.nguyen.andrew.appchat.MemUtils;
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

        option.setText(prCustom.getOption());
        value.setText(prCustom.getValue());
        int res = prCustom.getImageresource();
        loadImage(imageView, res);

        return view;
    }

    @Override
    public float readBitmapInfo(int resource) {
        Resources res = c.getResources();
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
        Resources res = c.getResources();
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
            image.setImageResource(resource);
        }
    }
}
