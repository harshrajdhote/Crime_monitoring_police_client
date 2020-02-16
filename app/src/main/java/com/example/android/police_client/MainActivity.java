package com.example.android.police_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Button vehicleButton;
    String vehicleStatus = "OFF";
    private static final int PERMISSIONS_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }
    private void startTrackerService() {
        startService(new Intent(this, TrackerService.class));
       // finish();
    }
    private void stopTrackerService(){
        Intent stopServiceIntent = new Intent(new Intent(this, TrackerService.class));
        stopService(stopServiceIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the service when the permission is granted
            startTrackerService();
        } else {
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_accepted:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AcceptedFragement()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_vehicle_mode:
                vehicleButton = findViewById(R.id.vehicle_mode);
                vehicleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(vehicleStatus.equals("OFF")){
                            vehicleStatus = "ON";
                            vehicleButton.setText("Vehicle Mode ON");
                            vehicleButton.setBackgroundColor(Color.parseColor("#00cc00"));
                            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
                            if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                Toast.makeText(MainActivity.this, "Please enable location services", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            // Check location permission is granted - if it is, start
                            // the service, otherwise request the permission
                            int permission = ContextCompat.checkSelfPermission(MainActivity.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION);
                            if (permission == PackageManager.PERMISSION_GRANTED) {
                                startTrackerService();
                            } else {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST);
                            }
                        }
                        else{
                            vehicleStatus = "OFF";
                            vehicleButton.setText("Vehicle Mode OFF");
                            vehicleButton.setBackgroundColor(Color.parseColor("#ff3300"));
                            stopTrackerService();
                            final String path = getString(R.string.firebase_path) + "/" + getString(R.string.transport_id);
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                            ref.child("status").setValue(false);
                        }
                    }
                });
                break;


        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
