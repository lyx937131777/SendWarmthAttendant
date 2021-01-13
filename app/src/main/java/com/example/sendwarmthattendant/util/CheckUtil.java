package com.example.sendwarmthattendant.util;

import android.content.Context;
import android.widget.Toast;

public class CheckUtil
{
    private Context context;

    public CheckUtil(Context context)
    {
        this.context = context;
    }

    public boolean checkLogin(String tel, String password)
    {
        if (tel.length() != 11) {
            Toast.makeText(context, "手机号码格式不正确", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(context, "密码位数不正确", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkRegister(String tel, String password, String confirmPassword, String name, int workType1, int workType2, String id, String idCardFront, String idCardBack)
    {
        if (tel.length() != 11) {
            Toast.makeText(context, "手机号码格式不正确", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(context, "请输入至少6位的密码", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(context, "两次密码输入不一致", Toast.LENGTH_LONG).show();
            return false;
        }
//        if (userName.length() < 1) {
//            Toast.makeText(context, "请填写用户名", Toast.LENGTH_LONG).show();
//            return false;
//        }
        if(name.length() < 1){
            Toast.makeText(context, "请填写姓名", Toast.LENGTH_LONG).show();
            return false;
        }
        if(workType1 == 0){
            Toast.makeText(context, "请选择工种1", Toast.LENGTH_LONG).show();
            return false;
        }
        if(workType1 == workType2){
            Toast.makeText(context, "工种不可选择相同", Toast.LENGTH_LONG).show();
            return false;
        }
        if(id.length() != 18 || !fixId(id)){
            Toast.makeText(context, "身份证号格式不正确", Toast.LENGTH_LONG).show();
            return false;
        }
        if(idCardFront == null){
            Toast.makeText(context, "请拍摄身份证正面照", Toast.LENGTH_LONG).show();
            return false;
        }
        if(idCardBack == null){
            Toast.makeText(context, "请拍摄身份证反面照", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkChangePassword(String oldPassword, String newPassword, String confirmNewPassword){
        if (oldPassword.length() < 1) {
            Toast.makeText(context, "请填写原密码", Toast.LENGTH_LONG).show();
            return false;
        }
        if (newPassword.length() < 6) {
            Toast.makeText(context, "请输入至少6位的新密码", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(context, "确认新密码与新密码不一致", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkSendVerificationCode(String tel){
        if (tel.length() != 11) {
            Toast.makeText(context, "手机号码格式不正确", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkSetNewPassword(String tel, String newPassword, String confirmNewPassword, String verificationCode){
        if (tel.length() != 11) {
            Toast.makeText(context, "手机号码格式不正确", Toast.LENGTH_LONG).show();
            return false;
        }
        if (newPassword.length() < 6) {
            Toast.makeText(context, "请输入至少6位的新密码", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(context, "确认新密码与新密码不一致", Toast.LENGTH_LONG).show();
            return false;
        }
        if (verificationCode.length() < 1) {
            Toast.makeText(context, "请填写验证码", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean fixId(String id){
        for(int i = 0; i < 17; i++){
            if(id.charAt(i) < '0' || id.charAt(i) > '9'){
                return false;
            }
        }
        return true;
    }
}
