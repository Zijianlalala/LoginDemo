package com.example.android.com.example.android.bean;

/**
 * Created by wuzijian on 2017/9/11.
 */

public class User {
    private int _id;
    private String name;
    private String password;

    public User(int _id, String name, String password) {
        this._id = _id;
        this.name = name;
        this.password = password;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
