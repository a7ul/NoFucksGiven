package rationalduos.atulsoori.nofucksgiven;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;

import rationalduos.atulsoori.nofucksgiven.models.CardInfo;
import rationalduos.atulsoori.nofucksgiven.utils.AppConstants;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence drawerTitle;
    private CharSequence title;
    private FragmentManager fragmentManager;
    private String[] navStringsArray;


    private CardHolderFragment getCardHolderFromList(ArrayList<CardInfo> cardsList) {
        CardHolderFragment cardHolder = new CardHolderFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("cardsList", cardsList);
        cardHolder.setArguments(bundle);
        return cardHolder;
    }

    private CardHolderFragment getCardHolderBasedOnCategory(int index) {

        ArrayList<CardInfo> listOfCardInfo = new ArrayList<>();

        listOfCardInfo.add(new CardInfo("t0", "test-text-card", "test1", AppConstants.CARD_TYPE_TEXT, "Test data"));
        listOfCardInfo.add(new CardInfo("t1", "test-markdown-card", "test2", AppConstants.CARD_TYPE_MARKDOWN, "https://raw.githubusercontent.com/BucketDevelopers/NFGAssets/master/assets/texts/sample-text.md"));
        listOfCardInfo.add(new CardInfo("i0", "test-image-card", "test3", AppConstants.CARD_TYPE_IMAGE, "https://pixabay.com/static/uploads/photo/2016/01/14/01/41/image-view-1139204_960_720.jpg"));

        switch (index) {
            case 0: //General
                return getCardHolderFromList(listOfCardInfo);
            case 1: //Images
                return new CardHolderFragment();
            case 2: //Text
                return new CardHolderFragment();
            case 3: //Favourites
                return new CardHolderFragment();
            default:
                return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        fragmentManager = getSupportFragmentManager();
        title = drawerTitle = getTitle();
        navStringsArray = new String[]{"General", "Images", "Text", "Favourites"};
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.drawerList);

        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, navStringsArray));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager.beginTransaction().replace(R.id.frameContainer, getCardHolderBasedOnCategory(0)).commit(); //Load General CardHolder

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        fragmentManager.beginTransaction().replace(R.id.frameContainer, getCardHolderBasedOnCategory(position)).commit();

        // Update Title on action bar
        drawerList.setItemChecked(position, true);
        setTitle(navStringsArray[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void setTitle(CharSequence _title) {
        title = _title;
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}