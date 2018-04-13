package mobile_app.trainingpal.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import mobile_app.trainingpal.me.fragments.containers.MyProfileContainerFragment;
import mobile_app.trainingpal.me.fragments.containers.TrainingLogContainerFragment;
import mobile_app.trainingpal.me.services.LocalAuthService;

public class NavBar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView navbarUsername;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    public FrameLayout frameLayout; //used to find frame layout
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_menu_layout);

        initialiseVariables();

        if (savedInstanceState == null) {
            TrainingLogContainerFragment fragment = new TrainingLogContainerFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    private void initialiseVariables() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        frameLayout = findViewById(R.id.content_frame);
        navbarUsername = header.findViewById(R.id.navbar_header_username);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.app_navigation_drawer_open,
                R.string.app_navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        setTitle(R.string.home_page_name);

        navbarUsername.setText(LocalAuthService.getAuthObject(this).getUserId());

        fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), CreateTrainingLogActivity.class));
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId(); //item that is pressed id

        Fragment fragment = null;

        //to prevent current item select over and over
        if (item.isChecked()){
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }//end if

        switch (id) {
            case R.id.navbar_training_log: {
                if (!fab.isShown()) {
                    fab.show();
                }

                item.setChecked(true);
                setTitle(R.string.traininglog_page_name);
                fragment = new TrainingLogContainerFragment();
                break;
            }

            case R.id.navbar_my_profile: {
                if (fab.isShown()) {
                    fab.hide();
                }

                item.setChecked(true);
                setTitle(R.string.myprofile_page_name);
                fragment = new MyProfileContainerFragment();
                break;
            }

            case R.id.navbar_logout: {
                LocalAuthService.deleteAuthObject(this);
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return false;
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
