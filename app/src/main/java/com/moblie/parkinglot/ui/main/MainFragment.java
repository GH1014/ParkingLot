package com.moblie.parkinglot.ui.main;

import static android.content.ContentValues.TAG;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.moblie.parkinglot.R;
import com.moblie.parkinglot.databinding.MainFragmentBinding;

import java.util.Map;

public class MainFragment extends Fragment {

    private MainFragmentBinding binding;
    View root;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private View ChangeView;
    private TextView StationNum;

    DatabaseReference database;

    String[] Stationstr = new String[8];
    String StationUsing;
    int StationUseNum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //뷰 바인딩
        binding = MainFragmentBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //데이터베이스 연결
        database = FirebaseDatabase.getInstance().getReference();

        //스테이션 데이터 저장
        for (int i = 0; i < Stationstr.length; i++ ){
            Stationstr[i] = Integer.toString(i+1);
        }

        database.addValueEventListener(new ValueEventListener() {
            @Override

            //데이터 변동 시 실행
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StationUsing = "";
                StationUseNum = 0;

                //데이터 값 변수에 저장
                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                Log.d(TAG, "Value is: " + map);

                Map map2 = (Map)map.get("Stationinfo");

                //데이터 값 중 USING 체크
                for (int i = 0; i < Stationstr.length; i++ ){
                    Map map3 = (Map)map2.get("Station" + Stationstr[i]);
                    String str =  (String)map3.get("USING");

                    if(str.equals("O")){
                        StationUseNum++;
                    }

                    StationUsing = StationUsing + str + " ";
                }

                //USING 값에 따른 텍스트 값 변경
                StationNum = (TextView) root.findViewById(R.id.StationNum);
                StationNum.setText(Integer.toString(StationUseNum) + "면 / 8면");

                Log.d(TAG, "Value is: "+ StationUsing);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //다음 프레그먼트로 이동
        ChangeView =(View)root.findViewById(R.id.ChangeView);
        ChangeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("StationUsing", StationUsing);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                StationViewFragment stationViewFragment = new StationViewFragment();
                stationViewFragment.setArguments(bundle);

                transaction.replace(R.id.container, stationViewFragment);
                transaction.commit();
                transaction.addToBackStack(null);
            }
        });
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