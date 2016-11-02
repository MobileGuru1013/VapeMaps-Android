package com.zkaren.springhappenings;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class PhotoFragment extends Fragment {

    public PhotoFragment() {
        // Required empty public constructor
    }
    ImageView img;
    MoreInformation main;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        img = (ImageView)view.findViewById(R.id.imageView4);
        main = (MoreInformation)getActivity();
        Picasso.with(getActivity()).load(MoreInformation.photoURL).error(R.drawable.default_image).into(img);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



}
