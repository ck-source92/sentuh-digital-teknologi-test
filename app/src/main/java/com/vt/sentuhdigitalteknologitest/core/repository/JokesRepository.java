package com.vt.sentuhdigitalteknologitest.core.repository;

import com.vt.sentuhdigitalteknologitest.core.api.ApiService;
import com.vt.sentuhdigitalteknologitest.core.model.SearchDummyResponse;

import javax.inject.Inject;

import retrofit2.Call;

public class JokesRepository {
    private final ApiService apiService;

    @Inject
    public JokesRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Call<SearchDummyResponse> getListBySearch(String query) {
        try {
            return apiService.getListBySearch(query);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
