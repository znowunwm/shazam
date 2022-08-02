package com.example.shazam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;

public class History extends AppCompatActivity {

    private String[] history = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        for(int i=0; i<5; i++)history[i] = "-------------";
        readHistory();

        TextView tv1 = (TextView)findViewById(R.id.last1);
        tv1.setText(history[0]);

        TextView tv2 = (TextView)findViewById(R.id.last2);
        tv2.setText(history[1]);

        TextView tv3 = (TextView)findViewById(R.id.last3);
        tv3.setText(history[2]);

        TextView tv4 = (TextView)findViewById(R.id.last4);
        tv4.setText(history[3]);

        TextView tv5 = (TextView)findViewById(R.id.last5);
        tv5.setText(history[4]);


    }

    private void readHistory(){

        try {
            File myObj = new File("/storage/emulated/0/AudioRecord/history.txt");
            Scanner myReader = new Scanner(myObj);
            int i=0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                history[i]=data;
                i++;
                if(i==5)break;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}