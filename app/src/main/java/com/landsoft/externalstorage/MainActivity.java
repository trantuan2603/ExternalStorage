package com.landsoft.externalstorage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String FILE_NAME = "trantuan.com";
    private  final String TAG = getClass().getSimpleName() ;
    MultiAutoCompleteTextView mtvReader;
    Button btnWriteEX, btnReadEX, btnReadPL, btnWritePL;
    EditText edtInputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mappedWidget();
        setOnClickListener();
        checkAndRequestPermissions();
    }

    private void setOnClickListener() {
        btnWritePL.setOnClickListener(this);
        btnWriteEX.setOnClickListener(this);
        btnReadPL.setOnClickListener(this);
        btnReadEX.setOnClickListener(this);
    }

    private void mappedWidget() {
        mtvReader = findViewById(R.id.mtv_reader);
        btnReadEX = findViewById(R.id.btn_read_ex);
        btnReadPL = findViewById(R.id.btn_read_pl);
        btnWriteEX = findViewById(R.id.btn_write_ex);
        btnWritePL = findViewById(R.id.btn_write_pl);
        edtInputData = findViewById(R.id.edt_input_data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_read_ex:
//                to do
                fileReadEX();
                break;
            case R.id.btn_read_pl:
//                to do
                fileReadPL();
                break;
            case R.id.btn_write_ex:
//                to do
                fileWriteEX();
                break;
            case R.id.btn_write_pl:
//                to do
                fileWritePL();
                break;
        }
    }

    private void fileReadPL() {
        if (isExternalStorageReadable()) {

            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
                StringBuffer buffer = new StringBuffer();
                String line = null;
                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }
                mtvReader.setText(buffer.toString());
                reader.close();
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            Toast.makeText(getApplicationContext()," Bo nho dang co van de khong the truy cap",Toast.LENGTH_SHORT).show();
    }

    private void fileWritePL() {
        if (isExternalStorageReadable()) {
            String strInput = edtInputData.getText().toString();
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(strInput.getBytes());
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
        else
            Toast.makeText(getApplicationContext()," Bo nho dang co van de khong the truy cap",Toast.LENGTH_SHORT).show();
    }

    private void fileReadEX() {
        if (isExternalStorageReadable()) {

            try {
                File file = new File(Environment.getExternalStorageDirectory(),FILE_NAME);
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
                StringBuffer buffer = new StringBuffer();
                String line = null;
                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }
                mtvReader.setText(buffer.toString());
                reader.close();
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            Toast.makeText(getApplicationContext()," Bo nho dang co van de khong the truy cap",Toast.LENGTH_SHORT).show();
    }

    // ghi du lieu vao bo nho trong thiet bi
    private void fileWriteEX() {
        String strInput = edtInputData.getText().toString();
        if (isExternalStorageReadable()) {
            try {
                File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
                Log.d(TAG, "fileWriteEX: " + file);
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(strInput.getBytes());
                Log.d(TAG, "fileWriteEX: " + outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            Toast.makeText(getApplicationContext()," Bo nho dang co van de khong the truy cap",Toast.LENGTH_SHORT).show();
    }


// xin quyen doc ghi file
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

// kiem tra bo nho ngoai co doc ghi duoc hay khong
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
