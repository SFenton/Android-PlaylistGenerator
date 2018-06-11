package com.sfent.playlistgenerator;

import android.app.Fragment;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
 PlaylistGenerationOptions.OnFragmentInteractionListener, Settings.OnFragmentInteractionListener,
Spotify.OnFragmentInteractionListener, DailyPlaylistOptions.OnFragmentInteractionListener {

    private DrawerLayout mDrawerLayout;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.current_fragment, new HomeFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        int id = menuItem.getItemId();

                        if (id == R.id.nav_home)
                        {
                            // Begin the transaction
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            // Replace the contents of the container with the new fragment
                            ft.replace(R.id.current_fragment, new HomeFragment());
                            // or ft.add(R.id.your_placeholder, new FooFragment());
                            // Complete the changes added above
                            ft.commit();

                        }
                        if (id == R.id.nav_daily)
                        {
                            // Begin the transaction
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            // Replace the contents of the container with the new fragment
                            ft.replace(R.id.current_fragment, new DailyPlaylistOptions());
                            // or ft.add(R.id.your_placeholder, new FooFragment());
                            // Complete the changes added above
                            ft.commit();

                        }
                        if (id == R.id.nav_playlists)
                        {
                            // Begin the transaction
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            // Replace the contents of the container with the new fragment
                            ft.replace(R.id.current_fragment, new PlaylistGenerationOptions());
                            // or ft.add(R.id.your_placeholder, new FooFragment());
                            // Complete the changes added above
                            ft.commit();

                        }
                        if (id == R.id.nav_settings)
                        {
                            // Begin the transaction
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            // Replace the contents of the container with the new fragment
                            ft.replace(R.id.current_fragment, new Settings());
                            // or ft.add(R.id.your_placeholder, new FooFragment());
                            // Complete the changes added above
                            ft.commit();

                        }
                        if (id == R.id.nav_spotify)
                        {
                            // Begin the transaction
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            // Replace the contents of the container with the new fragment
                            ft.replace(R.id.current_fragment, new Spotify());
                            // or ft.add(R.id.your_placeholder, new FooFragment());
                            // Complete the changes added above
                            ft.commit();

                        }

                        return true;
                    }
                });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // Empty
    }
}
