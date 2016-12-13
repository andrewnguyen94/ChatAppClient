package vn.nguyen.andrew.appchat.camera;

import android.content.Context;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by applefamily on 12/10/16.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private CameraModule mCamera;
    private CameraManager mManager;
    private SurfaceHolder surfaceHolder;

    public CameraPreview(Context context, CameraModule cameraModule) {
        super(context);
        this.mCamera = cameraModule;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
        }catch (Exception e){

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
