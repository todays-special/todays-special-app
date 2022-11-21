package com.example.app.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.start.hello
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_new_customer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class New_customer : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var email: String
    lateinit var passwordInput: String
    lateinit var passwordCheck: String
    lateinit var mAlertDialog: AlertDialog


    private lateinit var mPreferences: SharedPreferences

    companion object {
        const val sharedPrefFileName = "name"
    }

    //사용자 이름 설정하기 -> sharedPreference로 저장하기 때문에 로컬파일로 남게됨.
    private fun setName(values: String) {
        val prefs = getSharedPreferences(sharedPrefFileName, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("name", values)
        editor.apply()
    }

    //사용자 이름 가져오기
    private fun getName(key: String): String? {
        val prefs = getSharedPreferences(sharedPrefFileName, Context.MODE_PRIVATE)
        val value = prefs.getString(key, "User${Random(1000).nextInt()}")
        Log.d("name", "$value")
        return value
    }

    suspend fun loading(time: Long) {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.dialog_loading, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setCancelable(false) //외부영역 터치해도 dismiss 안되게
        mAlertDialog = mBuilder.create()

        /** dialog 배경 투명하게 만들기*/
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        mAlertDialog.show()
        delay(time)
        mAlertDialog.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_customer)
        auth = Firebase.auth
        val name = findViewById<EditText>(R.id.editText5)
        //SharedPreference로 처리.


        constraintLayout12.setOnClickListener {
            val signUpID = ID.text.toString()
            val signUpPW = password.text.toString()
            val signUpPWC = password_check.text.toString()

            email = signUpID
            passwordInput = signUpPW
            passwordCheck = signUpPWC



            if (passwordInput != passwordCheck) {
                Toast.makeText(this, "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show()
            } else if (name.text.isNullOrEmpty()) {
                Toast.makeText(this, "이름을 입력하세요", Toast.LENGTH_SHORT).show()
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
                            setName(name.text.toString())
                            callResult.enqueue(object : Callback<JsonArray> {
                                override fun onResponse(
                                    call: Call<JsonArray>,
                                    response: Response<JsonArray>
                                ) {
                                    Log.d("SignIn", "회원가입되었습니다 : ${response.body()}")
                                    Toast.makeText(
                                        baseContext, "회원가입되었습니다",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                                    Log.d("SignIn", "실패 : $t")
                                }
                            })
                            val intent = Intent(this, hello::class.java)
                            CoroutineScope(Dispatchers.Main).launch {
                                loading(2000)
                                startActivity(intent)
                                finish()
                            }
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