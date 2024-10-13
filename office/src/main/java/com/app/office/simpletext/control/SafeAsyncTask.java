package com.app.office.simpletext.control;

import android.os.AsyncTask;
import java.util.concurrent.RejectedExecutionException;

public abstract class SafeAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    public void safeExecute(Params... paramsArr) {
        try {
            execute(paramsArr);
        } catch (RejectedExecutionException unused) {
            onPreExecute();
            if (isCancelled()) {
                onCancelled();
            } else {
                onPostExecute(doInBackground(paramsArr));
            }
        }
    }
}
