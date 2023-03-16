package com.example.booknameapp
import android.net.Uri
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class NetworkUtils {
    private val LOG_TAG = NetworkUtils::class.java.simpleName
    // Base URL for Books API.
    private val BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?"
    // Parameter for the search string.
    private val QUERY_PARAM = "q"
    // Parameter that limits search results.
    private val MAX_RESULTS = "maxResults"
    // Parameter to filter by print type.
    private val PRINT_TYPE = "printType"
    public fun GetBookInfo(QuerryString:String): String? {
        var urlConnection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        var bookJSONString: String? = null
        try {
            val builtURI: Uri = Uri.parse(BOOK_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, QuerryString)
                .appendQueryParameter(MAX_RESULTS, "10")
                .appendQueryParameter(PRINT_TYPE, "books")
                .build()
            val requestURL = URL(builtURI.toString())
            //conversion of uri object to url is done here coz it is what actually is used to make the api calls
            urlConnection = requestURL.openConnection() as HttpURLConnection
            urlConnection.setRequestMethod("GET")
            urlConnection.connect()
            // Get the InputStream.
            val inputStream: InputStream = urlConnection.getInputStream()
            reader = BufferedReader(InputStreamReader(inputStream))
            val builder = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                builder.append(line)
                builder.append("\n")
            }
            if (builder.length == 0) {
                // Stream was empty. No point in parsing means like further working on this thing
                return null;
            }
            bookJSONString = builder.toString();

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        if (bookJSONString != null) {
            Log.d(LOG_TAG, bookJSONString)
        };
        return bookJSONString
    }

}