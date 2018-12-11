package com.example.emmahamel.ourania;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.provider.MediaStore;
import android.net.Uri;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PIC_REQUEST = 1337;
    private static final int PICK_IMAGE = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void photoLibrary(final View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        //i.setType("image/*");
        startActivityForResult(i, PICK_IMAGE);
    }

    public void camera(final View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void saved(final View view) {
        System.out.println("hey");
    }

    public void switchToLoadingScreen(View view) {
        Intent intent = new Intent(view.getContext(), explorescreen.class);
        startActivity(intent);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri image = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_PIC_REQUEST && data.hasExtra("data")) {
                final Uri uri = data.getData();
                image = uri;
            }
            if (requestCode == PICK_IMAGE && data != null) {
                final Uri uri = data.getData();
                image = uri;
            }

            if (image != null) {
                Intent intent = new Intent(this, explorescreen.class);
                intent.putExtra("UriImage", image);
                startActivity(intent);
            }
        }
    }

}
