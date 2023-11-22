package com.vt.sentuhdigitalteknologitest.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vt.sentuhdigitalteknologitest.core.model.SearchDummyResponse;
import com.vt.sentuhdigitalteknologitest.databinding.FragmentListJokesBinding;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListJokesFragment extends Fragment implements SearchView.OnQueryTextListener {
    private FragmentListJokesBinding _binding;

    private FragmentListJokesBinding getBinding() {
        return _binding;
    }

    ListJokesViewModel listJokesViewModel;
    private ListJokesAdapter listJokesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _binding = FragmentListJokesBinding.inflate(inflater, container, false);
        return getBinding().getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listJokesAdapter = new ListJokesAdapter();
        listJokesViewModel = new ViewModelProvider(requireActivity()).get(ListJokesViewModel.class);
        getBinding().listJokes.setAdapter(listJokesAdapter);
        getBinding().listJokes.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        getBinding().searchJokes.setOnQueryTextListener(this);
        observerView();
    }

    private void observerView() {
        listJokesViewModel.loading.observe(getViewLifecycleOwner(), this::showLoading);
        listJokesViewModel.searchEmitter.observe(getViewLifecycleOwner(), data -> {
            if (Objects.requireNonNull(data.getResult()).isEmpty()) {
                Toast.makeText(requireActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
            }
            getBinding().findDataFirst.setVisibility(View.GONE);
            setupListAdapter(data);
        });
        listJokesViewModel.errorMessage.observe(getViewLifecycleOwner(), event -> {
            String eventMessage = event.getContentIfNotHandled();
            if (eventMessage != null) {
                Toast.makeText(requireActivity(), eventMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupListAdapter(SearchDummyResponse searchResponse) {
        listJokesAdapter.submitList(searchResponse.getResult());
    }

    private void showLoading(Boolean state) {
        if (state) {
            getBinding().progressBar.setVisibility(View.VISIBLE);
        } else {
            getBinding().progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listJokesViewModel.getListBySearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _binding = null;
    }

}