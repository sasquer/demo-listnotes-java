package com.sasquer.listnotes.ui.notedetails;

public interface ICheckItemTouchHelper {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemRemove(int position);
}
