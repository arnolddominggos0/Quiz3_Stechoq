package com.stechoq.quiz3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.stechoq.quiz3.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonRegister.setOnClickListener {
            val email = binding.editTextEmailRegister.text.toString()
            val pass = binding.editTextPasswordRegister.text.toString()
            val confirmPass = binding.editTextConfirmPassword.text.toString()

            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                    if (pass == confirmPass) {
                        signUpUser(email, pass)
                    } else {
                        Toast.makeText(this, "Kata Sandi Tidak Sesuai", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Email Tidak Valid atau Kosong", Toast.LENGTH_LONG).show()
            }
        }

        binding.masukText.setOnClickListener {
            finish()
        }
    }

    private fun signUpUser(email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    Toast.makeText(this, "Pendaftaran Berhasil", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                    startActivity(intent)
                    finish()
                } else {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Pendaftaran gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
