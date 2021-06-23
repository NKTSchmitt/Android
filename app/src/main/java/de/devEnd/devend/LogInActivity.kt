package de.devEnd.devend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class LogInActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        // Firebase
        mAuth = FirebaseAuth.getInstance()


        // EditText
        val editText_email: EditText = findViewById(R.id.editText_email2)
        val editText_password: EditText = findViewById(R.id.editText_password2)

        // TextView
        val textView_create: TextView = findViewById(R.id.textView_create)

        // Button
        val button_login: Button = findViewById(R.id.button_login)


        if (mAuth != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        /* setOnClickListener */

        textView_create.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


        button_login.setOnClickListener {

            val email: String = editText_email.text.toString()
            val password: String = editText_password.text.toString()


            if (email.isEmpty()) {
                editText_email.error = "Bitte gebe deine Email ein!"
                editText_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editText_email.error = "Bitte gebe eine korrekte Email ein!"
                editText_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                editText_password.error = "Bitte gebe dein Passwort ein!"
                editText_password.requestFocus()
                return@setOnClickListener
            }

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{

                if (it.isSuccessful){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(this , "Log in fehlgeschlagen! Probiere es erneut!", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }
}