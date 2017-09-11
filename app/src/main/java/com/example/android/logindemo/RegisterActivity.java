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
 */

public class RegisterActivity extends AppCompatActivity{
    private EditText mName;
    private EditText mPwd;
    private Button mSubmit;
    private Button mReset;
    private MySqliteHelper mMySqliteHelper;
    SQLiteDatabase mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = (EditText) findViewById(R.id.edit_text_register_name);
        mPwd = (EditText) findViewById(R.id.edit_text_register_pwd);
        mSubmit = (Button) findViewById(R.id.btn_submit);
        mReset = (Button) findViewById(R.id.btn_reset);

        mMySqliteHelper = DbManager.getInstance(getApplicationContext());

        mDatabase = mMySqliteHelper.getWritableDatabase();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String password = mPwd.getText().toString();

                if (!("".equals(name) && "".equals(password))) {
                    //name和password都不为空时
                    if (addUser(name,password)) {
                        //注册成功
                        new AlertDialog.Builder(RegisterActivity.this).setTitle("注册成功")
                                .setMessage("恭喜您注册成功").setPositiveButton("确定", null)
                                .show();
                    } else {
                        //注册失败
                        new AlertDialog.Builder(RegisterActivity.this).setTitle("错误")
                                .setMessage("注册失败请输入新的用户名").setPositiveButton("确定", null)
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(RegisterActivity.this).setTitle("错误")
                            .setMessage("账号或密码不能为空").setPositiveButton("确定", null)
                            .show();
                }
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mName.setText("");
                mPwd.setText("");
            }
        });
    }

    public boolean addUser(String name, String password) {
        String sql = "insert into user values(?,?)";
        try {
            mDatabase.execSQL(sql, new String[] {name,password});
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
