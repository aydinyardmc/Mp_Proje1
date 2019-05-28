package com.aydinyardimci.mp_proje1;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class EditNote extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private EditText baslik ;
    private EditText icerik ;
    private String notFileName;
    private Not gettingNot;
    //private NumberPicker numberPicker2;
    public static int GettingNumberPicker;
    Dialog mainDialog;
    private ImageView alaramButton;
    public static String getNottificationTitle;//notifici icin
    public static String getNottificationContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        baslik = (EditText) findViewById(R.id.baslik);
        icerik = (EditText) findViewById(R.id.icerik);
        mainDialog = new Dialog(this);
        alaramButton = (ImageView) findViewById(R.id.alarmButton);

        alaramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new AlarmGetTImeFragment();
                timePicker.show(getSupportFragmentManager(),"timePicker");
            }
        });


        //numberPicker2 = (NumberPicker) findViewById(R.id.numberPickerPriority);
        //numberPicker2.setMinValue(1);
        //numberPicker2.setMaxValue(20);

        System.out.println("GETintet nFn:"+getIntent().getStringExtra("FileName"));


        notFileName= getIntent().getStringExtra("FileName");

        System.out.println("nFn:"+notFileName);


        getNottificationTitle=baslik.getText().toString();
        getNottificationContext=icerik.getText().toString();


        if((notFileName != null) && (!notFileName.isEmpty())){//daha once olusturulan not bilgilerini ekrana yukleme

            System.out.println("Dosya ismi alindi");
            gettingNot = SaveNote.getNoteName(this,notFileName);
            if(gettingNot != null){
                System.out.println("getting not bos degil setText");
                baslik.setText(gettingNot.getBaslik());//load baslik
                icerik.setText(gettingNot.getIcerik());//load not
                //numberPicker2.setValue(gettingNot.getPriorty());//loadPriortiy

            }
        }
        else{
            System.out.println("Dosya ismi alinamadi");
        }
    }

    public void Kaydet(){

        baslik = (EditText) findViewById(R.id.baslik);
        icerik = (EditText) findViewById(R.id.icerik);
        //numberPicker2 = (NumberPicker) findViewById(R.id.numberPickerPriority);
        Not not;
        System.out.println("picker"+GettingNumberPicker);

        getNottificationTitle=baslik.getText().toString();//notification icin
        getNottificationContext=icerik.getText().toString();

        if(gettingNot != null) {//daha onceden not olusturulmamissa
            System.out.println("getting not bos degil Not daha onceden vardi");
            not = new Not(baslik.getText().toString(), icerik.getText().toString(), gettingNot.getTime(),GettingNumberPicker);
        }else{//daha onceden not olusturulmussa
            not = new Not(baslik.getText().toString(), icerik.getText().toString(), System.currentTimeMillis(),GettingNumberPicker);
        }

        SaveNote.saveNotes(this,not);
        finish();
    }
    public void deleteNote(){
        if(gettingNot ==null){
            finish();

        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setTitle("Notu Sil")
                    .setMessage("Notu silmek istediğinizden emin misiniz ?")
                    .setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SaveNote.dellNote(getApplicationContext(),gettingNot.getTime()+ SaveNote.FILE_EXTENSION);
                            finish();
                        }
                    }).setNegativeButton("HAYIR",null).setCancelable(false);
            alertDialog.show();

        }
    }
    //share and dell button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu,menu);
        return true;


    }

    public void EditNoteSettings(){
         final NumberPicker numberPicker;
         TextView close;
         mainDialog.setContentView(R.layout.edit_note_settings);
         numberPicker = (NumberPicker) mainDialog.findViewById(R.id.MnumberPickerPriority);
         numberPicker.setMinValue(1);
         numberPicker.setMaxValue(20);
        if(gettingNot != null){
            System.out.println("getting not bos degil setText");
            numberPicker.setValue(GettingNumberPicker);//loadPriortiy

        }
         close = (TextView) mainDialog.findViewById(R.id.editNoteSettingsClose);
         close.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 GettingNumberPicker = numberPicker.getValue();
                 System.out.println("picker"+GettingNumberPicker);
                 mainDialog.dismiss();

             }
         });
         mainDialog.show();

    }
    //toolbar button ids
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        baslik = (EditText) findViewById(R.id.baslik);
        int buttonId = item.getItemId();

        if(buttonId == android.R.id.home){


            System.out.println("geri tusuna bastiiii");


            if(!baslik.getText().toString().isEmpty()){
                System.out.println("TEXT DOLUUUU");
                Kaydet();
            }
            else{
                System.out.println("TEXT bossss");
            }
        }
        if(buttonId == R.id.noteDell){
            deleteNote();
        }
        if(buttonId == R.id.noteSettings){
            EditNoteSettings();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        startAlarm(calendar);
    }
    private void startAlarm(Calendar calendar){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }
}

