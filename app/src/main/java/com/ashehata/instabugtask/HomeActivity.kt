package com.ashehata.instabugtask

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.ashehata.instabugtask.models.HttpErrorType
import com.ashehata.instabugtask.models.RequestModel
import com.ashehata.instabugtask.models.RequestType
import com.ashehata.instabugtask.util.makeApiCall
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import android.net.ConnectivityManager


class HomeActivity : AppCompatActivity() {


    private lateinit var typeGroup: RadioGroup
    private lateinit var getRadio: RadioButton
    private lateinit var postRadio: RadioButton
    private lateinit var sendButton: Button
    private lateinit var urlEt: EditText
    private lateinit var requestBody: EditText

    private lateinit var imageAddHeader: ImageView
    private lateinit var imageAddQuery: ImageView

    private lateinit var headersHostLinear: LinearLayout
    private lateinit var queriesHostLinear: LinearLayout

    private lateinit var headersViewsList: MutableList<View>
    private lateinit var queriesViewsList: MutableList<View>

    private lateinit var executor: Executor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        executor = Executors.newSingleThreadExecutor()
        initViews()
        initLists()
        onAddHeader()
        onAddQuery()
        onSendClick()
    }

    private fun initLists() {
        headersViewsList = mutableListOf()
        queriesViewsList = mutableListOf()
    }

    private fun onAddQuery() {
        imageAddQuery.setOnClickListener {
            val view = LayoutInflater.from(this)
                .inflate(R.layout.key_value_item, queriesHostLinear, false)

            view.tag = queriesViewsList.size.toString()
            Log.i("onAddHeader: Counter", queriesViewsList.size.toString())

            view.findViewById<ImageView>(R.id.iv_remove_item).setOnClickListener {
                // try to remove the header
                if (queriesViewsList.size != 0) {
                    val viewTag = (it.parent as View).tag.toString().toInt()
                    Log.i("onAddHeader: Tag", viewTag.toString())

                    val deletedView = queriesViewsList.find {
                        Log.i("onAddHeader: find", it.tag.toString())
                        return@find it.tag.toString().toInt() == viewTag
                    }
                    Log.i("onAddHeader: find", deletedView?.tag.toString())
                    //Toast.makeText(this, deletedView?.tag.toString(), Toast.LENGTH_SHORT).show()

                    queriesHostLinear.removeView(deletedView)
                    queriesViewsList.remove(deletedView)

                }
            }
            queriesHostLinear.addView(view)
            queriesViewsList.add(view)
        }
    }

    private fun onAddHeader() {
        imageAddHeader.setOnClickListener {
            val view = LayoutInflater.from(this)
                .inflate(R.layout.key_value_item, headersHostLinear, false)

            view.tag = headersViewsList.size.toString()
            Log.i("onAddHeader: Counter", headersViewsList.size.toString())

            view.findViewById<ImageView>(R.id.iv_remove_item).setOnClickListener {
                // try to remove the header
                if (headersViewsList.size != 0) {
                    val viewTag = (it.parent as View).tag.toString().toInt()
                    Log.i("onAddHeader: Tag", viewTag.toString())

                    val deletedView = headersViewsList.find {
                        Log.i("onAddHeader: find", it.tag.toString())
                        return@find it.tag.toString().toInt() == viewTag
                    }
                    Log.i("onAddHeader: find", deletedView?.tag.toString())
                    //Toast.makeText(this, deletedView?.tag.toString(), Toast.LENGTH_SHORT).show()

                    headersHostLinear.removeView(deletedView)
                    headersViewsList.remove(deletedView)

                }
            }
            headersHostLinear.addView(view)
            headersViewsList.add(view)
        }
    }

    private fun onSendClick() {
        sendButton.setOnClickListener {
            // validate url and type request

            /*if (!urlEt.text.toString().isValidURL()) {
                Toast.makeText(this, getString(R.string.empty_url), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (typeGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, getString(R.string.empty_request_type), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            // collect headers data
            val headersMap = collectHeadersData()
            headersMap.map {
                val header = KeyValue(it.key, it.value)
                Toast.makeText(this, header.all(), Toast.LENGTH_SHORT).show()
            }

            // collect quires data
            val queriesMap = collectQueriesData()
            queriesMap.map {
                val header = KeyValue(it.key, it.value)
                Toast.makeText(this, header.all(), Toast.LENGTH_SHORT).show()
            }
            val requestBody = requestBody.text.toString().trim()*/

            // after that try to get data
            val mRequestModel = RequestModel(
                // https://cat-fact.herokuapp.com/facts
                url = "https://dog.ceo/api/breeds/image/random",
                requestType = RequestType.GET,
                requestBody = ""
            )
            getApiData(mRequestModel)

        }
    }

    private fun getApiData(mRequestModel: RequestModel) {
        if (isNetworkConnected()) {


            executor.execute {
                makeApiCall(
                    requestModel = mRequestModel,
                    onSuccess = {
                        runOnUiThread {
                            Toast.makeText(this, it.responseBody.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    onFailure = {
                        runOnUiThread {
                            val message = when (it.error) {
                                HttpErrorType.BadGateway -> "Bad geteway"
                                HttpErrorType.BadRequest -> "BadRequest"
                                HttpErrorType.DataInvalid -> "DataInvalid"
                                HttpErrorType.Forbidden -> "Forbidden "
                                HttpErrorType.InternalServerError -> "InternalServerError "
                                HttpErrorType.NotAuthorized -> "NotAuthorized "
                                HttpErrorType.NotFound -> "NotFound "
                                HttpErrorType.Unknown -> "Unknown "
                                null -> "Null"
                                else -> "Error"
                            }
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        } else {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show()

        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    private fun collectHeadersData(): MutableMap<String, String> {
        val headers = mutableMapOf<String, String>()
        headersViewsList.map {
            val linearView = it as LinearLayout
            val key = (linearView.getChildAt(0) as EditText).text.toString()
            val value = (linearView.getChildAt(1) as EditText).text.toString()
            headers.put(key, value)
        }
        return headers
    }


    private fun collectQueriesData(): MutableMap<String, String> {
        val headers = mutableMapOf<String, String>()
        queriesViewsList.map {
            val linearView = it as LinearLayout
            val key = (linearView.getChildAt(0) as EditText).text.toString()
            val value = (linearView.getChildAt(1) as EditText).text.toString()
            headers.put(key, value)
        }
        return headers
    }

    private fun initViews() {
        sendButton = findViewById(R.id.btn_send)
        typeGroup = findViewById(R.id.type_group)
        getRadio = findViewById(R.id.rb_get)
        postRadio = findViewById(R.id.rb_post)
        urlEt = findViewById(R.id.et_url)
        requestBody = findViewById(R.id.et_request_body)

        imageAddHeader = findViewById(R.id.iv_add_header)
        imageAddQuery = findViewById(R.id.iv_add_query)

        headersHostLinear = findViewById(R.id.linear_headers_parent_host)
        queriesHostLinear = findViewById(R.id.linear_queries_parent_host)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}