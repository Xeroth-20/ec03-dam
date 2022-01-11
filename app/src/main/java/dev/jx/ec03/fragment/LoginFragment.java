package dev.jx.ec03.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import dev.jx.ec03.CodemoniumApplication;
import dev.jx.ec03.R;
import dev.jx.ec03.activity.MainActivity;
import dev.jx.ec03.entity.User;
import dev.jx.ec03.navigable.NavigationHost;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        TextInputLayout usernameTextInputLayout = view.findViewById(R.id.login_username_text_input_layout);
        TextInputEditText usernameTextInputEditText = view.findViewById(R.id.login_username_text_input_edit_text);
        usernameTextInputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isUsernameValid(usernameTextInputEditText.getText())) {
                    usernameTextInputLayout.setError(null);
                }

                return true;
            }
        });

        TextInputLayout passwordTextInputLayout = view.findViewById(R.id.login_password_text_input_layout);
        TextInputEditText passwordTextInputEditText = view.findViewById(R.id.login_password_text_input_edit_text);
        passwordTextInputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isUsernameValid(passwordTextInputEditText.getText())) {
                    passwordTextInputLayout.setError(null);
                }

                return true;
            }
        });

        MaterialButton loginButton = view.findViewById(R.id.login_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUsernameValid(usernameTextInputEditText.getText()) && isPasswordValid(passwordTextInputEditText.getEditableText())) {
                    User user = new User();
                    user.setName(usernameTextInputEditText.getText().toString().trim());

                    CodemoniumApplication.setUser(user);
                    ((NavigationHost) getActivity()).navigateTo(new HomeFragment(), false);
                }

                if (!isUsernameValid(usernameTextInputEditText.getText())) {
                    int minChars = getResources().getInteger(R.integer.login_password_min_characters);
                    int maxChars = getResources().getInteger(R.integer.login_username_max_characters);
                    String errorText = getResources().getString(R.string.login_username_error_text, minChars, maxChars);
                    usernameTextInputLayout.setError(errorText);
                }

                if (!isPasswordValid(passwordTextInputEditText.getText())) {
                    int minChars = getResources().getInteger(R.integer.login_password_min_characters);
                    String errorText = getResources().getString(R.string.login_password_error_text, minChars);
                    passwordTextInputLayout.setError(errorText);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            ((MainActivity) context).setBottomNavigationVisibility(View.GONE);
        }
    }

    private boolean isUsernameValid(Editable editable) {
        int minChars = getResources().getInteger(R.integer.login_password_min_characters);
        int maxChars = getResources().getInteger(R.integer.login_username_max_characters);
        int usernameLength = editable.toString().trim().length();

        return maxChars >= usernameLength && usernameLength >= minChars;
    }

    private boolean isPasswordValid(Editable editable) {
        int minChars = getResources().getInteger(R.integer.login_password_min_characters);
        int passwordLength = editable.length();

        return passwordLength >= minChars;
    }
}
