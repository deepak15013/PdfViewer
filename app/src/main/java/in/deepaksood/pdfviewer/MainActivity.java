package in.deepaksood.pdfviewer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final int WRITE_EXTERNAL_STORAGE = 100;

    private CoordinatorLayout coordinatorLayout;

    private Button btnShowPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        btnShowPdf = (Button) findViewById(R.id.btn_show_pdf);
        btnShowPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPdf();
            }
        });

    }

    /**
     * Main Logic for the showing pdf, getting from externalStorage and setting an explicit intent
     * reference - http://stackoverflow.com/a/30557505/5424888
     */
    private void showPdf() {
        File pdfFile = new File(Environment.getExternalStorageDirectory(),"GeneralInstructions.pdf");//File path
        if (pdfFile.exists()) //Checking for the file is exist or not
        {
            Uri path = Uri.fromFile(pdfFile);
            Intent objIntent = new Intent(Intent.ACTION_VIEW);
            objIntent.setDataAndType(path, "application/pdf");
            objIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(objIntent);//Staring the pdf viewer
        } else {

            Snackbar.make(coordinatorLayout, "File not present", Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE: {
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("permission", "Permission Granted");
                }
                else {
                    Log.d("permission", "Permission not granted");
                    finish();
                }
            }
        }
    }
}
