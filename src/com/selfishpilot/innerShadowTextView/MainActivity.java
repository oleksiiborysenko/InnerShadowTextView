package com.selfishpilot.innerShadowTextView;

import android.app.Activity;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /* first variant: embossFilter */
        TextView tv = (TextView) findViewById(R.id.textView);
        float[] direction = new float[]{0f, -1.0f, 0.5f};
        MaskFilter filter = new EmbossMaskFilter(direction, 0.8f, 15f, 3f);
        tv.getPaint().setMaskFilter(filter);

    }
}
