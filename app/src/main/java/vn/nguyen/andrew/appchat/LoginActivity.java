package vn.nguyen.andrew.appchat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity implements Button.OnClickListener,LoadImage{
    public static final String LOGINURL = "http://10.0.3.2:8080/login";
    public static final String USER_PROFILE_URL = "http://10.0.3.2:8080/user_profile";
    public static final String USER_PROFILE_PROFILE_URL = "http://10.0.3.2:8080/user_profile_profile";

    public static final float BYTES_PER_PX = 4.0f;
    public static final String LOGINSUCCESS = "Login Sucess";
    public static final String AVATARBASE64STRING = "avatarBase64";
    public static final String AVATARBITMAP = "avatarbitmap";
    public static final String USERNAME = "username";
    public static final String SETTING = "Setting";
    public static final int IMAGELVWANDH = 160;
    public static final String COVERIMAGE64STRING = "coverImage64";
    public static final String USERTARGETNAME = "user_target_name";
    public static final String ACCESSSUCCESS = "access success";
    public static final int IMAGEAVAWH = 200;
    public static final String USERPROFILEACCESS = "user profile";



    private String notification = "";
    private ImageView symbolImage;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private int screenHeight = 0;
    private int screenWidth = 0;
    private Button signInButton;
    private Button registerButton;
    private String userName;
    private String password;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private List<NameValuePair> params;
    private ServerRequest serverRequest;
    private String avatarBase64, coverImageBase64;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        symbolImage = (ImageView) findViewById(R.id.symbolImage);
        loadImage(symbolImage, R.drawable.symbol_100px);
        signInButton = (Button) findViewById(R.id.signinButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        setButtonSize(signInButton, screenWidth, screenHeight);
        setButtonSize(registerButton, screenWidth, screenHeight);
        signInButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        userNameEditText = (EditText) findViewById(R.id.userName);
        passwordEditText = (EditText) findViewById(R.id.password);
        serverRequest = new ServerRequest();

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

        return imageHeight * imageWidth * BYTES_PER_PX / MemUtils.BYTES_IN_MB;

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
        if(v.getId() == R.id.signinButton){
            signInButton.setBackgroundResource(R.drawable.button_press) ;
            userName = userNameEditText.getText().toString();
            password = passwordEditText.getText().toString();
            params = new ArrayList<NameValuePair>();
            if(userName.equals("")){
                notification = "You haven't choose a name!";
                Toast.makeText(getApplication(),notification,Toast.LENGTH_LONG).show();
            }else{
                params.add(new BasicNameValuePair("username",userName));
                params.add(new BasicNameValuePair("password", password));
                JSONObject jsonObject = serverRequest.getJSON(LOGINURL, params);
                if(jsonObject != null){
                    try {
                        String jsonString = jsonObject.getString("response");
                        if(jsonString.equals(LOGINSUCCESS)){
                            avatarBase64 = jsonObject.getString("avatar_base64");
                            username = jsonObject.getString("username");
                            coverImageBase64 = jsonObject.getString("cover_image_base64");
                            Intent userIntent = new Intent(LoginActivity.this, UserActivity.class);
                            userIntent.putExtra(AVATARBASE64STRING, avatarBase64);
                            userIntent.putExtra(USERNAME, username);
                            userIntent.putExtra(COVERIMAGE64STRING, coverImageBase64);
                            startActivity(userIntent);
                            finish();
                        }else{
                            Toast.makeText(getApplication(), jsonString, Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }else if(v.getId() == R.id.registerButton){
            registerButton.setBackgroundResource(R.drawable.button_press_regist);
            Intent regist = new Intent(LoginActivity.this, RegistActivity.class);
            startActivity(regist);
            finish();
        }
    }

    private void setButtonSize(Button b, int screenWidth, int screenHeight){
        float widthTmp = (float) ((float) screenWidth * 0.8);
        int width = (int) widthTmp;
        b.setWidth(width);
    }

    /*public Bitmap getBitmapFromUrl(String src){
        try {
            URL url = new URL(src);
            Http
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }*/
}
