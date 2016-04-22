package com.parlanto.parlantonmrs;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


/**
 * Created by angel on 1/4/16.
 */
public class TouchHelper extends ItemTouchHelper.SimpleCallback {

    private MyAdapter mAdapter;

    public TouchHelper(MyAdapter adapter) {
        super(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        this.mAdapter = adapter;

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.swap(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){

    }
}


