package org.ligi.ipfsdroid

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.*
import java.io.File
import javax.inject.Inject

class IPFSAPI {

    class AddResult(val Name:String,val Hash:String)

    @Inject
    lateinit var client: OkHttpClient
    lateinit var adapter:JsonAdapter<AddResult>

    init {
        App.component().inject(this)
        val build = Moshi.Builder().build()
        adapter = build.adapter(AddResult::class.java)
    }

    private val base_url = "http://127.0.0.1:5001/api/v0/"

    fun addFile(file: File): AddResult? {

        return addGeneric {
            val create = RequestBody.create(MediaType.parse("application/octet-stream"), file)
            it.addFormDataPart("file","file",create)
        }

    }

    fun addString(file: String): AddResult? {

        return addGeneric {
            val create = RequestBody.create(MediaType.parse("application/octet-stream"), file)
            it.addFormDataPart("string","string",create)
        }

    }
    fun addGeneric(f: (MultipartBody.Builder) -> Any): AddResult? {

        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        f(builder)
        val requestBody = builder.build();

        val request = Request.Builder()
                .url("${base_url}add?stream-channels=true&progress=false")
                .post(requestBody)
                .build();

        try {
            val response = client.newCall(request).execute();
            return adapter.fromJson(response.body().string())
        } catch ( e: Exception ) {
            return null
        }

    }
}