package vn.nguyen.andrew.appchat.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.nguyen.andrew.appchat.LoginActivity;
import vn.nguyen.andrew.appchat.R;
import vn.nguyen.andrew.appchat.ServerRequest;
import vn.nguyen.andrew.appchat.UserActivity;
import vn.nguyen.andrew.appchat.UserWallActivity;
import vn.nguyen.andrew.appchat.Utilities;
import vn.nguyen.andrew.appchat.custom.ProfileAdapter;
import vn.nguyen.andrew.appchat.fragment.ListView.ProfileListView;

/**
 * Created by trunganh on 21/09/2016.
 */
public class ProfileFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String SETUP = "Setting";

    private String avatarBase64, avatar, cover_image;
    private Utilities utilities;
    private Bitmap avatarBitmap;
    private String username;
    private ListView proListView;
    private List<ProfileListView> prList;
    private ServerRequest request;
    private List<NameValuePair> params;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);
        prList = new ArrayList<ProfileListView>();
        params = new ArrayList<NameValuePair>();
        request = new ServerRequest();
        if(savedInstanceState != null){
            avatarBase64 = savedInstanceState.getString(LoginActivity.AVATARBITMAP);
            username = savedInstanceState.getString(LoginActivity.USERNAME);
        }else{
            avatarBase64 = getArguments().getString(LoginActivity.AVATARBITMAP);
            username = getArguments().getString(LoginActivity.USERNAME);
        }
        utilities = new Utilities();
        if(!avatarBase64.equals("")){
            avatarBitmap = utilities.getBitmapFromBase64String(avatarBase64);
        }
        populateProfileListView();
        proListView = (ListView) view.findViewById(R.id.listview_profile);
        ArrayAdapter<ProfileListView> arrayList = new ProfileAdapter(getContext(), prList);
        proListView.setAdapter(arrayList);
        proListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstance(outState);
    }

    private void saveInstance(Bundle data){
        data.putString(LoginActivity.AVATARBITMAP, avatarBase64);
        data.putString(LoginActivity.USERNAME, username);
    }

    private Bitmap getBitmapFromResorce(int image){
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), image);
        return icon;
    }

    private void populateProfileListView(){
        prList.add(new ProfileListView(username, avatarBitmap));
        prList.add(new ProfileListView(LoginActivity.SETTING, getBitmapFromResorce(R.drawable.setting_icon_50px)));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int pos = position;
        if(pos == 0){
            ProfileListView user_target = prList.get(position);
            String user_target_name = user_target.getText();
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(LoginActivity.USERTARGETNAME, user_target_name));
            JSONObject json = request.getJSON(LoginActivity.USER_PROFILE_URL, params);
            if(json != null){
                try {
                    String res = json.getString("response");
                    if(res.equals(LoginActivity.ACCESSSUCCESS)){
                        Bundle bundle = new Bundle();
                        saveInstance(bundle);
                        avatar = json.getString("avatar");
                        cover_image = json.getString("cover_image");
                        Intent userWall = new Intent(this.getActivity(), UserWallActivity.class);
                        userWall.putExtra(LoginActivity.USERTARGETNAME, user_target_name);
                        userWall.putExtra(LoginActivity.USERNAME, username);
                        userWall.putExtra(LoginActivity.AVATARBASE64STRING, avatar);
                        userWall.putExtra(LoginActivity.COVERIMAGE64STRING, cover_image);
                        userWall.putExtra(LoginActivity.BUNDLEPROFILEFRAGMENT, bundle);
                        startActivity(userWall);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }else if(pos == 1){

        }
    }
}
