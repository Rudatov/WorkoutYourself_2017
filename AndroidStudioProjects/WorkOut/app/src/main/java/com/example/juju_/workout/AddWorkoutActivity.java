package com.example.juju_.workout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by juju_ on 19/08/2016.
 */
public class AddWorkoutActivity extends AppCompatActivity {
    final String EXTRA_NOM_WORKOUT="nom_du_workout";
    final String EXTRA_NOMBRE_ETAPES="nombre_d_etape";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addworkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditText le_name_workout = (EditText) findViewById(R.id.new_training_name);
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String blockCharacterSet = "&~#^|$%*!@/()'\"\\:;,?{}<>=!$^';,?×÷{}€£¥₩%~`¤♡♥|《》¡¿°•○●□■◇◆♧♣▲▼▶◀↑↓←→☆★▪";
                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };
        le_name_workout.setFilters(new InputFilter[]{filter});

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
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
        switch(view.getId()){
            case R.id.button_back_addwork :
                finish();
                break;

            case R.id.add_etape_workout_button :
                Toast toast=Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
                EditText nom_workout = (EditText) findViewById(R.id.new_training_name);
                EditText nombre_etape = (EditText) findViewById(R.id.nombre_d_etape);
                if(nom_workout!=null&&nombre_etape!=null) {
                    if(nom_workout.getText().toString().equals("")){
                        toast.setText("Name missing");
                        toast.show();
                        break;
                    }
                    if(nombre_etape.getText().toString().equals("")||Integer.parseInt(nombre_etape.getText().toString())==0){
                        toast.setText("No exercices for a training ?\n Are you sure about that ?");
                        toast.show();
                        break;
                    }
                    Intent intent = new Intent(AddWorkoutActivity.this, AddEtapeActivity.class);
                    intent.putExtra(EXTRA_NOM_WORKOUT, nom_workout.getText().toString());
                    intent.putExtra(EXTRA_NOMBRE_ETAPES, nombre_etape.getText().toString());
                    startActivity(intent);
                    break;
                }
                else{
                    toast.setText("Data missing, please check again");
                    toast.show();
                    break;
                }

        }
    }
}
