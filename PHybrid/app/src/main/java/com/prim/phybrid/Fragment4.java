package com.prim.phybrid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.prim.hybrid.PHybrid;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/24/21 - 4:04 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class Fragment4 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag4, container, false);

        FrameLayout view = inflate.findViewById(R.id.frameLayout);

        PHybrid.loadTemplate(getContext(), "channel", view);

        return inflate;
    }

}
