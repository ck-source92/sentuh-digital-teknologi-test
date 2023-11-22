package com.vt.sentuhdigitalteknologitest.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vt.sentuhdigitalteknologitest.core.model.SearchDummyResponse;
import com.vt.sentuhdigitalteknologitest.core.repository.JokesRepository;
import com.vt.sentuhdigitalteknologitest.utils.Event;

import java.util.Objects;
import java.util.concurrent.CancellationException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class ListJokesViewModel extends ViewModel {

    private final JokesRepository jokesRepository;

    @Inject
    public ListJokesViewModel(JokesRepository jokesRepository) {
        this.jokesRepository = jokesRepository;
    }

    private final MutableLiveData<SearchDummyResponse> _searchEmitter = new MutableLiveData<>();
    public LiveData<SearchDummyResponse> searchEmitter = _searchEmitter;

    private final MutableLiveData<Boolean> _loading = new MutableLiveData<>();
    public LiveData<Boolean> loading = _loading;

    private final MutableLiveData<Event<String>> _errorMessage = new MutableLiveData<>();
    public LiveData<Event<String>> errorMessage = _errorMessage;

    public void getListBySearch(String query) {
        Call<SearchDummyResponse> call = jokesRepository.getListBySearch(query);
        _loading.postValue(true);
        try {
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<SearchDummyResponse> call, @NonNull Response<SearchDummyResponse> response) {
                    _loading.postValue(false);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            _searchEmitter.postValue(response.body());
                        }
                    } else {
                        _errorMessage.postValue(new Event<>("Cari setidaknya 3 Huruf"));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SearchDummyResponse> call, @NonNull Throwable t) {
                    _loading.postValue(false);
                    Log.e("JOKES", "OH " + Objects.requireNonNull(t.getMessage()));
                    Log.e("JOKES", "OH 2 " + t.getMessage());
                    Log.e("JOKES", "OH 3 " + call.request());
                    _errorMessage.postValue(new Event<>(t.getMessage()));
                }
            });
        } catch (AssertionError | CancellationException e) {
            _loading.postValue(false);
            _errorMessage.postValue(new Event<>(e.getMessage()));
        }
    }

}
