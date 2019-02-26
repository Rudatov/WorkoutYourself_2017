package com.example.juju_.workout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by juju_ on 22/08/2016.
 */
public class ShowTrainingActivity extends AppCompatActivity{
    final String EXTRA_POSITION_CHOICE="0";
    final String EXTRA_NOM_ETAPE="etape_string";
    final String EXTRA_DESC_ETAPE="desc_string";
    final String EXTRA_TEMPS_ETAPE="temps_string";
    final String EXTRA_PAUSE_ETAPE="pause_string";
    final String EXTRA_POSITION_KEY="key_position";
    final String EXTRA_TYPE_ACTIVITY="activity_type";

    public int test_tmp=0;
    public boolean isUp=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_training);
        RelativeLayout rl_toto=(RelativeLayout)findViewById(R.id.total_view_show_train);
        if(rl_toto!=null) {
            rl_toto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isUp) {
                        RelativeLayout rl_tmp = (RelativeLayout) findViewById(R.id.rel_layout_edit_train);
                        if (rl_tmp != null) {
                            if (Build.VERSION.SDK_INT >= 21) {
                                if (rl_tmp != null) {
                                    rl_tmp.setTranslationY(200);
                                    isUp = false;
                                }
                            }
                        }
                    }
                }
            });
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView listV= (ListView) findViewById(R.id.listView_show_training);
        RelativeLayout rl_tmp=(RelativeLayout)findViewById(R.id.rel_layout_edit_train);
        if(Build.VERSION.SDK_INT>=21) {
            if(rl_tmp!=null) {
                rl_tmp.setZ(5);
            }
        }
        // parser le doc des workout
        XMLDOMParser parser = new XMLDOMParser(this);
        Intent intent=getIntent();
        FileInputStream stream_file;
        try {

            //stream = getResources().openRawResource(R.raw.total_list_workout);
            // pour ecrire sur sd

            File file = new File(Environment.getExternalStorageDirectory().toString()+"/Workout/total_list_workout.xml");
            stream_file = new FileInputStream(file);

            Document doc = parser.getDocument(stream_file);
            String str_pos=intent.getStringExtra(EXTRA_POSITION_CHOICE);

            int position=Integer.parseInt(str_pos);

            NodeList nodeList = doc.getElementsByTagName("workout");

            final ArrayList<Workout> final_workouts = parser.getXMLWorkoutValue(nodeList);
            final Workout wo_tmp=final_workouts.get(position);
            TextView txtV_nom=(TextView) findViewById(R.id.textView_lrg_training_name);
            if(txtV_nom!=null) {
                txtV_nom.setText(wo_tmp.toString());
            }

            TrainingAdapter adapter=new TrainingAdapter(ShowTrainingActivity.this,wo_tmp.getList_etape());
            if(listV!=null) {
                listV.setAdapter(adapter);


                listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Toast toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG);
                        if (isUp) {
                            RelativeLayout rl_tmp = (RelativeLayout) findViewById(R.id.rel_layout_edit_train);
                            if (rl_tmp != null) {
                                if (Build.VERSION.SDK_INT >= 21) {
                                    if (rl_tmp != null) {
                                        rl_tmp.setTranslationY(200);
                                        isUp = false;
                                    }
                                }
                            }
                        }
                        if(Build.VERSION.SDK_INT>=16) {
                            //Permet de ne pas recycler la vue de l'animation en cours d'execution(animation timer multiple)

                            view.setHasTransientState(true);


                        }
                        listV.setEnabled(false);

                        //listV.setScrollContainer(false);
                        view.findViewById(R.id.textView_go).setVisibility(View.GONE);
                        view.setOnClickListener(null);
                        final int pos=(int)id;
                        final View tmp_view=view;
                        final TimerView tmr_view=(TimerView) view.findViewById(R.id.time_etape_view);


                        if(tmr_view!=null) {
                                toast.setText("GO");
                                toast.show();

                                final TimerEtapeAnimation tmr_etp_anim = new TimerEtapeAnimation(tmr_view, 360);
                                tmr_etp_anim.setDuration(wo_tmp.getList_etape().get(pos).getTemps() * 1000);
                                tmr_etp_anim.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        tmr_view.setPaint(Color.rgb(254, 195, 1));
                                        toast.setText("Now rest time");
                                        toast.show();
                                        tmr_view.clearAnimation();
                                        tmr_view.setAngle(0);
                                        TimerEtapeAnimation tmr_etp_anim_2 = new TimerEtapeAnimation(tmr_view, 360);
                                        tmr_etp_anim_2.setDuration(wo_tmp.getList_etape().get(pos).getPause() * 1000);
                                        tmr_etp_anim_2.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {
                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                tmr_view.setPaint(Color.rgb(19, 142, 0));

                                                toast.setText("Done");
                                                toast.show();
                                                tmr_view.clearAnimation();
                                                tmr_view.setAngle(0);
                                                TimerEtapeAnimation tmr_etp_anim_3 = new TimerEtapeAnimation(tmr_view, 360);
                                                tmr_etp_anim_3.setDuration(200);

                                                tmr_view.startAnimation(tmr_etp_anim_3);
                                                if(Build.VERSION.SDK_INT>=16) {
                                                    tmp_view.setHasTransientState(true);
                                                }
                                                listV.setEnabled(true);

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {
                                            }
                                        });

                                        tmr_view.startAnimation(tmr_etp_anim_2);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }
                                });

                                tmr_view.startAnimation(tmr_etp_anim);

                            }
                        }


                });
                listV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        RelativeLayout rl_tmp=(RelativeLayout)findViewById(R.id.rel_layout_edit_train);
                        if(Build.VERSION.SDK_INT>=21) {
                            if(rl_tmp!=null) {

                                rl_tmp.setTranslationY(-200);
                                isUp=true;
                            }
                        }
                        test_tmp=(int)id;
                        return true;
                    }
                });
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }// fin parseur


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {
        try{
        final ListView lst_view_tmp = (ListView) findViewById(R.id.listView_show_training);
        if (resultCode == 1) {
            String name_etape_tmp, desc_etape_tmp;
            int temps_etape_tmp, pause_etape_tmp;
            RelativeLayout rl_toto=(RelativeLayout)findViewById(R.id.total_view_show_train);
            if(rl_toto!=null) {
                rl_toto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isUp) {
                            RelativeLayout rl_tmp = (RelativeLayout) findViewById(R.id.rel_layout_edit_train);
                            if (rl_tmp != null) {
                                if (Build.VERSION.SDK_INT >= 21) {
                                    if (rl_tmp != null) {
                                        rl_tmp.setTranslationY(200);
                                        isUp = false;
                                    }
                                }
                            }
                        }
                    }
                });
            }
            Etape etap_tmp;

            if (intent != null) {
                name_etape_tmp = intent.getExtras().getString(EXTRA_NOM_ETAPE);
                desc_etape_tmp = intent.getStringExtra(EXTRA_DESC_ETAPE);
                temps_etape_tmp = Integer.parseInt(intent.getStringExtra(EXTRA_TEMPS_ETAPE));
                pause_etape_tmp = Integer.parseInt(intent.getStringExtra(EXTRA_PAUSE_ETAPE));
                int indice_key = Integer.parseInt(intent.getStringExtra(EXTRA_POSITION_KEY));
                etap_tmp = new Etape(name_etape_tmp, desc_etape_tmp, temps_etape_tmp, pause_etape_tmp);
                int indice_workout = Integer.parseInt(getIntent().getStringExtra(EXTRA_POSITION_CHOICE));
                File file = new File(Environment.getExternalStorageDirectory().toString() + "/Workout/total_list_workout.xml");
                FileInputStream stream_file = new FileInputStream(file);
                XMLDOMParser parser = new XMLDOMParser(getApplicationContext());
                Document doc = parser.getDocument(stream_file);

                if (file.length() != 0) {
                    NodeList nodeList = doc.getElementsByTagName("workout");

                    ArrayList<Workout> final_workouts = parser.getXMLWorkoutValue(nodeList);
                    final Workout workout = final_workouts.get(indice_workout);
                    workout.getList_etape().set(indice_key, etap_tmp);
                    final_workouts.set(indice_workout,workout);
                    parser.writeToSDCardWorkoutXML("",final_workouts);



                    TrainingAdapter train_adap = new TrainingAdapter(getApplicationContext(),workout.getList_etape());

                    final ListView lstview = (ListView) findViewById(R.id.listView_add_etape);
                    lst_view_tmp.setAdapter(train_adap);
                    lst_view_tmp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (isUp) {
                                RelativeLayout rl_tmp = (RelativeLayout) findViewById(R.id.rel_layout_edit_train);
                                if (rl_tmp != null) {
                                    if (Build.VERSION.SDK_INT >= 21) {
                                        if (rl_tmp != null) {
                                            rl_tmp.setTranslationY(200);
                                            isUp = false;
                                        }
                                    }
                                }
                            }
                            final Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
                            view.findViewById(R.id.textView_go).setVisibility(View.GONE);
                            view.setOnClickListener(null);
                            final int pos = (int) id;
                            final TimerView tmr_view = (TimerView) view.findViewById(R.id.time_etape_view);
                            if (tmr_view != null) {
                                ListView lst_view_tmp = (ListView) findViewById(R.id.listView_show_training);
                                if(lst_view_tmp!=null) {
                                    lst_view_tmp.setEnabled(false);
                                }
                                toast.setText("GO");
                                toast.show();

                                final TimerEtapeAnimation tmr_etp_anim = new TimerEtapeAnimation(tmr_view, 360);
                                tmr_etp_anim.setDuration(workout.getList_etape().get(position).getTemps() * 1000);
                                tmr_etp_anim.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        tmr_view.setPaint(Color.rgb(254, 195, 1));
                                        toast.setText("Now rest time");
                                        toast.show();
                                        tmr_view.clearAnimation();
                                        tmr_view.setAngle(0);
                                        TimerEtapeAnimation tmr_etp_anim_2 = new TimerEtapeAnimation(tmr_view, 360);
                                        tmr_etp_anim_2.setDuration(workout.getList_etape().get(pos).getPause() * 1000);
                                        tmr_etp_anim_2.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {
                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                ListView lst_view_tmp = (ListView) findViewById(R.id.listView_show_training);
                                                if(lst_view_tmp!=null) {
                                                    lst_view_tmp.setEnabled(true);
                                                }
                                                tmr_view.setPaint(Color.rgb(19, 142, 0));
                                                toast.setText("Done");
                                                toast.show();
                                                tmr_view.clearAnimation();
                                                tmr_view.setAngle(0);
                                                TimerEtapeAnimation tmr_etp_anim_3 = new TimerEtapeAnimation(tmr_view, 360);
                                                tmr_etp_anim_3.setDuration(200);
                                                tmr_view.startAnimation(tmr_etp_anim_3);

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {
                                            }
                                        });

                                        tmr_view.startAnimation(tmr_etp_anim_2);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }
                                });

                                tmr_view.startAnimation(tmr_etp_anim);

                            }
                        }
                    });
                    lst_view_tmp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            RelativeLayout rl_tmp=(RelativeLayout)findViewById(R.id.rel_layout_edit_train);
                            if(Build.VERSION.SDK_INT>=21) {
                                if(rl_tmp!=null) {
                                    rl_tmp.setTranslationY(-200);
                                    isUp=true;
                                }
                            }
                            test_tmp=(int)id;
                            return true;
                        }
                    });
                }
            }
            super.onActivityResult(requestCode, resultCode, intent);
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
        RelativeLayout rl_tmp;
        switch (view.getId()) {
            case R.id.button_back_showtrain:
                ListView lst_tmp = (ListView) findViewById(R.id.listView_show_training);
                if (lst_tmp != null) {
                    if (!lst_tmp.isEnabled())
                        lst_tmp.setEnabled(true);
                }
                finish();
                break;

            case R.id.button_back_transit:
                rl_tmp = (RelativeLayout) findViewById(R.id.rel_layout_edit_train);
                if (rl_tmp != null) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        if (rl_tmp != null) {
                            rl_tmp.setTranslationY(200);
                            isUp = false;
                        }
                    }

                }
                break;

            case R.id.button_edit_transit:
                rl_tmp = (RelativeLayout) findViewById(R.id.rel_layout_edit_train);
                if (rl_tmp != null) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        if (rl_tmp != null) {
                            rl_tmp.setTranslationY(200);
                            isUp = false;
                        }
                    }
                }
                Intent intent = new Intent(ShowTrainingActivity.this, EditEtapeActivity.class);
                intent.putExtra(EXTRA_POSITION_KEY, "" + test_tmp);
                intent.putExtra(EXTRA_TYPE_ACTIVITY, "transition");
                intent.putExtra(EXTRA_POSITION_CHOICE, getIntent().getStringExtra(EXTRA_POSITION_CHOICE));
                startActivityForResult(intent, 1);
        }
    }

}
