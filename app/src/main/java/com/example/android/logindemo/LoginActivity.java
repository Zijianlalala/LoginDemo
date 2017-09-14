package com.example.android.logindemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.utils.DbManager;
import com.example.android.utils.MySqliteHelper;
import com.example.android.utils.UserConstance;

public class LoginActivity extends AppCompatActivity {
    private EditText mPhoneNumber;
    private EditText mPwd;
    private Button mLogin;
    private Button mRegiste;
    private Button mChangePwd;
    private static SQLiteDatabase mDatabase;
    private MySqliteHelper mMySqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPhoneNumber = (EditText) findViewById(R.id.edit_text_name);
        mPwd = (EditText) findViewById(R.id.edit_text_pwd);
        mLogin = (Button) findViewById(R.id.btn_login);
        mRegiste = (Button) findViewById(R.id.btn_register);
        mChangePwd = (Button) findViewById(R.id.btn_forget_pwd);

        mMySqliteHelper = DbManager.getInstance(this);
        mDatabase = mMySqliteHelper.getWritableDatabase();
        //跳转到注册页面
        mRegiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegisterActivity.getInstance(LoginActivity.this);
                startActivity(intent);
            }
        });

        mLogin.setOnClickListener(new LoginListener());

        //跳转到忘记密码页面
        mChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ChangePwdActivity.getInstance(LoginActivity.this);
                startActivity(intent);
            }
        });

    }

    private class LoginListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String phoneNumber = mPhoneNumber.getText().toString();
            String password = mPwd.getText().toString();
            if ("".equals(phoneNumber) || "".equals(password)) {
                new AlertDialog.Builder(LoginActivity.this).setTitle("错误")
                        .setMessage("账号或密码不能为空").setPositiveButton("确定", null)
                        .show();
            } else {
                //判断该用户是否存在
                isUserInfo(phoneNumber, password);
            }
        }

        public Boolean isUserInfo(String phoneNumber, String password) {
            try {
                String sql = "select * from " + UserConstance.TABLE_NAME + " where "
                        + UserConstance.Cols.PHONE_NUMBER + "=? and "
                        + UserConstance.Cols.PASSWORD + "=?";
                Cursor cursor = mDatabase.rawQuery(sql, new String[] {phoneNumber,password});
                if (cursor.getCount() <= 0) {
                    new AlertDialog.Builder(LoginActivity.this).setTitle("错误")
                            .setMessage("账号或密码错误").setPositiveButton("确定", null)
                            .show();
                    return false;
                } else {
                    new AlertDialog.Builder(LoginActivity.this).setTitle("正确")
                            .setMessage("成功登陆").setPositiveButton("确定", null)
                            .show();
                    return true;
                }
            } catch (SQLiteException e) {
               // createDB();
                e.printStackTrace();
            }
            return false;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }
}
