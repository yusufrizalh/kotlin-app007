package id.yusufrizalh.app007.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.yusufrizalh.app007.R
import id.yusufrizalh.app007.extensions.Extensions.toast
import id.yusufrizalh.app007.utils.FirebaseUtils
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnSignOut.setOnClickListener {
            FirebaseUtils.firebaseAuth.signOut()
            startActivity(Intent(this, CreateAccountActivity::class.java))
            toast("Signed Out")
            finish()
        }
    }
}