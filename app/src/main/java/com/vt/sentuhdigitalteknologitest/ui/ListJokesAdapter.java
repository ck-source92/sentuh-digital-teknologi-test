package com.vt.sentuhdigitalteknologitest.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.vt.sentuhdigitalteknologitest.R;
import com.vt.sentuhdigitalteknologitest.core.model.ResultSearchItem;
import com.vt.sentuhdigitalteknologitest.databinding.ItemJokesBinding;

import java.util.Objects;

public class ListJokesAdapter extends ListAdapter<ResultSearchItem, ListJokesAdapter.ListViewHolder> {
    static Integer lastPosition = -1;
    static Integer selectedItem = -1;

    protected ListJokesAdapter() {
        super(DIFF_CALLBACK);
    }


    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final ItemJokesBinding binding;

        ListViewHolder(ItemJokesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("NotifyDataSetChanged")
        void bindTo(ResultSearchItem data) {
            binding.title.setText(data.getId());
            binding.detail.setText(data.getValue());
            if (selectedItem == getBindingAdapterPosition()) {
                binding.radioButton.setChecked(true);
                binding.container.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_item_selected));
            } else {
                binding.radioButton.setChecked(false);
                binding.container.setBackgroundColor(Color.WHITE);
            }
            setAnimation(itemView, getBindingAdapterPosition());
            itemView.setOnClickListener(view -> {
                selectedItem = getBindingAdapterPosition();
                notifyDataSetChanged();
            });
        }

    }

    private void setAnimation(View viewToAnimate, Integer position) {
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(550);
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemJokesBinding binding = ItemJokesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ResultSearchItem data = getItem(position);
        holder.bindTo(data);
    }

    public static final DiffUtil.ItemCallback<ResultSearchItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(ResultSearchItem oldItem, ResultSearchItem newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(ResultSearchItem oldItem, @NonNull ResultSearchItem newItem) {
            return oldItem.equals(newItem);
        }
    };

}

