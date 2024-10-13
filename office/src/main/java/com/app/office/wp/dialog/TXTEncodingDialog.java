package com.app.office.wp.dialog;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import com.app.office.fc.codec.CharEncoding;
import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import com.app.office.system.beans.ADialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class TXTEncodingDialog extends ADialog {
    public static final String BACK_PRESSED = "BP";
    public static final String[] ENCODING_ITEMS = {"GBK", "GB2312", "BIG5", "Unicode", "UTF-8", "UTF-16", "UTF-16LE", "UTF-16BE", "UTF-7", "UTF-32", "UTF-32LE", "UTF-32BE", CharEncoding.US_ASCII, CharEncoding.ISO_8859_1, "ISO-8859-2", "ISO-8859-3", "ISO-8859-4", "ISO-8859-5", "ISO-8859-6", "ISO-8859-7", "ISO-8859-8", "ISO-8859-9", "ISO-8859-10", "ISO-8859-11", "ISO-8859-13", "ISO-8859-14", "ISO-8859-15", "ISO-8859-16", "ISO-2022-JP", "ISO-2022-KR", "ISO-2022-CN", "ISO-2022-CN-EXT", "UCS-2", "UCS-4", "Windows-1250", "Windows-1251", "Windows-1252", "Windows-1253", " Windows-1254", "Windows-1255", "Windows-1256", "Windows-1257", "Windows-1258", "KOI8-R", "Shift_JIS", "CP864", "EUC-JP", "EUC-KR", "BOCU-1", "CESU-8", "SCSU", "HZ-GB-2312", "TIS-620", "macintosh", "x-UTF-16LE-BOM", "x-iscii-as", "x-iscii-be", "x-iscii-de", "x-iscii-gu", "x-iscii-ka", "x-iscii-ma", "x-iscii-or", "x-iscii-pa", "x-iscii-ta", "x-iscii-te", "x-mac-cyrillic"};
    private char[] buffer = new char[1024];
    private WebView previewText;
    private ScrollView scrollView;
    /* access modifiers changed from: private */
    public Spinner spinner;

    public TXTEncodingDialog(IControl iControl, Context context, IDialogAction iDialogAction, Vector<Object> vector, int i) {
        super(iControl, context, iDialogAction, vector, i, iDialogAction.getControl().getMainFrame().getLocalString("DIALOG_ENCODING_TITLE"));
        init(context);
    }

    public void init(Context context) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, 17367048, ENCODING_ITEMS);
        arrayAdapter.setDropDownViewResource(17367049);
        Spinner spinner2 = new Spinner(context);
        this.spinner = spinner2;
        spinner2.setAdapter(arrayAdapter);
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                String obj = TXTEncodingDialog.this.spinner.getSelectedItem().toString();
                if (obj != null) {
                    TXTEncodingDialog.this.setPreviewText(obj);
                }
            }
        });
        this.dialogFrame.addView(this.spinner);
        WebView webView = new WebView(context);
        this.previewText = webView;
        webView.setPadding(5, 2, 5, 2);
        ScrollView scrollView2 = new ScrollView(context);
        this.scrollView = scrollView2;
        scrollView2.setFillViewport(true);
        this.scrollView.addView(this.previewText);
        this.dialogFrame.addView(this.scrollView);
        this.ok = new Button(context);
        this.ok.setText(this.action.getControl().getMainFrame().getLocalString("BUTTON_OK"));
        this.ok.setOnClickListener(this);
        this.dialogFrame.addView(this.ok);
    }

    public void doLayout() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((getContext().getResources().getDisplayMetrics().widthPixels - 50) - 10, (((getContext().getResources().getDisplayMetrics().heightPixels - (getWindow().getDecorView().getHeight() - this.dialogFrame.getHeight())) - 50) - this.spinner.getBottom()) - this.ok.getHeight());
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.bottomMargin = 5;
        this.scrollView.setLayoutParams(layoutParams);
    }

    public void onConfigurationChanged(Configuration configuration) {
        doLayout();
    }

    public void onClick(View view) {
        Vector vector = new Vector();
        vector.add(this.spinner.getSelectedItem().toString());
        this.action.doAction(this.dialogID, vector);
        dismiss();
    }

    public void onBackPressed() {
        Vector vector = new Vector();
        vector.add(BACK_PRESSED);
        this.action.doAction(this.dialogID, vector);
        super.onBackPressed();
    }

    /* access modifiers changed from: private */
    public void setPreviewText(String str) {
        if (str != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.model.get(0).toString())), str));
                int read = bufferedReader.read(this.buffer);
                if (read > 0) {
                    this.previewText.loadDataWithBaseURL((String) null, ("<a>" + new String(this.buffer, 0, read) + "</a>").replaceAll("\\r\\n", "<br />"), "text/html", "UTF-8", (String) null);
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dispose() {
        super.dispose();
        this.spinner = null;
        this.previewText = null;
        this.buffer = null;
        this.scrollView = null;
    }
}
