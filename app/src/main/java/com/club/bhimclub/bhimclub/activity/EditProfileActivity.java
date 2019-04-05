package com.club.bhimclub.bhimclub.activity;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.club.bhimclub.bhimclub.GlideApp;
import com.club.bhimclub.bhimclub.R;
import com.club.bhimclub.bhimclub.model.ProfileImages;
import com.club.bhimclub.bhimclub.viewModel.ProfileViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditProfileActivity extends BaseActivity {

    private static final String TAG = EditProfileActivity.class.getSimpleName();
    private Unbinder unbinder;
    private ProfileViewModel mProfileViewModel;
    private ProfileImages mProfileImages;
    public static final int REQUEST_IMAGE = 100;

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

    @BindView(R.id.theamCamera)
    ImageView mtheamCamera;

    @BindView(R.id.imgTheam)
    ImageView mimgTheam;

    @BindView(R.id.cpersonview)
    ImageView mCpersonview;

    @BindView(R.id.personCamera)
    ImageView mPersonCamera;


    private boolean mWhichImgae;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        unbinder = ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        loadProfileDefault();
        // Get a new or existing ViewModel from the ViewModelProvider.
        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

    }

    @OnClick(R.id.theamCamera)
    public void TheamCamera() {
        mWhichImgae = false;
        popUpMenuCall(mtheamCamera);
    }

    @OnClick(R.id.personCamera)
    public void PersonCamera() {
        mWhichImgae = true;
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
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions(v);
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }

                }).check();

    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void showImagePickerOptions(View v) {
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

        switch (menuItem.getItemId()) {
            //image from gallery
            case R.id.image_item:
                launchGalleryIntent();
                break;
            case R.id.camera_item:
                launchCameraIntent();
                break;
            default:
                break;
        }
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(EditProfileActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(EditProfileActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        String base64string = Base64String(url);
        mProfileImages = new ProfileImages();
        mProfileImages.setUserType(loginPreferences.getString("UserType", "1"));
        mProfileImages.setPersonalImage(base64string);
        mProfileImages.setTheamImage(base64string);
        mProfileImages.setUserName(loginPreferences.getString("username", "1"));
        mProfileImages.setAddress(loginPreferences.getString("Address", "1"));
        mProfileImages.setCity(loginPreferences.getString("City", "1"));
        mProfileImages.setState(loginPreferences.getString("State", "1"));
        mProfileImages.setPhoneno(loginPreferences.getString("PhoneNumber", "1"));
        mProfileImages.setDesignation(loginPreferences.getString("Designation", "1"));
        mProfileImages.setDepartment(loginPreferences.getString("Department", "1"));
        mProfileImages.setEmail(loginPreferences.getString("username", "1"));
        mProfileImages.setEmployeeType(loginPreferences.getString("EmployeeType", "1"));
//        mProfileImages.setUserid(loginPreferences.getString("Userid", ""));
//        mProfileImages.setLoginUserName(loginPreferences.getString("LoginUserName", ""));


        if (mWhichImgae) {
            GlideApp.with(this).load(url)
                    .into(mCpersonview);
            mCpersonview.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
        } else {
            GlideApp.with(this).load(url)
                    .into(mimgTheam);
            mimgTheam.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
        }

    }

    private String Base64String(String url) {
        Bitmap bitmap = loadBitmap(url);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    private void loadProfileDefault() {
        GlideApp.with(this).load(R.drawable.contact_icon_profile)
                .into(mCpersonview);
        mCpersonview.setColorFilter(ContextCompat.getColor(this, R.color.profile_default_tint));
    }


    public Bitmap loadBitmap(String url) {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            mProfileViewModel.insertPresonalInfo(mProfileImages);
            onBackPressed();
            return true;
        }else if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
