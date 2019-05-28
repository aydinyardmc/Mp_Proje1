package com.aydinyardimci.mp_proje1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class TrashActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ListView listViewNote;
    private SwipeMenuListView swipeMenu;
    //private Not nott;
    private Not gettingNot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("TRASHHHHHHH");
        swipeMenu =(SwipeMenuListView) findViewById(R.id.TrashlistView);


        setContentView(R.layout.activity_trash);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("cop kutusu");
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FloatingActionButton floatingActionButton= findViewById(R.id.delAllNotesButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Not> notlar2 = TrashNote.TrashAllDelNotes(getApplicationContext());
                NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(),R.layout.single_note_view,notlar2);
                swipeMenu.setAdapter(noteAdapter);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        swipeMenu =(SwipeMenuListView) findViewById(R.id.TrashlistView);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth((90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth((90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_menu_camera);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        swipeMenu.setMenuCreator(creator);


        swipeMenu.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        System.out.println("Option");
                        // open
                        break;
                    case 1:
                        System.out.println("dell");
                        //nott.setTrash(true);
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });



        //listViewNote.setAdapter(null);
        //swipeMenu.setAdapter(null);
        ArrayList<Not> notlar2 = TrashNote.TrashAllSaveNotes(this);

        if(notlar2 == null || notlar2.size() == 0){

            System.out.println("hic not kaydedilmedi");
        }else{
            NoteAdapter noteAdapter = new NoteAdapter(this,R.layout.single_note_view,notlar2);
            swipeMenu.setAdapter(noteAdapter);
            ///UPTO NOT KAYIT

            swipeMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //TIKLANAN NOT FILE NAME ALIR VE GORUNTELEME EKRANNA AKTARIR
                    String filename = ((Not)swipeMenu.getItemAtPosition(position)).getTime() + TrashNote.FILE_EXTENSION;

                    Intent sendIntent = new Intent(getApplicationContext(), EditNote.class);
                    //sendIntent.putExtra("fileName ", filename);//isimle not activiyde not cagiriyoruz
                    sendIntent.putExtra("FileName",filename);
                    System.out.println("Dosya ismi "+filename);
                    startActivity(sendIntent);

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trash, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // acilir menu elemanlari
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            Intent MainIntent= new Intent(TrashActivity.this,MainActivity.class);
            startActivity(MainIntent);

        } else if (id == R.id.nav_trash) {
            Intent TrashIntent= new Intent(TrashActivity.this,TrashActivity.class);
            startActivity(TrashIntent);
        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
