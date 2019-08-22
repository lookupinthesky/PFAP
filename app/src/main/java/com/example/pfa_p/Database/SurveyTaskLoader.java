package com.example.pfa_p.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;


public class SurveyTaskLoader<String> extends AsyncTaskLoader<String> {

    String prisonerId;

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public SurveyTaskLoader(@NonNull Context context/*, String prisonerId*/) {
        super(context);
    //    this.prisonerId = prisonerId;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return null;
    }
}
