package com.monke.ui;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CompoundButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class TextChipAdapter {

    public interface OnChipSelectedListener {
        void onCheckedChangeListener(int index);
    }

    private ChipGroup parent;
    private LayoutInflater inflater;
    private OnChipSelectedListener onChipSelectedListener;

    public TextChipAdapter(ChipGroup parent, LayoutInflater inflater) {
        this.parent = parent;
        this.inflater = inflater;
    }

    public void setOnChipSelectedListener(OnChipSelectedListener onChipSelectedListener) {
        this.onChipSelectedListener = onChipSelectedListener;
    }

    public void bind(int zeroQuantityText, int pluralsId, int count, int layoutId) {
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                bindChip(getResources().getString(zeroQuantityText), layoutId, i);
            } else {
                bindChip(getResources().getQuantityString(pluralsId, i, i), layoutId, i);
            }
        }
    }

    private Resources getResources() {
        return parent.getContext().getResources();
    }

    private Button bindChip(String text, int layoutId, int index) {
        Button chip = (Button) inflater.inflate(layoutId, parent, false);
        chip.setText(text);
        if (onChipSelectedListener != null) {
            chip.setOnClickListener(v -> {
                onChipSelectedListener.onCheckedChangeListener(index);
            });
        }
        parent.addView(chip);

        return chip;
    }
}
