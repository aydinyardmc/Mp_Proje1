package com.aydinyardimci.mp_proje1;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SaveNote {

    public static int notDisplaySettings=0;
    public static int notSetDisplayPriority;

    public static final String FILE_EXTENSION = ".bin";

    public static boolean saveNotes(Context context,Not not){

        String file = String.valueOf(not.getTime())+ FILE_EXTENSION ;

        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        try{
            fileOutputStream = context.openFileOutput(file,context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(not);
            fileOutputStream.close();
            objectOutputStream.close();

        }catch (IOException e){
            e.printStackTrace();
            return  false;
        }

        return true;

    }
    public static ArrayList<Not> allDelNotes(Context context){

        int i;
        ArrayList<Not> notlar = new ArrayList<>();

        File filesdir = context.getFilesDir();
        ArrayList<String> notFile = new ArrayList<>();

        for(String file : filesdir.list()){
            if(file.endsWith(FILE_EXTENSION)){
                notFile.add(file);
            }
        }
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        for(i=0; i<notFile.size(); i++){
            try {
                fileInputStream = context.openFileInput(notFile.get(i));
                objectInputStream = new ObjectInputStream(fileInputStream);

                notlar.clear();
                fileInputStream.close();
                objectInputStream.close();

            }catch (IOException e){
                e.printStackTrace();
                return  null;
            }
        }
        return notlar;
    }
    public static ArrayList<Not> allSaveNotes(Context context){

        int i;
        ArrayList<Not> notlar = new ArrayList<>();

        File filesdir = context.getFilesDir();
        ArrayList<String> notFile = new ArrayList<>();

        for(String file : filesdir.list()){
            if(file.endsWith(FILE_EXTENSION)){
                notFile.add(file);
            }
        }
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        for(i=0; i<notFile.size(); i++){
            try {

                fileInputStream = context.openFileInput(notFile.get(i));
                objectInputStream = new ObjectInputStream(fileInputStream);
                if(notDisplaySettings == 0) {
                    notlar.add(0, (Not) objectInputStream.readObject());//son eklenen liste basinda
                }
                if(notDisplaySettings == 1){
                    notlar.add( (Not) objectInputStream.readObject());//son eklenen liste sonunda
                }
                if(notDisplaySettings == 2){
                    notlar.add(notSetDisplayPriority,(Not) objectInputStream.readObject());//oncelige gore ekle
                }
                fileInputStream.close();
                objectInputStream.close();

            }catch (IOException e){
                e.printStackTrace();
                return  null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return  null;
            }
        }
        return notlar;
    }
    public static Not getNoteName(Context context,String fileName){
        File file = new File(context.getFilesDir(),fileName);
        Not not;
        if(file.exists()){
            FileInputStream fileInputStream;
            ObjectInputStream objectInputStream;
            try {
                fileInputStream = context.openFileInput(fileName);
                objectInputStream= new ObjectInputStream(fileInputStream);

                not =(Not) objectInputStream.readObject();

                fileInputStream.close();
                objectInputStream.close();


            }catch (IOException e){
                e.printStackTrace();
                return null;
            }catch (ClassNotFoundException e){
                e.printStackTrace();
                return  null;
            }
            return not;
        }
        return null;
    }

    public static void dellNote(Context context,String fileName){

        File dir = context.getFilesDir();
        File file = new File(dir, fileName);

        if(file.exists()){

            file.delete();

        }
    }

}

