package com.ivantha.mobileatm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ivantha.mobileatm.R;
import com.ivantha.mobileatm.common.Session;
import com.ivantha.mobileatm.fragment.AccountFragment;
import com.ivantha.mobileatm.fragment.DealsFragment;
import com.ivantha.mobileatm.fragment.HistoryFragment;
import com.ivantha.mobileatm.fragment.HomeFragment;
import com.ivantha.mobileatm.fragment.PaymentsFragment;
import com.ivantha.mobileatm.fragment.RechargeFragment;
import com.ivantha.mobileatm.fragment.SettingsFragment;
import com.ivantha.mobileatm.model.Account;
import com.ivantha.mobileatm.model.Settings;
import com.ivantha.mobileatm.model.User;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Contract;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static MainActivity mainActivity;

    public MainActivity() {

    }

    @Contract(pure = true)
    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Emulating the login
        User user = new User();
        user.setFirstName("Oshan");
        user.setLastName("Mudannayake");
        user.setEmail("oshan.ivantha@gmail.com");
        user.setPassword("oshan1234");
        user.setSeed("VKN9VNOZUFMWMMIUVZLVXUTFPWRGQQBNGEYWBHUYQMXNKPDDFAHVQJKCQRHUYHGRBLCIHDWHUGK99FHCI");

        Account account = new Account();
        account.setSpendingLimit(4570);
        user.setAccount(account);

        Settings settings = new Settings();
        user.setSettings(settings);

        Session.setCurrentUser(user);
        ////////////////////////////////////////////////////////////////////////////////////////////

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.setPrompt("Scan your transaction QR code");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mainActivity = MainActivity.this;

        // Set Home as default view
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = HomeFragment.newInstance();
        transaction.replace(R.id.container, fragment);
        transaction.commit();

        // Set Picasso debugging
        Picasso.get().setIndicatorsEnabled(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            return true;
        } else if (id == R.id.action_report) {
            // TMP: Open Login Page //
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = null;

        if (id == R.id.nav_home) {
            fragment = HomeFragment.newInstance();
        } else if (id == R.id.nav_payments) {
            fragment = PaymentsFragment.newInstance();
        } else if (id == R.id.nav_recharge) {
            fragment = RechargeFragment.newInstance();
        } else if (id == R.id.nav_deals) {
            fragment = DealsFragment.newInstance();
        } else if (id == R.id.nav_history) {
            fragment = HistoryFragment.newInstance();
        } else if (id == R.id.nav_account) {
            fragment = AccountFragment.newInstance();
        } else if (id == R.id.nav_settings) {
            fragment = SettingsFragment.newInstance();
        }
        transaction.replace(R.id.container, fragment);
        transaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
