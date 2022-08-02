package com.example.shazam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Listening extends AppCompatActivity {

    RecordWavMaster RWM = new RecordWavMaster();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);
        RWM.recordWavStart();

        Thread sluchanie =  new Thread(new Sluchanie());
        sluchanie.start();

        Thread gif =  new Thread(new Gif());
        gif.start();


    }


    class MySend implements Runnable{

        @Override
        public void run() {
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect("192.168.1.101", 21); //serwer
                ftpClient.login("user", "123");
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
                BufferedInputStream buffIn = null;
                buffIn = new BufferedInputStream(new FileInputStream("/storage/emulated/0/AudioRecord/recording.wav"));
                ftpClient.enterLocalPassiveMode();
                ftpClient.storeFile("recording.wav", buffIn);
                buffIn.close();
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Sluchanie implements Runnable{
        @Override
        public void run() {
            SystemClock.sleep(7000);
            RWM.recordWavStop();
            try{
                Thread send =  new Thread(new MySend());
                send.start();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class Gif implements Runnable{
        @Override
        public void run() {
            int i=2;
            ImageView iv = (ImageView) findViewById(R.id.guzik);
            while (true){
                SystemClock.sleep(150);
                switch (i){
                    case 0:{
                        iv.setImageResource(R.drawable.przycisk_po0);
                    }break;
                    case 1:{
                        iv.setImageResource(R.drawable.przycisk_po1);
                    }break;
                    case 2:{
                        iv.setImageResource(R.drawable.przycisk_po2);
                    }break;
                    case 3:{
                        iv.setImageResource(R.drawable.przycisk_po3);
                    }break;
                    case 4:{
                        iv.setImageResource(R.drawable.przycisk_po4);
                    }break;
                }
                i++;
                if(i==5)i=0;
            }
        }
    }

}