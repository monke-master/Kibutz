package com.monke.ui;

import android.view.LayoutInflater;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.monke.identity.Identity;

import java.util.List;

public class IdentityChipAdapter {

    public interface OnChipSelectedListener {
        void onCheckedChangeListener(Identity identity, boolean isChecked);
    }

    private ChipGroup parent;
    private LayoutInflater inflater;

    public IdentityChipAdapter(ChipGroup parent, LayoutInflater inflater) {
        this.parent = parent;
        this.inflater = inflater;
    }

    private void bindChip(Identity identity, OnChipSelectedListener listener) {
        Chip chip = bindChip(identity, parent.getChildCount(), true);
        chip.setOnCheckedChangeListener((buttonView, isChecked) ->
                listener.onCheckedChangeListener(identity, isChecked));
    }

    private Chip bindChip(Identity identity, int position, boolean isCheckable) {
        Chip chip = (Chip) inflater.inflate(R.layout.item_identity_chip, parent, false);
        chip.setText(identity.getName());
        chip.setCheckable(isCheckable);
        parent.addView(chip, position);
        return chip;
    }

    public void bind(List<Identity> identities, OnChipSelectedListener listener) {
        for (Identity identity: identities) {
            bindChip(identity, listener);
        }
    }

    public void bind(List<Identity> identities, boolean isCheckable) {
        for (Identity identity: identities) {
            bindChip(identity,parent.getChildCount() - 1, isCheckable );
        }
    }

    public void bindFromLast(List<Identity> identities, boolean isCheckable) {
        for (Identity identity: identities) {
            bindChip(identity, parent.getChildCount() - 1, isCheckable);
        }
    }
}
