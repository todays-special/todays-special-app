package com.example.app.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_new_customer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class New_customer : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var email: String
    lateinit var passwordInput: String
    lateinit var passwordCheck: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_customer)
        auth = Firebase.auth


        constraintLayout12.setOnClickListener {
            val signUpID = ID.text.toString()
            val signUpPW = password.text.toString()
            val signUpPWC = password_check.text.toString()

            email = signUpID
            passwordInput = signUpPW
            passwordCheck = signUpPWC

            if (passwordInput != passwordCheck) {
                Toast.makeText(this, "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show()
            } else {
                //input == check 일 때
                auth.createUserWithEmailAndPassword(email, passwordInput)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success")
                            val user = auth.currentUser

                            var gson = GsonBuilder().setLenient().create()
                            val retrofit = Retrofit.Builder()
                                .baseUrl("http://jaeryurp.duckdns.org:40131/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build()
                            val api = retrofit.create(createAPI::class.java)
                            val callResult = api.createUser(user!!.uid)

                            callResult.enqueue(object : Callback<JsonArray> {
                                override fun onResponse(
                                    call: Call<JsonArray>,
                                    response: Response<JsonArray>
                                ) {
                                    Log.d("SignIn", "회원가입되었습니다 : ${response.body()}")
                                }

                                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                                    Log.d("SignIn", "실패 : $t")
                                }
                            })

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                this, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}