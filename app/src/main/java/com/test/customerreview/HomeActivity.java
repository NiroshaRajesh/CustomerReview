package com.test.customerreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.Toast;

import com.test.customerreview.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    SharedPreferences mPreferences;
    String name = "";
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.background1));

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        name = mPreferences.getString("name", null);
        binding.textViewName.setText(name);

        binding.feedbackBedCover.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, FeedbackActivity.class).putExtra("flag", String.valueOf(1)));
        });

        binding.feedbackGardenTable.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, TableFeedbackActivity.class).putExtra("flag", String.valueOf(2)));
        });
    }


    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}