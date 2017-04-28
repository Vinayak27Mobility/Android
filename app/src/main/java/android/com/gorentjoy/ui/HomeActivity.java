package android.com.gorentjoy.ui;

import android.app.ProgressDialog;
import android.com.gorentjoy.R;
import android.com.gorentjoy.async.MyAsyncTask;
import android.com.gorentjoy.async.OnTaskCompleted;
import android.com.gorentjoy.model.AdResponse;
import android.com.gorentjoy.model.CategoryResponse;
import android.com.gorentjoy.model.DataHolder;
import android.com.gorentjoy.model.SectionDataModel;
import android.com.gorentjoy.net.NetworkErrorManager;
import android.com.gorentjoy.service.CategoryService;
import android.com.gorentjoy.service.ListingService;
import android.com.gorentjoy.service.Logger;
import android.com.gorentjoy.storage.SharedPrefWrapper;
import android.com.gorentjoy.ui.adapter.HomeAdAdapter;
import android.com.gorentjoy.ui.fragments.AdsFragment;
import android.com.gorentjoy.ui.fragments.CategoriesListFragment;
import android.com.gorentjoy.ui.fragments.ContactUsFragment;
import android.com.gorentjoy.ui.fragments.TermsOfUseFragment;
import android.com.gorentjoy.ui.fragments.UserProfileFragment;
import android.com.gorentjoy.ui.widgets.CustomProgressDialog;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.util.Util;
import android.com.gorentjoy.util.WeakReferenceHandler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//import com.facebook.AccessToken;
//import com.facebook.login.LoginManager;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnTaskCompleted {

    private TextView userName, userEmail;
    private ImageView userIcon;
    private Context context;
    private ProgressDialog progressDialog;
    private final String TAG = LoginActivity.class.getSimpleName();
    Toolbar toolbar;
    public static FragmentManager fragmentManager;
    private RecyclerView mrecyclerview;
    private ArrayList<SectionDataModel> dmSet;
    private MenuItem selectedMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;

        // Insert the fragment by replacing any existing fragment
        fragmentManager = getSupportFragmentManager();

        mrecyclerview = (RecyclerView) findViewById(R.id.my_recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(layoutManager);
        dmSet = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.welcome) + SharedPrefWrapper.getInstance().getUserName());
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(HomeActivity.this, PublishListingActivity.class);
                startActivity(inte);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userName = (TextView) headerView.findViewById(R.id.textView);
        userEmail = (TextView) headerView.findViewById(R.id.textView1);

        if (TextUtils.isEmpty(SharedPrefWrapper.getInstance().getUserIcon())) {
        } else {
            userIcon = (ImageView) headerView.findViewById(R.id.imageView);
            Picasso.with(context).load(SharedPrefWrapper.getInstance().getUserIcon()).into(userIcon);
        }
        userName.setText(SharedPrefWrapper.getInstance().getUserName());
        userEmail.setText(SharedPrefWrapper.getInstance().getUserEmail());

        navigationView.setNavigationItemSelectedListener(this);

        //
        progressDialog = new CustomProgressDialog(HomeActivity.this);
        progressDialog.show();
        CategoryService categoryService = CategoryService.getInstance();
        categoryService.getCategories(this, new FetchHandler(this), TAG);
        //
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentManager != null && fragmentManager.getBackStackEntryCount() > 0) {
                Util.handleFragmentBack(fragmentManager);
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "count is " + fragmentManager.getBackStackEntryCount());
                if (fragmentManager.getBackStackEntryCount() == 1) {
                    setActionBarTitle(getResources().getString(R.string.welcome) + SharedPrefWrapper.getInstance().getUserName());
                    if (selectedMenuItem != null) selectedMenuItem.setChecked(false);
                }
            } else {
                android.app.AlertDialog.Builder alert = Util.createAlert(context,
                        null,
                        context.getResources().getString(R.string.error_alert_title),
                        context.getResources().getString(R.string.confirm_exit),
                        R.string.button_yes,
                        R.string.button_no,
                        0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DataHolder.getInstance().clearData();
                                HomeActivity.this.finish();
                            }
                        }
                        ,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        },
                        null);

                android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
            //super.onBackPressed();
        }
    }

    @Override
    public void onTaskCompleted(Object result) {
        List<CategoryResponse.Category> homeCate = (List<CategoryResponse.Category>) result;
        if (homeCate != null && !homeCate.isEmpty()) {
            DataHolder.getInstance().setHomeCategories(homeCate);
            ListingService listingService = ListingService.getInstance();
            for (CategoryResponse.Category homeCategory : homeCate) {
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "home cate is " + homeCategory.getName());
            }
            String inputParam = "&id_category=" + homeCate.get(0).getIdCategory();
            listingService.getListings(context, new AdHandler(this), null, inputParam, TAG);
        } else {
            findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        }
    }

    private static final class FetchHandler extends WeakReferenceHandler<HomeActivity> {

        Context context;

        public FetchHandler(HomeActivity reference) {
            super(reference);
        }

        @Override
        protected void handleMessage(HomeActivity reference, Message msg) {
            context = reference.context;
            reference.progressDialog.dismiss();
            if (msg.arg2 == Constants.SUCCESS) {
                ArrayList<CategoryResponse.Category> dataSet;

                dataSet = (ArrayList<CategoryResponse.Category>) msg.obj;

                if (!dataSet.isEmpty()) {
                    DataHolder.getInstance().setAllCategories(dataSet);
                    MyAsyncTask myAsyncTask = new MyAsyncTask(reference, Constants.FETCH_CATEGORY, reference);
                    myAsyncTask.execute();
                }
            } else {
                NetworkErrorManager.showErrors(context, msg.arg2, null);
            }
        }
    }


    public static class AdHandler extends WeakReferenceHandler<HomeActivity> {

        Context context;
        public static int index = 0;
        public ListingService listingService = ListingService.getInstance();
        public static HomeAdAdapter adapter;

        public AdHandler(HomeActivity reference) {
            super(reference);
        }

        @Override
        public void handleMessage(HomeActivity reference, Message msg) {
            context = reference.context;
            if (reference.progressDialog != null) {
                reference.progressDialog.dismiss();
            }


            if (msg.arg2 == Constants.SUCCESS) {
                index++;
                ArrayList<AdResponse.Ad> dataSet = (ArrayList) msg.obj;
                if (!dataSet.isEmpty()) {
                    CategoryResponse.Category category = DataHolder.getInstance().getHomeCategories().get(index - 1);
                    SectionDataModel dm = new SectionDataModel();
                    dm.setHeaderTitle(category.getName());
                    dm.setHeaderId(category.getIdCategory());
                    dm.setAllItemsInSection(dataSet);
                    reference.dmSet.add(dm);


                    if (index == 1) {
                        adapter = new HomeAdAdapter(context, reference.dmSet.get(0));
                        reference.mrecyclerview.setAdapter(adapter);
                    } else {
                        adapter.addView(dm);
                    }
                }
                try {
                    CategoryResponse.Category homeCate = DataHolder.getInstance().getHomeCategories().get(index);
                    String inputParam = "&id_category=" + homeCate.getIdCategory();
                    listingService.getListings(context, new AdHandler(reference), null, inputParam, reference.TAG);
                } catch (Exception e) {
                }
            } else {
                android.app.AlertDialog.Builder alert = Util.createAlert(reference.context,
                        null,
                        context.getResources().getString(R.string.error_alert_title),
                        null,
                        R.string.button_ok,
                        0,
                        0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }
                        ,
                        null,
                        null);

                NetworkErrorManager.showErrors(context, msg.arg2, alert);
            }
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setActionBarTitle(String title) {
        toolbar.setTitle(title);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        selectedMenuItem = item;

        if (id == R.id.nav_home) {
            Util.clearFragmentStack(fragmentManager);
            setActionBarTitle(getResources().getString(R.string.welcome) + SharedPrefWrapper.getInstance().getUserName());
        } else if (id == R.id.nav_profile) {
            selectDrawerItem(item);
        } else if (id == R.id.nav_store) {
            selectDrawerItem(item);
        } else if (id == R.id.nav_cate) {
            selectDrawerItem(item);
        } /*else if (id == R.id.nav_wish) {
            selectDrawerItem(item);
        } else if (id == R.id.nav_rate) {

        } */ else if (id == R.id.nav_about) {
            selectDrawerItem(item);
        } else if (id == R.id.nav_tou) {
            selectDrawerItem(item);
        } else if (id == R.id.nav_logout) {
            android.app.AlertDialog.Builder alert = Util.createAlert(context,
                    null,
                    context.getResources().getString(R.string.error_alert_title),
                    context.getResources().getString(R.string.confirm_logout),
                    R.string.button_yes,
                    R.string.button_no,
                    0,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DataHolder.getInstance().clearData();
                            SharedPrefWrapper.getInstance().resetLoginData();
                            /*AccessToken accessToken = AccessToken.getCurrentAccessToken();
                            if(accessToken != null) {
                                LoginManager.getInstance().logOut();
                            }*/
                            HomeActivity.this.finish();
                        }
                    }
                    ,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    },
                    null);

            android.app.AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                fragmentClass = UserProfileFragment.class;
                break;
            case R.id.nav_cate:
                fragmentClass = CategoriesListFragment.class;
                break;
            case R.id.nav_store:
                fragmentClass = AdsFragment.class;
                break;
            /*case R.id.nav_wish:
                fragmentClass = FavoriteFragment.class;
                break;*/
            case R.id.nav_about:
                fragmentClass = ContactUsFragment.class;
                break;
            case R.id.nav_tou:
                fragmentClass = TermsOfUseFragment.class;
                break;

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (menuItem.isChecked())
            return;

        Util.clearFragmentStack(fragmentManager);

        switch (menuItem.getItemId()) {
            case R.id.nav_store:
            case R.id.nav_cate:
                Bundle arguments = new Bundle();
                arguments.putInt(Constants.FRAGMENT_KEY, menuItem.getItemId());
                fragment.setArguments(arguments);
                break;
        }


        String tag = fragment.getClass().getSimpleName();

        fragmentManager.beginTransaction().add(R.id.flContent, fragment, tag).addToBackStack(tag).commit();
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
    }
}
