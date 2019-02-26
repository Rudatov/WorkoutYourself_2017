package com.example.juju_.workout;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by juju_ on 19/08/2016.
 */
public class AddEtapeActivity extends AppCompatActivity {
    final String EXTRA_NOM_WORKOUT="nom_du_workout";
    final String EXTRA_NOMBRE_ETAPES="nombre_d_etape";
    final String EXTRA_NOM_ETAPE="etape_string";
    final String EXTRA_DESC_ETAPE="desc_string";
    final String EXTRA_TEMPS_ETAPE="temps_string";
    final String EXTRA_PAUSE_ETAPE="pause_string";
    final String EXTRA_TYPE_ACTIVITY="activity_type";
    final String EXTRA_POSITION_KEY="key_position";
    public ArrayList<Workout> array_work;
    public Workout workout;
    ArrayList<String> array_etape_tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addetape);


        Intent intent = getIntent();
        TextView new_name_workout=(TextView)findViewById(R.id.textView1_add_etape);
        TextView nombre_d_etape=(TextView) findViewById(R.id.textView2_add_etape);

        if(intent!=null){
            new_name_workout.setText(intent.getStringExtra(EXTRA_NOM_WORKOUT));
            nombre_d_etape.setText(intent.getStringExtra(EXTRA_NOMBRE_ETAPES)+" exercices.");
            workout=new Workout(Integer.parseInt(intent.getStringExtra(EXTRA_NOMBRE_ETAPES)),intent.getStringExtra(EXTRA_NOM_WORKOUT));
            for(int i=0;i<workout.getNbEtape();i++){
                workout.getList_etape().add(new Etape("New Exercice",0,0));
            }
            array_etape_tmp=new ArrayList<>(workout.getNbEtape());
            for(int i=0;i<workout.getNbEtape();i++){
                array_etape_tmp.add("New Exercice");
            }

            ArrayAdapter<String> array_adapt_tmp = new ArrayAdapter<String>(AddEtapeActivity.this,android.R.layout.simple_list_item_1,array_etape_tmp);
            final ListView lstview= (ListView) findViewById(R.id.listView_add_etape);
            lstview.setAdapter(array_adapt_tmp);
            lstview.setOnItemClickListener(new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Intent intent_tmp=new Intent(AddEtapeActivity.this,EditEtapeActivity.class);
                    intent_tmp.putExtra(EXTRA_TYPE_ACTIVITY,"Add_etape");
                    intent_tmp.putExtra(EXTRA_POSITION_KEY,""+arg3);
                    startActivityForResult(intent_tmp,1);

                }
            });
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {

        if (resultCode == 1) {
            String name_etape_tmp, desc_etape_tmp;
            int temps_etape_tmp, pause_etape_tmp;

            Etape etap_tmp;

            if (intent != null) {
                name_etape_tmp = intent.getExtras().getString(EXTRA_NOM_ETAPE);
                desc_etape_tmp = intent.getStringExtra(EXTRA_DESC_ETAPE);
                temps_etape_tmp = Integer.parseInt(intent.getStringExtra(EXTRA_TEMPS_ETAPE));
                pause_etape_tmp = Integer.parseInt(intent.getStringExtra(EXTRA_PAUSE_ETAPE));
                int indice_key = Integer.parseInt(intent.getStringExtra(EXTRA_POSITION_KEY));
                etap_tmp = new Etape(name_etape_tmp, desc_etape_tmp, temps_etape_tmp, pause_etape_tmp);
                if (!workout.getList_etape().contains(etap_tmp)) {
                    workout.getList_etape().set(indice_key,etap_tmp);
                }


                array_etape_tmp.set(workout.getList_etape().indexOf(etap_tmp),etap_tmp.getTitre());

                ArrayAdapter<String> array_adapt_tmp = new ArrayAdapter<String>(AddEtapeActivity.this, android.R.layout.simple_list_item_1, array_etape_tmp);
                final ListView lstview = (ListView) findViewById(R.id.listView_add_etape);
                lstview.setAdapter(array_adapt_tmp);
                lstview.setOnItemClickListener(new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        Intent intent_tmp=new Intent(AddEtapeActivity.this,EditEtapeActivity.class);
                        intent_tmp.putExtra(EXTRA_TYPE_ACTIVITY,"Add_etape");
                        intent_tmp.putExtra(EXTRA_POSITION_KEY,""+arg3);
                        startActivityForResult(intent_tmp,1);
                    }
                });
            }
        }
        super.onActivityResult(requestCode,resultCode,intent);
    }

    public void buttonPressed(View view) {
        switch(view.getId()) {
            case R.id.button_workout_add :
                XMLDOMParser parser = new XMLDOMParser(this);
                AssetManager manager = getAssets();
                InputStream stream;
                FileInputStream stream_file;
                FileWriter writer=null;
                try {

                    //stream = getResources().openRawResource(R.raw.total_list_workout);
                    // pour ecrire sur sd

                    File file = new File(Environment.getExternalStorageDirectory().toString(),"/Workout/total_list_workout.xml");
                    if(file.length()==0){

                        writer= new FileWriter(file);
                        writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
                        writer.write("<workouts>\n<workout name_workout=\""+workout.toString()+"\" nb_etape=\""+workout.getNbEtape()+"\">\n");
                        for(Etape etp_tmp:workout.getList_etape()){
                            writer.write("<etape nom_eta=\""+etp_tmp.getTitre()+"\" desc_eta=\""+etp_tmp.getDesc()+"\" temps=\""+etp_tmp.getTemps()+"\" pause=\""+etp_tmp.getPause()+"\"></etape>\n");
                        }
                        writer.write("</workout>\n</workouts>\n");
                        writer.flush();

                    }
                    else {
                        stream_file = new FileInputStream(file);

                        Document doc = parser.getDocument(stream_file);


                        NodeList nodeList = doc.getElementsByTagName("workout");
                        ArrayList<Workout> final_workouts = parser.getXMLWorkoutValue(nodeList);
                        final_workouts.add(workout);

                        parser.writeToSDCardWorkoutXML(Environment.getExternalStorageDirectory().toString() + "Workout/total_list_workout.xml", final_workouts);
                    }
                    Toast toast = Toast.makeText(getApplicationContext(),workout.toString()+" was succefully added.",Toast.LENGTH_LONG);
                    toast.show();
                    finish();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }// fin parseur
                break;

            case R.id.button_back_addetp :
                finish();
                break;

        }
    }
}
