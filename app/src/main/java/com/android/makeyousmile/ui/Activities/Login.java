package com.android.makeyousmile.ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.makeyousmile.R;
import com.android.makeyousmile.ui.Utility.Utils;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Login extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 123;
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
//                new AuthUI.IdpConfig.PhoneBuilder().build()
        );
        showSignInOptions();

    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(), MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user.getEmail() != null) {
                    if (user != null && (Objects.requireNonNull(user.getEmail().equalsIgnoreCase("muhammadraheel_95@yahoo.com")))) {
                        Utils.getInstance().setBoolean("isAdmin", true, getApplicationContext());
                        Utils.getInstance().setDefaults("userID", user.getUid(), getApplicationContext());
                        Utils.getInstance().setDefaults("userDisplayName",user.getDisplayName(),getApplicationContext());
                        Utils.getInstance().setDefaults("userEmail",user.getEmail(),getApplicationContext());
                        Utils.getInstance().setDefaults("userPhone",user.getPhoneNumber(),getApplicationContext());
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                        Utils.getInstance().setBoolean("isLoggedIn", true, this);
                        Toast.makeText(this, "Welcome! " + user.getDisplayName(), Toast.LENGTH_LONG).show();


                    } else {
                        Utils.getInstance().setBoolean("isAdmin", false, getApplicationContext());
                        Utils.getInstance().setDefaults("userID", user.getUid(), getApplicationContext());
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                        Utils.getInstance().setBoolean("isLoggedIn", true, this);
                        Toast.makeText(this, "Welcome! " + user.getDisplayName(), Toast.LENGTH_LONG).show();
                        Utils.getInstance().setDefaults("userDisplayName", user.getDisplayName(), getApplicationContext());
                        Utils.getInstance().setDefaults("userEmail", user.getEmail(), getApplicationContext());
                        Utils.getInstance().setDefaults("userPhone", user.getPhoneNumber(), getApplicationContext());
                    }
                } else {
                    Utils.getInstance().setBoolean("isAdmin", false, getApplicationContext());
                    Utils.getInstance().setDefaults("userID", user.getUid(), getApplicationContext());
                    Utils.getInstance().setDefaults("userDisplayName", user.getDisplayName(), getApplicationContext());
                    Utils.getInstance().setDefaults("userEmail", user.getEmail(), getApplicationContext());
                    Utils.getInstance().setDefaults("userPhone", user.getPhoneNumber(), getApplicationContext());
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    Utils.getInstance().setBoolean("isLoggedIn", true, this);
                }
            }
        }

    }
}
