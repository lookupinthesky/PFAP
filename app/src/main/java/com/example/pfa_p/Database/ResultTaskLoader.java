package com.example.pfa_p.Database;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class ResultTaskLoader <String> extends AsyncTaskLoader<String> {

    String prisonerId;
    Bundle args;

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

    public ResultTaskLoader(@NonNull Context context/*, String prisonerId*/, Bundle args) {
        super(context);
        this.args = args;
        //    this.prisonerId = prisonerId;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return null;
    }
}