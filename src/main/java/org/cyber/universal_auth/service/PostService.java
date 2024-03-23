package org.cyber.universal_auth.service;

import org.cyber.universal_auth.api.JsonPlaceholderApi;
import org.cyber.universal_auth.dto.Post;
import org.cyber.universal_auth.retrofit.RetrofitUtil;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private Retrofit retrofit;
    private JsonPlaceholderApi jsonPlaceholderApi;

    public  PostService(){
        retrofit = RetrofitUtil.getClient();
        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class); //interface er class banaiye dei
    }
    public List<Post> getAllPost(){
        List<Post> postList = new ArrayList<>();
        Call<List<Post>> postCall= jsonPlaceholderApi.getAllPost();
        try{
            Response<List<Post>> response = postCall.execute();
            if(response.isSuccessful() && response.body() != null){
                postList = response.body();
                return postList;
               // print(postList);
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());;
            throw new RuntimeException();
        }
        return postList;
    }

    public static void print(List<Post> postList){
        for (Post post : postList) {
            System.out.println(post);
        }
    }
}
