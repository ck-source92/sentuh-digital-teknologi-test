package com.vt.sentuhdigitalteknologitest.core.api;

import com.vt.sentuhdigitalteknologitest.core.model.SearchDummyResponse;
import com.vt.sentuhdigitalteknologitest.core.model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("jokes/search")
    public Call<SearchDummyResponse> getListBySearch(
            @Query("query") String query
    );
}
