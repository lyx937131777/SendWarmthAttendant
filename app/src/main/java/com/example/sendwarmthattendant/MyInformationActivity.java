package com.example.sendwarmthattendant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.Account;
import com.example.sendwarmthattendant.db.Worker;
import com.example.sendwarmthattendant.presenter.MyInformationPresenter;
import com.example.sendwarmthattendant.util.HttpUtil;
import com.example.sendwarmthattendant.util.LogUtil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyInformationActivity extends AppCompatActivity
{
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private static final int REQUEST_CODE = 1024;
    //photo
    private Dialog dialog;
    private Uri imageUri;
    private String imagePath = null;

    private Account account;

    private CircleImageView profile;
    private TextView nameText, addressText, telText, idText, workerClassText1, workerClassText2, levelText;
    private CardView profileCard, addressCard, workerClassCard1, workerClassCard2;

    private String role;
    private Helper helper;
    private Worker worker;

    private Button logoutButton,changePasswordButton;

    private Dialog profileDialog;
    private ImageView image;
    private Context context;

    private MyInformationPresenter myInformationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(this)).build();
        myInformationPresenter = myComponent.myInformationPresenter();
        context = this;

        initProfileDialog();
        initDialog();
        initUser();

        changePasswordButton = findViewById(R.id.change_password);
        changePasswordButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MyInformationActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MyInformationActivity.this).edit();
                editor.remove("userId");
                editor.remove("password");
                editor.apply();
                Intent intent_logout = new Intent(MyInformationActivity.this, LoginActivity.class);
                startActivity(intent_logout);
                MainActivity.instance.finish();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myInformationPresenter.updateAccount();
    }

    private void initProfileDialog()
    {
        profileDialog = new Dialog(MyInformationActivity.this,R.style.FullActivity);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        profileDialog.getWindow().setAttributes(attributes);

        image = getImageView();
        profileDialog.setContentView(image);

        //大图的点击事件（点击让他消失）
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileDialog.dismiss();
            }
        });
    }

    //动态的ImageView
    private ImageView getImageView(){
        ImageView imageView = new ImageView(this);

        //宽高
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //imageView设置图片
        @SuppressLint("ResourceType")
        InputStream is = getResources().openRawResource(R.drawable.profile_uri);

        Drawable drawable = BitmapDrawable.createFromStream(is, null);
        imageView.setImageDrawable(drawable);

        return imageView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void initUser()
    {
        role = getIntent().getStringExtra("role");
        if(role.equals("helper")){
            helper = (Helper) getIntent().getSerializableExtra("helper");
        }else{
            worker = (Worker) getIntent().getSerializableExtra("worker");
        }

        profile = findViewById(R.id.profile);
        profileCard = findViewById(R.id.profile_card);
        nameText = findViewById(R.id.name);
        addressText = findViewById(R.id.address);
        addressCard = findViewById(R.id.address_card);
        telText = findViewById(R.id.tel);
        idText = findViewById(R.id.id);
        workerClassText1 = findViewById(R.id.worker_class_1);
        workerClassText2 = findViewById(R.id.worker_class_2);
        workerClassCard1 = findViewById(R.id.worker_class_card_1);
        workerClassCard2 = findViewById(R.id.worker_class_card_2);
        levelText = findViewById(R.id.title);

        Glide.with(this).load(R.drawable.profile_uri).into(profile);
        if(role.equals("helper")){
            nameText.setText(helper.getHelperName());
            addressCard.setVisibility(View.GONE);
            telText.setText(helper.getHelperTel());
            idText.setText(helper.getHelperIdCard());
            workerClassText1.setText(helper.getWorkerClass1().toString());
            workerClassText2.setText(helper.getWorkerClass2().toString());
            levelText.setText(helper.getLevel()+"级助老员");
        }else{
            nameText.setText(worker.getWorkerName());
            addressText.setText(worker.getStoreName());
            telText.setText(worker.getWorkerTel());
            idText.setText(worker.getEmployeeId());
            levelText.setText(worker.getLevel()+"级护理员");
            workerClassCard1.setVisibility(View.GONE);
            workerClassCard2.setVisibility(View.GONE);
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileDialog.show();
            }
        });

        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }

    public void setAccount(Account account){
        this.account = account;
        String proFile = account.getProFile();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(proFile != null){
                    Glide.with(context).load(HttpUtil.getResourceURL(proFile)).into(profile);
                    Glide.with(context).load(HttpUtil.getResourceURL(proFile)).into(image);
                }else {
                    Glide.with(context).load(R.drawable.profile_uri).into(profile);
                    Glide.with(context).load(R.drawable.profile_uri).into(image);
                }
            }
        });
    }


    private void initDialog()
    {
        dialog = new Dialog(this, R.style.AppTheme);
        View view = View.inflate(this, R.layout.dialog_choose_image, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        //view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(this).getScreenHeight() *
        // 0.23f));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.width = (int) (ScreenSizeUtils.getInstance(this).getScreenWidth() * 0.9f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        Button dialogCameraBotton = view.findViewById(R.id.dialog_camera);
        Button dialogAlbumBotton = view.findViewById(R.id.dialog_album);
        Button dialogCancelBotton = view.findViewById(R.id.dialog_cancel);
        dialogCameraBotton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MyInformationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_PHOTO);
                } else {
                    takePhoto();
                }
                dialog.cancel();
            }
        });

        dialogAlbumBotton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MyInformationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PHOTO);
                } else {
                    openAlbum();
                }
                dialog.cancel();
            }

        });
        dialogCancelBotton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.cancel();
            }
        });
    }

    private void takePhoto(){
        long time = System.currentTimeMillis();
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(getExternalCacheDir(), time+".jpeg");
        imagePath = outputImage.getAbsolutePath();
        try
        {
            if (outputImage.exists())
            {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24)
        {
            imageUri = Uri.fromFile(outputImage);
        } else
        {
            imageUri = FileProvider.getUriForFile(context,
                    "com.example.sendwarmthattendant.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    // Camera and Album
    private void openAlbum()
    {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case TAKE_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    Toast.makeText(this, "你拒绝了权限申请", Toast.LENGTH_SHORT).show();
                }
                break;
            case CHOOSE_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "你拒绝了权限申请", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try
                    {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Glide.with(this).load(imageUri).into(profile);
                        resetProfile();
//                        imageView.setImageBitmap(bitmap);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }else{
                    imagePath = null;
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                    resetProfile();
                }
                break;
            case REQUEST_CODE:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()){
                    myInformationPresenter.resetProfile(imagePath);
                }else {
                    Toast.makeText(this,"存储权限未获取，无法使用此功能",Toast.LENGTH_LONG).show();
                }
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data)
    {
        Uri uri = data.getData();
        LogUtil.e("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data)
    {
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection)
    {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath)
    {
        LogUtil.e("album", "imagePath:" + imagePath);
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Glide.with(this).load(imagePath).into(profile);
//            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "打开图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetProfile(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                myInformationPresenter.resetProfile(imagePath);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
            }
        }else {
            myInformationPresenter.resetProfile(imagePath);
        }
    }

}