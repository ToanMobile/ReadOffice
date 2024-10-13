package com.app.office.system.beans.pagelist;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class APageListAdapter extends BaseAdapter {
    private APageListView listView;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public APageListAdapter(APageListView aPageListView) {
        this.listView = aPageListView;
    }

    public int getCount() {
        return this.listView.getPageCount();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        APageListItem aPageListItem = (APageListItem) view;
        Rect pageSize = this.listView.getPageListViewListener().getPageSize(i);
        if (view == null && (aPageListItem = this.listView.getPageListItem(i, view, viewGroup)) == null) {
            pageSize.right = 794;
            pageSize.bottom = 1124;
            aPageListItem = new APageListItem(this.listView, pageSize.width(), pageSize.height());
        }
        aPageListItem.setPageItemRawData(i, pageSize.width(), pageSize.height());
        return aPageListItem;
    }

    public void dispose() {
        this.listView = null;
    }
}
