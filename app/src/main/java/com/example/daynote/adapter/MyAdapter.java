package com.example.daynote.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daynote.AddActivity;
import com.example.daynote.EidtActivity;
import com.example.daynote.NoteDbOpenHelper;
import com.example.daynote.R;
import com.example.daynote.bean.Note;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Note> mBeanList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private NoteDbOpenHelper mDbOpenHelper;

    public MyAdapter(Context context, List<Note> beanList){
        this.mBeanList = beanList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mDbOpenHelper = new NoteDbOpenHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = mBeanList.get(position);
        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContent.setText(note.getContent());
        holder.mTvTime.setText(note.getCreatedTime());
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EidtActivity.class);
                intent.putExtra("note", note);
                mContext.startActivity(intent);
            }
        });
        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO 长按删除或编辑
                Dialog dialog = new Dialog(mContext, com.google.android.material.R.style.ThemeOverlay_Material3_Light_Dialog_Alert_Framework);
                View view = mLayoutInflater.inflate(R.layout.list_item_dialog_layout, null);
                View tvDelete = view.findViewById(R.id.tv_delete);
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 删除
                        int row = mDbOpenHelper.deleteData(note.getId());
                        if(row > 0){
                            deleteData(position);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(view);
                dialog.show();
                return false;
            }
        });
    }


    public void deleteData(int pos){
        mBeanList.remove(pos);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    public void refreshData(List<Note> beanList){
        //更新数据
        this.mBeanList = beanList;
        // 通知适配器数据源已更改，以便更新视图
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTvTitle;
        TextView mTvContent;
        TextView mTvTime;
        ViewGroup rlContainer;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvTime = itemView.findViewById(R.id.tv_time);
            rlContainer = itemView.findViewById(R.id.list_item_layout);
        }
    }

}
