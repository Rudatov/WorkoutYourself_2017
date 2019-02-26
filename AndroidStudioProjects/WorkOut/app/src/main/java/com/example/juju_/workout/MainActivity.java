package com.example.juju_.workout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
//Gist passwork : tomato95
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileWriter file_ouput=null;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.isStoragePermissionGranted();
        setSupportActionBar(toolbar);
        File f1=new File(Environment.getExternalStorageDirectory().toString()+File.separator+"Workout");
        f1.mkdirs();
        //String str=Environment.getExternalStorageDirectory().toString()+File.separator+"Workout"+File.separator+"total_list_workout.xml";
        File file = new File(Environment.getExternalStorageDirectory().toString(),"/Workout/total_list_workout.xml");

        try {
                boolean try_test=file.createNewFile();
            if(try_test) {
                file_ouput = new FileWriter(file);

                file_ouput.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                file_ouput.write("<workouts><workout name_workout=\"le Nom\" nb_etape=\"3\">\n" +
                        "        <etape nom_eta=\"Etapeux\" desc_eta=\"ne rien faire\" temps=\"30\" pause=\"45\"></etape>\n" +
                        "        <etape nom_eta=\"Etapeux2\" desc_eta=\"ne rien faire\" temps=\"30\" pause=\"45\"></etape>\n" +
                        "        <etape nom_eta=\"Etapeux3\" desc_eta=\"ne rien faire\" temps=\"30\" pause=\"45\"></etape>\n" +
                        "    </workout></workouts>");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonPressed(View view) {
        switch(view.getId()) {
            case R.id.showWorkout :
                startActivity(new Intent(this, ShowWorkActivity.class));

                break;
            case R.id.addWorkout :
                startActivity(new Intent(this, AddWorkoutActivity.class));

                break;

            case R.id.button_quit:
                finish();
                break;
        }
    }
    public  boolean isStoragePermissionGranted() {
        String TAG="";
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }

        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        String TAG="";
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}
