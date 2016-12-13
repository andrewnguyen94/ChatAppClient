package vn.nguyen.andrew.appchat.camera;

import android.content.Context;
import android.os.Build;

import vn.nguyen.andrew.appchat.interfaces.CameraSupport;

/**
 * Created by applefamily on 12/13/16.
 */

public class CameraModule {
    private Context mContext;
    public CameraModule(Context context){
        this.mContext = context;
    }

    public CameraSupport provideCameraSupport(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            return new CameraNew(mContext);
        }
        return new CameraOld();
    }
}
