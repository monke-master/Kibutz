package com.monke.rental;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigation.Navigator;
import com.example.navigation.ResponsesNavigationContract;
import com.example.navigation.UserNavigationContract;
import com.monke.rental.databinding.FragmentResponsesBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.rental.ResponseRWAdapter;
import com.monke.ui.rental.ResponseViewHolder;
import com.monke.user.User;

import java.util.ArrayList;

import javax.inject.Inject;

public class ResponsesFragment extends Fragment {

    @Inject
    public ResponsesViewModel.Factory factory;

    private ResponsesViewModel mViewModel;
    private FragmentResponsesBinding mBinding;
    private ResponseRWAdapter mResponseRWAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(ResponsesViewModel.class);
        getArgs();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentResponsesBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
        initRecyclerView();
        observeResponses();
    }

    private void getArgs() {
        String rentalId = getArguments().getString(ResponsesNavigationContract.RENTAL_ID_KEY);
        mViewModel.init(rentalId);
    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
    }



    private void initRecyclerView() {
        RecyclerView recyclerView = mBinding.listResponses;
        mResponseRWAdapter = new ResponseRWAdapter();
        mResponseRWAdapter.setResponseInteractor(new ResponseViewHolder.ResponseInteractor() {
            @Override
            public void onDislike(Response response) {
                mViewModel.changeResponseStatus(response, Response.Status.DISLIKED);
            }

            @Override
            public void onLike(Response response) {
                mViewModel.changeResponseStatus(response, Response.Status.LIKED);
            }

            @Override
            public void onAddFlatmate(Response response) {
                mViewModel.changeResponseStatus(response, Response.Status.FLATMATE);
            }

            @Override
            public void onRemoveFlatmate(Response response) {
                mViewModel.changeResponseStatus(response, Response.Status.LIKED);
            }

            @Override
            public void onClicked(User user) {
                NavHostFragment
                        .findNavController(ResponsesFragment.this)
                        .navigate(UserNavigationContract.createDeepLinkRequest(user.getId()), Navigator.DEFAULT_OPTIONS.build());
            }
        });
        recyclerView.setAdapter(mResponseRWAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void observeResponses() {
        mViewModel.responses.observe(getViewLifecycleOwner(), responses -> {
            mResponseRWAdapter.setResponses(new ArrayList<>(responses));
        });
    }
}