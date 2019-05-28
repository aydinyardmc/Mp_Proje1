package com.aydinyardimci.mp_proje1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuInflater;
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
import android.widget.SearchView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ListView listViewNote;
    private SwipeMenuListView swipeMenu;
    private Not nott;
    private BottomSheetDialog bottomSheetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        System.out.println("MAINNNNN");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //baslangistaki main fragment tanimi
        //getSupportFragmentManager().beginTransaction().replace(R.id.MainFrameLayout,new NoteListFragment()).commit();

        //navigationView.setCheckedItem(R.id.nav_notes);

        FloatingActionButton floatingActionButton= findViewById(R.id.addNoteButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (MainActivity.this, EditNote.class));
            }
        });

        //listViewNote =  (ListView) findViewById(R.id.listVw);

        //siralam menusu
        bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        View btsView = getLayoutInflater().inflate(R.layout.main_note_sorting_settings_menu,null);
        bottomSheetDialog.setContentView(btsView);


    }

    @Override
    protected void onResume() {
        super.onResume();


        swipeMenu =(SwipeMenuListView) findViewById(R.id.listView);
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
                        String filename = ((Not)swipeMenu.getItemAtPosition(position)).getTime() + SaveNote.FILE_EXTENSION;
                        nott = SaveNote.getNoteName(getApplicationContext(),filename);
                       // nott.setTrash(true);
                        //SaveNote.saveNotes(getApplicationContext(),nott);

                        //SaveNote.dellNote(getApplicationContext(),nott.getTime()+ SaveNote.FILE_EXTENSION);
                        ArrayList<Not> notlar = SaveNote.allSaveNotes(getApplicationContext());
                        //notlar.remove((Not)swipeMenu.getItemAtPosition(position));
                        notlar.remove(position);
                        //SaveNote.allSaveNotes(getApplicationContext());
                        //SaveNote.saveNotes(getApplicationContext(),nott);

                        //burada yeni bir listeye kaydettirmeliyiz

                        //System.out.println("TIMMEEEEMEMEMEMEME "+ String.valueOf(nott.getTime()).toLowerCase() + " REAL " + nott.getTime());
                        //System.out.println("TIMMEEEEMEMEMEMEME "+ Long.toString(nott.getTime())+ " REAL " + nott.getTime());


                       // TrashNote.trashNotes(getApplicationContext(),nott);

                        SaveNote.dellNote(getApplicationContext(),nott.getTime()+ SaveNote.FILE_EXTENSION);

                        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(),R.layout.single_note_view,notlar);
                        swipeMenu.setAdapter(noteAdapter);


                        //SaveNote.dellNote(getApplicationContext(),nott.getTime()+ SaveNote.FILE_EXTENSION);


                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        //listViewNote.setAdapter(null);
        //swipeMenu.setAdapter(null);
        ArrayList<Not> notlar = SaveNote.allSaveNotes(this);

        if(notlar == null || notlar.size() == 0){

            System.out.println("hic not kaydedilmedi");
        }else{
            NoteAdapter noteAdapter = new NoteAdapter(this,R.layout.single_note_view,notlar);
            swipeMenu.setAdapter(noteAdapter);
            ///UPTO NOT KAYIT

            swipeMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //TIKLANAN NOT FILE NAME ALIR VE GORUNTELEME EKRANNA AKTARIR
                    String filename = ((Not)swipeMenu.getItemAtPosition(position)).getTime() + SaveNote.FILE_EXTENSION;

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
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);

        MenuItem search = menu.findItem(R.id.noteSearch);
        SearchView searchView = (SearchView) search.getActionView();
//        SearchView searchView = (SearchView) menu.findItem(R.id.noteSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Not> notlar = SaveNote.allSaveNotes(getApplicationContext());//ekrandaki notlari alir
                ArrayList<Not> SearchNotlar = new ArrayList<>();//yeni not llstelemek icin
                NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(),R.layout.single_note_view,SearchNotlar);
                swipeMenu.setAdapter(noteAdapter);
                System.out.println(" grilen"+newText.toString());
                for(Not item : notlar)
                //if(String.valueOf(item.getTime()).toLowerCase().contains(newText.toLowerCase())){
                    if(item.getBaslik().toLowerCase().contains(newText.toLowerCase())){
                       SearchNotlar.add(item);

                    }
                   // if(item.getBaslik().equals(newText)){
                      //  SearchNotlar.add(item);
                    //}

                NoteAdapter noteAdapter2 = new NoteAdapter(getApplicationContext(),R.layout.single_note_view,SearchNotlar);
                swipeMenu.setAdapter(noteAdapter2);

                return true;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.noteSort) {
            bottomSheetDialog.show();

            View TarihUptoDown = bottomSheetDialog.findViewById(R.id.upToDownButton);
            TarihUptoDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Not> notlar = SaveNote.allSaveNotes(getApplicationContext());//ekrandaki notlari alir
                    Collections.reverse(notlar);
                    NoteAdapter noteAdapter2 = new NoteAdapter(getApplicationContext(),R.layout.single_note_view,notlar);
                    swipeMenu.setAdapter(noteAdapter2);
                    SaveNote.notDisplaySettings =1;//yeni siralmaya gore listeyi kayit eder
                }
            });
            View TarihDownToUp = bottomSheetDialog.findViewById(R.id.downToUpButton);
            TarihDownToUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Not> notlar = SaveNote.allSaveNotes(getApplicationContext());//ekrandaki notlari alir
                    Collections.reverse(notlar);
                    NoteAdapter noteAdapter2 = new NoteAdapter(getApplicationContext(),R.layout.single_note_view,notlar);
                    swipeMenu.setAdapter(noteAdapter2);
                    SaveNote.notDisplaySettings =0;//yeni siralmaya gore listeyi kayit eder
                }
            });
            View TarihPriorty = bottomSheetDialog.findViewById(R.id.priortyButton);
            TarihPriorty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveNote.notDisplaySettings =2;//yeni siralmaya gore listeyi kayit eder
                }
            });


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
            Intent MainIntent= new Intent(MainActivity.this, MainActivity.class);
            startActivity(MainIntent);

        } else if (id == R.id.nav_trash) {
            Intent TrashIntent= new Intent(MainActivity.this, TrashActivity.class);
            startActivity(TrashIntent);

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
