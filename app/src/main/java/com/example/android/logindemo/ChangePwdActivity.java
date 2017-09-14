package com.example.android.logindemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.utils.DbManager;
import com.example.android.utils.MySqliteHelper;
import com.example.android.utils.UserConstance;

/**
 * Created by wuzijian on 2017/9/14.
 */

public class ChangePwdActivity extends AppCompatActivity {
    private EditText mPhoneNumber;
    private EditText mPwd1;
    private EditText mPwd2;
    private Button mButton;
    private MySqliteHelper mMySqliteHelper;
    private SQLiteDatabase mDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
                         @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_change_password);

        mPhoneNumber = (EditText) findViewById(R.id.edit_change_phone_number);
        mPwd1 = (EditText) findViewById(R.id.edit_change_pwd1);
        mPwd2 = (EditText) findViewById(R.id.edit_change_pwd2);
        mButton = (Button) findViewById(R.id.btn_change_submit);

        mMySqliteHelper = DbManager.getInstance(getApplicationContext());
        mDatabase = mMySqliteHelper.getWritableDatabase();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = mPhoneNumber.getText().toString();
                String pwd1 = mPwd1.getText().toString();
                String pwd2 = mPwd2.getText().toString();

                if (!pwd1.equals(pwd2)) {
                    new AlertDialog.Builder(ChangePwdActivity.this).setTitle("错误")
                            .setMessage("两次输入密码不一致").setPositiveButton("确定", null)
                            .show();
                } else {
                    isUser(phoneNumber,pwd1);
                }
            }
        });


    }

    private void isUser(String phoneNumber, String pwd) {
        try {
            String sql = "select * from " + UserConstance.TABLE_NAME + " where "
                    + UserConstance.Cols.PHONE_NUMBER + "=?";
            Cursor cursor = mDatabase.rawQuery(sql, new String[] {phoneNumber});
            if (cursor.getCount() <= 0) {
                new AlertDialog.Builder(ChangePwdActivity.this).setTitle("错误")
                        .setMessage("该用户未注册").setPositiveButton("确定", null)
                        .show();
            } else {
                String updateSql = "update "+UserConstance.TABLE_NAME+
                        " set "+ UserConstance.Cols.PASSWORD+"="+pwd+
                        " where "+ UserConstance.Cols.PHONE_NUMBER+"="+phoneNumber;
                mDatabase.execSQL(updateSql);
                new AlertDialog.Builder(ChangePwdActivity.this).setTitle("正确")
                        .setMessage("修改成功").setPositiveButton("确定", null)
                        .show();
            }
        } catch (SQLiteException e) {
            // createDB();
            e.printStackTrace();
        }
    }

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, ChangePwdActivity.class);
        return intent;
    }
}
