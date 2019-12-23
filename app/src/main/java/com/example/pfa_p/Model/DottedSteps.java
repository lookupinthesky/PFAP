package com.example.pfa_p.Model;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class DottedSteps {


    int n;

    LinearLayout parent;

    List<View> views = new ArrayList<>();
    int COLOR_GREY;
    int strokeWidth = 10;
    int COLOR_WHITE;


    public DottedSteps(int number_of_steps, Context context) {
        this.n = number_of_steps;
        createViews(n, context);
    }

    void createViews(int number_of_steps, Context context) {

        COLOR_GREY = context.getResources().getColor(android.R.color.darker_gray);
        COLOR_WHITE = context.getResources().getColor(android.R.color.white);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        params.setMargins(16, 16, 16, 16);
        for (int i = 0; i < number_of_steps; i++) {
            View view = new View(context);
            view.setLayoutParams(params);
            view.setBackground(drawCircle(COLOR_WHITE, COLOR_GREY, strokeWidth));
        }
    }

    public LinearLayout getView(Context context) {

        parent = new LinearLayout(context);
        parent.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parent.setLayoutParams(params);


        for (View view : views) {

            parent.addView(view);
        }

        return parent;
    }

    private GradientDrawable drawCircle(int backgroundColor, int borderColor, int borderWidth) {


        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        /*drawable.setCornerRadii(new float[]{});*/
        drawable.setColor(backgroundColor);
        drawable.setStroke(borderWidth, borderColor);
        return drawable;


    }

    int mCurrentSelected = 0;

    public void next() {

        if (mCurrentSelected < n - 1) {
            mCurrentSelected++;
            invalidate();
        }

    }

    private void invalidate() {

        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i);
            if (i == mCurrentSelected) {
                select(view);
            }
        }
    }

    private void select(View view) {
        deselectViews();
        view.setBackground(drawCircle(COLOR_GREY, COLOR_GREY, strokeWidth));
    }

    private void deselectViews() {
        for (View view : views) {
            view.setBackground(drawCircle(COLOR_WHITE, COLOR_GREY, strokeWidth));
        }
    }


}
