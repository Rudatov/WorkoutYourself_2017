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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by juju_ on 22/08/2016.
 */
public class EditEtapeActivity extends AppCompatActivity {
    final String EXTRA_NOM_ETAPE="etape_string";
    final String EXTRA_DESC_ETAPE="desc_string";
    final String EXTRA_TEMPS_ETAPE="temps_string";
    final String EXTRA_PAUSE_ETAPE="pause_string";
    final String EXTRA_POSITION_KEY="key_position";
    final String EXTRA_TYPE_ACTIVITY="activity_type";
    final String EXTRA_POSITION_CHOICE="0";


    public Etape etape_tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editetape);

        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditText le_name_exercice = (EditText) findViewById(R.id.edit_etape_nom);
        EditText le_desc_exercice = (EditText) findViewById(R.id.edit_etape_desc);
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String blockCharacterSet = "&~#^|$%*!@/()'\"\\:;,?{}=!$^';,?×><÷{}€£¥₩%~`¤♡♥|《》¡¿°•○●□■◇◆♧♣▲▼▶◀↑↓←→☆★▪";
                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };
        InputFilter[] input_tab= new InputFilter[]{filter};
        le_name_exercice.setFilters(input_tab);
        le_desc_exercice.setFilters(input_tab);
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
            case R.id.button_back_edit :
                finish();
                break;

            case R.id.button_etape_add :
                int position_key=Integer.parseInt(getIntent().getStringExtra(EXTRA_POSITION_KEY));
                String position_workout = getIntent().getStringExtra(EXTRA_POSITION_CHOICE);
                EditText titre_tmp = (EditText) findViewById(R.id.edit_etape_nom);
                EditText desc_tmp = (EditText) findViewById(R.id.edit_etape_desc);
                EditText temps_tmp = (EditText) findViewById(R.id.edit_etape_temps);
                EditText pause_tmp = (EditText) findViewById(R.id.edit_etape_pause);
                Intent intent;
                if(getIntent().getStringExtra(EXTRA_TYPE_ACTIVITY).equals("transition")){
                    intent = new Intent(EditEtapeActivity.this,ShowTrainingActivity.class);
                }
                else {
                    intent = new Intent(EditEtapeActivity.this, AddEtapeActivity.class);
                }
                Toast toast = Toast.makeText(getApplicationContext(),"Data missing, please check again",Toast.LENGTH_LONG);

                if((titre_tmp==null)||(temps_tmp==null)||(pause_tmp==null)){
                    toast.setText("Data missing, please check again");
                    toast.show();
                    break;
                }
                else {
                    if(titre_tmp.getText().toString().equals("")){
                        toast.setText("Exercice name is missing");
                        toast.show();
                        break;
                    }
                    if(temps_tmp.getText().toString().equals("")){
                        toast.setText("Timer is missing.\n If you don\'t want it, please set it to 0");
                        toast.show();
                        break;
                    }
                    if(pause_tmp.getText().toString().equals("")){
                        toast.setText("Pause is missing.\n" +
                                " If you don't want it, please set it to 0");
                        toast.show();
                        break;
                    }
                    intent.putExtra(EXTRA_NOM_ETAPE, titre_tmp.getText().toString());
                    if(desc_tmp==null){
                        intent.putExtra(EXTRA_DESC_ETAPE, "");
                    }
                    else {
                        intent.putExtra(EXTRA_DESC_ETAPE, desc_tmp.getText().toString());
                    }
                    intent.putExtra(EXTRA_TEMPS_ETAPE, temps_tmp.getText().toString());
                    intent.putExtra(EXTRA_PAUSE_ETAPE, pause_tmp.getText().toString());
                    intent.putExtra(EXTRA_POSITION_KEY,""+position_key);
                    intent.putExtra(EXTRA_POSITION_CHOICE,position_workout);
                    setResult(1, intent);
                    finish();
                    break;
                }
        }
    }

}

