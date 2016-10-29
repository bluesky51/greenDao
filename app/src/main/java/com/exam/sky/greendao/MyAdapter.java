package com.exam.sky.greendao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exam.sky.greendao.bean.Product;
import com.exam.sky.greendao.callback.OnItemClickListener;
import com.exam.sky.greendao.callback.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bluesky on 16/10/19.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Product>    productList =new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    OnItemClickListener mOnItemClickListener;
    OnItemLongClickListener mOnItemLongClickListener;

    public MyAdapter(Context context) {

        this.context = context;
       inflater =  LayoutInflater.from(context);
    }



    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view  = inflater.inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(productList.get(position).getName());
        //取出最新的位置
        final int pos = holder.getLayoutPosition();
        //整条item的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.setOnItemClickListener(pos);
                }

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener!=null){
                    mOnItemLongClickListener.setOnItemLongClickListener(pos,productList.get(position));
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
