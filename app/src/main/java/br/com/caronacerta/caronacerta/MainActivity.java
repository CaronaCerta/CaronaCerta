package br.com.caronacerta.caronacerta;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import br.com.caronacerta.caronacerta.adapter.NavDrawerListAdapter;
import br.com.caronacerta.caronacerta.contract.RidesContract;
import br.com.caronacerta.caronacerta.fragment.AvaliarCaronasFragment;
import br.com.caronacerta.caronacerta.fragment.MinhasCaronasFragment;
import br.com.caronacerta.caronacerta.fragment.HomeFragment;
import br.com.caronacerta.caronacerta.fragment.OferecerCaronaFragment;
import br.com.caronacerta.caronacerta.fragment.ProcurarCaronaFragment;
import br.com.caronacerta.caronacerta.fragment.VisualizarContaFragment;
import br.com.caronacerta.caronacerta.fragment.VisualizarMotoristaFragment;
import br.com.caronacerta.caronacerta.model.NavDrawerItem;
import br.com.caronacerta.caronacerta.util.SessionUtil;

public class MainActivity extends BasicActivity {
    public static List<String> caronasAvailable;
    public static List<String> caronasELVGroup;
    public static HashMap<String, List<String>> caronasELVChild;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    // nav drawer title
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // preparing list data
        prepareListData();

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(4, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                (Toolbar) findViewById(R.id.toolbar), //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                navigateToSettingsActivity();
                return true;
            case R.id.action_profile:
                navigateToFragment(new VisualizarContaFragment());
                return true;
            case R.id.action_logout:
                SessionUtil.logout(getApplicationContext());
                navigateToLoginActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//      menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Displaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new VisualizarMotoristaFragment();
                break;
            case 2:
                fragment = new MinhasCaronasFragment();
                break;
            case 3:
                fragment = new OferecerCaronaFragment();
                break;
            case 4:
                fragment = new ProcurarCaronaFragment();
                break;
            case 5:
                fragment = new AvaliarCaronasFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            navigateToFragment(fragment);

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        caronasELVGroup = new ArrayList<String>();
        caronasELVChild = new HashMap<String, List<String>>();

        // Adding child data
        caronasELVGroup.add("15/10 - 14h00");
        caronasELVGroup.add("29/10 - 19h00");
        caronasELVGroup.add("01/11 - 18h00");

        // Adding child data
        List<String> l1 = new ArrayList<String>();
        l1.add("Xandão - 11 98456 1123");
        l1.add("Elizeu - 19 98234 2267");
        l1.add("PD - 19 9655 7234");

        List<String> l2 = new ArrayList<String>();
        l2.add("Oscar - 11 97667 5543");
        l2.add("Batata - 19 98738 9755");
        l2.add("PD - 19 9655 7234");

        List<String> l3 = new ArrayList<String>();
        l3.add("Xandão - 11 98456 1123");
        l3.add("Batata - 19 98738 9755");
        l3.add("Giu - 19 97656 9908");

        caronasELVChild.put(caronasELVGroup.get(0), l1); // Header, Child data
        caronasELVChild.put(caronasELVGroup.get(1), l2);
        caronasELVChild.put(caronasELVGroup.get(2), l3);

        Vector<ContentValues> cVVector = new Vector<ContentValues>(caronasELVGroup.size());

        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            this.getContentResolver().bulkInsert(RidesContract.RidesGroupEntry.CONTENT_URI, cvArray);
        }

        /* Caronas disponiveis */
        caronasAvailable = new ArrayList<String>();

    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
}
