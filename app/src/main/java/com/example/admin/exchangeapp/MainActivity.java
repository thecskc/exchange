package com.example.admin.exchangeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final String TAG = MainActivity.class.getSimpleName();
    // Choose authentication providers
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build(),

            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build()

    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        //startActivity(new Intent(MainActivity.this, MenuActivity.class));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {

                Toast.makeText(this, "Successfully signed in", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                finish();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    //showSnackbar(R.string.sign_in_cancelled);
                    Toast.makeText(this, "Sign-in failed!", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    //showSnackbar(R.string.no_internet_connection);
                    Toast.makeText(this, "No Internet connection!", Toast.LENGTH_SHORT).show();

                    return;
                }

                //showSnackbar(R.string.unknown_error);
                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
    }
}
