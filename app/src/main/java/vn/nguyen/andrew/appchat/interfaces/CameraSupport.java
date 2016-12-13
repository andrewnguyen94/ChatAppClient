package vn.nguyen.andrew.appchat.interfaces;

/**
 * Created by applefamily on 12/13/16.
 */

public interface CameraSupport {
    CameraSupport open(int cameraId);
    int getOrientation(int cameraId);
    CameraSupport provideCameraSupport();
}
