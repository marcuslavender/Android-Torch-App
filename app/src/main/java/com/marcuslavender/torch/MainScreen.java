package com.marcuslavender.torch;

import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.view.Menu;
import android.view.MenuItem;

public class MainScreen extends AppCompatActivity {

    boolean flashEnabled;
    String[] cameraIDList;


    MainScreen() {

        flashEnabled = false;
        cameraIDList = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);





        //   Creates Camera objects to check camera characteristics


        CameraManager manager = (CameraManager) getSystemService(CAMERA_SERVICE);




        //  Generates a list of CameraID's installed in the handset





        try {
            this.cameraIDList = manager.getCameraIdList();

            //cameraIDList = getCameraManager().getCameraIdList();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        // loop through camera ID's and pop up a message if camera flash is missing.
        try {
            for (int i = 0; i < 2; i++) {
                CameraCharacteristics chars = manager.getCameraCharacteristics(cameraIDList[i]);

                if (!checkCameraFlash(chars)) {

                    Context context = getApplicationContext();
                    CharSequence text = "Camera flash missing " + i;
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Camera flash present " + i;
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }


            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);

        return super.onCreateOptionsMenu(menu);
    }



    public void openAboutMenu(MenuItem item) {
        Intent intent = new Intent(this, about_menu.class);
        startActivity(intent);
    }

    /**
     *
     *
     * @return Camera ID String
     */
    public String getCameraID() {
        return this.cameraIDList[0];
    }


    /**
     *
     * @param status
     * Method to set flashEnabled class variable
     */
    private void setFlashEnabled(boolean status) {
        this.flashEnabled = status;
    }

    /**
     *
     * Method to get status of flashEnabled variable
     * @return flashEnabled,
     */
    public boolean getFlashEnabled() {
        return this.flashEnabled;


    }

    /**
     *
     * @param chars
     * @return boolean
     * CameraCharacteristics chars, Method to check device has flash capability.
     */
    public boolean checkCameraFlash(CameraCharacteristics chars) {
        if (chars.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
            return true;
        }
        return false;
    }

    /**
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void powerOn(View powerButton) {
        CameraManager manager = (CameraManager) getSystemService(CAMERA_SERVICE);
        enableFlash(manager);




    }

    /**
     *
     * Method to enable/disable torch mode
     * @param manager
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void enableFlash(CameraManager manager) {
        ImageView imgview = (ImageView)findViewById(R.id.lightCone);

        if (getFlashEnabled() == false) {
            try {
                manager.setTorchMode(getCameraID(), true);
                setFlashEnabled(true);
                imgview.setVisibility(View.VISIBLE);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else if (getFlashEnabled() == true) {
            try {
                manager.setTorchMode(getCameraID(), false);
                setFlashEnabled(false);
                imgview.setVisibility(View.INVISIBLE);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        }

    }



}