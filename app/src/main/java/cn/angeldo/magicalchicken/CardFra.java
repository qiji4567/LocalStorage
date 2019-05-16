package cn.angeldo.magicalchicken;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.angeldo.magicalchicken.adp.CardAdp;
import cn.angeldo.magicalchicken.model.CardInfoBean;
import cn.angeldo.magicalchicken.utils.LogUtils;
import cn.angeldo.magicalchicken.view.DividerItemDecoration1;


/**
 * @author qiji
 * @title 首页确认保存记录列表
 */
public class CardFra extends Fragment {
    // TODO: Rename and change types of parameters
    private String fragData = "";
    private CardAdp cardAdp;

    public CardFra() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragData = getArguments().getString("fragData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, null);

        findViews(view);

        if (!TextUtils.isEmpty(fragData)) {
            Log.i("传递的参数是：" + getClass().getSimpleName(), fragData.substring(2, fragData.length() - 2) + "");
        }

        return view;
    }

    private void findViews(View view) {
        cardAdp = new CardAdp(getActivity());

        RecyclerView rv_card = (RecyclerView) view.findViewById(R.id.rv_card);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rv_card.addItemDecoration(new DividerItemDecoration1(10, 20));
        rv_card.setLayoutManager(layoutManager2);


        rv_card.setAdapter(cardAdp);
        loadDate();

    }


    /**
     * 加载数据
     */
    private void loadDate() {
        new Thread() {
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = JSON.parseArray(fragData);
                    List<CardInfoBean> infoBeanList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        if (jsonArray != null && jsonArray.size() > 0) {
                            CardInfoBean infoBean = new CardInfoBean();
                            JSONObject object = JSON.parseObject(jsonArray.getString(i));
                            infoBean.setCreated(object.getString("created"));
                            infoBean.setType(object.getInteger("type"));
                            infoBean.setMark(object.getString("mark"));
                            infoBean.setRedball(object.getString("redball"));
                            infoBean.setBlueball(object.getString("blueball"));
                            infoBean.setCharstr(object.getString("charstr"));
                            infoBeanList.add(infoBean);
                        }
                    }
//                    Log.d("解析数据是:....." + getClass().getSimpleName(), jsonArray.toString());
                    if (jsonArray != null && jsonArray.size() > 0) {
                        LogUtils.d("解析数据是....." ,infoBeanList.toString());
                        cardAdp.setList(infoBeanList);
                        cardAdp.notifyDataSetChanged();
                    }


//                    Log.i("输出内容" + getClass().getSimpleName(), "" + js.getString("created"));


                } catch (JSONException e) {
                    Log.i("输出错误", e.toString());
                }
            }
        }.start();
    }

}
