package com.pnit.smartbag;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pnit.smartbag.data.jwt.Jwt;
import com.pnit.smartbag.data.login.model.LoggedInUser;
import org.json.JSONObject;
import com.google.android.material.snackbar.Snackbar;
import com.pnit.smartbag.bluetooth.BluetoothConnectionService;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.container)
    ConstraintLayout container;

    private int ACCESS_FINE_LOCATION_REQUEST_CODE = 1;

    private static LoggedInUser loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_statistics, R.id.navigation_rating, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        checkPermissions();

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Snackbar snackbar = Snackbar.make(container, R.string.bluetooth_not_supported, Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            startBluetoothService();
        } else {
            startBluetoothService();
        }
    }

    private void startBluetoothService() {
        Intent intent = new Intent(this, BluetoothConnectionService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_REQUEST_CODE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACCESS_FINE_LOCATION_REQUEST_CODE) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar snackbar = Snackbar
                        .make(container, R.string.permission_granted, Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                Snackbar snackbar = Snackbar
                        .make(container, R.string.permission_denied, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(new Intent(this, BluetoothConnectionService.class));
        unregisterReceiver(mReceiver);
    }

    public static void setLoggedInUser(LoggedInUser user){
        loggedInUser = user;
    }

    public static LoggedInUser getLoggedInUser(){
        return loggedInUser;
    }

    //TODO
    private void makeJsonObjectRequest(){
        Jwt jwt = new Jwt(MainActivity.class.getSimpleName(), new JSONObject());
        jwt.makeJsonRequest();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.v("MainActivity", "Bluetooth off");
                        Snackbar snackbar = Snackbar.make(container, "Bluetooth turned off", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.v("MainActivity", "Turning Bluetooth off...");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.v("MainActivity", "Bluetooth on");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.v("MainActivity", "Turning Bluetooth on...");
                        break;
                }
            }
        }
    };

    public void doPositiveClick() {
        finish();
    }

    public void doNegativeClick() {
        stopService(new Intent(this, BluetoothConnectionService.class));
        finish();
    }
}
