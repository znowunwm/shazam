package com.example.shazam;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wynik);

        TextView tv1 = (TextView)findViewById(R.id.wykpod);
        tv1.setText(MainActivity.wykonawca);

        TextView tv2 = (TextView)findViewById(R.id.linkpod);
        tv2.setText(MainActivity.link);
        SpannableString content = new SpannableString(tv2.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv2.setText(content);

        TextView tv3 = (TextView)findViewById(R.id.tytpod);
        tv3.setText(MainActivity.utwor);

    }
    public void goToMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void linkCLick(View v){
        TextView tv = (TextView)findViewById(R.id.linkpod);
        Uri uri = Uri.parse(String.valueOf(tv.getText())); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }



}