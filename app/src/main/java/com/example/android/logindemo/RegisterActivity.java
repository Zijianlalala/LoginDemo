package com.example.android.logindemo;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.utils.DbManager;
import com.example.android.utils.MySqliteHelper;

/**
 * Created by wuzijian on 2017/9/11.
 * 需要改正的地方
 * 字段加上手机号
 * 需要二次确认密码而且密码长度要大于6
 */

public class RegisterActivity extends AppCompatActivity{
    private EditText mName;
    private EditText mPhoneNumber;
    private EditText mPwd1;
    private EditText mPwd2;
    private Button mSubmit;
    private MySqliteHelper mMySqliteHelper;
    SQLiteDatabase mDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = (EditText) findViewById(R.id.edit_text_register_name);
        mPhoneNumber = (EditText) findViewById(R.id.edit_text_phone_number);
        mPwd1 = (EditText) findViewById(R.id.edit_text_register_pwd_1);
        mPwd2 = (EditText) findViewById(R.id.edit_text_register_pwd_2);

        mSubmit = (Button) findViewById(R.id.btn_submit);

        mMySqliteHelper = DbManager.getInstance(getApplicationContext());

        mDatabase = mMySqliteHelper.getWritableDatabase();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String phoneNumber = mPhoneNumber.getText().toString();
                String password = mPwd1.getText().toString();
                String password2 = mPwd2.getText().toString();

                if (!("".equals(name) && "".equals(password) && "".equals(password2))) {
                    //name和password都不为空时
                    if (!password.equals(password2)) {
                        new AlertDialog.Builder(RegisterActivity.this).setTitle("错误")
                                .setMessage("两次输入密码不一致").setPositiveButton("确定", null)
                                .show();
                    }
                    else if(password.length() <= 6){
                        new AlertDialog.Builder(RegisterActivity.this).setTitle("错误")
                                .setMessage("密码不能小于等于六位").setPositiveButton("确定", null)
                                .show();
                    }
                    else if (addUser(name, phoneNumber, password)) {
                        //注册成功

                        new AlertDialog.Builder(RegisterActivity.this).setTitle("注册成功")
                                .setMessage("恭喜您注册成功").setPositiveButton("确定", null)
                                .show();
                    } else {
                        //注册失败
                        new AlertDialog.Builder(RegisterActivity.this).setTitle("错误")
                                .setMessage("注册失败，该手机号已经注册").setPositiveButton("确定", null)
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(RegisterActivity.this).setTitle("错误")
                            .setMessage("账号或密码不能为空").setPositiveButton("确定", null)
                            .show();
                }
            }
        });
    }

    public boolean addUser(String name, String phoneNumber, String password) {
        String sql = "insert into user values(?,?,?)";
        try {
            mDatabase.execSQL(sql, new String[] {phoneNumber, name ,password});
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }
}
