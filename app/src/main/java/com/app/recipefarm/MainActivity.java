package com.app.recipefarm;

import static com.app.recipefarm.utility.Constants.USERID;
import static com.app.recipefarm.utility.Constants.detailUserEndpoint;
import static com.app.recipefarm.utility.RFFunctions.getHeaders;
import static com.app.recipefarm.utility.RFFunctions.isNullOrBlank;
import static com.app.recipefarm.utility.RFFunctions.isResponseSuccessful;
import static com.app.recipefarm.utility.RFFunctions.responseErrorHandler;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.app.recipefarm.core.RFActivity;
import com.app.recipefarm.core.RFDialog;
import com.app.recipefarm.home.HomeFragment;
import com.app.recipefarm.profile.ProfileFragment;
import com.app.recipefarm.model.request.generic.EmptyRequest;
import com.app.recipefarm.model.response.user.UserDetailResponse;
import com.app.recipefarm.onboarding.GetStartedFragment;
import com.app.recipefarm.utility.NetworkManager;
import com.app.recipefarm.utility.SharedPrefsManager;

import java.util.Objects;

public class MainActivity extends RFActivity {

    private View navBar;
    private LinearLayout navHome,navBookmarks, navProfile;
    private ImageView iconHome, iconBookmarks, iconProfile;
    private TextView textHome, textBookmarks, textProfile;

    private FrameLayout frameOnBoarding, frameBody;

    //main page fragment
    private Fragment profileFragment, homeFragment, registerFragment;

    private String userId;

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

        // Click listeners
        navHome.setOnClickListener(v -> selectTab(navHome));
        navBookmarks.setOnClickListener(v -> selectTab(navBookmarks));
        navProfile.setOnClickListener(v -> selectTab(navProfile));

        initFragments();

        //user logged in
        userId = SharedPrefsManager.shared(this).getData(USERID, String.class);
        if (userId != null){
            // show home screen
            initMainBody();
        }
        else {
            //show onboarding page
            onBoarding();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = SharedPrefsManager.shared(this).getData(USERID, String.class);
        if (!isNullOrBlank(userId) && RFDataManager.shared().user == null){
            fetchUserDetail();
        }

        if (RFDataManager.shared().mainActivityHelper.navigationPage != null){
            switch (RFDataManager.shared().mainActivityHelper.navigationPage) {
                case HOME:
                    selectTab(navHome);
                    break;
                case BOOKMARK:
                    selectTab(navBookmarks);
                    break;
                case PROFILE:
                    selectTab(navProfile);
                    break;
            }
            // reset navigation helper
            RFDataManager.shared().mainActivityHelper = new MainActivityHelper();
        }
    }

    private void fetchUserDetail() {
        Context cxt = this;
        showLoader(null);
        //send request
        NetworkManager.getInstance(this).post(
                detailUserEndpoint,
                getHeaders(this),
                new EmptyRequest(),
                UserDetailResponse.class,
                new NetworkManager.ResponseCallback<>() {
                    @Override
                    public void onSuccess(UserDetailResponse response) {
                        loader.hide();
                        parseUserDetailResponse(response);
                    }

                    @Override
                    public void onError(String error) {
                        loader.hide();
                        RFDialog dialog = new RFDialog(cxt, "Error", error, null, "Close", null);
                        dialog.show();
                    }
                }
        );

    }

    private void parseUserDetailResponse(UserDetailResponse response) {
        if (isResponseSuccessful(response)){
            SharedPrefsManager.shared(this).saveData(USERID, response.data.userId);
            RFDataManager.shared().user = response.data;
        }
        else {
            if (response != null && Objects.equals(response.errorMessage, "User does not exist.")) {
                // force user to logout (account probably got deleted)
                logout();
            }
            else {
                responseErrorHandler(this, response);
            }
        }
    }

    private void logout() {
        // clear all data
        RFDataManager.reset();
        SharedPrefsManager.shared(this).clearAll();

        // redirect
        onBoarding();
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
        replaceFragment(getStartedFragment, R.id.onboarding_frame);
    }

    public void initMainBody(){
        //hide navigation bar
        navBar.setVisibility(View.VISIBLE);

        //hide main fragment
        frameBody.setVisibility(View.VISIBLE);

        //hide onboarding frame
        frameOnBoarding.setVisibility(View.GONE);

        // Default selection
        selectTab(navHome);

    }

    public void initFragments() {
        profileFragment = new ProfileFragment();
        homeFragment = new HomeFragment();
    }

    private void selectTab(View selectedTab) {
        resetTabs();
        int selectedTabColor = R.color.cadet_blue;
        if (selectedTab.equals(navHome)) {
            changeTabButtonColor(iconHome, textHome, selectedTabColor);
            switchFragments(homeFragment, R.id.mainbody_frame);

        } else if (selectedTab.equals(navBookmarks)) {
            changeTabButtonColor(iconBookmarks, textBookmarks, selectedTabColor);

        } else if (selectedTab.equals(navProfile)) {
            changeTabButtonColor(iconProfile, textProfile, selectedTabColor);
            switchFragments(profileFragment, R.id.mainbody_frame);
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