package com.example.retrofit_practice


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    //Создали объект авторизации
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)  //вызов экрана авторизации
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieIntent = Intent(this, MoviesActivity::class.java)
        startActivity(movieIntent)

        database = Firebase.database.reference  //database init
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())

        // Create and launch sign-in intent
    //  val signInIntent = AuthUI.getInstance()
    //      .createSignInIntentBuilder()
    //      .setAvailableProviders(providers)
    //      .build()
    //  signInLauncher.launch(signInIntent)  //запуск экрана авторизации

    }


    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse  //результат с экрана firebase auth
        if (result.resultCode == RESULT_OK) {

            // Successfully signed in
            val authUser = FirebaseAuth.getInstance().currentUser

            //if user != null, then save to Database:
            authUser?.let {

                //Make a user
                val firebaseUser = User(it.email, it.uid)

                //save new user in path: database.child.users (id):
                database.child("users").child(it.uid).setValue(firebaseUser)


            }

        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

}