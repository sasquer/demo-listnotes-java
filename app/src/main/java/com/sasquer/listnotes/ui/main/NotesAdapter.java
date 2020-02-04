package com.sasquer.listnotes.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.sasquer.listnotes.base.BaseViewHolder;
import com.sasquer.listnotes.data.local.db.entity.Note;
import com.sasquer.listnotes.databinding.ItemNoteBinding;
import com.sasquer.listnotes.diffutills.NoteListDiffUtil;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<Note> mList;
    private INoteItemClick mListener;
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

    public NotesAdapter(INoteItemClick listener) {
        mListener = listener;
        mList = new ArrayList<>();
    }

    public void setItems(List<Note> list) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new NoteListDiffUtil(mList, list), true);
        result.dispatchUpdatesTo(listUpdateCallback);
        mList.clear();
        mList.addAll(list);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding noteListViewHolder = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new NoteViewHolder(noteListViewHolder);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class NoteViewHolder extends BaseViewHolder implements INoteItemClick {
        ItemNoteBinding binding;

        public NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            binding.setEvent(mListener);

            Note note = mList.get(position);
            binding.setNote(note);
        }

        @Override
        public void onItemClick(View view, int id) {
            if (mListener != null)
                mListener.onItemClick(view, id);
        }
    }
}
