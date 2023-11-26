package com.test.customerreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.test.customerreview.databinding.ActivityLoginBinding;
import com.test.customerreview.databinding.ActivityRegisterBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    DBHelper dbHelper = new DBHelper(this);
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    int customer_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.background1));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        binding.textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    public void validate() {

        String name = binding.editTextName.getText().toString();
        String mobile = binding.editTextMobile.getText().toString();
        String email = binding.editTextEmail.getText().toString();
        String password = binding.editTextPassword.getText().toString();

        if (name.isEmpty()) {
            binding.editTextName.setError("Required");
            return;
        }

        if (mobile.isEmpty()) {
            binding.editTextMobile.setError("Required");
            return;
        }

        if (mobile.length() != 10) {
            binding.editTextMobile.setError("Invalid Mobile no.");
            return;
        }

        if (email.isEmpty()) {
            binding.editTextEmail.setError("Required");
            return;
        }

        if (!isEmailValid(email)) {
            binding.editTextEmail.setError("Invalid Email");
            return;
        }

        if (password.isEmpty()) {
            binding.editTextPassword.setError("Required");
            return;
        }

        if (password.length() < 6) {
            binding.editTextPassword.setError("Password should have atleast 6 character");
            return;
        }


//        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
//        String projection[] = {"mobile"};
//        Cursor c = db1.query("Customer", projection, null, null, null, null, null);
//        c.moveToFirst();
//        if (c.getString(0).isEmpty())


        if(!isValueExist(mobile)) { // sava data to table if the mobile number is not exixsting
            // write to db
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // insert values
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("mobile", mobile);
            values.put("email", email);
            values.put("password", password);
            long row = db.insert("Customer", null, values);
            Log.d("Register", "row = " + row);
            db.close();

            if (row == -1)
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                customer_id = dbHelper.getCustomerId(mobile);
                mEditor = mPreferences.edit();
                mEditor.putInt("customer_id", customer_id);
                mEditor.putString("name", name);
                mEditor.apply();
                startActivity(new Intent(RegisterActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }

        } else
            Toast.makeText(this, "Mobile number already exist", Toast.LENGTH_SHORT).show();
    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // to check whether the phone number already exist
    private boolean isValueExist(String mobile){
        String query = "select * from Customer where mobile = ?";
        String[] whereArgs = {mobile};

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count >= 1;
    }
}