package com.monke.ui;

import android.view.LayoutInflater;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.monke.identity.Identity;

import java.util.List;

public class IdentityChipAdapter {

    private ChipGroup parent;
    private LayoutInflater inflater;

    public IdentityChipAdapter(ChipGroup parent, LayoutInflater inflater) {
        this.parent = parent;
        this.inflater = inflater;
    }


    private void bindChip(Identity identity) {
        Chip chip = (Chip) inflater.inflate(R.layout.item_identity_chip, parent, false);
        chip.setText(identity.getName());
        parent.addView(chip);
    }

    public void bind(List<Identity> identities) {
        for (Identity identity: identities) {
            bindChip(identity);
        }
    }
}
