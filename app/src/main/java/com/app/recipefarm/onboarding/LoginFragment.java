package com.app.recipefarm.onboarding;

import static com.app.recipefarm.utility.Constants.USERID;
import static com.app.recipefarm.utility.Constants.loginUserEndpoint;
import static com.app.recipefarm.utility.RFFunctions.getInvalidEntries;
import static com.app.recipefarm.utility.RFFunctions.responseErrorHandler;
import static com.app.recipefarm.utility.ValidationMethods.validateEmailAddress;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.app.recipefarm.R;
import com.app.recipefarm.RFDataManager;
import com.app.recipefarm.core.RFDialog;
import com.app.recipefarm.core.RFFragment;
import com.app.recipefarm.models.base.ValidationModel;
import com.app.recipefarm.models.request.user.UserLoginRequest;
import com.app.recipefarm.models.response.user.UserDetailResponse;
import com.app.recipefarm.utility.NetworkManager;
import com.app.recipefarm.utility.SharedPrefsManager;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends RFFragment {
    private LinearLayout registerText;
    private EditText emailTextField;
    private EditText passwordTextField;
    private Button loginBtn;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.onboarding_fragment_login, container, false);

        initActionBar("Login");
        registerText = mainView.findViewById(R.id.sign_up_text);
        registerText.setOnClickListener(l -> navigateToRegister());

        loginBtn = mainView.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(v -> onSubmit());

        emailTextField = mainView.findViewById(R.id.emailLoginTextField);
        passwordTextField = mainView.findViewById(R.id.passwordLoginTextField);

        return mainView;
    }

    private void onSubmit() {
        //validate

        String inputEmailAddress = String.valueOf(emailTextField.getText()).trim();
        String inputPassword = String.valueOf(passwordTextField.getText()).trim();

        List<ValidationModel> validationList = new ArrayList<>();
        validationList.add(validateEmailAddress(inputEmailAddress));
        List<ValidationModel> invalidEntries = getInvalidEntries(validationList);

        if (!invalidEntries.isEmpty()){
            RFDialog dialog = new RFDialog(getContext(), "Error", invalidEntries.get(0).message, null, "Close", null);
            dialog.show();
            return;
        }

        UserLoginRequest userLoginRequest = new UserLoginRequest(inputEmailAddress, inputPassword);

        showLoader(null);
        //send request
        NetworkManager.getInstance(getContext()).post(
                loginUserEndpoint,
                null,
                userLoginRequest,
                UserDetailResponse.class,
                new NetworkManager.ResponseCallback<UserDetailResponse>() {
                    @Override
                    public void onSuccess(UserDetailResponse response) {
                        loader.hide();
                        parseResponse(response);
                    }

                    @Override
                    public void onError(String error) {
                        loader.hide();
                        RFDialog dialog = new RFDialog(getContext(), "Error", error, null, "Close", null);
                        dialog.show();
                    }
                }
        );
    }

    private void parseResponse(UserDetailResponse response) {
        if (response.success == true){
            SharedPrefsManager.shared(getContext()).saveData(USERID, response.data.userId);
            RFDataManager.shared().user = response.data;
            backToHome();
        }
        else {
            responseErrorHandler(getContext(), response);
        }
    }


    private void navigateToRegister() {
        Fragment registerFragment = new RegisterFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.onboarding_frame, registerFragment);
        transaction.addToBackStack(null); // Add this transaction to the back stack
        transaction.commit();
    }
}