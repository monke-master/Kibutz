package com.monke.rental;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.navigation.ContactsNavigationContract;
import com.example.navigation.PhotoNavigationContract;
import com.example.navigation.RentalNavigationContract;
import com.example.navigation.ResponsesNavigationContract;
import com.example.navigation.UserNavigationContract;
import com.monke.rental.databinding.FragmentRentalBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.photo.PhotoPagerAdapter;
import com.monke.ui.rental.RealtyUiHelpers;
import com.monke.ui.user.FlatmateRWAdapter;
import com.monke.ui.rental.RealtyDetailRWAdapter;
import com.monke.utils.StringsHelper;

import javax.inject.Inject;

public class RentalFragment extends Fragment {

    private RentalViewModel mViewModel;
    private FragmentRentalBinding mBinding;
    private FlatmateRWAdapter flatmateRWAdapter;
    private RealtyDetailRWAdapter realtyDetailRWAdapter;

    @Inject
    public RentalViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(RentalViewModel.class);
        getArgs();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRentalBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
        initFlatmateAdapter();
        initDetailsAdapter();
        observeRental();
        observeFlatmates();
        initRespondBtn();
        observeResponseStatus();
    }

    private void getArgs() {
        String rentalId = getArguments().getString(RentalNavigationContract.RENTAL_ID_KEY);
        mViewModel.setRentalId(rentalId);

    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigateUp();
        });
    }

    private void initResponsesInfo() {
        if (mViewModel.userIfAuthor()) {
            mBinding.responses.btnContact.setVisibility(View.GONE);
            mBinding.responses.txtStatus.setVisibility(View.GONE);
            mBinding.responses.btnResponses.setVisibility(View.VISIBLE);
            mBinding.responses.btnResponses.setOnClickListener(v -> navigateToResponses());
            mBinding.btnRespond.setVisibility(View.GONE);
        } else {
            mBinding.responses.btnResponses.setVisibility(View.GONE);
        }
    }

    private void navigateToResponses() {
        Bundle bundle = new Bundle();
        bundle.putString(
                ResponsesNavigationContract.RENTAL_ID_KEY,
                mViewModel.rental.getValue().getId());
        NavHostFragment
                .findNavController(this)
                .navigate(R.id.action_rentalFragment_to_responsesFragment, bundle);
    }

    private void navigateToContacts() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(
                ContactsNavigationContract.CONTACTS_KEY,
                new ContactsModel(mViewModel.rental.getValue().getContacts()));
        NavHostFragment
                .findNavController(this)
                .navigate(R.id.action_rentalFragment_to_userContactsFragment, bundle);
    }

    private void initFlatmateAdapter() {
        flatmateRWAdapter = new FlatmateRWAdapter();
        var recyclerView = mBinding.flatmates.listFlatmates;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(flatmateRWAdapter);
        flatmateRWAdapter.setOnItemClickedListener(user -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(UserNavigationContract.createDeepLinkRequest(user.getId()));
        });
    }

    private void initDetailsAdapter() {
        realtyDetailRWAdapter = new RealtyDetailRWAdapter();
        var recyclerView = mBinding.details.listDetails;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(realtyDetailRWAdapter);
    }

    private void observeRental() {
        mViewModel.rental.observe(getViewLifecycleOwner(), rental -> {
            initResponsesInfo();

            PhotoPagerAdapter photoPagerAdapter = new PhotoPagerAdapter(
                    rental.getPhotos(),
                    getContext(),
                    com.monke.ui.R.layout.item_rental_photo
            );
            photoPagerAdapter.setOnPhotoClickedListener(uri -> {
                NavHostFragment
                        .findNavController(this)
                        .navigate(PhotoNavigationContract.createDeepLinkRequest(uri));
            });
            mBinding.image.setAdapter(photoPagerAdapter);
            mBinding.txtPrice.setText(getString(com.monke.ui.R.string.price_info, rental.getPrice()));
            mBinding.txtAddress.setText(rental.getRealty().getAddress());
            mBinding.txtDescription.setText(rental.getDescription());
            var info = mBinding.info;
            info.txtRoomCount.setText(
                    getResources().getQuantityString(
                            com.monke.ui.R.plurals.rooms,
                            rental.getRealty().getRoomsCount(),
                            rental.getRealty().getRoomsCount())
            );
            info.txtType.setText(com.monke.ui.R.string.flat);
            info.txtArea.setText(getString(
                    com.monke.ui.R.string.area_info,
                    StringsHelper.formatFloat(rental.getRealty().getArea())));
            if (rental.getRealty() instanceof Flat) {
                bindFlatInfo((Flat) rental.getRealty());
            }


        });
    }

    private void bindFlatInfo(Flat flat) {
        mBinding.info.txtFloor.setText(
                getString(com.monke.ui.R.string.floors_info, flat.getFloor(), flat.getFloorsCount())
        );

        realtyDetailRWAdapter.setDetailList(RealtyUiHelpers.getFlatRealtyDetails(getContext(), flat));
    }

    private void observeFlatmates() {
        mViewModel.flatmates.observe(getViewLifecycleOwner(), users -> {
            flatmateRWAdapter.setUsersList(users);
        });
    }

    private void initRespondBtn() {
        mBinding.btnRespond.setOnClickListener(v -> {
            mViewModel.respondToRental();
            v.setVisibility(View.GONE);
            mBinding.responses.txtStatus.setText(getString(com.monke.ui.R.string.response_status_hanging));
            mBinding.responses.txtStatus.setVisibility(View.VISIBLE);
        });
    }

    private void observeResponseStatus() {
        mViewModel.responseStatus.observe(getViewLifecycleOwner(), status -> {
            if (status == null) return;
            String responseStatus = "";
            switch (status) {
                case HANGING, DISLIKED -> responseStatus = getString(com.monke.ui.R.string.response_status_hanging);
                case LIKED ->  {
                    responseStatus = getString(com.monke.ui.R.string.response_status_like);
                    mBinding.responses.btnContact.setVisibility(View.VISIBLE);
                }
                case FLATMATE -> {
                    responseStatus = getString(com.monke.ui.R.string.response_status_flatmate);
                    mBinding.responses.btnContact.setVisibility(View.VISIBLE);
                }
            }
            mBinding.responses.txtStatus.setText(responseStatus);
            mBinding.responses.txtStatus.setVisibility(View.VISIBLE);
            mBinding.responses.btnContact.setOnClickListener(v -> navigateToContacts());
            mBinding.btnRespond.setVisibility(View.GONE);
        });
    }
}