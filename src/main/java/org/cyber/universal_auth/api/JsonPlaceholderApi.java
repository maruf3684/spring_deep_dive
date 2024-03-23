package org.cyber.universal_auth.api;

import org.cyber.universal_auth.dto.Post;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface JsonPlaceholderApi {
    @GET(value = "/posts")
    Call<List<Post>> getAllPost();
}
