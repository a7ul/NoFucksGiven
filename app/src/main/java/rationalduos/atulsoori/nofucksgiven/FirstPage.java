package rationalduos.atulsoori.nofucksgiven;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Random;

import rationalduos.atulsoori.nofucksgiven.cardViews.TextCard;

public class FirstPage extends AppCompatActivity {

    private TextCard getRandomTextCard(){
        Bundle bundle = new Bundle();
        Random t = new Random();
        bundle.putString("textContent", "Random "+t.nextInt());
        TextCard tCard = new TextCard();
        tCard.setArguments(bundle);
        return tCard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);

        DynamicPagerAdapter mDynamicPagerAdapter = new DynamicPagerAdapter(getSupportFragmentManager());

        mDynamicPagerAdapter.addFragment(getRandomTextCard());
        mDynamicPagerAdapter.addFragment(getRandomTextCard());
        mDynamicPagerAdapter.addFragment(getRandomTextCard());

        if (pager != null) {
            pager.setAdapter(mDynamicPagerAdapter);
        } else {
            Log.d("NFG", "Pager not found");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
