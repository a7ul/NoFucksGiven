package rationalduos.atulsoori.nofucksgiven;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;

import rationalduos.atulsoori.nofucksgiven.models.CardInfo;
import rationalduos.atulsoori.nofucksgiven.utils.AppConstants;
import rationalduos.atulsoori.nofucksgiven.utils.DatabaseHandler;
import rationalduos.atulsoori.nofucksgiven.utils.HackyDrawerLayout;

public class MainActivity extends AppCompatActivity {
    private HackyDrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private DatabaseHandler dbHandler;

    private CharSequence drawerTitle;
    private CharSequence title;
    private FragmentManager fragmentManager;


    private CardHolderFragment getCardHolderFromList(ArrayList<CardInfo> cardsList) {
        CardHolderFragment cardHolder = new CardHolderFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("cardsList", cardsList);
        cardHolder.setArguments(bundle);
        return cardHolder;
    }

    private CardHolderFragment getCardHolderBasedOnCategory(String category) {

        switch (category) {
            case AppConstants.NAVIGATION_GENERAL: //General
                return getCardHolderFromList((ArrayList<CardInfo>) dbHandler.getAllFucks());
            case AppConstants.NAVIGATION_IMAGES: //Images
                return getCardHolderFromList((ArrayList<CardInfo>) dbHandler.getAllFucksOfType(AppConstants.CARD_TYPE_IMAGE));
            case AppConstants.NAVIGATION_TEXTS: //Text
                ArrayList<CardInfo> listOfCards = new ArrayList<>();
                listOfCards.addAll(dbHandler.getAllFucksOfType(AppConstants.CARD_TYPE_TEXT));
                listOfCards.addAll(dbHandler.getAllFucksOfType(AppConstants.CARD_TYPE_TEXT_URL));
                return getCardHolderFromList(listOfCards);
            case AppConstants.NAVIGATION_FAVOURITES: //Favourites
                return getCardHolderFromList((ArrayList<CardInfo>) dbHandler.getAllFavouriteFucks());
            case AppConstants.NAVIGATION_ABOUT_US:
                ArrayList<CardInfo> cardList = new ArrayList<>();
                cardList.add(new CardInfo(null,null,null,AppConstants.CARD_TYPE_ABOUT_US,null,0));
                return getCardHolderFromList(cardList);
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
        drawerLayout = (HackyDrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.drawerList);
        dbHandler = new DatabaseHandler(this);

        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, AppConstants.NAVIGATION_ITEMS));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager.beginTransaction().replace(R.id.frameContainer,
                getCardHolderBasedOnCategory(AppConstants.NAVIGATION_GENERAL)).commit(); //Load General CardHolder

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
        String category = AppConstants.NAVIGATION_ITEMS[position];
        fragmentManager.beginTransaction().replace(R.id.frameContainer, getCardHolderBasedOnCategory(category)).commit();

        // Update Title on action bar
        drawerList.setItemChecked(position, true);
        setTitle(AppConstants.NAVIGATION_ITEMS[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void setTitle(CharSequence _title) {
        title = _title;
        try{
            getSupportActionBar().setTitle(title);
        }catch (Exception exception){
            Log.e("NFG",Log.getStackTraceString(exception));
        }
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