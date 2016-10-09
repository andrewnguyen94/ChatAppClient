package vn.nguyen.andrew.appchat;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.nguyen.andrew.appchat.custom.PopupAdapter;
import vn.nguyen.andrew.appchat.fragment.ListView.PopupListView;

/**
 * Created by trunganh on 10/10/2016.
 */
public class PopupActivity extends Activity {
    private TextView titlePopup;
    private ListView popupContent;
    private String titlePopupVal;
    private List<PopupListView> plv;
    private LinearLayout userWall;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            userWall.setAlpha((float) 1);
        }
    }
}
