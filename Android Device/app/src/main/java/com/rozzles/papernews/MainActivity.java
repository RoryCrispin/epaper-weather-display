package com.rozzles.papernews;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    Timer regular_timer = new Timer();
    ImageLoader imageLoader;

    ImageView image;


    public IntentFilter intent_filter() {
        IntentFilter iF = new IntentFilter();
        iF.addAction("com.rozzles.papernews.refreshImage");

        return iF;

    }
    String refresh_broadcast_recv(Intent intent){
        refreshImage();
        return null;
    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh_broadcast_recv(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.imageView);
//        image.setImageResource(R.drawable.im);

//        image.setImageDrawable(LoadImageFromWebOperations("https://uboachan.net/fg/src/1371599781698.png"));



//        Bitmap b;
//
//
//        try
//        {
//            URL url = new URL("http://www.madgamedev.com/wp-content/uploads/contest/2010/rollingworld/gfx/texture_support_layout.png");
//            InputStream is = new BufferedInputStream(url.openStream());
//            b = BitmapFactory.decodeStream(is);
//            image.setImageBitmap(b);
//        } catch(Exception e){
//            System.out.println(e);
//        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance(); // Get singleton instance
        registerReceiver(mReceiver, intent_filter());

        TimerTask regular_timer_task_obj = new regular_timer_task();
        regular_timer.scheduleAtFixedRate(regular_timer_task_obj, 0, 2700000);

    }

    @Override
    protected void onStop(){
        regular_timer.cancel();
        unregisterReceiver(mReceiver);
    }


    class regular_timer_task extends TimerTask {

        public void run() {
            Intent i = new Intent();
            i.setAction("com.rozzles.papernews.refreshImage");
            sendBroadcast(i);
        }
    }





public void refreshImage(){
    imageLoader.displayImage("http://192.168.1.14:8000/home/pi/pyImages/weather-script-output.png", image);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
