package com.www.funone.managers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.www.funone.util.Logger;
import com.www.funone.util.Validator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CameraManager {

    public static final String TAG = CameraManager.class.getSimpleName();

    private static CameraManager instance = new CameraManager();
    private Action CURRENT_ACTION = null;
    private Activity mActivity = null;

    private static final String ERROR_CANNOT_START_CAMERA = "Cannot start Camera";
    private static final String ERROR_FAILED_TO_CREATE_DIRECTORY = "Failed to create directory";
    private static final String ERROR_STORAGE_NOT_MOUNTED = "App's External storage is not mounted READ/WRITE.";
    private static final String ERROR_NOT_ENOUGH_PERMISSIONS = "Not enough permissions";
    private static final String ERROR_CANNOT_PERFORM_ACTION = "Cannot perform this action due to luck if appropriate Intent";

    private static final String APP_IMAGE_DIR_NAME = "fun_one";
    public static final int RC_PERMISSIONS = 1212;
    private static final String JPEG_FILE_BODY_DATE_PATTERN = "yyyyMMdd_HHmmss";
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";


    public static final int ACTION_TAKE_GALLERY_PHOTO = 10;
    public static final int ACTION_TAKE_GALLERY_VIDEO = 11;
    public static final int ACTION_TAKE_CAMERA_VIDEO = 13;
    public static final int ACTION_TAKE_CAMERA_PHOTO = 12;

    private String mCurrentPhotoPath;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;


    private CameraManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
    }

    public static CameraManager getInstance() {
        if (!Validator.isObjectValid(instance)) {
            instance = new CameraManager();
        }
        return instance;
    }

    public enum Action {
        TAKE_PHOTO,
        TAKE_VIDEO,
        SELECT_PHOTO,
        SELECT_VIDEO
    }

    public void launch(Activity activity, Action what, View button) {
        this.mActivity = activity;
        CURRENT_ACTION = what;
        switch (CURRENT_ACTION) {
            case TAKE_PHOTO:
                setBtnListenerOrDisable(button, takePicListener());
                break;
            case TAKE_VIDEO:
                setBtnListenerOrDisable(button, takeVideoListener());
                break;
            case SELECT_PHOTO:
                setBtnListenerOrDisable(button, selectPictureFromGalleryListener());
                break;
            case SELECT_VIDEO:
                setBtnListenerOrDisable(button, selectVideoFromGalleryListener());
                break;
        }
    }

    public void onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCurrentAction();
                } else {
                    PermissionsManager.handleDenyAndNeverAskAgain(mActivity, permissions, grantResults, requestCode,
                            new PermissionsManager.OnNeverAskAgainListener() {
                                @Override
                                public void onNeverAskAgain() {
                                    Toast.makeText(mActivity, ERROR_NOT_ENOUGH_PERMISSIONS, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
        }
    }

    /**
     * Enables or disables button for photo-video Intent depending whether this Intent is available
     *
     * @param btn             - Button
     * @param onClickListener - Target onClickListener
     */
    private void setBtnListenerOrDisable(View btn,
                                         View.OnClickListener onClickListener) {
        btn.setOnClickListener(onClickListener);
    }

    /**
     * View take photo listener
     *
     * @return - OnClickListener
     */
    private View.OnClickListener takePicListener() {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCurrentAction();
            }
        };
    }

    /**
     * Button take video listener
     *
     * @return - OnClickListener
     */
    private Button.OnClickListener takeVideoListener() {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCurrentAction();
            }
        };
    }

    /**
     * Select video from gallery
     *
     * @return - OnClickListener
     */
    private Button.OnClickListener selectPictureFromGalleryListener() {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCurrentAction();
            }
        };
    }

    /**
     * Select video from gallery
     *
     * @return - OnClickListener
     */
    private View.OnClickListener selectVideoFromGalleryListener() {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCurrentAction();
            }
        };
    }

    private void startCurrentAction() {
        if (CURRENT_ACTION != null) {
            if (arePermissionsGranted(mActivity, RC_PERMISSIONS)) {
                switch (CURRENT_ACTION) {
                    case TAKE_PHOTO:
                        dispatchTakePictureIntent(ACTION_TAKE_CAMERA_PHOTO);
                        break;
                    case TAKE_VIDEO:
                        dispatchTakeVideoIntent();
                        break;
                    case SELECT_PHOTO:
                        takePhotoFromGallery();
                        break;
                    case SELECT_VIDEO:
                        takeVideoFromGallery();
                        break;
                }
            }
        }
    }

    private boolean arePermissionsGranted(Activity activity, int requestCode) {
        String[] permissions = {Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        return PermissionsManager.hasPermissions(activity, requestCode, permissions);
    }

    /**
     * Selects photo from Gallery
     */
    private void takePhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(mActivity.getPackageManager()) == null) {
            Logger.e(TAG, ERROR_CANNOT_PERFORM_ACTION);
            return;
        }
        mActivity.startActivityForResult(intent, ACTION_TAKE_GALLERY_PHOTO);
    }

    /**
     * Selects video from Gallery
     */
    private void takeVideoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.4);
        intent.setType("video/*");
        if (intent.resolveActivity(mActivity.getPackageManager()) == null) {
            Logger.e(TAG, ERROR_CANNOT_PERFORM_ACTION);
            return;
        }
        mActivity.startActivityForResult(intent, ACTION_TAKE_GALLERY_VIDEO);
    }

    private void dispatchTakeVideoIntent() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(mActivity.getPackageManager()) == null) {
            Logger.e(TAG, ERROR_CANNOT_PERFORM_ACTION);
            return;
        }
        mActivity.startActivityForResult(intent, ACTION_TAKE_CAMERA_VIDEO);
    }


    /**
     * Creates empty File for future image and also creates an Intent responsible for Camera
     *
     * @param actionCode - code of action
     */
    private void dispatchTakePictureIntent(int actionCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch (actionCode) {
            case ACTION_TAKE_CAMERA_PHOTO:
                File f;
                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    galleryAddPic(mActivity);
                } catch (IOException e) {
                    Logger.e(TAG, e.toString());
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;
            case ACTION_TAKE_GALLERY_PHOTO:
                takePhotoFromGallery();
                break;

            default:
                break;
        } // switch
        if (intent.resolveActivity(mActivity.getPackageManager()) == null) {
            Logger.e(TAG, ERROR_CANNOT_PERFORM_ACTION);
            return;
        }
        mActivity.startActivityForResult(intent, actionCode);
    }

    /* Photo album for this application */
    private String getAlbumName() {
        return APP_IMAGE_DIR_NAME;
    }


    /**
     * Creates app's storage directory
     *
     * @return - File
     */
    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Logger.e(TAG, ERROR_FAILED_TO_CREATE_DIRECTORY);
                        return null;
                    }
                }
            }

        } else {
            Logger.e(TAG, ERROR_STORAGE_NOT_MOUNTED);
        }

        return storageDir;
    }

    /**
     * Creates image File for future image
     *
     * @return - File
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat(JPEG_FILE_BODY_DATE_PATTERN, Locale.getDefault()).format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        return File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
    }

    /**
     * Sets up Photo file
     *
     * @return - File
     * @throws IOException
     */
    private File setUpPhotoFile() throws IOException {
        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();
        return f;
    }

    /**
     * Adds picture to gallery
     *
     * @param context - Context
     */
    private void galleryAddPic(Context context) {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File file = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(new File(mCurrentPhotoPath));
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * Abstract factory for getting album storage directory
     */
    abstract class AlbumStorageDirFactory {
        public abstract File getAlbumStorageDir(String albumName);
    }

    public final class BaseAlbumDirFactory extends AlbumStorageDirFactory {

        // Standard storage location for digital camera files
        private static final String CAMERA_DIR = "/test_storage/";

        @Override
        public File getAlbumStorageDir(String albumName) {
            return new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath()
                            + CAMERA_DIR
                            + albumName
            );
        }
    }

    public final class FroyoAlbumDirFactory extends AlbumStorageDirFactory {

        @Override
        public File getAlbumStorageDir(String albumName) {
            return new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES
                    ),
                    albumName
            );
        }
    }


}
