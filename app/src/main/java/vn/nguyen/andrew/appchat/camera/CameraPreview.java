package vn.nguyen.andrew.appchat.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;


/**
 * Created by applefamily on 12/10/16.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback{

    public static final String TAG = "tag";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    public boolean inPreview;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        myStopPreview();
        mCamera.release();
        mCamera = null;
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
//        Camera.Parameters parameters = mCamera.getParameters();
//        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
//        Camera.Size optimalPreviewSize = getOptimalPreviewSize(supportedPreviewSizes, w, h);
//        if(optimalPreviewSize != null){
//            parameters.setPreviewSize(optimalPreviewSize.width, optimalPreviewSize.height);
//            mCamera.setParameters(parameters);
//            mCamera.startPreview();
//        }
        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            myStartPreview();
        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {

    }

    public void myStartPreview(){
        if(inPreview == false && (mCamera != null)){
            mCamera.startPreview();
            inPreview = true;
        }
    }

    public void myStopPreview(){
        if(inPreview && mCamera != null){
            mCamera.stopPreview();
            inPreview = false;
        }
    }

    public void restartPreview(int currentCameraId){
        myStopPreview();
        myStartPreview();
    }
}
