//package cn.angeldo.magicalchicken;
//
//
//import android.app.Fragment;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;
//
//import static android.support.v4.content.ContextCompat.getDrawable;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link CardFragmentNew#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class CardFragmentNew extends Fragment {
//    // TODO: Rename and change types of parameters
//    private String fragData = "";
//    private TextView title;
//    private TextView ctime;
//    private LinearLayout listNum;
//
//    public CardFragmentNew() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @return A new instance of fragment CardFra.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static CardFragmentNew newInstance(String param1) {
//        CardFragmentNew fragment = new CardFragmentNew();
//        Bundle args = new Bundle();
//        args.putString("fragData", param1);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            fragData = getArguments().getString("fragData");
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_card, container, false);
//
//        listNum = (LinearLayout) view.findViewById(R.id.list_num);
//
//        title = (TextView) view.findViewById(R.id.title);
//        ctime = (TextView) view.findViewById(R.id.ctime);
//
//        if (!TextUtils.isEmpty(fragData)) {
//            Log.i("传递的参数是：" + getClass().getSimpleName(), fragData.substring(2, fragData.length() - 2) + "");
//        }
//
//
//        return view;
//    }
//
//    private void findViews(View view) {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                   JSONArray jsonArray= JSON.parseArray(fragData);
//                    for (int i = 0; i <jsonArray.size() ; i++) {
//
//                    }
//                    JSONObject js = JSON.parseObject(jsonArray.getString(0));
//
//                    Log.i("输出内容" + getClass().getSimpleName(), "" + js.getString("created"));
//
//                    title.setText(js.getString("mark"));
//                    ctime.setText(js.getString("created"));
//
//                    if (js.getInteger("type") == 1) {//双色球
//                        String balllist = js.getString("redball") + "," + js.getString("blueball");
//                        String[] blist = balllist.split(",");
//                        Log.i("输出字符串：" + getClass().getSimpleName(), balllist);
//                        for (int i = 0; i < 7; i++) {
//                            TextView num1 = new TextView(getActivity().getApplicationContext());
//                            num1.setText(blist[i]);
//                            num1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                            num1.setGravity(Gravity.CENTER);
//                            if (i > 5) {
//                                num1.setTextColor(Color.WHITE);
//                                num1.setBackground(getDrawable(getActivity().getApplicationContext(), R.drawable.bg_blue));
//                            } else {
//                                num1.setTextColor(Color.BLACK);
//                                num1.setBackground(getDrawable(getActivity().getApplicationContext(), R.drawable.bg_red));
//                            }
//                            LinearLayout.LayoutParams btn_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                                    ViewGroup.LayoutParams.WRAP_CONTENT);
//                            btn_params.setMargins(4, 4, 4, 4);
//                            btn_params.gravity = Gravity.CENTER_HORIZONTAL;
//                            num1.setLayoutParams(btn_params);
//                            listNum.addView(num1);
//                        }
//                    }
//                    if (js.getInteger("type") == 2) {//大乐透
//
//                        String balllist = js.getString("redball") + "," + js.getString("blueball");
//                        String[] blist = balllist.split(",");
//
//                        for (int i = 0; i < 7; i++) {
//                            TextView num1 = new TextView(getActivity().getApplicationContext());
//                            num1.setText(blist[i]);
//                            num1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                            num1.setGravity(Gravity.CENTER);
//                            if (i > 4) {
//                                num1.setTextColor(Color.WHITE);
//                                num1.setBackground(getDrawable(getActivity().getApplicationContext(), R.drawable.bg_blue));
//                            } else {
//                                num1.setTextColor(Color.BLACK);
//                                num1.setBackground(getDrawable(getActivity().getApplicationContext(), R.drawable.bg_red));
//                            }
//                            LinearLayout.LayoutParams btn_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                                    ViewGroup.LayoutParams.WRAP_CONTENT);
//                            btn_params.setMargins(4, 4, 4, 4);
//                            btn_params.gravity = Gravity.CENTER_HORIZONTAL;
//                            num1.setLayoutParams(btn_params);
//                            listNum.addView(num1);
//                        }
//                    }
//                    if (js.getInteger("type") == 3) {//双色球
//
//                        TextView num1 = new TextView(getActivity().getApplicationContext());
//                        num1.setText(js.getString("charstr"));
//                        num1.setTextColor(Color.BLACK);
//                        num1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                        num1.setGravity(Gravity.CENTER);
//                        listNum.addView(num1);
//
//                    }
//                } catch (JSONException e) {
//                    Log.i("输出错误", e.toString());
//                }
//            }
//        }.start();
//    }
//
//}
