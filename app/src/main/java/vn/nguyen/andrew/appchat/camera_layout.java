package vn.nguyen.andrew.appchat;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vn.nguyen.andrew.appchat.camera.CameraPreview;

public class Camera_layout extends Activity implements View.OnClickListener{
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final String TAG = "TAG";


    private Camera mCamera;
    private CameraPreview mPreview;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_layout);

        mCamera = getCameraInstance();
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout cameraPreview = (FrameLayout) findViewById(R.id.camera_preview);
        cameraPreview.addView(mPreview);

        mButton = (Button) findViewById(R.id.button_capture);
        mButton.setOnClickListener(this);
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        return c;
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if(pictureFile == null){
                Log.d(TAG, "Error creating mediafile, check your storage permissions");
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(bytes);
                fos.close();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "ChatApp");
        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                Log.d("ChatApp", this.getResources().getString(R.string.fail_to_create_new_directory));
                return null;
            }
        }

        String timpStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if(type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" +
                    timpStamp + ".jpg");
        }else if(type == MEDIA_TYPE_VIDEO){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timpStamp + ".mp4");
        }else{
            return null;
        }

        return mediaFile;

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_capture){
            mCamera.takePicture(null,null,mPicture);
        }
    }
}
