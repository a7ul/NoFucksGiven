package rationalduos.atulsoori.nofucksgiven;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;

import rationalduos.atulsoori.nofucksgiven.adapters.NavDrawerListAdapter;
import rationalduos.atulsoori.nofucksgiven.models.CardInfo;
import rationalduos.atulsoori.nofucksgiven.models.NavDrawerItem;
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

    private ArrayList<NavDrawerItem> navDrawerItems;
    private LinearLayout drawerLinear;
    private int activeDrawerItemPosition;

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
                cardList.add(new CardInfo(null, null, null, AppConstants.CARD_TYPE_ABOUT_US, null, 0));
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

        dbHandler = new DatabaseHandler(this);
        fragmentManager = getSupportFragmentManager();
        title = drawerTitle = getTitle();

        drawerLayout = (HackyDrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.drawerList);
        drawerLinear = (LinearLayout) findViewById(R.id.drawerLinear);

        navDrawerItems = new ArrayList<>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(AppConstants.NAVIGATION_ITEMS[0], AppConstants.NAVIGATION_ITEMS_ICONS[0], Color.parseColor("#000000")));
        navDrawerItems.add(new NavDrawerItem(AppConstants.NAVIGATION_ITEMS[1], AppConstants.NAVIGATION_ITEMS_ICONS[1], Color.parseColor("#000000")));
        navDrawerItems.add(new NavDrawerItem(AppConstants.NAVIGATION_ITEMS[2], AppConstants.NAVIGATION_ITEMS_ICONS[2], Color.parseColor("#1976D2")));
        navDrawerItems.add(new NavDrawerItem(AppConstants.NAVIGATION_ITEMS[3], AppConstants.NAVIGATION_ITEMS_ICONS[3], Color.parseColor("#b71c1c")));
        navDrawerItems.add(new NavDrawerItem(AppConstants.NAVIGATION_ITEMS[4], AppConstants.NAVIGATION_ITEMS_ICONS[4], Color.parseColor("#1976D2")));

        drawerList.setAdapter(new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        } else {
            int position = savedInstanceState.getInt("currentNavItem");
            drawerList.setItemChecked(position, true);
            setTitle(AppConstants.NAVIGATION_ITEMS[position]);
            drawerLayout.closeDrawer(drawerLinear);
            activeDrawerItemPosition = position;
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
        Log.d("NFG1","Selecting item");
        fragmentManager.beginTransaction().replace(R.id.frameContainer, getCardHolderBasedOnCategory(category)).commit();

        Log.d("NFG1","After Selecting item");
        // Update Title on action bar
        drawerList.setItemChecked(position, true);
        setTitle(AppConstants.NAVIGATION_ITEMS[position]);
        drawerLayout.closeDrawer(drawerLinear);
        activeDrawerItemPosition = position;
    }

    @Override
    public void setTitle(CharSequence _title) {
        title = _title;
        try {
            getSupportActionBar().setTitle(title);
        } catch (Exception exception) {
            Log.e("NFG", Log.getStackTraceString(exception));
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //save current item index
        savedInstanceState.putInt("currentNavItem",activeDrawerItemPosition);
        super.onSaveInstanceState(savedInstanceState);
    }

}