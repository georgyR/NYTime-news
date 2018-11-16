package com.androidacademy.msk.exerciseproject.screen.intro;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidacademy.msk.exerciseproject.R;

public class PageFragment extends Fragment {

    private static final String KEY_IMAGE = "KEY_IMAGE";

    public static PageFragment newInstance(@DrawableRes int drawableRes) {

        Bundle args = new Bundle();
        args.putInt(KEY_IMAGE, drawableRes);

        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        int drawableRes = getArguments().getInt(KEY_IMAGE);

        ImageView imageView = view.findViewById(R.id.imageview_page);
        imageView.setImageResource(drawableRes);


        return view;
    }
}
