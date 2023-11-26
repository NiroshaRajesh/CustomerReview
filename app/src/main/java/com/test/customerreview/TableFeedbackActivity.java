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
import android.widget.RadioButton;
import android.widget.Toast;

import com.test.customerreview.databinding.ActivityFeedbackBinding;
import com.test.customerreview.databinding.ActivityTableFeedbackBinding;

public class TableFeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTableFeedbackBinding binding;
    DBHelper dbHelper = new DBHelper(this);
    boolean edit = true, flag = false;
    SharedPreferences mPreferences;
    int customer_id = 0;
    int height = -1, quality = -1, appearance = -1, satisfaction =-1, future_buy =-1, rate = 0;
    String familiarity = "", review = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_table_feedback);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.background1));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        customer_id = mPreferences.getInt("customer_id", 0);
        if (customer_id != 0) {
            if (feedback(customer_id)) {
                edit = false;
            }
        }

        if (!edit) {
            setViewMode();
        }

        binding.back.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.buttonCancel.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.rate1.setOnClickListener(this);
        binding.rate2.setOnClickListener(this);
        binding.rate3.setOnClickListener(this);
        binding.rate4.setOnClickListener(this);
        binding.rate5.setOnClickListener(this);

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

    }


    private boolean feedback(int customer_id) {
        String query = "select * from GardenTable where cid = ?";
        String[] whereArgs = {String.valueOf(customer_id)};

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();
        if (count > 0) {
            cursor.moveToFirst();

        }

        cursor.close();
        db.close();

        return count >= 1;
    }

    void setViewMode() {
        binding.buttonLayout.setVisibility(View.GONE);
        binding.rateLayout.setVisibility(View.GONE);
        binding.displayrateLayout.setVisibility(View.VISIBLE);
        binding.radioButton.setEnabled(false);
        binding.radioButton2.setEnabled(false);
        binding.radioButton3.setEnabled(false);
        binding.radioButton4.setEnabled(false);
        binding.radioButton5.setEnabled(false);
        binding.radioButton6.setEnabled(false);
        binding.radioButton7.setEnabled(false);
        binding.radioButton8.setEnabled(false);
        binding.radioButton9.setEnabled(false);
        binding.radioButton10.setEnabled(false);
        binding.radioButton11.setEnabled(false);
        binding.radioButton12.setEnabled(false);
        binding.radioButton13.setEnabled(false);
        binding.radioButton14.setEnabled(false);
        binding.radioButton15.setEnabled(false);
        binding.radioButton16.setEnabled(false);
        binding.editTextText2.setFocusable(false);
        binding.editTextText2.setEnabled(false);
        binding.editTextTextMultiLine.setFocusable(false);
        binding.editTextTextMultiLine.setEnabled(false);

        Cursor cursor = dbHelper.getData("GardenTable", customer_id);
        if (cursor.getCount() > 0) {
            rate = Integer.parseInt(cursor.getString(1));
            height = Integer.parseInt(cursor.getString(2));
            quality = Integer.parseInt(cursor.getString(3));
            appearance = Integer.parseInt(cursor.getString(4));
            satisfaction = Integer.parseInt(cursor.getString(5));
            future_buy = Integer.parseInt(cursor.getString(6));
            familiarity = cursor.getString(7);
            review = cursor.getString(8);
        }

        Log.d("Test", "rate = " + rate);
        Log.d("Test", "height = " + height);
        Log.d("Test", "quality = " + quality);
        Log.d("Test", "appearance = " + appearance);
        Log.d("Test", "satisfaction = " + satisfaction);
        Log.d("Test", "future_buy = " + future_buy);
        Log.d("Test", "familiarity = " + familiarity);
        Log.d("Test", "review = " + review);
        setRate(rate);
        if (height > -1)
            ((RadioButton)binding.radioGroup1.getChildAt(height)).setChecked(true);
        if (quality > -1)
            ((RadioButton)binding.radioGroup2.getChildAt(quality)).setChecked(true);
        if (appearance > -1)
            ((RadioButton)binding.radioGroup3.getChildAt(appearance)).setChecked(true);
        if (satisfaction > -1)
            ((RadioButton)binding.radioGroup4.getChildAt(satisfaction)).setChecked(true);
        if (future_buy > -1)
            ((RadioButton)binding.radioGroup5.getChildAt(future_buy)).setChecked(true);

        binding.editTextText2.setText(familiarity);
        binding.editTextText2.setTextColor(getColor(R.color.background1));
        binding.editTextTextMultiLine.setText(review);
        binding.editTextTextMultiLine.setTextColor(getColor(R.color.background1));

        cursor.close();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rate1) {
            flag = true;
            rate = 1;
            binding.rate1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate2.setImageDrawable(getResources().getDrawable(R.drawable.star_border));
            binding.rate3.setImageDrawable(getResources().getDrawable(R.drawable.star_border));
            binding.rate4.setImageDrawable(getResources().getDrawable(R.drawable.star_border));
            binding.rate5.setImageDrawable(getResources().getDrawable(R.drawable.star_border));
        }
        else if (view.getId() == R.id.rate2) {
            flag = true;
            rate = 2;
            binding.rate1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate2.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate3.setImageDrawable(getResources().getDrawable(R.drawable.star_border));
            binding.rate4.setImageDrawable(getResources().getDrawable(R.drawable.star_border));
            binding.rate5.setImageDrawable(getResources().getDrawable(R.drawable.star_border));
        }
        else if (view.getId() == R.id.rate3) {
            flag = true;
            rate = 3;
            binding.rate1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate2.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate3.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate4.setImageDrawable(getResources().getDrawable(R.drawable.star_border));
            binding.rate5.setImageDrawable(getResources().getDrawable(R.drawable.star_border));
        }
        else if (view.getId() == R.id.rate4) {
            flag = true;
            rate = 4;
            binding.rate1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate2.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate3.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate4.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate5.setImageDrawable(getResources().getDrawable(R.drawable.star_border));
        }

        else if (view.getId() == R.id.rate5) {
            flag = true;
            rate = 5;
            binding.rate1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate2.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate3.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate4.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate5.setImageDrawable(getResources().getDrawable(R.drawable.star));
        }

    }


    private void submit() {
        if (flag) {
            height = checkHeight();
            quality = checkQuality();
            appearance = checkAppearance();
            satisfaction = checkSatisfaction();
            future_buy = checkFutureBuy();
            familiarity = binding.editTextText2.getText().toString();
            review = binding.editTextTextMultiLine.getText().toString();

            saveData();
        } else {
            binding.textView11.setError("Required");
        }
    }


    private void saveData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // insert values
        Log.d("save", "rate = " + rate);
        Log.d("save", "height = " + height);
        Log.d("save", "quality = " + quality);
        Log.d("save", "appearance = " + appearance);
        Log.d("save", "satisfaction = " + satisfaction);
        Log.d("save", "future_buy = " + future_buy);
        Log.d("save", "familiarity = " + familiarity);
        Log.d("save", "review = " + review);
        ContentValues values = new ContentValues();
        values.put("cid", customer_id);
        values.put("feedback_rate", String.valueOf(rate));
        values.put("height", String.valueOf(height));
        values.put("quality", String.valueOf(quality));
        values.put("appearance", String.valueOf(appearance));
        values.put("satisfaction", String.valueOf(satisfaction));
        values.put("future_buy", String.valueOf(future_buy));
        values.put("familiarization", familiarity);
        values.put("review", review);
        long row = db.insert("GardenTable", null, values);

        db.close();
        if (row == -1)
            Toast.makeText(this, "Review submission failed", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Review submission successful", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(TableFeedbackActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }


    private void setRate(int rate) {
        if (rate == 1) {
            binding.rate11.setImageDrawable(getResources().getDrawable(R.drawable.star));
        } else if (rate == 2) {
            binding.rate11.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate12.setImageDrawable(getResources().getDrawable(R.drawable.star));
        } else if (rate == 3) {
            binding.rate11.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate12.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate13.setImageDrawable(getResources().getDrawable(R.drawable.star));
        } else if (rate == 4) {
            binding.rate11.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate12.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate13.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate14.setImageDrawable(getResources().getDrawable(R.drawable.star));
        } else {
            binding.rate11.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate12.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate13.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate14.setImageDrawable(getResources().getDrawable(R.drawable.star));
            binding.rate15.setImageDrawable(getResources().getDrawable(R.drawable.star));
        }
    }


    private int checkHeight() {
        int x = -1;
        if (binding.radioButton5.isChecked())
            x = 0;
        else if (binding.radioButton6.isChecked())
            x = 1;
        else if (binding.radioButton7.isChecked())
            x = 2;
        else if (binding.radioButton8.isChecked())
            x = 3;
        Log.d("checkh", "x = " + x);
        return x;
    }


    private int checkQuality() {
        int x = -1;
        if (binding.radioButton9.isChecked())
            x = 0;
        else if (binding.radioButton10.isChecked())
            x = 1;
        else if (binding.radioButton11.isChecked())
            x = 2;
        else if (binding.radioButton12.isChecked())
            x = 3;
        Log.d("checkq", "x = " + x);
        return x;
    }


    private int checkAppearance() {
        int x = -1;
        if (binding.radioButton13.isChecked())
            x = 0;
        else if (binding.radioButton14.isChecked())
            x = 1;
        else if (binding.radioButton15.isChecked())
            x = 2;
        else if (binding.radioButton16.isChecked())
            x = 3;
        Log.d("checka", "x = " + x);
        return x;
    }


    private int checkSatisfaction() {
        int x = -1;
        if (binding.radioButton3.isChecked())
            x = 0;
        else if (binding.radioButton4.isChecked())
            x = 1;
        Log.d("checks", "x = " + x);
        return x;
    }


    private int checkFutureBuy() {
        int x = -1;
        if (binding.radioButton.isChecked())
            x = 0;
        else if (binding.radioButton2.isChecked())
            x = 1;
        Log.d("checkb", "x = " + x);
        return x;
    }

}