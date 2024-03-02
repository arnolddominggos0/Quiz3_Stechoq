package com.stechoq.quiz3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.stechoq.quiz3.databinding.ActivityLupaPasswordBinding

class LupaPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLupaPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.resetPasswordButton.setOnClickListener {
            val email: String = binding.editTextEmail.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                Toast.makeText(this, "Silahkan masukkan alamat email", Toast.LENGTH_LONG).show()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Cek Emailmu! termasuk folder spam",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                task.exception?.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }
}
