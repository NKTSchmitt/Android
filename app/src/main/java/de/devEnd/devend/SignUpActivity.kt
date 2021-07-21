package de.devEnd.devend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Firebase
        mAuth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        // EditText
        val editText_username: EditText = findViewById(R.id.editText_username)
        val editText_email: EditText = findViewById(R.id.editText_email)
        val editText_password: EditText = findViewById(R.id.editText_password)

        // TextView
        val textView_login: TextView = findViewById(R.id.textView_login)

        // Buttons
        val button_createAccount: Button = findViewById(R.id.button_createAccount)

        // Checkbox
        val checkBox: CheckBox = findViewById(R.id.checkBox)

        /* setOnClickListener */


        textView_login.setOnClickListener{

            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)

        }



        button_createAccount.setOnClickListener {

            val username: String = editText_username.text.toString()
            val email: String = editText_email.text.toString()
            val password: String = editText_password.text.toString()


            if (username.isEmpty()) {
                editText_username.error = "Bitte gebe einen Benutzernamen ein!"
                editText_username.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                editText_email.error = "Bitte gebe eine Email ein!"
                editText_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editText_email.error = "Bitte gebe eine korrekte Email ein!"
                editText_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                editText_password.error = "Bitte gebe eine Passwort ein!"
                editText_password.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                editText_password.error = "Dein Passwort muss mehr als 6 Zeichen haben!"
                editText_password.requestFocus()
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{

                if (it.isSuccessful) {

                    val user: MutableMap<String, Any> = HashMap()
                    user["email"] = email
                    user["uid"] = mAuth.currentUser?.uid.toString()
                    user["username"] = username

                    db.collection("User").document(mAuth.currentUser?.uid.toString()).set(user)

                    Toast.makeText(this, "Account erfolgreich erstellt!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(this, "Account Erstellung fehlgeschlagen!", Toast.LENGTH_SHORT).show()
                }

            }




        }
    }


}