package de.devEnd.devend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button_logout: Button = findViewById(R.id.button_logout)
        mAuth = FirebaseAuth.getInstance()



        button_logout.setOnClickListener {

            mAuth.signOut()
            val Intent: Intent = Intent(this, LogInActivity::class.java)
            startActivity(Intent)

        }

    }
}