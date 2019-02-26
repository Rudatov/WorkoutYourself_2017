package com.example.juju_.workout;

/**
 * Created by juju_ on 19/08/2016.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class XMLDOMParser {
    public Context c;
    public XMLDOMParser(Context c){
           this.c=c;
    }
    //Returns the entire XML document
    public Document getDocument(InputStream inputStream) {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(inputStream);
            document = db.parse(inputSource);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        return document;
    }


    public ArrayList<Workout> getXMLWorkoutValue(NodeList nodes){

        //

        ArrayList<Workout> workouts=new ArrayList<Workout>(nodes.getLength());

        NamedNodeMap nnm, nnm_tmp;
        NodeList children;
        int int_nb_etape;
        int int_temps_etape;
        int int_pause_etape;
        String str_nom_workout;
        String str_titre_etape;
        String str_desc_etape;
        Node node,tmp_nod;
        Workout tmp_workout;
        Etape tmp_etape;

        for(int i=0;i<nodes.getLength();i++){
            node=nodes.item(i);

            nnm=node.getAttributes();
            Attr tmp_attr1,tmp_attr2;
            tmp_attr1=(Attr) nnm.getNamedItem("nb_etape");
            tmp_attr2=(Attr) nnm.getNamedItem("name_workout");

            int_nb_etape = Integer.parseInt(tmp_attr1.getValue());
            str_nom_workout=tmp_attr2.getValue();

            tmp_workout=new Workout(int_nb_etape,str_nom_workout);

            children=node.getChildNodes();
            for (int j=0;j<children.getLength();j++){
                if(j%2!=0) {
                    Attr tmp_attr3, tmp_attr4, tmp_attr5, tmp_attr6;
                    tmp_nod = children.item(j);
                    nnm_tmp = tmp_nod.getAttributes();
                    //
                    //
                    tmp_attr3 = (Attr) nnm_tmp.getNamedItem("nom_eta");
                    tmp_attr4 = (Attr) nnm_tmp.getNamedItem("desc_eta");
                    tmp_attr5 = (Attr) nnm_tmp.getNamedItem("temps");
                    tmp_attr6 = (Attr) nnm_tmp.getNamedItem("pause");
                    str_titre_etape = tmp_attr3.getValue();
                    str_desc_etape = tmp_attr4.getValue();
                    int_temps_etape = Integer.parseInt(tmp_attr5.getValue());
                    int_pause_etape = Integer.parseInt(tmp_attr6.getValue());

                    tmp_etape = new Etape(str_titre_etape, str_desc_etape, int_temps_etape, int_pause_etape);

                    tmp_workout.addEtape(tmp_etape);
                }
            }
            workouts.add(tmp_workout);

        }
        return workouts;
    }

    public void writeToSDCardWorkoutXML(String fileName,ArrayList<Workout> arrayworkout){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            fileName="total_list_workout";
            FileWriter writer = null;
            Workout wo_tmp;
            Etape etp_tmp;
            try {
                File directory= Environment.getExternalStorageDirectory();
                File file = new File(directory+"/Workout/total_list_workout.xml");

                writer= new FileWriter(file);
                writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
                writer.write("<workouts>\n");
                for( int i=0;i<arrayworkout.size();i++){
                    wo_tmp=arrayworkout.get(i);
                    writer.write("<workout name_workout=\""+wo_tmp.toString()+"\" nb_etape=\""+wo_tmp.getNbEtape()+"\">\n");
                    System.out.println(wo_tmp.toString());
                    for(int j=0;j<wo_tmp.getNbEtape();j++){
                        etp_tmp=wo_tmp.getList_etape().get(j);
                        writer.write("<etape nom_eta=\""+etp_tmp.getTitre()+"\" desc_eta=\""+etp_tmp.getDesc()+"\" temps=\""+etp_tmp.getTemps()+"\" pause=\""+etp_tmp.getPause()+"\"></etape>\n");
                    }
                    writer.write("</workout>\n");
                }
                writer.write("</workouts>\n");
                writer.flush();
            }
            catch(IOException e){
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public ArrayList<Workout> deleteWorkoutByNumber(int indice,View v){
        try {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/Workout/total_list_workout.xml");
            FileInputStream stream_file = new FileInputStream(file);
            Document doc = getDocument(stream_file);
            NodeList nodeList = doc.getElementsByTagName("workout");
            ArrayList<Workout> final_workouts = getXMLWorkoutValue(nodeList);
            Collections.sort(final_workouts, new Comparator<Workout>() {
                @Override
                public int compare(Workout o1, Workout o2) {
                    return o1.toString().compareTo(o2.toString());
                }
            });
            final_workouts.remove(indice);
            writeToSDCardWorkoutXML("",final_workouts);
            return final_workouts;


        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
