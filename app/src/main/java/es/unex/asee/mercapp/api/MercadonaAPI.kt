package es.unex.asee.mercapp.api


import com.google.gson.GsonBuilder
import es.unex.asee.mercapp.data.api.CategoryPageInfo
import es.unex.asee.mercapp.data.api.SuperCategoryPage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private val service: MercadonaAPI by lazy {

        val gson = GsonBuilder().setLenient().create()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://tienda.mercadona.es/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(MercadonaAPI::class.java)

}

fun getNetworkService() = service

interface MercadonaAPI {

    //Supercategorias con sus categorias obtienen de aquí https://tienda.mercadona.es/api/categories/
    @GET("categories/")
    @Headers(
        "Accept-Encoding: gzip, deflate, br",
    "Accept-Language: en-US,en;q=0.9",
    "Connection: keep-alive",
    "Cookie: __mo_ui={\"language\":\"es\"}; _ga=GA1.1.727600926.1704515454; __zlcmid=1JgmOtUaCrvesnF; _ga_VHPZE4NYBL=GS1.1.1704515454.1.1.1704516379.60.0.0; amplitude_id_79df67fe141fc3f96c86626c407a01c1tienda.mercadona.es=eyJkZXZpY2VJZCI6IjRhNDU5Yzc0LWVmYTktNDNlZi1hYzMxLWMyYzI3OTY4MGM4Y1IiLCJ1c2VySWQiOm51bGwsIm9wdE91dCI6ZmFsc2UsInNlc3Npb25JZCI6MTcwNDUxNTQ1Mjk5MiwibGFzdEV2ZW50VGltZSI6MTcwNDUxNjM4MjcyMiwiZXZlbnRJZCI6OCwiaWRlbnRpZnlJZCI6Miwic2VxdWVuY2VOdW1iZXIiOjEwfQ==",
    "Host: tienda.mercadona.es",
    "sec-ch-ua: \"Google Chrome\";v=\"113\", \"Chromium\";v=\"113\", \"Not-A.Brand\";v=\"24\"",
    "sec-ch-ua-mobile: ?1",
    "sec-ch-ua-platform: \"Android\"",
    "Sec-Fetch-Dest: document",
    "Sec-Fetch-Mode: navigate",
    "Sec-Fetch-Site: none",
    "Sec-Fetch-User: ?1",
    "Upgrade-Insecure-Requests: 1",
    "User-Agent: Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Mobile Safari/537.36"
    )
    suspend fun getCategories(): SuperCategoryPage

    //Subcategorias con sus productos obtienen de aquí https://tienda.mercadona.es/api/categories/{categoryid}

    @GET("categories/{categoryId}")
    suspend fun getSubCategories(@Path("categoryId") categoryId: Int): CategoryPageInfo
}

class APIError(message: String, cause: Throwable?) : Throwable(message, cause)