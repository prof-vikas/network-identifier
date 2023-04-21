package com.sipl.networkidentifier.retrofit;

import com.sipl.networkidentifier.retrofit.retryinterceptor.RetryInterceptor;
import com.sipl.networkidentifier.utils.Api_Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitController {
    private RetrofitApi retrofitApiMobNet;
    private RetrofitApi retrofitApiWifiNet;
    private static RetrofitController instance = null;

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new RetryInterceptor(3, 1000))
            .build();
    private RetrofitController() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api_Constants.BASE_API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApiMobNet = retrofit.create(RetrofitApi.class);

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(Api_Constants.BASE_API2)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApiWifiNet = retrofit1.create(RetrofitApi.class);
    }

    public static synchronized RetrofitController getInstance() {
        if (instance == null) {
            instance = new RetrofitController();
        }
        return instance;
    }

    public RetrofitApi getRetrofitApiMobileNet() {
        return retrofitApiMobNet;
    }

    public RetrofitApi getRetrofitApiWifiNet() {
        return retrofitApiWifiNet;
    }
}
