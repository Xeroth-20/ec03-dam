package dev.jx.ec03.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import dev.jx.ec03.R;
import dev.jx.ec03.fragment.HomeFragment;
import dev.jx.ec03.fragment.LoginFragment;
import dev.jx.ec03.fragment.SettingsFragment;
import dev.jx.ec03.navigable.NavigationHost;

public class MainActivity extends AppCompatActivity implements NavigationHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setUpBottomNavigation();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container, new LoginFragment())
                    .commit();
        }
    }

    private void setUpBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = getFragmentByMenuItemId(item.getItemId());
                if (!item.isChecked() && fragment != null) {
                    navigateTo(fragment, true);
                    return true;
                } else if (fragment == null) {
                    Toast.makeText(MainActivity.this, "No available now", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }

    @Nullable
    private Fragment getFragmentByMenuItemId(int id) {
        switch (id) {
            case R.id.bottom_nav_home_menu_item:
                return new HomeFragment();
            case R.id.bottom_nav_person_menu_item:
                return new SettingsFragment();
            default:
                return null;
        }
    }

    public void setBottomNavigationVisibility(int visibility) {
        BottomAppBar bottomAppbar = findViewById(R.id.bottom_appbar);
        FloatingActionButton floatingActionButton = findViewById(R.id.bottom_nav_fab);
        bottomAppbar.setVisibility(visibility);
        floatingActionButton.setVisibility(visibility);
    }

    public void navigateTo(@NonNull Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }
}