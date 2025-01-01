package com.app.recipefarm;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.recipefarm.onboarding.GetStartedFragment;
import com.app.recipefarm.onboarding.LoginFragment;
import com.app.recipefarm.onboarding.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    private View navBar;
    private LinearLayout navHome,navBookmarks, navProfile;
    private ImageView iconHome, iconBookmarks, iconProfile;
    private TextView textHome, textBookmarks, textProfile;

    private FrameLayout frameOnBoarding, frameBody;

    //onboarding fragment
    private Fragment getStartedFragment, loginFragment, registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        navBar = findViewById(R.id.navbar);

        frameBody = findViewById(R.id.mainbody_frame);
        frameOnBoarding = findViewById(R.id.onboarding_frame);

        navHome = findViewById(R.id.nav_home);
        navBookmarks = findViewById(R.id.nav_bookmarks);
        navProfile = findViewById(R.id.nav_profile);

        iconHome = findViewById(R.id.icon_home);
        iconBookmarks = findViewById(R.id.icon_bookmarks);
        iconProfile = findViewById(R.id.icon_profile);

        textHome = findViewById(R.id.text_home);
        textBookmarks = findViewById(R.id.text_bookmarks);
        textProfile = findViewById(R.id.text_profile);

        //user logged in
        if (false){
            // show home screen
        }
        else {
            //show onboarding page
            onBoarding();
        }

    }

    private void onBoarding() {
        //hide navigation bar
        navBar.setVisibility(View.GONE);

        //hide main fragment
        frameBody.setVisibility(View.GONE);

        //display onboarding frame
        frameOnBoarding.setVisibility(View.VISIBLE);

        //Init fragment
        Fragment getStartedFragment = new GetStartedFragment();
        Fragment  loginFragment = new LoginFragment();
        Fragment registerFragment = new RegisterFragment();

        replaceFragment(getStartedFragment, R.id.onboarding_frame);
    }

    private  void initMainBody(){
        // Default selection
        selectTab(navHome);

        // Click listeners
        navHome.setOnClickListener(v -> selectTab(navHome));
        navBookmarks.setOnClickListener(v -> selectTab(navBookmarks));
        navProfile.setOnClickListener(v -> selectTab(navProfile));
    }

    public void replaceFragment(Fragment fragment, int frame){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frame, fragment);
        fragmentTransaction.commit();
    }

    private void selectTab(View selectedTab) {
        resetTabs();
        int selectedTabColor = R.color.cadet_blue;
        if (selectedTab.equals(navHome)) {
            changeTabButtonColor(iconHome, textHome, selectedTabColor);
        } else if (selectedTab.equals(navBookmarks)) {
            changeTabButtonColor(iconBookmarks, textBookmarks, selectedTabColor);
        } else if (selectedTab.equals(navProfile)) {
            changeTabButtonColor(iconProfile, textProfile, selectedTabColor);
        }
    }

    private void resetTabs() {
        int unSelectedTabColor = R.color.light_grey;
        changeTabButtonColor(iconHome,textHome,unSelectedTabColor);
        changeTabButtonColor(iconBookmarks,textBookmarks,unSelectedTabColor);
        changeTabButtonColor(iconProfile,textProfile,unSelectedTabColor);
    }

    private void changeTabButtonColor(ImageView imageView, TextView textView, int color){
        imageView.setColorFilter(getResources().getColor(color, null));
        textHome.setTextColor(getResources().getColor(color, null));
    }
}