package com.compiler.mycameraapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends Activity {
    private ImageView pictureIv;

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    ;

    String imageFileLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pictureIv = (ImageView) findViewById(R.id.pictureIv);
    }

    public void startCamera(View view) {
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = createImageFile();
        if (photoFile == null){
            Log.e("photoFile", "startCamera: "+"NULL");
        } else {
            Log.e("photoFile", "startCamera: "+"OK");
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

        startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
           /*Toast.makeText(MainActivity.this, "Picture Taken Successfully", Toast.LENGTH_SHORT).show();
            Log.e("picture", "onActivityResult: "+"Picture Taken Successfully" );*/

            /*Bundle extras = data.getExtras();
            Bitmap pictureBitmap = (Bitmap) extras.get("data");
            pictureIv.setImageBitmap(pictureBitmap);*/
            Bitmap pictureBitmap = BitmapFactory.decodeFile(imageFileLocation);
            if (pictureBitmap == null){
                Log.e("picture", "onActivityResult: "+"this is running NULL" );
            } else {
                pictureIv.setImageBitmap(pictureBitmap);
            }


        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String imageFileName = "Image_" + timeStamp + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageFileLocation = image.getAbsolutePath();

        return image;
    }
}
