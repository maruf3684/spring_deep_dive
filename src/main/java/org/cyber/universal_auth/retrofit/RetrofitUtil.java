package org.cyber.universal_auth.retrofit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import java.util.concurrent.TimeUnit;

public class RetrofitUtil {
    private static Retrofit retrofit = null;

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5000, TimeUnit.MICROSECONDS);
        builder.addInterceptor(new RetrofitInterceptor()); // Example: Add a custom interceptor
        //builder.readTimeout(10, TimeUnit.SECONDS); // Example: 10 seconds
        //builder.writeTimeout(10, TimeUnit.SECONDS); // Example: 10 seconds
        //builder.retryOnConnectionFailure(true); // Enable retries on connection failures
        //builder.sslSocketFactory(sslSocketFactory, trustManager); // Example: Configure SSL socket factory
        //builder.connectionPool(new ConnectionPool(5, 10, TimeUnit.MINUTES)); // Example: Configure connection pool
        //builder.proxy(proxy); // Example: Configure proxyy
        return builder.build();
    }

    public static Retrofit getClient(){
        if(retrofit==null){
            OkHttpClient okHttpClient = createOkHttpClient();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
            retrofit = new Retrofit.Builder()
                    .baseUrl(RetrofitConstant.JsonPlaceholderBaseUrl)
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .client(okHttpClient).build();
            return retrofit;
        }
        return retrofit;
    }
}
