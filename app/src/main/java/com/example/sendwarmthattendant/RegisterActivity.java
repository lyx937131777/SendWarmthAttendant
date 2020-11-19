package com.example.sendwarmthattendant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.db.ServiceSubject;
import com.example.sendwarmthattendant.presenter.RegisterPresenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

public class RegisterActivity extends AppCompatActivity implements OnClickListener
{
    //photo
    public static final int TAKE_PHOTO_FRONT = 1;
    public static final int TAKE_PHOTO_BACK = 2;
    private Uri imageUriFront;
    private String imagePathFront = null;
    private Uri imageUriBack;
    private String imagePathBack = null;

    private ImageView idCardFront;
    private ImageView idCardBack;


    private EditText telText, passwordText, confirmPasswordText,nameText,idText;
    private Button telClearButton, passwordClearButton, confirmPasswordClearButton,nameClearButton,idClearButton;
    private Button registerButton;
    private String tel, password, confirmPassword, name, id;

    private Spinner workTypeSpinner1;
    private List<ServiceSubject> workTypeList1 = new ArrayList<>();
    private ArrayAdapter<ServiceSubject> workTypeArrayAdapter1;
    private int workType1;

    private Spinner workTypeSpinner2;
    private List<ServiceSubject> workTypeList2 = new ArrayList<>();
    private ArrayAdapter<ServiceSubject> workTypeArrayAdapter2;
    private int workType2;

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(this)).build();
        registerPresenter = myComponent.registerPresenter();

        initView();

        idCardFront = findViewById(R.id.id_card_front);
        idCardBack = findViewById(R.id.id_card_back);
        idCardFront.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_PHOTO_FRONT);
                } else
                {
                    takePhotoFront();
                }
            }
        });

        idCardBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest
                        .permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_PHOTO_BACK);
                } else
                {
                    takePhotoBack();
                }
            }
        });
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

    private void initView()
    {
        telText = findViewById(R.id.tel);
        // 监听文本框内容变化
        telText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // 获得文本框中的用户
                tel = telText.getText().toString();
                if (tel.equals("")) {
                    // 用户名为空,设置按钮不可见
                    telClearButton.setVisibility(View.INVISIBLE);
                } else {
                    // 用户名不为空，设置按钮可见
                    telClearButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        passwordText = findViewById(R.id.password);
        passwordText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                password = passwordText.getText().toString();
                if (password.equals("")) {
                    passwordClearButton.setVisibility(View.INVISIBLE);
                } else {
                    passwordClearButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        confirmPasswordText = findViewById(R.id.confirm_password);
        confirmPasswordText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                confirmPassword = confirmPasswordText.getText().toString();
                if (confirmPassword.equals("")) {
                    confirmPasswordClearButton.setVisibility(View.INVISIBLE);
                } else {
                    confirmPasswordClearButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
//        userNameText = findViewById(R.id.user_name);
//        userNameText.addTextChangedListener(new TextWatcher()
//        {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count)
//            {
//                userName = userNameText.getText().toString();
//                if (userName.equals("")) {
//                    userNameClearButton.setVisibility(View.INVISIBLE);
//                } else {
//                    userNameClearButton.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after)
//            {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s)
//            {
//            }
//        });
        nameText = findViewById(R.id.name);
        nameText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                name = nameText.getText().toString();
                if(name.equals("")){
                    nameClearButton.setVisibility(View.INVISIBLE);
                }else {
                    nameClearButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
        idText = findViewById(R.id.id);
        idText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                id = idText.getText().toString();
                if(id.equals("")){
                    idClearButton.setVisibility(View.INVISIBLE);
                }else {
                    idClearButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        initworkTypeList();
        workTypeArrayAdapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,workTypeList1);
        workTypeArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workTypeSpinner1.setAdapter(workTypeArrayAdapter1);
        workTypeArrayAdapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,workTypeList2);
        workTypeArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workTypeSpinner2.setAdapter(workTypeArrayAdapter2);
        registerPresenter.updateServiceSubject(workTypeArrayAdapter1, workTypeList1, workTypeArrayAdapter2, workTypeList2);

        telClearButton = findViewById(R.id.tel_clear);
        telClearButton.setOnClickListener(this);
        passwordClearButton = findViewById(R.id.password_clear);
        passwordClearButton.setOnClickListener(this);
        confirmPasswordClearButton = findViewById(R.id.confirm_password_clear);
        confirmPasswordClearButton.setOnClickListener(this);
//        userNameClearButton = findViewById(R.id.user_name_clear);
//        userNameClearButton.setOnClickListener(this);
        nameClearButton = findViewById(R.id.name_clear);
        nameClearButton.setOnClickListener(this);
        idClearButton = findViewById(R.id.id_clear);
        idClearButton.setOnClickListener(this);
        registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(this);
    }

    private void initworkTypeList()
    {
        workTypeSpinner1 = findViewById(R.id.work_type_1);
        workTypeSpinner2 = findViewById(R.id.work_type_2);
        workTypeSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                workType1 = Integer.parseInt(workTypeList1.get(i).getInternetId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        workTypeSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                workType2 = Integer.parseInt(workTypeList2.get(i).getInternetId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            // 清除
            case R.id.tel_clear:
                telText.setText("");
                break;
            case R.id.password_clear:
                passwordText.setText("");
                break;
            case R.id.confirm_password_clear:
                confirmPasswordText.setText("");
                break;
//            case R.id.user_name_clear:
//                userNameText.setText("");
//                break;
            case R.id.name_clear:
                nameText.setText("");
                break;
            case R.id.id_clear:
                idText.setText("");
                break;

            case R.id.register:
                tel = telText.getText().toString();
                password = passwordText.getText().toString();
                confirmPassword = confirmPasswordText.getText().toString();
//                userName = userNameText.getText().toString();
                name = nameText.getText().toString();
                id = idText.getText().toString();
                registerPresenter.register(tel,password,confirmPassword,name,workType1,workType2,id,imagePathFront,imagePathBack);
                break;

            default:
                break;
        }


    }

    private void takePhotoFront(){
        // 创建File对象，用于存储拍照后的图片
        long time = System.currentTimeMillis();
        File outputImage = new File(getExternalCacheDir(), time+".jpeg");
        imagePathFront = outputImage.getAbsolutePath();
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
            imageUriFront = Uri.fromFile(outputImage);
        } else
        {
            imageUriFront = FileProvider.getUriForFile(RegisterActivity.this,
                    "com.example.sendwarmthattendant.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFront);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 0);
        startActivityForResult(intent, TAKE_PHOTO_FRONT);
    }

    private void takePhotoBack(){
        // 创建File对象，用于存储拍照后的图片
        long time = System.currentTimeMillis();
        File outputImage = new File(getExternalCacheDir(), time+".jpeg");
        imagePathBack = outputImage.getAbsolutePath();
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
            imageUriBack = Uri.fromFile(outputImage);
        } else
        {
            imageUriBack = FileProvider.getUriForFile(RegisterActivity.this,
                    "com.example.sendwarmthattendant.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriBack);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 0);
        startActivityForResult(intent, TAKE_PHOTO_BACK);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case TAKE_PHOTO_FRONT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoFront();
                } else {
                    Toast.makeText(this, "你拒绝了权限请求！", Toast.LENGTH_LONG).show();
                }
                break;
            case TAKE_PHOTO_BACK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoBack();
                } else {
                    Toast.makeText(this, "你拒绝了权限请求！", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case TAKE_PHOTO_FRONT:
                if (resultCode == RESULT_OK) {
                    try
                    {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUriFront));
//                        photoButton.setImageBitmap(bitmap);
                        Glide.with(this).load(imageUriFront).into(idCardFront);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } else {
                    imagePathFront = null;
                }
                break;
            case TAKE_PHOTO_BACK:
                if (resultCode == RESULT_OK) {
                    try
                    {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUriBack));
//                        photoButton.setImageBitmap(bitmap);
                        Glide.with(this).load(imageUriBack).into(idCardBack);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } else {
                    imagePathBack = null;
                }
                break;
            default:
                break;
        }
    }
}

