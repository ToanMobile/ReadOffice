package com.app.office.common.bulletnumber;

import java.util.LinkedHashMap;

public class ListManage {
    private LinkedHashMap<Integer, ListData> lists = new LinkedHashMap<>();

    public int putListData(Integer num, ListData listData) {
        this.lists.put(num, listData);
        return this.lists.size() - 1;
    }

    public ListData getListData(Integer num) {
        return this.lists.get(num);
    }

    public void resetForNormalView() {
        for (Integer num : this.lists.keySet()) {
            ListData listData = this.lists.get(num);
            if (listData != null) {
                listData.setNormalPreParaLevel((byte) 0);
                listData.resetForNormalView();
            }
        }
    }

    public void dispose() {
        LinkedHashMap<Integer, ListData> linkedHashMap = this.lists;
        if (linkedHashMap != null) {
            for (Integer num : linkedHashMap.keySet()) {
                this.lists.get(num).dispose();
            }
            this.lists.clear();
        }
    }
}
