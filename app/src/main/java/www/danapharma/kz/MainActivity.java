package www.danapharma.kz;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import org.osmdroid.config.Configuration;

public class MainActivity extends AppCompatActivity {

    final Fragment accountFragment = new Account();
    final Fragment mapFragment = new Map();
    final Fragment searchFragment = new Search();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx =getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        fm.beginTransaction().add(R.id.main_container, accountFragment, "3").hide(accountFragment).commit();
        fm.beginTransaction().add(R.id.main_container, mapFragment, "2").hide(mapFragment).commit();
        fm.beginTransaction().add(R.id.main_container,searchFragment, "1").commit();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.account:
                        fm.beginTransaction().hide(active).show(accountFragment).commit();
                        active = accountFragment;
                        return true;

                    case R.id.map:
                        fm.beginTransaction().hide(active).show(mapFragment).commit();
                        active = mapFragment;
                        return true;

                    case R.id.search:
                        fm.beginTransaction().hide(active).show(searchFragment).commit();
                        active = searchFragment;
                        return true;
                }
                return false;
            }
        });

    }


}
