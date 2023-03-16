package com.example.booknameapp
import android.os.AsyncTask
import android.widget.TextView
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONArray
import java.lang.Exception


class FetchBook(var mtitletext:TextView,var mauthortext:TextView,var querryString: String):AsyncTask<String,Void,String>() {
    override fun doInBackground(vararg p0: String?): String {
        val obj=NetworkUtils()
        return obj.GetBookInfo(querryString).toString()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        try {
            //...
            val jsonObject = JSONObject(result)
            val itemsArray = jsonObject.getJSONArray("items")
            var i = 0
            var title: String? = null
            var authors: String? = null
            while (i < itemsArray.length() &&
                authors == null && title == null
            ) {
                // Get the current item information.
                val book = itemsArray.getJSONObject(i)
                val volumeInfo = book.getJSONObject("volumeInfo")

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title")
                    authors = volumeInfo.getString("authors")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                // Move to the next item.
                i++
            }
            if (title != null && authors != null) {
                mtitletext.setText(title);
                mauthortext.setText(authors);
            }
            else{
                mtitletext.setText("No Result Found")
                mauthortext.setText("")
            }

        } catch (e: JSONException) {
            mtitletext.setText(R.string.no_results);
            mauthortext.setText("");
            e.printStackTrace()
        }
    }
}