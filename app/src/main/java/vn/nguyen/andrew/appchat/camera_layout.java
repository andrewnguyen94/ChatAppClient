package vn.nguyen.andrew.appchat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import vn.nguyen.andrew.appchat.camera.CameraPreview;
import vn.nguyen.andrew.appchat.interfaces.LoadImage;

public class Camera_layout extends Activity implements LoadImage, View.OnTouchListener{
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final String TAG = "TAG";
    public static final String PICTURE_BYTE = "BYTE";
    public static final int FRONT_CAMERA = Camera.CameraInfo.CAMERA_FACING_FRONT;
    public static final int BACK_CAMERA = Camera.CameraInfo.CAMERA_FACING_BACK;


    private Camera mCamera;
    private CameraPreview mPreview;
    private ImageButton mButton, mRotate;
    private Rect rect;
    private int currentCameraId;
    private FrameLayout cameraPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_layout);

        rect = new Rect();
        currentCameraId = FRONT_CAMERA;
//        mCamera = getCameraInstance(currentCameraId);
//        mPreview = new CameraPreview(this, mCamera);
        cameraPreview = (FrameLayout) findViewById(R.id.camera_preview);
//        cameraPreview.addView(mPreview);
        mRotate = (ImageButton) findViewById(R.id.rotate_button);
        loadImage(mRotate, R.drawable.btn_rotate);
        mButton = (ImageButton) findViewById(R.id.button_capture);
        loadImage(mButton, R.drawable.btn_capture);
        mButton.setOnTouchListener(this);
        mRotate.setOnTouchListener(this);
    }

    public static Camera getCameraInstance(int currentCameraId){
        Camera c = null;
        try {
            c = Camera.open(currentCameraId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return c;
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
//            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
//            if(pictureFile == null){
//                Log.d(TAG, "Error creating mediafile, check your storage permissions");
//                return;
//            }
            try {
                Intent capturePhoto = new Intent(Camera_layout.this, CapturedPhotoActivity.class);
                capturePhoto.putExtra(PICTURE_BYTE, bytes);
                startActivity(capturePhoto);
                finish();
//                FileOutputStream fos = new FileOutputStream(pictureFile);
//                fos.write(bytes);
//                fos.close();
            }catch (Exception e) {
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
    protected void onPause() {
        super.onPause();
        if(mCamera != null){
            mPreview.myStopPreview();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        restartCamera(currentCameraId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this.getApplication(), "on Activity Result", Toast.LENGTH_SHORT).show();
    }

    private void releaseCamera(){
        if(mCamera != null){
            mCamera.release(); //release camera for other applications
            mCamera = null;
        }
    }

    @Override
    public float readBitmapInfo(int resource) {
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

    @Override
    public void subSampleImage(int powerOf2, int resource, ImageView image) {
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

    @Override
    public void loadImage(ImageView image, int resource) {
        if(readBitmapInfo(resource) > MemUtils.megabytesFree()){
            subSampleImage(32, resource, image);
        }else{
            image.setImageResource(resource);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.button_capture){
            final int action = motionEvent.getAction();
            view.getHitRect(rect);
            final float x = motionEvent.getX() + rect.left;
            final float y = motionEvent.getY() + rect.top;

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    loadImage(mButton, R.drawable.capture_button_48);
                    break;
                case MotionEvent.ACTION_UP:
                    loadImage(mButton, R.drawable.capture_button);
                    mCamera.takePicture(null, null, mPicture);
                    break;
                case MotionEvent.ACTION_MOVE:
                    loadImage(mButton, R.drawable.capture_button_48);
                    break;
            }
            return false;
        }else if(view.getId() == R.id.rotate_button){
            final int action  = motionEvent.getAction();
            view.getHitRect(rect);
            final float x = motionEvent.getX() + rect.left;
            final float y = motionEvent.getY() + rect.top;

            switch (action){
                case MotionEvent.ACTION_DOWN:
                    loadImage(mRotate, R.drawable.button_rotate_48);
                    break;
                case MotionEvent.ACTION_UP:
                    loadImage(mRotate, R.drawable.button_rotate_32);
                    if(currentCameraId == FRONT_CAMERA){
                        currentCameraId = BACK_CAMERA;
                    }else{
                        currentCameraId = FRONT_CAMERA;
                    }

                    mPreview.myStopPreview();
                    cameraPreview.removeAllViews();
                    mCamera = getCameraInstance(currentCameraId);
                    mPreview = new CameraPreview(this.getApplication(), mCamera);
                    cameraPreview.addView(mPreview);
                    break;
                case MotionEvent.ACTION_MOVE:
                    loadImage(mRotate, R.drawable.button_rotate_48);
                    break;
            }
        }
        return true;
    }

    private void restartCamera(int currentCameraId){
        if(mPreview != null){
            mPreview.myStopPreview();
            cameraPreview.removeAllViews();
            mCamera = getCameraInstance(currentCameraId);
            mPreview = new CameraPreview(this.getApplication(), mCamera);
            cameraPreview.addView(mPreview);
        }else {
            releaseCamera();
            mCamera = getCameraInstance(currentCameraId);
            mPreview = new CameraPreview(this.getApplication(), mCamera);
            cameraPreview.addView(mPreview);
        }
    }

}
