package ipramodsinghrawat.googlemapalpha;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.WAKE_LOCK};

        if(!hasPermisssions(getApplicationContext(),permissions)) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }else{
            Toast.makeText(this, "ALL Permission Granted", Toast.LENGTH_SHORT).show();
            this.go2MapActivity();
        }

    }

    private void go2MapActivity(){
        this.startActivity(new Intent(this, MapsActivity.class));
    }

    boolean hasPermisssions(Context context, String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        //Toast.makeText(this, "onRequestPermissionsResult", Toast.LENGTH_SHORT).show();

        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    Toast.makeText(this, "g lenght if more then 0 ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "grantResults: "+grantResults[0], Toast.LENGTH_SHORT).show();
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted){
                        Toast.makeText(this, "Permission Granted.", Toast.LENGTH_SHORT).show();
                        this.go2MapActivity();

                    }else {
                        Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to Location permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                            LOCATION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
