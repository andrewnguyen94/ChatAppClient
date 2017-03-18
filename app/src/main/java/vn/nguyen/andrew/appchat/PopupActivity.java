package vn.nguyen.andrew.appchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.nguyen.andrew.appchat.custom.PopupAdapter;
import vn.nguyen.andrew.appchat.fragment.ListView.PopupListView;

/**
 * Created by trunganh on 10/10/2016.
 */
public class PopupActivity extends Activity implements AdapterView.OnItemClickListener {
    private TextView titlePopup;
    private ListView popupContent;
    private String titlePopupVal, username, avatarBase64;
    private List<PopupListView> plv;
    private LinearLayout userWall;
    private List<NameValuePair> params;
    private ServerRequest request;
    private int totlaImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);
        userWall = (LinearLayout) UserWallActivity.main;
        DisplayMetrics md = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(md);
        int width = md.widthPixels;
        Bundle args = getIntent().getExtras();
        if(args != null){
            username = args.getString(LoginActivity.USERNAME);
            titlePopupVal = args.getString(LoginActivity.TITLEPOPUP);
            Bundle tmp = args.getBundle(LoginActivity.POPUPLISTVIEW);
            plv = (ArrayList<PopupListView>) tmp.getSerializable(LoginActivity.POPUPLISTVIEW);
        }

        titlePopup = (TextView) findViewById(R.id.popupTitle);
        popupContent = (ListView) findViewById(R.id.popupContent);
        int tmp = (int) getResources().getDimension(R.dimen.popup_height);
        getWindow().setLayout((int)(width * 0.8), (int)(tmp * (plv.size() + 1)));

        if(!titlePopupVal.equals("")){
            titlePopup.setText(titlePopupVal);
        }
        ArrayAdapter<PopupListView> arrayAdapter = new PopupAdapter(getApplication(), plv);
        popupContent.setAdapter(arrayAdapter);
        popupContent.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            userWall.setAlpha((float) 1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.popupContent){
            String content = plv.get(position).getContent();
            if(position == 0 && content.equals(getResources().getString(R.string.watch_avatar))){
                request = new ServerRequest();
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(LoginActivity.USERNAME, username));
                JSONObject jsonObject = request.getJSON(LoginActivity.VIEW_AVATAR, params);
                if(jsonObject != null){
                    try {
                        String res = jsonObject.getString("response");
                        if(res.equals("access success")){
                            avatarBase64 = jsonObject.getString(LoginActivity.AVATAR);
                            totlaImage = jsonObject.getInt(LoginActivity.TOTALIMAGE);
                        }
                        Intent viewImageIntent = new Intent(PopupActivity.this, ViewImageActivity.class);
                        viewImageIntent.putExtra(LoginActivity.AVATARBASE64STRING, avatarBase64);
                        viewImageIntent.putExtra(LoginActivity.USERNAME, username);
                        viewImageIntent.putExtra(LoginActivity.IMAGEID, (int)(1));
                        viewImageIntent.putExtra(LoginActivity.TOTALIMAGE, totlaImage);
                        startActivity(viewImageIntent);
                        finish();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }else if(position == 1 && content.equals(getResources().getString(R.string.take_newphoto))){
                Intent cameraPreviewIntent = new Intent(PopupActivity.this, Camera_layout.class);
                startActivity(cameraPreviewIntent);
                finish();
            }
        }
    }
}
