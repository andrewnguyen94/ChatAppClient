package vn.nguyen.andrew.appchat.fragment.userwall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.nguyen.andrew.appchat.LoginActivity;
import vn.nguyen.andrew.appchat.R;
import vn.nguyen.andrew.appchat.ServerRequest;
import vn.nguyen.andrew.appchat.Utilities;
import vn.nguyen.andrew.appchat.custom.ListTitleAdapter;
import vn.nguyen.andrew.appchat.custom.MergerAdapter;
import vn.nguyen.andrew.appchat.custom.ProfileUserWallAdapterCustom;
import vn.nguyen.andrew.appchat.custom.ProfileUserWallAdapterDefault;
import vn.nguyen.andrew.appchat.fragment.ListView.ProfileListViewUserWallCustom;
import vn.nguyen.andrew.appchat.fragment.ListView.ProfileListViewUserWallDefault;

/**
 * Created by trunganh on 30/09/2016.
 */
public class ProfileUserWallFragment extends Fragment{
    private String username, user_target_name;
    private Utilities utilities;
    private ServerRequest request;
    private List<ProfileListViewUserWallDefault> prDefault;
    private List<ProfileListViewUserWallCustom> prCustom;
    private List<NameValuePair> params;
    private ListView profile_default, profile_custom;
    private String response, username_sv, gender, school_name, company_name, favorite, relationship_status, address;
    private int age;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        prDefault = new ArrayList<ProfileListViewUserWallDefault>();
        prCustom = new ArrayList<ProfileListViewUserWallCustom>();
        MergerAdapter mergerAdapter = new MergerAdapter();
        params = new ArrayList<NameValuePair>();
        request = new ServerRequest();
        username = getArguments().getString(LoginActivity.USERNAME);
        user_target_name = getArguments().getString(LoginActivity.USERTARGETNAME);
        View view = inflater.inflate(R.layout.profile_userwall_layout, container, false);
        profile_default = (ListView) view.findViewById(R.id.list_view_profile);
//        profile_custom = (ListView) view.findViewById(R.id.list_view_profile_custom);

        params.add(new BasicNameValuePair(LoginActivity.USERTARGETNAME, user_target_name));
        params.add(new BasicNameValuePair(LoginActivity.USERNAME, username));
        JSONObject json = request.getJSON(LoginActivity.USER_PROFILE_PROFILE_URL, params);
        try {
            if(json != null){
                response = json.getString("response");
                if(response.equals(LoginActivity.USERPROFILEACCESS)){
                    username_sv = json.getString("username");
                    gender = json.getString("gender");
                    age = json.getInt("age");
                    school_name = json.getString("school_name");
                    company_name = json.getString("company_name");
                    favorite = json.getString("favorite");
                    relationship_status = json.getString("relationship_status");
                    address = json.getString("address");
                }else{
                    school_name = json.getString("school_name");
                    company_name = json.getString("company_name");
                    favorite = json.getString("favorite");
                    relationship_status = json.getString("relationship_status");
                    address = json.getString("address");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        populateListView();
        ArrayAdapter<ProfileListViewUserWallDefault> arrayListDefault = new ProfileUserWallAdapterDefault(getContext(), prDefault);
        ArrayAdapter<ProfileListViewUserWallCustom> arrayListCustom = new ProfileUserWallAdapterCustom(getContext(), prCustom);
        //add Adapter to MergerAdapter
        mergerAdapter.addAdapter(new ListTitleAdapter(getContext(), getResources().getString(R.string.basicInfo), arrayListDefault));
        mergerAdapter.addAdapter(arrayListDefault);

        mergerAdapter.addAdapter(new ListTitleAdapter(getContext(), getResources().getString(R.string.advanceInfo), arrayListCustom));
        mergerAdapter.addAdapter(arrayListCustom);

        profile_default.setAdapter(mergerAdapter);
//        profile_custom.setAdapter(arrayListCustom);

        return view;
    }

    private void populateListView(){
        if(response.equals(LoginActivity.USERPROFILEACCESS)){
            prDefault.add(new ProfileListViewUserWallDefault(getResources().getString(R.string.username), username_sv));
            prDefault.add(new ProfileListViewUserWallDefault(getResources().getString(R.string.gender), gender));
            prDefault.add(new ProfileListViewUserWallDefault(getResources().getString(R.string.age), String.valueOf(age)));

            prCustom.add(new ProfileListViewUserWallCustom(getResources().getString(R.string.school_name),
                    school_name, R.drawable.edit_icon_16px));
            prCustom.add(new ProfileListViewUserWallCustom(getResources().getString(R.string.company_name),
                    company_name, R.drawable.edit_icon_16px));
            prCustom.add(new ProfileListViewUserWallCustom(getResources().getString(R.string.favorite),
                    favorite, R.drawable.edit_icon_16px));
            prCustom.add(new ProfileListViewUserWallCustom(getResources().getString(R.string.relationship_status),
                    relationship_status, R.drawable.edit_icon_16px));
            prCustom.add(new ProfileListViewUserWallCustom(getResources().getString(R.string.address),
                    address, R.drawable.edit_icon_16px));
        }else{
            prCustom.add(new ProfileListViewUserWallCustom(getResources().getString(R.string.school_name),
                    school_name, R.drawable.edit_icon_16px));
            prCustom.add(new ProfileListViewUserWallCustom(getResources().getString(R.string.company_name),
                    company_name, R.drawable.edit_icon_16px));
            prCustom.add(new ProfileListViewUserWallCustom(getResources().getString(R.string.favorite),
                    favorite, R.drawable.edit_icon_16px));
            prCustom.add(new ProfileListViewUserWallCustom(getResources().getString(R.string.relationship_status),
                    relationship_status, R.drawable.edit_icon_16px));
            prCustom.add(new ProfileListViewUserWallCustom(getResources().getString(R.string.address),
                    address, R.drawable.edit_icon_16px));
        }
    }

}
