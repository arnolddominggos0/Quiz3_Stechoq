package com.stechoq.quiz3.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.stechoq.quiz3.MainActivity
import com.stechoq.quiz3.R
import com.stechoq.quiz3.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("FRAGMENT_NAME", "TRANSACTION_FRAGMENT")
            startActivity(intent)
            finish()
        }

        binding.daftarText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, LupaPasswordActivity::class.java))
        }

        emailLogin()
    }

    private fun emailLogin() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val pass = binding.editTextPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Gagal Masuk!", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Tidak boleh kosong.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            binding.editTextEmail.visibility = View.GONE
            binding.buttonLogin.text = getString(R.string.login)
            binding.editTextEmail.setText(user.displayName)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("FRAGMENT_NAME", "TRANSACTION_FRAGMENT")
            Toast.makeText(this, "Selamat datang ${user.email}", Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility = View.GONE
            startActivity(intent)
            finish()
        } else {
            binding.editTextEmail.visibility = View.VISIBLE
            binding.buttonLogin.text = getString(R.string.logout)
            binding.editTextEmail.text = null
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val exitIntent = Intent(Intent.ACTION_MAIN)
        exitIntent.addCategory(Intent.CATEGORY_HOME)
        exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(exitIntent)
    }
}
