package com.example.nargel.prova1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private ImageButton but;
    private ImageButton sos;
    private boolean flash=false;
    private boolean ON=false;
    private boolean SOSon=false;
    Camera camera;
    Camera.Parameters parameters;

    protected void onStop() {
        super.onStop();
        if(camera != null){
            camera.release();
            camera = null;
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sos = (ImageButton) findViewById(R.id.SOS);
        but = (ImageButton) findViewById(R.id.onoff);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,}, 1000);
        } else {


            if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                camera = camera.open();
                parameters = camera.getParameters();
                flash = true;
            }


          /*  but.setOnClickListener(new View.OnClickListener(){

                @Override
                public  void onClick(View v) {
                    if (flash) {
                        if (!ON) ON();
                        else OF();
                    }
                }
            });

          /*  sos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!SOSon) SOSon=true;
                    else SOSon=false;
                }
            });
          */


        }
    }
    public void SOS(View view){
        if(SOSon)SOSon=false;
        else{
            SOSon= true;
            SOSrun();
        }
    }
    private void SOSrun(){
        int cont=0;
        while(SOSon){
            if (!ON) {
                ON();
                if(cont<3||cont>5)SystemClock.sleep(400);
                else  SystemClock.sleep(1100);
                cont++;
                if(cont>8){
                    cont=0;
                    SOSon=false;
                }
            }else {
                SystemClock.sleep(100);
                OF();
            }
            //SystemClock.sleep(1000);
        }
        OF();

    }
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camara= camara.open();
        parametros=camara.getParameters();


        linterna();


    }
*/
    public void linterna(View view){

        if (flash) {
            if (!ON) ON();
            else OF();
        }

    }




    private void ON(){
        if(!ON){

            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            ON=true;
        }
    }

    private void OF(){
        if(ON){

            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            ON=false;
        }
    }


}
