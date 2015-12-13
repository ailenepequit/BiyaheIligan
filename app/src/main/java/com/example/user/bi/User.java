package com.example.user.bi;

import android.net.Uri;

/**
 * Created by USER on 12/13/2015.
 */
public class User {
    private String _name, _username, _email, _pwd;
    private Uri _imageUri;
    private int _id;

    public User(int id,String name,String username, String email, String pwd){
        _id=id;
        _name = name;
        _username = username;
        _email = email;
        _pwd = pwd;
        //_imageUri = imageUri;
    }

    public int getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public String getUsername(){
        return _username;
    }

    public String getEmail(){
        return _email;
    }

    public String getPassword(){
        return _pwd;
    }

    public Uri getImageUri(){
        return _imageUri;
    }
}