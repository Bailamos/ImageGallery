package com.example.kaczor.imagegallery.listeners;

import android.widget.AbsListView;

import com.example.kaczor.imagegallery.core.interfaces.IOnScroll;

public class BasicOnScrollListener implements AbsListView.OnScrollListener {

    private IOnScroll onScroll;

    public BasicOnScrollListener(IOnScroll action) {
        this.onScroll = action;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        onScroll.action(firstVisibleItem, visibleItemCount, totalItemCount);
    }
}
