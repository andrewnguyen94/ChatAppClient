package vn.nguyen.andrew.appchat.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import vn.nguyen.andrew.appchat.interfaces.CameraSupport;

/**
 * Created by applefamily on 12/13/16.
 */

public class CameraNew implements CameraSupport {
    private CameraDevice camera;
    private CameraManager manager;

    public CameraNew(Context context) {
        this.manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    @Override
    public CameraSupport open(int cameraId) {
        try {
            String[] cameraIds = new String[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cameraIds = manager.getCameraIdList();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                manager.openCamera(cameraIds[cameraId], new CameraDevice.StateCallback() {
                    @Override
                    public void onOpened(CameraDevice cameraDevice) {
                        CameraNew.this.camera = cameraDevice;
                    }

                    @Override
                    public void onDisconnected(CameraDevice cameraDevice) {
                        CameraNew.this.camera = cameraDevice;
                    }

                    @Override
                    public void onError(CameraDevice cameraDevice, int i) {
                        CameraNew.this.camera = cameraDevice;
                    }
                }, null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int getOrientation(int cameraId) {
        try {
            String[] cameraIds = manager.getCameraIdList();
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraIds[cameraId]);
            return  characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        }catch (CameraAccessException e){
            return 0;
        }
    }
}
