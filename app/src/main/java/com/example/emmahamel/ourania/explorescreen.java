package com.example.emmahamel.ourania;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.view.*;
import android.net.Uri;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.annotation.SuppressLint;
import android.widget.LinearLayout;
import android.provider.MediaStore;
import android.database.Cursor;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.content.res.Resources;
import android.graphics.Bitmap;

import java.io.IOException;
import java.net.URL;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.security.AccessController.getContext;

public class explorescreen extends AppCompatActivity {

    private final float X_MAX = (float) 474.96878;
    private float X_MIN;
    private float Y_MIN;
    private final float Y_MAX = (float) 570.9453;

    private float Y_HEIGHT;
    private float X_HEIGHT;
    private FrameLayout results;
    private TextView text_results;
    private TextView text_stats;
    private Uri image;
    ImageView imageView;
    //public final ImageView imageView = findViewById(R.id.imageView2);

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorescreen);
        Intent intent = getIntent();
        image = intent.getParcelableExtra("UriImage");
        //imageView = findViewById(R.id.imageView2);
        imageView = findViewById(R.id.imageView2);
        final ImageView target = findViewById(R.id.imageView);
        results = findViewById(R.id.frameLayout2);
        text_results = findViewById(R.id.textView3);
        text_stats = findViewById(R.id.textView5);
        //AlertDialog.Builder builder1 = new AlertDialog.Builder(explorescreen.this);
        imageView.setImageURI(image);
        final int width = getPicWidth(image);
        final int height = getPicHeight(image);
        System.out.println("PAY GUÜ");
        System.out.println(height);
        System.out.println(width);
        int[] viewCoords = new int[2];
        imageView.getLocationOnScreen(viewCoords);
        X_MIN = viewCoords[0];
        Y_MIN = viewCoords[1];
        X_HEIGHT = imageView.getMeasuredWidth();

        //if (!isNightSky(image)) {
            //AlertDialog error = makeErrorPopUp();
            //error.setOnDismissListener(new DialogInterface.OnDismissListener() {
                //@Override
                //public void onDismiss(final DialogInterface arg0) {
                    //goToMainScreen();
                //}
            //});
        //}


        imageView.setOnTouchListener(new OnTouchListener()
        {

            @Override
            public boolean onTouch(View view, MotionEvent event)
            {
                float[] results = calulateBoarder(width, height);
                float y_max = results[1]; float y_min = results[0];
                float y = event.getY();
                float x = event.getX();

                    System.out.println("Hi");
                    System.out.println(event.getY());
                    System.out.println(event.getX());
                    target.setY(y + 250);
                    target.setX(x+ 130);
                    target.setVisibility(View.VISIBLE);
                    String re = getResults(x, y);
                    System.out.println("\n\nThe results are: " + re);
                    //AlertDialog test = makePopUp(re);
                    //test.show();
                    //test.getWindow().setLayout(400, 400);
                    //return true;
                return false;
            }
        });
    }


    private int getPicHeight(Uri uri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if (uri.getPath() != null) {
            BitmapFactory.decodeFile(getPath(uri), options);
            return options.outHeight;
        } else {
            return -1;
        }
    }

    private int getPicWidth(Uri uri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if (uri.getPath() != null) {
            BitmapFactory.decodeFile(getPath(uri), options);
            return options.outWidth;
        } else {
            return -1;
        }
    }

    private String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    private float[] calulateBoarder(int width, int height) {
        float[] toReturn = new float[2];
//        if (width >= X_HEIGHT) {
//            System.out.println("Used");
//            float ratio = X_HEIGHT/width;
//            toReturn[0] = X_MIN; toReturn[1] = X_MAX;
//            toReturn[2] = Math.abs((Y_HEIGHT / 2) - (ratio * height / 2));
//            toReturn[3] = Math.abs((Y_HEIGHT / 2) + (ratio * height / 2));
//            System.out.println(toReturn[2]);
//            System.out.println(toReturn[3]);
//        } else if (height >= Y_HEIGHT) {
//            System.out.println("to");
//            toReturn[3] = Y_MAX; toReturn[2] = Y_MIN;
//            toReturn[0] = Math.abs((X_HEIGHT / 2) - (width / 2));
//            toReturn[1] = Math.abs((X_HEIGHT / 2) + (width / 2));
//        } else {
//            System.out.println("be");
//            toReturn[2] = Math.abs((Y_HEIGHT / 2) - (height / 2));
//            toReturn[3] = Math.abs((Y_HEIGHT / 2) + (height / 2));
//            toReturn[0] = Math.abs((X_HEIGHT / 2) - (width / 2));
//            toReturn[1] = Math.abs((X_HEIGHT / 2) + (width / 2));
//        }
//        System.out.println("Y_HEIGHT");
//        System.out.println(Y_HEIGHT);
//        System.out.println("height");
//        System.out.println(height);

        toReturn[0] = Math.abs((Y_HEIGHT / 2) - (height / 2)); //y_min
        toReturn[1] = Math.abs((Y_HEIGHT / 2) + (height / 2)); //Y_max
        //toReturn[0] = Math.abs((X_HEIGHT / 2) - (width / 2)); // x_min
        //toReturn[1] = Math.abs((X_HEIGHT / 2) + (width / 2)); // x_max

        return toReturn;
    }

    private void writePopUp(String star, int stats) {
        //AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//        results.setVisibility(View.VISIBLE);
//        text_results.setText(star);
//        text_stats.setText(String.valueOf(stats));
//        results.setX((getScreenWidth() / 2));
//        results.setY((getScreenHeight() / 2));
    }


    public void sendWholeImage(android.view.View veiw) {
        Intent intent = new Intent(this, whole_results.class);
        String results = getResultsWholeImage();
        intent.putExtra("Results", results);
        startActivity(intent);
    }

    public void backClick(android.view.View veiw) {
        results.setVisibility(View.INVISIBLE);
        results.setX(0);
        results.setY(0);
    }

    public AlertDialog makePopUp(String results) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(explorescreen.this, R.style.Ourania);
        builder1.setCancelable(true);
        LayoutInflater li = LayoutInflater.from(explorescreen.this);
        LinearLayout someLayout = (LinearLayout)li.inflate(R.layout.results2, null);
        TextView star = (TextView) findViewById(R.id.textView2_results);
        System.out.println("\nThe results of the thingy are..." + results);
        //star.setText(results);
        builder1.setView(someLayout);
        AlertDialog test = builder1.create();
        return test;
    }

    public android.graphics.Bitmap getPortion(float clickX, float clickY) {
        Bitmap bitmapD = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapD, 240, 286, false);
        imageView.setImageBitmap(scaled);
//        try {
//            final int width = getPicWidth(image);
//            final int height = getPicHeight(image);
//            float[] results = calulateBoarder(width, height);
//            int y_min = (int) results[0]; //int x_min = (int) results[0];
//            InputStream in = getContentResolver().openInputStream(this.image);
//            Bitmap bitmap= BitmapFactory.decodeStream(in);
//            int x = (int) clickX; int y = (int) clickY;
            System.out.println(clickX);
            System.out.println(clickY);
//            System.out.println(y_min);
//            System.out.println(X_MIN);
            Bitmap bm = Bitmap.createBitmap(scaled, (int) clickX - 97, (int) clickY - 106, 20, 20);
            int heyAmit = bitmapD.getHeight();
            int heyEmma = bitmapD.getWidth();
            return bm;
//        } catch (FileNotFoundException E) {
//            Log.e("File Not Found", "hey");
//        }
        //return null;
    }

    private String getResults(float clickX, float clickY) {
        Bitmap selectedPortion = getPortion(clickX, clickY);
        String star = determineStellarClassification(selectedPortion);
        return star;

    }

    private String getResultsWholeImage() {
        return "Hi";
    }

    public AlertDialog makeErrorPopUp() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(explorescreen.this, R.style.Ourania);
        builder1.setCancelable(true);
        LayoutInflater li = LayoutInflater.from(explorescreen.this);
        LinearLayout someLayout = (LinearLayout)li.inflate(R.layout.error_screen, null);
        builder1.setView(someLayout);
        AlertDialog test = builder1.create();
        return test;
    }

    protected static boolean isNightSky(Uri uri) {

        return true;
    }

    private void goToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void enableStrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public String determineStellarClassification(Bitmap image1) {
        int[] centerCoordinates = getCenter(image1);
        int[] centerColors = new int[3];
        centerColors[0] = Color.red(image1.getPixel(centerCoordinates[0], centerCoordinates[1]));
        centerColors[1] = Color.green(image1.getPixel(centerCoordinates[0], centerCoordinates[1]));
        centerColors[2] = Color.blue(image1.getPixel(centerCoordinates[0], centerCoordinates[1]));

	/*
	DefaultHttpClient client = new DefaultHttpClient();
	HttpGet getter = new HttpGet("http://www.vendian.org/mncharity/dir3/blackbody/UnstableURLs/bbr_color.html");
	ResponseHandler<String> resHandler = new BasicResponseHandler();
	String page = httpClient.execute(getter, resHandler);
	*/
	    enableStrictMode();
        String page = "";
	    try {
	        URL url = new URL("http://www.vendian.org/mncharity/dir3/blackbody/UnstableURLs/bbr_color.html");
            URLConnection connect = url.openConnection();
            InputStream is = connect.getInputStream();
            Scanner reader = new Scanner(is);
            while (reader.hasNext()) {
            page += reader.nextLine();
        }
	    } catch (java.io.IOException E) {
	        Log.d("DETERMINE STELLAR", "java.io.IOException");
        }

        //System.out.println(page);

        ArrayList<String> dataCells = new ArrayList<String>();

        for (int i = 0; i < page.length(); i++) {
            if (page.indexOf("<span", i) > 0) {
                i = page.indexOf("<span", i);
                int end = page.indexOf("</span>", i);
                dataCells.add(page.substring(i, end));
            }
        }
        /*for (String str : dataCells) {
            System.out.println(str);
        }*/

        //colors[0] = red
        //colors[1] = green
        //colors[2] = blue
        //System.out.println("\nHello? " + dataCells.get(0));
        for (int i = 0; i < dataCells.size(); i++) {
            int index = dataCells.get(i).indexOf("#");
            String hexRGB = dataCells.get(i).substring(index, index + 7);
            //System.out.println(hexRGB);
            int[] colors = convertHexRGBToRegularRGB(hexRGB);
            //maybe print out the hexRGB value so we know that we're getting the right stuff here, and from up above

            if (colors[0] == centerColors[0] && colors[1] == centerColors[1] && colors[2] == centerColors[2]) {
                String temperature = dataCells.get(i).substring(0, dataCells.get(i).indexOf("K"));
                temperature = temperature.trim();
                long temp = Long.parseLong(temperature);
                return getStarClassification(temp);
            }
        }
        return "A planetoid, an artifical satellite, an extra-galactic object, or something else that isn't a star";
    }

    public String getStarClassification(long temp) {
        if (temp >= 1000 && temp < 2400) {
            return "Brown Dwarf - Either L, T, or Y";
        } else if (temp >= 2400 && temp < 3700) {
            return "Red Dwarf, Red Giant, or Red Supergiant - M Class";
        } else if (temp >= 3700 && temp < 5200) {
            return "K - Type Main Sequence Star";
        } else if (temp >= 5200 && temp < 6000) {
            return "G - Type Main Sequence Star";
        } else if (temp >= 6000 && temp < 7500) {
            return "F - Type Main Sequence Star";
        } else if (temp >= 7500 && temp < 10000) {
            return "A - Type Main Sequence Star";
        } else if (temp >= 10000 && temp < 30000) {
            return "B - Type Main Sequence Star, Blue Giant, or Blue Supergiant";
        } else if (temp >= 30000) {
            return "O - Type Main Sequence Star, Blue Giant, or Blue Supergiant";
        } else {
            return "Error: Something strange is happening here, or you found something new and exciting!";
        }
    }

    public int[] convertHexRGBToRegularRGB(String input) {
        int[] output = new int[3];
        output[0] = (int) Long.parseLong(input.substring(1, 3), 16);
        output[1] = (int) Long.parseLong(input.substring(3, 5), 16);
        output[2] = (int) Long.parseLong(input.substring(5, 7), 16);
        return output;
    }

    /**
     * This method will return the coordinates of the brightest pixel
     * in the bitmap that is passed to it.  The output is in the form
     * of an int[], where arr[0] is the width and arr[1] is the height,
     * so possibly x and y, respectively.
     *
     * @param image1 The Bitmap image to find the brightest pixel of.
     * @return The brightest pixel in the Bitmap image.
     */
    public int[] getCenter(Bitmap image1) {
        int[] center = new int[2];
        int color = image1.getPixel(0, 0);
        center[0] = 0;
        center[1] = 0;
        int alphaMax = Color.alpha(color);
        for (int i = 0; i < image1.getWidth(); i++) {
            for (int x = 0; x < image1.getHeight(); x++) {
                color = image1.getPixel(i, x);
                int temp = Color.alpha(color);
                if (temp > alphaMax) {
                    alphaMax = temp;
                    center[0] = i;
                    center[1] = x;
                }
            }
        }
        return center;
    }

}

