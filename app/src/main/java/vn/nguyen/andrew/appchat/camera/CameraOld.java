package vn.nguyen.andrew.appchat.camera;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.os.Build;

import vn.nguyen.andrew.appchat.interfaces.CameraSupport;

/**
 * Created by applefamily on 12/13/16.
 */

public class CameraOld implements CameraSupport {
    private Camera camera;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public CameraSupport open(int cameraId) {
        this.camera = Camera.open(cameraId);
        return this;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public int getOrientation(int cameraId) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        return info.orientation;
    }
}
