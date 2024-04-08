package com.moblie.parkinglot.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moblie.parkinglot.R;
import com.moblie.parkinglot.databinding.StationViewFragmentBinding;


public class StationViewFragment extends Fragment {

    private StationViewFragmentBinding binding;
    View root;

    String StationUsing;

    String[] StationUsingArr = new String[8];
    View[] StationView = new View[8];

    Integer[] Station = {R.id.Station1, R.id.Station2, R.id.Station3, R.id.Station4, R.id.Station5,
            R.id.Station6, R.id.Station7, R.id.Station8 };

    public static StationViewFragment newInstance() {
        return new StationViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //뷰 바인딩
        binding = StationViewFragmentBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //전 프레그먼트 값 받아오기
        if (getArguments() != null)
        {
            StationUsing = getArguments().getString("StationUsing"); // 프래그먼트1에서 받아온 값 넣기

            StationUsingArr = StationUsing.split(" ");
        }

        //받은 값을 이용해 구역 색 변경
        for(int i = 0; i < StationUsingArr.length; i++){
            if (StationUsingArr[i].equals("O")){
                StationView[i] =(View)root.findViewById(Station[i]);
                StationView[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.border_red));
            }
            else {
                StationView[i] =(View)root.findViewById(Station[i]);
                StationView[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.border));
            }
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}