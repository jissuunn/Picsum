package com.example.blast_asiaexam_jayson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.blast_asiaexam_jayson.Fragments.MainFragment;

import java.util.Objects;

import okhttp3.internal.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        Utils.DisplayFragment(this, MainFragment.newInstance(), MainFragment.class.getName(), false);
        Utils.activity = this;
    }
}