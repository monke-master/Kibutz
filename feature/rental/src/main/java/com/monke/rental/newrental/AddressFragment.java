package com.monke.rental.newrental;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.monke.rental.R;
import com.monke.rental.databinding.FragmentAddressBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapWindow;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;

import javax.inject.Inject;

public class AddressFragment extends Fragment {

    @Inject
    public AddressViewModel.Factory factory;

    private AddressViewModel mViewModel;
    private FragmentAddressBinding mBinding;
    private Map map;
    private MapWindow mapWindow;

    private CameraListener cameraListener = new CameraListener() {
        @Override
        public void onCameraPositionChanged(@NonNull Map map,
                                            @NonNull CameraPosition cameraPosition,
                                            @NonNull CameraUpdateReason cameraUpdateReason,
                                            boolean b) {
            movePointer();
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
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
//        bottomSheetDialog.setContentView(R.layout.layout_address_bottom_sheet);
//        bottomSheetDialog.setCancelable(false);
//        bottomSheetDialog.show();
//        NavHostFragment.findNavController(this).navigate(R.id.action_addressFragment_to_roomsFragment);
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
        movePointer();

        observeAddress();

    }


    private void movePointer() {
        var centerX = mapWindow.width() / 2f;
        var centerY = mapWindow.height() / 2f;
        var centerPoint = new ScreenPoint(centerX, centerY);
        var worldPoint = mapWindow.screenToWorld(centerPoint);

        if (pointer == null) {
            pointer = map.getMapObjects().addPlacemark(
                    worldPoint,
                    ImageProvider.fromResource(getContext(), com.monke.ui.R.drawable.ic_map_pointer));
        } else {
            pointer.setGeometry(worldPoint);
        }
        mViewModel.stopSearchingProcess();
        mViewModel.getAddress(worldPoint);
    }

    private void observeAddress() {
        mViewModel.address.observe(getViewLifecycleOwner(), address -> {
            Toast.makeText(getContext(), address, Toast.LENGTH_SHORT).show();
        });
    }


}