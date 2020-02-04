package com.sasquer.listnotes.ui.notedetails;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.sasquer.listnotes.base.BaseViewHolder;
import com.sasquer.listnotes.data.local.db.entity.NoteCheckItem;
import com.sasquer.listnotes.databinding.ItemNoteCheckItemBinding;
import com.sasquer.listnotes.databinding.ItemNoteEmptyItemBinding;

import java.util.ArrayList;
import java.util.List;

public class CheckItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int TYPE_EMPTY_ITEM = 0;
    public static final int TYPE_ITEM = 1;

    private List<NoteCheckItem> mList = new ArrayList<>();
    private ListUpdateCallback listUpdateCallback = new ListUpdateCallback() {
        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count, Object payload) {
            notifyItemRangeChanged(position, count);
        }
    };

    public CheckItemAdapter() {
        NoteCheckItem item = new NoteCheckItem();
        item.setEmpty(true);
        mList.add(item);
        notifyDataSetChanged();
    }

    public void addItem(NoteCheckItem item) {
        NoteCheckItem emptyItem = mList.get(0);
        mList.add(item);
        mList.remove(0);
        notifyDataSetChanged();
    }

    public void setItems(List<NoteCheckItem> list) {
//        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new UserListDiffUtil(mList, list), true);
//        result.dispatchUpdatesTo(listUpdateCallback);
//        mList.clear();
//        mList.addAll(list);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_EMPTY_ITEM:
                ItemNoteEmptyItemBinding emptyViewHolder = ItemNoteEmptyItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyItemViewHolder(emptyViewHolder);
            case TYPE_ITEM:
            default:
                ItemNoteCheckItemBinding userImgListViewHolder = ItemNoteCheckItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new CheckItemViewHolder(userImgListViewHolder);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        NoteCheckItem item = mList.get(position);
        if (item.isEmpty()) {
            return TYPE_EMPTY_ITEM;
        } else
            return TYPE_ITEM;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class EmptyItemViewHolder extends BaseViewHolder {
        ItemNoteEmptyItemBinding binding;
        private int position;

        public EmptyItemViewHolder(ItemNoteEmptyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            this.position = position;
            NoteCheckItem item = mList.get(position);
            binding.setItem(item);
        }
    }

    public class CheckItemViewHolder extends BaseViewHolder {
        ItemNoteCheckItemBinding binding;
        private int position;

        public CheckItemViewHolder(ItemNoteCheckItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            this.position = position;
            NoteCheckItem item = mList.get(position);
            binding.setItem(item);
        }
    }
}
