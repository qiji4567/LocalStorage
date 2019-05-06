package cn.angeldo.magicalchicken.adp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.angeldo.magicalchicken.R;
import cn.angeldo.magicalchicken.model.CardInfoBean;

import static android.support.v4.content.ContextCompat.getDrawable;


/**
 * @author qiji
 * @time 2019-05-06
 */
public class CardAdp extends RecyclerView.Adapter {

    private Context context;
    private List<CardInfoBean> mList;
    private OnItemClickListener mOnItemClickListener;

    public CardAdp(Context context, List<CardInfoBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        /**
         * @param view     条目被点击
         * @param position 点击的角标
         */
        void onItemClick(View view, int position);


    }


    public CardAdp(Context context) {
        this.context = context;
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        } else {
            mList = new ArrayList<>();
        }
    }

    public void setList(List<CardInfoBean> data) {
        this.mList = data;
    }

    public List<CardInfoBean> getList() {
        return mList;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardHolder(LayoutInflater.from(context).inflate(R.layout.item_fra_card, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final CardHolder holder = (CardHolder) viewHolder;
        CardInfoBean bean = mList.get(position);
        if (bean != null && mList != null && mList.size() > 0) {


            holder.title.setText(bean.getMark());
            holder.ctime.setText(bean.getCreated());

            if (bean.getType() == 1) {//双色球
                String balllist = bean.getRedball() + "," + bean.getBlueball();
                String[] blist = balllist.split(",");
                Log.i("输出字符串：" + getClass().getSimpleName(), balllist);
                for (int i = 0; i < 7; i++) {
                    TextView num1 = new TextView(context.getApplicationContext());
                    num1.setText(blist[i]);
                    num1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    num1.setGravity(Gravity.CENTER);
                    if (i > 5) {
                        num1.setTextColor(Color.WHITE);
                        num1.setBackground(getDrawable(context.getApplicationContext(), R.drawable.bg_blue));
                    } else {
                        num1.setTextColor(Color.BLACK);
                        num1.setBackground(getDrawable(context.getApplicationContext(), R.drawable.bg_red));
                    }
                    LinearLayout.LayoutParams btn_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    btn_params.setMargins(4, 4, 4, 4);
                    btn_params.gravity = Gravity.CENTER_HORIZONTAL;
                    num1.setLayoutParams(btn_params);
                    holder.listNum.addView(num1);
                }
            }
            if (bean.getType() == 2) {//大乐透

                String balllist = bean.getRedball() + "," + bean.getBlueball();
                String[] blist = balllist.split(",");

                for (int i = 0; i < 7; i++) {
                    TextView num1 = new TextView(context.getApplicationContext());
                    num1.setText(blist[i]);
                    num1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    num1.setGravity(Gravity.CENTER);
                    if (i > 4) {
                        num1.setTextColor(Color.WHITE);
                        num1.setBackground(getDrawable(context.getApplicationContext(), R.drawable.bg_blue));
                    } else {
                        num1.setTextColor(Color.BLACK);
                        num1.setBackground(getDrawable(context.getApplicationContext(), R.drawable.bg_red));
                    }
                    LinearLayout.LayoutParams btn_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    btn_params.setMargins(4, 4, 4, 4);
                    btn_params.gravity = Gravity.CENTER_HORIZONTAL;
                    num1.setLayoutParams(btn_params);
                    holder.listNum.addView(num1);
                }
            }
            if (bean.getType() == 3) {//双色球

                TextView num1 = new TextView(context.getApplicationContext());
                num1.setText(bean.getCharstr());
                num1.setTextColor(Color.BLACK);
                num1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                num1.setGravity(Gravity.CENTER);
                holder.listNum.addView(num1);

            }
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class CardHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView ctime;
        private LinearLayout listNum;


        public CardHolder(View view) {
            super(view);

            listNum = (LinearLayout) view.findViewById(R.id.list_num);

            title = (TextView) view.findViewById(R.id.title);
            ctime = (TextView) view.findViewById(R.id.ctime);


        }
    }


}

