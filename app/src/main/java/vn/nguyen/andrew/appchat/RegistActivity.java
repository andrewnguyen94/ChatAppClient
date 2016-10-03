package vn.nguyen.andrew.appchat;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegistActivity extends Activity implements LoadImage,Button.OnClickListener {

    public static final String REGISTERURL = "http://10.0.3.2:8080/register";

    private ImageView bannerRegist;
    private Button regisButton;
    private Button loginButton;
    private String email;
    private String username;
    private String password;
    private String gender;
    private RadioGroup gender_group;
    private RadioButton man;
    private RadioButton women;
    private RadioButton others;
    private EditText emailTxt;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private List<NameValuePair> params;
    private ServerRequest request;
    private Bitmap avatarDefaultBitmap, manCoverDefaultBitmap, womanCoverDefaultBitmap;
    private Utilities utilities;
    private String avatarDefaultString, coverDefaultString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        utilities = new Utilities();
        request = new ServerRequest();

        bannerRegist = (ImageView) findViewById(R.id.banner_regist);
        loadImage(bannerRegist, R.drawable.banner_regist_300px);
        regisButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginBackButton);
        regisButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        gender_group = (RadioGroup) findViewById(R.id.gender_group);
        man = (RadioButton) findViewById(R.id.gender_man);
        women = (RadioButton) findViewById(R.id.gender_women);
        others = (RadioButton) findViewById(R.id.gender_others);

        emailTxt = (EditText) findViewById(R.id.emailRegist);
        usernameTxt = (EditText) findViewById(R.id.usernameRegist);
        passwordTxt = (EditText) findViewById(R.id.passwordRegist);
        avatarDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.symbol_75px);
        manCoverDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.man_cover_default_256px);
        womanCoverDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.women_cover_default_256px);
    }

    public float readBitmapInfo(int resource){
        Resources res = this.getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resource, options);
        float imageHeight = options.outHeight;
        float imageWidth = options.outWidth;
        String imageMimeTypes = options.outMimeType;

        Log.d("Scale Before Load", "w,h,type : " + imageWidth + ", " + imageHeight + ", " + imageMimeTypes);

        return imageHeight * imageWidth * LoginActivity.BYTES_PER_PX / MemUtils.BYTES_IN_MB;

    }

    public void subSampleImage(int powerOf2, int resource, ImageView image){
        if(powerOf2 < 1 || powerOf2 > 32){
            Log.e("Scale Before Load", "Trying to apply upscale or excessive downscale" + powerOf2);
            return;
        }
        Resources res = this.getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = powerOf2;
        Bitmap bitmap = BitmapFactory.decodeResource(res, resource, options);
        image.setImageBitmap(bitmap);
    }

    public void loadImage(ImageView image, int resource){
        if(readBitmapInfo(resource) > MemUtils.megabytesFree()){
            subSampleImage(32, resource, image);
        }else{
            image.setImageResource(resource);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.registerButton){
            regisButton.setBackgroundResource(R.drawable.button_press_regist);
            email = emailTxt.getText().toString();
            username = usernameTxt.getText().toString();
            password = passwordTxt.getText().toString();
            avatarDefaultString = utilities.getStringFromBitmap(avatarDefaultBitmap);
            int idCheck = gender_group.getCheckedRadioButtonId();
            if(idCheck == R.id.gender_man){
                gender = man.getText().toString();
                coverDefaultString = utilities.getStringFromBitmap(manCoverDefaultBitmap);
            }else if(idCheck == R.id.gender_women){
                gender = women.getText().toString();
                coverDefaultString = utilities.getStringFromBitmap(womanCoverDefaultBitmap);
            }else if(idCheck == R.id.gender_others){
                gender = others.getText().toString();
                coverDefaultString = utilities.getStringFromBitmap(womanCoverDefaultBitmap);
            }
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("avatar", avatarDefaultString));
            params.add(new BasicNameValuePair("cover_image", coverDefaultString));
            JSONObject jsonObject = request.getJSON(REGISTERURL, params);
            if(jsonObject != null){
                try {
                    String response = jsonObject.getString("response");
                    Toast.makeText(getApplication(), response, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }else if(v.getId() == R.id.loginBackButton){

        }
    }
}
