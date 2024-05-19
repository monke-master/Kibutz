package com.monke.rental.newrental;

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

import com.example.navigation.SearchAddressNavigationContract;
import com.monke.rental.R;
import com.monke.rental.databinding.FragmentAddressBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.rental.AddressBottomSheetDialog;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapWindow;
import com.yandex.mapkit.map.PlacemarkMapObject;

import javax.inject.Inject;

public class AddressFragment extends Fragment implements AddressBottomSheetDialog.AddressDialogInteractor {

    @Inject
    public AddressViewModel.Factory factory;

    private AddressViewModel mViewModel;
    private FragmentAddressBinding mBinding;
    private Map map;
    private MapWindow mapWindow;
    private AddressBottomSheetDialog mDialog;

    private CameraListener cameraListener = new CameraListener() {
        @Override
        public void onCameraPositionChanged(@NonNull Map map,
                                            @NonNull CameraPosition cameraPosition,
                                            @NonNull CameraUpdateReason cameraUpdateReason,
                                            boolean b) {
            getAddress();
        }
    };

    private PlacemarkMapObject pointer;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(AddressViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAddressBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mBinding.mapview.onStart();

        mapWindow = mBinding.mapview.getMapWindow();
        map = mBinding.mapview.getMapWindow().getMap();
        map.addCameraListener(cameraListener);
        getAddress();

        observeAddress();
    }


    private void getAddress() {
        var centerX = mapWindow.width() / 2f;
        var centerY = mapWindow.height() / 2f + mBinding.imgPointer.getHeight() / 2;
        var centerPoint = new ScreenPoint(centerX, centerY);
        var worldPoint = mapWindow.screenToWorld(centerPoint);

        mViewModel.stopSearchingProcess();
        mViewModel.getAddress(worldPoint);
    }

    private void observeAddress() {
        mViewModel.address.observe(getViewLifecycleOwner(), address -> {
            showAddressBottomSheet(address);
        });
    }

    private void showAddressBottomSheet(String address) {
        if (mDialog != null) {
            mDialog.dismiss();
        }

        mDialog = AddressBottomSheetDialog.newInstance(address);
        mDialog.show(getChildFragmentManager(), AddressBottomSheetDialog.TAG);
    }

    @Override
    public void onNextButtonClicked() {
        mViewModel.saveAddress();
        NavHostFragment
                .findNavController(this)
                .navigate(R.id.action_addressFragment_to_roomsFragment);
    }

    @Override
    public void onAddressEditTextClicked() {
        Bundle bundle = new Bundle();
        bundle.putString(SearchAddressNavigationContract.ADDRESS_KEY, mViewModel.address.getValue());
        NavHostFragment
                .findNavController(this)
                .navigate(R.id.action_addressFragment_to_searchAddressFragment, bundle);
    }
}