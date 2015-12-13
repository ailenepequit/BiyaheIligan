package com.example.user.bi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private DatabaseHandler dbHandler;
    List<User> Users = new ArrayList<>();
    EditText name, username,email, password;
    String _name,_username,_email,_password;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHandler = new DatabaseHandler(getApplicationContext());
        name = (EditText)findViewById(R.id.editText);
        username = (EditText)findViewById(R.id.editTextUN);
        email = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPW);

        _name = String.valueOf(name.getText());
        _username = String.valueOf(username.getText());
        _email = String.valueOf(email.getText());
        _password = String.valueOf(password.getText());


        ImageButton signup = (ImageButton) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.print("signup");
                if(_name.equals(null) || _username.equals(null)) {
                    Toast.makeText(getApplicationContext(), "All Fields are Required.", Toast.LENGTH_SHORT).show();
                }
                if (isEmailValid(_email)) {
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                }
                if (isPasswordValid(_password)) {
                } else {
                    Toast.makeText(getApplicationContext(), "Password must be more than 4 characters", Toast.LENGTH_SHORT).show();
                }
                if (!_name.equals(null) && !_username.equals(null) && isEmailValid(_email) && isPasswordValid(_password)) {
                    user = new User(dbHandler.getUserCount(), _name, _username, _email, _password);
                    if (userExist(user)) {
                        dbHandler.createUser(user);
                        Users.add(user);
                        Toast.makeText(getApplicationContext(), _name + " successfully made an account!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(i);
                        return;
                    }
                    Toast.makeText(getApplicationContext(), _username + " already exist. Please use another username.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        System.out.println(user);
    }

    private boolean isEmailValid(String email) {
        if(email.contains("@") && (email.contains(".com") || email.contains(".edu")) )
            return true;
        else
            return false;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean userExist(User user){
        String name = user.getUsername();
        int userCount = Users.size();

        for (int i = 0; i < userCount; i++){
            if (name.compareToIgnoreCase(Users.get(i).getName()) == 0)
                return false;
        }
        return true;
    }

}
