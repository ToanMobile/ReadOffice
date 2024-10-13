package com.app.office.officereader.beans;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.app.office.R;

public class SingleChoiceList extends ListView {
    public void dispose() {
    }

    public SingleChoiceList(Context context, int i) {
        super(context);
        setChoiceMode(1);
        setAdapter(new ArrayAdapter(context, R.layout.select_dialog_singlechoice, 16908308, context.getResources().getStringArray(i)));
    }
}
