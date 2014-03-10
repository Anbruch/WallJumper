package com.me.walljumper;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.me.walljumper.WallJumper;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
       
        initialize(new WallJumper(), cfg);
    }
}