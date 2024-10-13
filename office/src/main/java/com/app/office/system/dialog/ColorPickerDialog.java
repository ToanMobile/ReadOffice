package com.app.office.system.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import com.app.office.R;
import com.app.office.system.IControl;
import com.app.office.system.beans.CalloutView.CalloutManager;

public class ColorPickerDialog extends Dialog {
    public static int mInitialColor;
    /* access modifiers changed from: private */
    public CalloutManager calloutMgr;
    private IControl control;
    private Context mContext;

    public ColorPickerDialog(Context context, IControl iControl) {
        super(context);
        this.control = iControl;
        CalloutManager calloutManager = iControl.getSysKit().getCalloutManager();
        this.calloutMgr = calloutManager;
        this.mContext = context;
        mInitialColor = calloutManager.getColor();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.pen_setting_dialog, (ViewGroup) null);
        final ColorPickerView colorPickerView = (ColorPickerView) inflate.findViewById(R.id.colorPickerView);
        final SeekBar seekBar = (SeekBar) inflate.findViewById(R.id.strokeBar);
        final SeekBar seekBar2 = (SeekBar) inflate.findViewById(R.id.alphaBar);
        seekBar.setProgress(this.calloutMgr.getWidth());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (i < 1) {
                    seekBar.setProgress(1);
                }
            }
        });
        seekBar2.setProgress(this.calloutMgr.getAlpha());
        colorPickerView.setAlpha(this.calloutMgr.getAlpha());
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                colorPickerView.setAlpha(i);
            }
        });
        ((Button) inflate.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ColorPickerDialog.this.calloutMgr.setColor(colorPickerView.getColor());
                ColorPickerDialog.this.calloutMgr.setWidth(seekBar.getProgress());
                ColorPickerDialog.this.calloutMgr.setAlpha(seekBar2.getProgress());
                ColorPickerDialog.this.dismiss();
            }
        });
        ((Button) inflate.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ColorPickerDialog.this.dismiss();
            }
        });
        setContentView(inflate);
        setTitle(R.string.app_toolsbar_color);
    }
}
