package com.club.bhimclub.bhimclub.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.club.bhimclub.bhimclub.R;
import com.club.bhimclub.bhimclub.viewModel.ProfileViewModel;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditProfileActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private ProfileViewModel mProfileViewModel;
    private static final int RESULT_PROFILE_IMAGE = 1;
    private static final int REQ_CAMERA_IMAGE = 2;

    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private static final int PERMISSION_REQUEST_WRITE = 2;
    private static final int PERMISSION_REQUEST_READ = 13;
    private static final int RESULT_CROP = 5;
    private Uri imageUri;
    // UI references.

    @BindView(R.id.personName)
    EditText mPersonName;

    @BindView(R.id.personEmail)
    EditText mPersonEmail;

    @BindView(R.id.personPhone)
    EditText mPersonPhone;

    @BindView(R.id.personDesignation)
    EditText mPersonDesignation;

    @BindView(R.id.personDepartment)
    EditText mPersonDepartment;

    @BindView(R.id.personCamera)
    ImageView mPersonCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        unbinder = ButterKnife.bind(this);

    }

    @OnClick(R.id.theamCamera)
    public void TheamCamera(){
        
    }
    @OnClick(R.id.personCamera)
    public void PersonCamera(){
        popUpMenuCall(mPersonCamera);
    }

    private static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
//            Logger.e(TAG, e.getMessage());
        }
    }
    private void popUpMenuCall(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.profile_option, popup.getMenu());
        setForceShowIcon(popup);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                performMenu(menuItem);
                return false;
            }
        });
        popup.show();
    }
    private void performMenu(MenuItem menuItem) {
        Intent intent;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
        }
        switch (menuItem.getItemId()) {
            //image from gallery
            case R.id.image_item:
//                Logger.d(TAG, " image_item");
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Permission is already available, start camera preview
                    /*Snackbar.make(ApplicationController.appactivity.findViewById(android.R.id.content),
                            "permission is available.",
                            Snackbar.LENGTH_SHORT).show();*/
                    intent.setType("image/jpg | image/jpeg");
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_PROFILE_IMAGE);
                } else {
                    // Permission is missing and must be requested.
                    requestCameraPermission(PERMISSION_REQUEST_READ);
                }
                break;
            case R.id.camera_item:
//                Logger.d(TAG, " Camera");

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Permission is already available, start camera preview
                    // Permission is already available, start camera preview
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                      /*  Snackbar.make(ApplicationController.appactivity.findViewById(android.R.id.content),
                            "Camera permission is available. Starting preview.",
                            Snackbar.LENGTH_SHORT).show();*/
                        startCamera();
                    } else {
                        requestCameraPermission(PERMISSION_REQUEST_WRITE);
                    }
                } else {
                    // Permission is missing and must be requested.
                    requestCameraPermission(PERMISSION_REQUEST_CAMERA);
                }
                break;
            default:
                break;
        }
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File sdcard = Environment.getExternalStorageDirectory();
        File filePath = new File(sdcard + "/bhimclub/Capature/");
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        File photo = new File(filePath, System.currentTimeMillis() + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, REQ_CAMERA_IMAGE);
    }

    /**0
     * Req0uests the {@link android.Manifest.permission#CAMERA} permission.
     * If0 an additional rationale should be displayed, the user has to launch the request from
     * a0 SnackBar that includes additional information.
     */
    private void requestCameraPermission(int permissonValue) {
        switch (permissonValue) {
            case PERMISSION_REQUEST_CAMERA:
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    // Provide an00 additional rationale to the user if the permission was not granted
                    // and the u0ser would benefit from additional context for the use of the permission.
                    // Display a SnackBar with a button to request the missing permission.
                    /*Snackbar.make(ApplicationController.appactivity.findViewById(android.R.id.content), "Camera access is required to display the camera preview.",
                            Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {*/
                            // Request the permission
                            ActivityCompat.requestPermissions(EditProfileActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    PERMISSION_REQUEST_CAMERA);
                  /*      }
                    }).show();*/

                } else {
                    /*Snackbar.make(ApplicationController.appactivity.findViewById(android.R.id.content),
                            "Camera access Permission is not available.",
                            Snackbar.LENGTH_SHORT).show();*/
                    // Request the permission. The result will be received in onRequestPermissionResult().
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CAMERA);
                }
                break;
            case PERMISSION_REQUEST_WRITE:
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    /*Snackbar.make(ApplicationController.appactivity.findViewById(android.R.id.content), "Storage access is required to save images.",
                            Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {*/
                            // Request the permission
                            ActivityCompat.requestPermissions(EditProfileActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSION_REQUEST_WRITE);
                      /*  }
                    }).show();*/
                } else {
                    /*Snackbar.make(ApplicationController.appactivity.findViewById(android.R.id.content),
                            "Storage access Permission is not available.",
                            Snackbar.LENGTH_SHORT).show();*/
                    // Request the permission. The result will be received in onRequestPermissionResult().
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_WRITE);
                }
                break;
            case PERMISSION_REQUEST_READ:
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    /*Snackbar.make(ApplicationController.appactivity.findViewById(android.R.id.content), "Storage access is required to display images.",
                            Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {*/
                            // Request the permission
                            ActivityCompat.requestPermissions(EditProfileActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    PERMISSION_REQUEST_READ);
                      /*  }
                    }).show();*/
                } else {
                    /*Snackbar.make(ApplicationController.appactivity.findViewById(android.R.id.content),
                            "Permissions is not available.",
                            Snackbar.LENGTH_SHORT).show();*/
                    // Request the permission. The result will be received in onRequestPermissionResult().
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_READ);
                }
                break;
            default:
                break;
        }

    }

}
