package com.example.android.logindemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.utils.DbManager;
import com.example.android.utils.MySqliteHelper;

import java.sql.SQLDataException;

public class MainActivity extends AppCompatActivity {
    private EditText mName;
    private EditText mPwd;
    private Button mLogin;
    private Button mRegiste;
    private static SQLiteDatabase mDatabase;
    private MySqliteHelper mMySqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mName = (EditText) findViewById(R.id.edit_text_name);
        mPwd = (EditText) findViewById(R.id.edit_text_pwd);
        mLogin = (Button) findViewById(R.id.btn_login);
        mRegiste = (Button) findViewById(R.id.btn_register);

//        mDatabase = SQLiteDatabase.openOrCreateDatabase(MainActivity.this.getFilesDir().toString()
//                        + "/user.dbs", null);
//        createDB();
        mMySqliteHelper = DbManager.getInstance(this);
        mDatabase = mMySqliteHelper.getWritableDatabase();
        //跳转到注册页面
        mRegiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegisterActivity.getInstance(MainActivity.this);
                startActivity(intent);
            }
        });
        mLogin.setOnClickListener(new LoginListener());

    }

    private class LoginListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String name = mName.getText().toString();
            String password = mPwd.getText().toString();
            if ("".equals(name) || "".equals(password)) {
                new AlertDialog.Builder(MainActivity.this).setTitle("错误")
                        .setMessage("账号或密码不能为空").setPositiveButton("确定", null)
                        .show();
            } else {
                //判断该用户是否存在
                isUserInfo(name, password);
            }
        }

        public Boolean isUserInfo(String name, String password) {
            try {
                String sql = "select * from user where name=? and password=?";
                Cursor cursor = mDatabase.rawQuery(sql, new String[] {name,password});
                if (cursor.getCount() <= 0) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("错误")
                            .setMessage("账号或密码错误").setPositiveButton("确定", null)
                            .show();
                    return false;
                } else {
                    new AlertDialog.Builder(MainActivity.this).setTitle("正确")
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

    //创建数据库和用户表
    public void createDB() {
        mDatabase.execSQL("create table user(name varchar(20) primary key,password varchar(20))");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }
}
