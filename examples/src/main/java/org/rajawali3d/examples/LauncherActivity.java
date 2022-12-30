package org.rajawali3d.examples;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.rajawali3d.examples.wallpaper.WallpaperPreferenceActivity;

public class LauncherActivity extends AppCompatActivity {

    private static final String TAG = "LauncherActivity";

    private static final int REQUEST_PERMISSIONS = 1;
    private static final String[] PERMISSIONS = {
            permission.CAMERA,
            permission.INTERNET,
            permission.ACCESS_NETWORK_STATE,
            permission.ACCESS_WIFI_STATE,
            permission.WAKE_LOCK,
            permission.NFC,
            permission.VIBRATE,
            permission.READ_EXTERNAL_STORAGE,
            permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ExamplesFragment(), ExamplesFragment.TAG)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                String permissionMissing = hasAllPermissions();
                if (permissionMissing.equals("")) {
                    Log.d(TAG, "All permissions granted!");
                } else {
                    Toast.makeText(
                            this,
                            "permissions:" + permissionMissing,
                            Toast.LENGTH_LONG
                    ).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(this, WallpaperPreferenceActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Determine if the application has the necessary permissions to perform an action.
     *
     * @return true if application has all permissions
     */
    String hasAllPermissions() {
        for (String permission : LauncherActivity.PERMISSIONS) {
            final int result = checkSelfPermission(permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return permission;
            }
        }

        return "";
    }
}
