package com.kit.utils.camera.torch;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import androidx.annotation.NonNull;

/**
 * A {@link Torch} implementation built on the {@link android.hardware.camera2} API for use on
 * Android Marshmallow (API 23) and above.
 * <p/>
 * Note: as it turns out, the Camera2 is very slow compared to the deprecated Camera API. It may not
 * be suitable for a flashlight/torch that attempts to support rapid toggling.
 */
@TargetApi(Build.VERSION_CODES.M)
public final class Camera2Torch extends CameraManager.TorchCallback implements Torch {

    private CameraManager cameraManager;
    private Context context;
    private boolean torchEnabled;

    /**
     * Constructs a new Camera2Torch object using an instance of the {@link CameraManager} system
     * service obtained from the application Context.
     *
     * @param context any Context (the application Context will be retrieved from the provided
     *                Context and used to obtain an instance of the CameraManager system service)
     */
    public Camera2Torch(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void init() {
        try {
            cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            cameraManager.registerTorchCallback(this, null);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Failed to register with the Camera service!", e);
        } catch (NullPointerException e) {
            throw new IllegalStateException("Object is in an invalid state!", e);
        }
    }

    @Override
    public void toggle(boolean enabled) {
        try {
            String[] cameraIds = cameraManager.getCameraIdList();
            for (String cameraId : cameraIds) {
                CameraCharacteristics cc = cameraManager.getCameraCharacteristics(cameraId);
                Boolean flashAvailable = cc.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                Integer intValue = cc.get(CameraCharacteristics.LENS_FACING);
                Boolean lensFacingBack = (intValue != null)
                        && intValue == CameraCharacteristics.LENS_FACING_BACK;
                if ((flashAvailable != null && flashAvailable)
                        && lensFacingBack) {
                    cameraManager.setTorchMode(cameraId, enabled);
                    torchEnabled = enabled; // TODO: Stop cheating!
                }
            }
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            throw new IllegalStateException("No camera with torch mode was available!", e);
        } catch (CameraAccessException e) {
            throw new IllegalStateException("Failed to access the camera device!", e);
        }
    }

    @Override
    public boolean isOn() {
        return torchEnabled;
    }

    @Override
    public void tearDown() {
        cameraManager.unregisterTorchCallback(this);
        cameraManager = null;
        torchEnabled = false;
        context = null;
    }

    @Override
    public void onTorchModeUnavailable(@NonNull String cameraId) {
        super.onTorchModeUnavailable(cameraId);
        torchEnabled = false;
    }

    @Override
    public void onTorchModeChanged(@NonNull String cameraId, boolean enabled) {
        super.onTorchModeChanged(cameraId, enabled);
        torchEnabled = enabled;
    }
}