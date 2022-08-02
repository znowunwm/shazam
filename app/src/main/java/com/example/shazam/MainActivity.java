package com.example.shazam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


//////////////////////////////////////////////////////////////////////////////////////////////


    private static int MICROPHONE_PERMISSION_CODE = 200;
    public static String link;
    public static String utwor;
    public static String wykonawca;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            Thread myThread = new Thread(new MyServerThread());
            myThread.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(isMicrophonePresent()){
            getMicrophonePermission();
        }
    }



    public void btnRecord(View v){
        Intent listening = new Intent(this, Listening.class);
        startActivity(listening);
    }

    private boolean isMicrophonePresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }
        else return false;
    }

    private void getMicrophonePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},MICROPHONE_PERMISSION_CODE);
        }
    }

    private void showWynik(){
        Intent intent = new Intent(this, Result.class);
        startActivity(intent);

    }

    private void showError(){
        Intent intent = new Intent(this, Error.class);
        startActivity(intent);
    }

    private void writeHistory(String autor, String tytul){

        String zapis = autor + " - " + tytul;

        List<String> list = new ArrayList<>();
        try {
            File myObj = new File("/storage/emulated/0/AudioRecord/history.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                list.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        try{
            File file = new File("/storage/emulated/0/AudioRecord/history.txt");

            // if file doesnt exists, then create it
            if (!file.exists())
                file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(zapis);
            bw.write("\n");
            for (int i = 0; i <list.size(); i++) {
                bw.write(list.get(i));
                bw.write("\n");
            }
            bw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void historyClick(View v){

        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }



    class MyServerThread implements Runnable{
        Socket s;
        ServerSocket ss;
        InputStreamReader isr;
        BufferedReader bufferedReader;
        String message;

        Handler h = new Handler();



        @Override
        public void run() {
            try {
                ss = new ServerSocket(8080);
                while (true){
                    s = ss.accept();
                    isr = new InputStreamReader(s.getInputStream());
                    bufferedReader = new BufferedReader(isr);
                    message= bufferedReader.readLine();
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            if (message.equals("=!=")) {
                                showError();
                            }
                            else {
                                String[] separated = message.split("=!=");
                                utwor=separated[1];
                                wykonawca=separated[0];
                                link=separated[2];
                                writeHistory(wykonawca,utwor);
                                showWynik();
                            }
                        }
                    });
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

}