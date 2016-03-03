package com.droidislands.othello;

import android.widget.Button;
import android.content.Context;
import android.widget.ImageButton;

/**
 * Created by ID032389 on 8/21/2014.
 */
public class othello_buttons extends Button {
    public boolean isWhite=true;
    public boolean isBlack=true;
    public boolean isGreen=true;
    public int row = 0;
    public int column = 0;

private Context context = null;

    public othello_buttons(Context context) {
        super(context);
        this.context = context;
    }


}
