package com.example.booknameapp
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.net.ConnectivityManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService

class MainActivity : AppCompatActivity() {
    private var mBookInput:EditText?=null
    private var mTitleDisplay:TextView?=null
    private  var mAuthorDisplay:TextView?=null
    private var mSearhBooks:Button?=null

    //variables to establish the connection between UI and code:

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main)
        mBookInput = findViewById(R.id.booksinput_id)
        mTitleDisplay = findViewById(R.id.bookname_id)
        mAuthorDisplay = findViewById(R.id.author_id)
        mSearhBooks = findViewById(R.id.button_id)

        //all the view variables being initialised:
        val inputstring: String = mBookInput?.text.toString()
        mSearhBooks?.setOnClickListener {
            //implement the search method here:
            var QuerryString: String = mBookInput?.text.toString()
            val view: View? = this.currentFocus

            // on below line checking if view is not null.
            if (view != null) {
                // on below line we are creating a variable
                // for input manager and initializing it.
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

                // on below line hiding our keyboard.
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
                FetchBook(mTitleDisplay!!, mAuthorDisplay!!, QuerryString).execute(QuerryString)
                mTitleDisplay?.setText("Loading")
                mAuthorDisplay?.setText("Loading")
            }

        }
    }
}