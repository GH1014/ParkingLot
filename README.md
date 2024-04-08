## 주차장 관리 어플리케이션

**프로젝트 개요**

HOUD 전기차 충전소 어플리케이션 코드를 기반으로 만든 주차장 관리 어플리케이션입니다.
코드의 가독성을 높이고 유지보수를 편하게 하였습니다.

--------------------------------------------------------

**사용한 기술 정보**

Java, Android Studio, Firebase

--------------------------------------------------------

**구동 스크린샷**

디자인은 담당한 부분이 아니라 스크린샷은 첨부하지 않았습니다.

--------------------------------------------------------

**주요 코드**

Firebase와 연동되는 부분입니다.
```java
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
```

뷰에 데이터를 전달하는 부분입니다.
```java
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
        if (StationUsingArr[i].equals("O")){ // 자동차가 들어와 있다면 실행
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
```
--------------------------------------------------------

안드로이드의 다양한 기능 및 라이브러리에 대한 이해와 활용능력을 향상시키는데 도움이 되었습니다.

코드 리팩토링은 처음 해봤는데, 많이 부족하다고 느꼈습니다.
