package com.example.pfa_p.Model;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Bar {


    LinearLayout parent;
    LinearLayout rating;
    TextView description;
    TextView value;
    View valueSpace;
    View emptySpace;


    String descriptionText;
    String valueText;
    int maxValue;
    int actualValue;
    int valueColor;
    int emptyColor;
    float valueWeight;
    float emptyWeight;

    LinearLayout.LayoutParams descriptionParams;


    public Bar(Context context, int maxValue, int actualValue, String description, String valueText) {

        valueWeight = (float) actualValue / maxValue;
        emptyWeight = (float) (maxValue - actualValue)/maxValue;
        this.descriptionText = description;
        this.valueText = valueText;

        createBar(context);


    }


    public LinearLayout getView(){


        return parent;
    }


    private void createBar(Context context) {


        parent = new LinearLayout(context);
        parent.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50);
        params.bottomMargin = 8;
        parent.setLayoutParams(params);
        description = new TextView(context);
        descriptionParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        descriptionParams.weight = 3;
        descriptionParams.rightMargin = 12;
        description.setLayoutParams(descriptionParams);
        description.setText(descriptionText);
      //  description.setBackgroundColor(Color.YELLOW);

        parent.addView(description);
        rating = new LinearLayout(context);
        rating.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params_rating = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params_rating.weight = 5;
        rating.setLayoutParams(params_rating);
        valueSpace = new View(context);
        LinearLayout.LayoutParams params_space = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params_space.weight = valueWeight;
        valueSpace.setLayoutParams(params_space);
        valueSpace.setBackgroundColor(Color.RED);
        rating.addView(valueSpace);
        emptySpace = new View(context);
        LinearLayout.LayoutParams params_empty_space = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params_empty_space.weight = emptyWeight;
        emptySpace.setLayoutParams(params_empty_space);
        //   emptySpace.setBackgroundColor(Color.BLUE);
        rating.addView(emptySpace);
        parent.addView(rating);
        value = new TextView(context);
        LinearLayout.LayoutParams params_value = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params_value.weight = 2;

        value.setLayoutParams(params_value);
        value.setText(valueText);
        value.setBackgroundColor(Color.YELLOW);
        parent.addView(value);

    }


}
