package com.example.android.utils;

import android.content.Context;
import android.database.Cursor;

import com.example.android.com.example.android.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzijian on 2017/9/11.
 */

public class DbManager {
    private  static MySqliteHelper sMySqliteHelper;

    public static MySqliteHelper getInstance(Context context) {
        if (sMySqliteHelper ==  null) {
            sMySqliteHelper = new MySqliteHelper(context);
        }
        return sMySqliteHelper;
    }
    //将cursor对象转换成list
//    public static List<User> cursorToList(Cursor cursor) {
//        List<User> list = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(cursor.getColumnIndex(UserConstance.Cols._ID));
//            String name = cursor.getString(cursor.getColumnIndex(UserConstance.Cols.NAME));
//            String password = cursor.getString(cursor.getColumnIndex(UserConstance.Cols.PASSWORD));
//            User user = new User(id, name, password);
//            list.add(user);
//        }
//        return list;
//    }

}
