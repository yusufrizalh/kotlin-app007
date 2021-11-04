package id.yusufrizalh.app007.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.auth.FirebaseUser
import id.yusufrizalh.app007.R
import id.yusufrizalh.app007.extensions.Extensions.toast
import id.yusufrizalh.app007.utils.FirebaseUtils
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class CreateAccountActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        createAccountInputsArray = arrayOf(etEmail, etPassword, etConfirmPassword)

        btnCreateAccount.setOnClickListener {
            signIn()
        }

        btnSignIn2.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            toast("Please SignIn into your account!")
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = FirebaseUtils.firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, HomeActivity::class.java))
            toast("Welcome here")
        }
    }

    private fun signIn() {
        if (identicalPassword()) {
            userEmail = etEmail.text.toString().trim()
            userPassword = etPassword.text.toString().trim()

            /*create user via firebase*/
            FirebaseUtils.firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        toast("Created account is successful!")
                        sendEmailVerification()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        toast("Failed to authenticate user!")
                    }
                }
        }
    }

    private fun sendEmailVerification() {
        FirebaseUtils.firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful){
                    toast("Email verification is sent to $userEmail")
                }
            }
        }
    }

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() && etPassword.text.toString().trim() == etConfirmPassword.text.toString().trim()){
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required!"
                }
            }
        } else {
            toast("Passwords are not matching!")
        }
        return identical
    }

    private fun notEmpty(): Boolean = etEmail.text.toString().trim().isNotEmpty() &&
            etPassword.text.toString().trim().isNotEmpty() &&
            etConfirmPassword.text.toString().trim().isNotEmpty()
}