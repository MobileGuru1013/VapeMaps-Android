package com.zkaren.springhappenings;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Handler;
import com.parse.ParseFile;
import com.squareup.picasso.Picasso;



public class AdvImageFragment extends Fragment {

    int index  = 0;

    public AdvImageFragment() {
        // Required empty public constructor
    }
    int count;
    ImageView img;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setImage();
        }
    };
    Handler handler = new Handler();
    void setImage()
    {
        ParseFile pf = null;
        if (index == count) index = 0;
//        if ()
        pf =  MainActivity.advImages.get(index).getParseFile(Constant.AD_IMAGE);
        Picasso.with(getActivity()).load(pf.getUrl()).into(img);
        index++;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adv_image, container, false);
        img= (ImageView)view.findViewById(R.id.imageView5);
        count = MainActivity.advImages.size();
        img.setImageResource(R.drawable.ic_launcher);
        handler.postAtTime(runnable, 1000);
//        if (pf == null)  Toast.makeText(getActivity(),"null " + MainActivity.advImages.size(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(),.getUrl(), Toast.LENGTH_SHORT).show();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
