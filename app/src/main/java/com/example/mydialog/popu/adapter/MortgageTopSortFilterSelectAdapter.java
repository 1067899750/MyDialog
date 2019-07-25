package com.example.mydialog.popu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydialog.R;

import java.util.ArrayList;


/**
 * @author puyantao
 * @create 2019/5/9
 * @Describe PopupWindow 的 adapter
 */
public class MortgageTopSortFilterSelectAdapter extends RecyclerView.Adapter<MortgageTopSortFilterSelectAdapter.MyVierHolder> {
    private Context mContext;
    private ArrayList<String> mArrayList;
    private int mSelectionLocation; //选着的位置

    private OnItemClickListener mOnItemClickListener;

    public MortgageTopSortFilterSelectAdapter(Context context, ArrayList<String> arrayList, int location) {
        mContext = context;
        mArrayList = arrayList;
        mSelectionLocation = location;
    }

    @NonNull
    @Override
    public MyVierHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_left_filter_seleter, viewGroup, false);
        return new MyVierHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVierHolder myVierHolder, final int i) {
        if (mSelectionLocation == i){
            myVierHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }
        myVierHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(i, mArrayList.get(i));
            }
        });
        myVierHolder.itemNameTv.setText(mArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class MyVierHolder extends RecyclerView.ViewHolder {
        TextView itemNameTv;

        public MyVierHolder(View view) {
            super(view);
            itemNameTv = view.findViewById(R.id.item_name_tv);
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        if (listener != null){
            mOnItemClickListener = listener;
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int postion, String name);
    }



}















