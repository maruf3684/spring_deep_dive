package org.cyber.universal_auth.retrofit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

public class RetrofitInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        // Add Bearer token to the request
        Request originalRequest = chain.request();
        Request modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer token123") // Example: Add authorization header
                .build();
        // Proceed with the modified request
        // main call ta aikhane theke e hoi
        Response response = chain.proceed(modifiedRequest);


        // Add a custom header to the response
        Response modifiedResponse = response.newBuilder()
                .header("Custom-Header", "Custom-Value")
                .build();

        //logging response information
        System.out.println("Response code: " + response.code());

        return modifiedResponse;
    }
}
