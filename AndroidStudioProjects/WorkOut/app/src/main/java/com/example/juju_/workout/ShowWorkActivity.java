package com.example.juju_.workout;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by juju_ on 19/08/2016.
 */
public class ShowWorkActivity extends AppCompatActivity{
    final String EXTRA_POSITION_CHOICE="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showork);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView listV= (ListView) findViewById(R.id.workoutListView);

        // parser le doc des workout
        XMLDOMParser parser = new XMLDOMParser(this);

        FileInputStream stream_file;
        try {

            //stream = getResources().openRawResource(R.raw.total_list_workout);
            // pour ecrire sur sd

            File file = new File(Environment.getExternalStorageDirectory().toString()+"/Workout/total_list_workout.xml");
            stream_file = new FileInputStream(file);

            Document doc = parser.getDocument(stream_file);

            if(file.length()!=0){
            NodeList nodeList = doc.getElementsByTagName("workout");

            ArrayList<Workout> final_workouts = parser.getXMLWorkoutValue(nodeList);
                Collections.sort(final_workouts, new Comparator<Workout>() {
                    @Override
                    public int compare(Workout o1, Workout o2) {
                        return o1.toString().compareTo(o2.toString());
                    }
                });

            WorkoutAdapter adapter=new WorkoutAdapter(ShowWorkActivity.this,final_workouts);
            if(listV!=null){

                listV.setOnItemClickListener(new ListView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){
                        Intent intent=new Intent(ShowWorkActivity.this,ShowTrainingActivity.class);
                        String str_tmp=""+arg3;
                        intent.putExtra(EXTRA_POSITION_CHOICE,str_tmp);
                        startActivity(intent);
                    }
                });

                listV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        ImageButton img_buton_tmp=(ImageButton)view.findViewById(R.id.imageButton_delete);
                        if(img_buton_tmp!=null) {
                            if (img_buton_tmp.getVisibility() == View.INVISIBLE) {
                                img_buton_tmp.setVisibility(View.VISIBLE);
                            } else {
                                img_buton_tmp.setVisibility(View.INVISIBLE);
                            }
                        }
                        String str_tmp = "" + id;
                        getIntent().putExtra(EXTRA_POSITION_CHOICE, str_tmp);
                        return true;
                    }
                });
                listV.setAdapter(adapter);

            }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }// fin parseur


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

    public void buttonPressed(View view){
        switch (view.getId()){
            case R.id.button_back_showork :
                finish();
                break;
        }
    }


    //TODO
    public void buttonLongPressed(View v){
        switch(v.getId()){
            case R.id.imageButton_delete:
                XMLDOMParser parser = new XMLDOMParser(getApplicationContext());

                System.out.println(getIntent().getStringExtra(EXTRA_POSITION_CHOICE));

                ArrayList<Workout> array_wo_tmp=parser.deleteWorkoutByNumber(Integer.parseInt(getIntent().getStringExtra(EXTRA_POSITION_CHOICE)),v);
                ListView lv_tmp= (ListView)findViewById(R.id.workoutListView);
                WorkoutAdapter wo_adapt_tmp=(WorkoutAdapter)lv_tmp.getAdapter();
                wo_adapt_tmp.clear();
                wo_adapt_tmp.addAll(array_wo_tmp);
                wo_adapt_tmp.notifyDataSetChanged();

                ImageButton img_but_del_tmp=(ImageButton)v.findViewById(R.id.imageButton_delete);
                img_but_del_tmp.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
