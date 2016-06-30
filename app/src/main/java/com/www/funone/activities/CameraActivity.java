package com.www.funone.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.www.funone.R;
import com.www.funone.fragments.NativeCameraFragment;
import com.www.funone.util.Validator;

public class CameraActivity extends BaseActivity implements View.OnClickListener {

    public static final int ACTION_TAKE_GALLERY_PHOTO = 3;

    public static final int CAPTURE_IMAGE = 1;
    public static final int CAPTURE_VIDEO = 2;

    private ImageButton mIbGallery;
    private ImageButton mIbCapture;
    private ImageButton mIbReverseCameras;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        loadFrg(NativeCameraFragment.newInstance(), R.id.content_camera);
        setUpToolBar();

        initCameraButtons();
        addButtonClickListeners(this);
    }

    private void initCameraButtons() {
        mIbGallery = (ImageButton) findViewById(R.id.ib_gallery);
        mIbCapture = (ImageButton) findViewById(R.id.ib_capture);
        mIbReverseCameras = (ImageButton) findViewById(R.id.ib_reverse_cameras);
        mIbReverseCameras.setTag(CAPTURE_IMAGE);
    }

    private void addButtonClickListeners(View.OnClickListener listener) {
        mIbGallery.setOnClickListener(listener);
        mIbCapture.setOnClickListener(listener);
        mIbReverseCameras.setOnClickListener(listener);
    }

    private void setUpToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResString(R.string.add_photo));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void reverseImages(View v) {
        int state = (int) v.getTag();
        if (state == CAPTURE_IMAGE) {
            mIbReverseCameras.setImageDrawable(getCompatDrawable(R.drawable.photo));
            mIbCapture.setImageDrawable(getCompatDrawable(R.drawable.shot));
            getSupportActionBar().setTitle(getResString(R.string.add_video));
            v.setTag(CAPTURE_VIDEO);
        } else {
            mIbReverseCameras.setImageDrawable(getCompatDrawable(R.drawable.video));
            mIbCapture.setImageDrawable(getCompatDrawable(R.drawable.make_photo));
            getSupportActionBar().setTitle(getResString(R.string.add_photo));
            v.setTag(CAPTURE_IMAGE);
        }
    }

    /**
     * Selects photo from Gallery
     *
     * @param activity - Activity
     */
    private void takePhotoFromGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (Validator.canResolveIntent(intent)) {
            activity.startActivityForResult(intent, ACTION_TAKE_GALLERY_PHOTO);
        } else {
            TOAST(getResString(R.string.err_could_not_open_gallery));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            TOAST("OK");
            CameraActivity.this.finish();
        } else if (requestCode == RESULT_CANCELED) {
            TOAST("CANCELED");
        } else if (requestCode == RESULT_FIRST_USER) {
            TOAST("FIRST USER");
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.content_camera) != null) {
            if (getSupportFragmentManager().findFragmentById(R.id.content_camera) instanceof NativeCameraFragment) {
                this.finish();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_gallery:
                takePhotoFromGallery(this);
                break;
            case R.id.ib_capture:
                break;
            case R.id.ib_reverse_cameras:
                reverseImages(v);
                break;
        }
    }
}
