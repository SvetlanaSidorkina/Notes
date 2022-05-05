package com.example.notes.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.notes.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements ToolbarHolder {

    public DrawerLayout drawerLayout;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);

        NavigationView navigationView = findViewById(R.id.navigation);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_notes:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new NotesListFragment())
                            .commit();
                    drawerLayout.close();
                    return true;
                case R.id.action_settings:
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new NoteSettingsFragment())
                            .commit();
                    drawerLayout.close();
                    return true;
            }
            return false;
        });
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }

}