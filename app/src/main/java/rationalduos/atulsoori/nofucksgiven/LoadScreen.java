package rationalduos.atulsoori.nofucksgiven;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);
        Thread loader = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent i = new Intent(getApplicationContext(),FirstPage.class);
                    startActivity(i);
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }
        });
        loader.start();
    }
}
