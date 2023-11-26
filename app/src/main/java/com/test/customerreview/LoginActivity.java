package com.test.customerreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.test.customerreview.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DBHelper dbHelper = new DBHelper(this);
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    String name, pswrd = "";
    int customer_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
//        setContentView(R.layout.activity_login);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.background1));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        binding.textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }


    public void validate() {

        String username = binding.editTextUserName.getText().toString();
        String password = binding.editTextPassword.getText().toString();

        if (username.isEmpty()) {
            binding.editTextUserName.setError("Required");
            return;
        }

        if (username.length() != 10) {
            binding.editTextUserName.setError("Invalid Mobile no.");
            return;
        }

        if (password.isEmpty()) {
            binding.editTextPassword.setError("Required");
            return;
        }

        if (isValueExist(username)) {       // to check whether the username exists
            if (pswrd.equals(password)) {      // comparing the passwords
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                mEditor = mPreferences.edit();
                mEditor.putInt("customer_id", customer_id);
                mEditor.putString("name", name);
                mEditor.apply();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            } else
                Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid Username", Toast.LENGTH_SHORT).show();
        }
    }


   /* private String getPassword(String mobile){
        String query = "select * from Customer where mobile = ?";
        String[] whereArgs = {mobile};

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        cursor.moveToFirst();
        String password = cursor.getString(4);
        name = cursor.getString(1);
        customer_id = cursor.getInt(0);

        cursor.close();

        return password;
    }*/


    private boolean isValueExist(String mobile){
        String query = "select * from Customer where mobile = ?";
        String[] whereArgs = {mobile};

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();
        if (count > 0) {
            cursor.moveToFirst();
            pswrd = cursor.getString(4);
            name = cursor.getString(1);
            customer_id = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return count >= 1;
    }
}