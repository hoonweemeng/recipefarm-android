package com.app.recipefarm.onboarding;

import static com.app.recipefarm.utility.Constants.USERID;
import static com.app.recipefarm.utility.Constants.registerUserEndpoint;
import static com.app.recipefarm.utility.RFFunctions.getInvalidEntries;
import static com.app.recipefarm.utility.RFFunctions.isResponseSuccessful;
import static com.app.recipefarm.utility.RFFunctions.responseErrorHandler;
import static com.app.recipefarm.utility.ValidationMethods.validateEmailAddress;
import static com.app.recipefarm.utility.ValidationMethods.validatePassword;
import static com.app.recipefarm.utility.ValidationMethods.validateUsername;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.recipefarm.R;
import com.app.recipefarm.core.RFDialog;
import com.app.recipefarm.core.RFFragment;
import com.app.recipefarm.models.base.User;
import com.app.recipefarm.models.base.ValidationModel;
import com.app.recipefarm.models.response.user.UserRegisterResponse;
import com.app.recipefarm.utility.NetworkManager;
import com.app.recipefarm.utility.SharedPrefsManager;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends RFFragment {

    private Button registerBtn;
    private EditText emailTextField;
    private EditText usernameTextField;
    private EditText passwordTextField;
    private EditText retypePasswordTextField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.onboarding_fragment_register, container, false);
        initActionBar("Register", true);
        registerBtn = mainView.findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(v -> onSubmit());

        emailTextField = mainView.findViewById(R.id.emailRegisterTextField);
        usernameTextField = mainView.findViewById(R.id.usernameRegisterTextField);
        passwordTextField = mainView.findViewById(R.id.passwordRegisterTextField);
        retypePasswordTextField = mainView.findViewById(R.id.retypePasswordRegisterTextField);

        usernameTextField.addTextChangedListener(new TextWatcher() {
            private boolean isEditing = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Called before text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Called as text is being changed
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEditing) return; // Prevent infinite loop
                isEditing = true;

                // Get the input text
                String input = s.toString();

                // Convert to lowercase and remove whitespace
                String modifiedText = input.toLowerCase().replaceAll("\\s+", "");

                // Update the EditText only if changes were made
                if (!modifiedText.equals(input)) {
                    usernameTextField.setText(modifiedText);
                    usernameTextField.setSelection(modifiedText.length()); // Move cursor to the end
                }

                isEditing = false;
            }
        });

        return mainView;
    }

    private void onSubmit() {
        String inputEmailAddress = String.valueOf(emailTextField.getText()).trim();
        String inputUsername = String.valueOf(usernameTextField.getText()).trim();
        String inputPassword = String.valueOf(passwordTextField.getText()).trim();
        String inputRetypePassword = String.valueOf(retypePasswordTextField.getText()).trim();

        List<ValidationModel> validationList = new ArrayList<>();

        validationList.add(validateEmailAddress(inputEmailAddress));
        validationList.add(validateUsername(inputUsername));
        validationList.add(validatePassword(inputPassword, inputRetypePassword));

        List<ValidationModel> invalidEntries = getInvalidEntries(validationList);

        if (!invalidEntries.isEmpty()){
            RFDialog dialog = new RFDialog(getContext(), "Error", invalidEntries.get(0).message, null, "Close", null);
            dialog.show();
            return;
        }

        User user = new User(null, inputEmailAddress, inputUsername, inputPassword, null, null, null);


        showLoader(null);
        //send request
        NetworkManager.getInstance(getContext()).post(
                registerUserEndpoint,
                null,
                user,
                UserRegisterResponse.class,
                new NetworkManager.ResponseCallback<UserRegisterResponse>() {
                    @Override
                    public void onSuccess(UserRegisterResponse response) {
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

    private void parseResponse(UserRegisterResponse response) {
        if (isResponseSuccessful(response)){
            SharedPrefsManager.shared(getContext()).saveData(USERID, response.data.id);
            backToHome();
        }
        else {
            responseErrorHandler(getContext(), response);
        }
    }

}