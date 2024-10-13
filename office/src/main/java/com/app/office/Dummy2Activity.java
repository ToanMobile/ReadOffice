package com.app.office;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.app.office.constant.MainConstant;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import com.app.office.officereader.AppActivity;
import java.io.File;

public class Dummy2Activity extends Activity {
    TextView textView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //TODO TOAN
//        setContentView(R.layout.my_dummy_activity);
//        TextView textView2 = (TextView) findViewById(R.id.onClick);
//        this.textView = textView2;
//        textView2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent("android.intent.action.GET_CONTENT");
//                intent.addCategory("android.intent.category.OPENABLE");
//                intent.setType("*/*");
//                intent.putExtra("android.intent.extra.MIME_TYPES", new String[]{"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/msword", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/vnd.ms-powerpoint", "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-excel"});
//                Dummy2Activity.this.startActivityForResult(intent, 200);
//            }
//        });
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 200) {
            Uri data = intent.getData();
            data.getPath();
            String fileNameByUri = ExtenFuncKt.getFileNameByUri(this, data);
            ExtenFuncKt.copyUriToExternalFilesDir(this, data, fileNameByUri);
            String str = getExternalCacheDir().toString() + PackagingURIHelper.FORWARD_SLASH_STRING + fileNameByUri;
            new File(str);
            Toast.makeText(this, str + "", Toast.LENGTH_SHORT).show();
            Log.d("#ERROR", str);
            Intent intent2 = new Intent();
            intent2.setClass(this, AppActivity.class);
            intent2.putExtra(MainConstant.INTENT_FILED_FILE_PATH, str);
            intent2.putExtra("FileName", fileNameByUri);
            startActivityForResult(intent2, 1);
        }
    }
}
