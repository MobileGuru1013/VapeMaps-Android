package com.zkaren.springhappenings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



    // TODO: Rename and change types and number of parameters

    TextView txt1, txt2, txt3;
    public DetailFragment() {
        // Required empty public constructor
    }
    FrameLayout childFrame;
    FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
//        childFrame = (FrameLayout) view.findViewById(R.id.childFrame);
        Button btn = (Button)view.findViewById(R.id.button);
        txt1 = (TextView)view.findViewById(R.id.textView10);
        txt2 = (TextView)view.findViewById(R.id.textView11);
        txt3 = (TextView)view.findViewById(R.id.textView12);
//        fragmentManager = getChildFragmentManager();
        txt3.setMovementMethod(new ScrollingMovementMethod());
                txt1.setText(MoreInformation.timeStr);
        txt2.setText(MoreInformation.title);
        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.add(R.id.childFrame, new AdvImageFragment());
//        ft.commit();
        txt3.setText(MoreInformation.contents);
        if (MoreInformation.url == null || MoreInformation.url.equals("")) btn.setVisibility(View.GONE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), WebViewActivity.class);
                in.putExtra(Constant.URL, MoreInformation.url);
                startActivity(in);
            }
        });
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
