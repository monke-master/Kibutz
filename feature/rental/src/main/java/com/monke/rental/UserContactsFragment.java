package com.monke.rental;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigation.ContactsNavigationContract;
import com.monke.rental.databinding.FragmentUserContactsBinding;
import com.monke.ui.ContactsViewController;


public class UserContactsFragment extends Fragment {

    private FragmentUserContactsBinding mBinding;
    private Contacts contacts;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getArgs();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentUserContactsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        var contactsViewController = new ContactsViewController(getActivity(), mBinding.getRoot(), contacts);
        contactsViewController.initViews();
        initToolbar();
    }

    private void getArgs() {
        contacts = ((ContactsModel)getArguments().getParcelable(ContactsNavigationContract.CONTACTS_KEY))
                .getContacts();
    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
    }
}