package com.sasquer.listnotes.ui.notedetails;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.sasquer.listnotes.base.BaseViewHolder;
import com.sasquer.listnotes.data.local.db.entity.NoteCheckItem;
import com.sasquer.listnotes.databinding.ItemNoteCheckItemBinding;
import com.sasquer.listnotes.databinding.ItemNoteEmptyItemBinding;
import com.sasquer.listnotes.diffutills.CheckItemsDiffUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CheckItemAdapter extends RecyclerView.Adapter<BaseViewHolder> implements ICheckItemTouchHelper {
    private static final int TYPE_EMPTY_ITEM = 0;
    private static final int TYPE_ITEM = 1;
    private NoteDetailsViewModel vm;
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

    public CheckItemAdapter(NoteDetailsViewModel viewModel) {
        vm = viewModel;
    }

    public void addEmptyItem() {
        NoteCheckItem emptyItem = new NoteCheckItem();
        emptyItem.setEmpty(true);
        mList.add(emptyItem);
        notifyItemInserted(mList.size());
    }

    private void changeEmptyItem(int position, String value) {
        NoteCheckItem noteCheckItem = mList.get(position);
        noteCheckItem.setEmpty(false);
        noteCheckItem.setValue(value);
        vm.addCheckItem(noteCheckItem);
        notifyItemChanged(position);
    }

    private void removeItem(int position) {
        mList.remove(position);
        vm.removeCheckItem(position);
        notifyItemRemoved(position);
    }

    public List<NoteCheckItem> getList() {
        mList.remove(mList.size() - 1);
        return mList;
    }

    public void setItems(List<NoteCheckItem> list) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new CheckItemsDiffUtil(mList, list), true);
        result.dispatchUpdatesTo(listUpdateCallback);
        mList.clear();
        mList.addAll(list);
        if (mList.isEmpty() || !mList.get(mList.size() - 1).isEmpty())
            addEmptyItem();
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

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (!mList.get(fromPosition).isEmpty() && toPosition < mList.size() - 1) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mList, i, i - 1);
                }
            }
            vm.setCheckItems(mList);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        } else return false;
    }

    @Override
    public void onItemRemove(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public class EmptyItemViewHolder extends BaseViewHolder implements TextWatcher {
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
            binding.setWatcher(this);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() > 0 && getLayoutPosition() == mList.size() - 1) {
                changeEmptyItem(getLayoutPosition(), editable.toString());
                addEmptyItem();
            }
        }
    }

    public class CheckItemViewHolder extends BaseViewHolder implements TextWatcher, View.OnKeyListener {
        ItemNoteCheckItemBinding binding;
        private int position;
        private HashMap<Integer, Boolean> shouldRemove = new HashMap<>();
        private NoteCheckItem currentItem;

        public CheckItemViewHolder(ItemNoteCheckItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            this.position = position;
            currentItem = mList.get(position);
            binding.setItem(currentItem);
            binding.setCheckBoxListener((compoundButton, b) -> onCheckBoxChanged(currentItem, b));
            binding.setWatcher(this);
            binding.setKeyListener(this);
        }

        void onCheckBoxChanged(NoteCheckItem item, boolean isChecked) {
            item.setChecked(isChecked);
            notifyItemChanged(getAdapterPosition());
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() == 0) {
                shouldRemove.put(getLayoutPosition(), true);
            } else {
                currentItem.setValue(editable.toString());
                shouldRemove.put(getLayoutPosition(), false);
            }
        }

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                    keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                if (shouldRemove.get(getLayoutPosition())) {
                    removeItem(getLayoutPosition());
                    return true;
                }
            }
            return false;
        }
    }
}
