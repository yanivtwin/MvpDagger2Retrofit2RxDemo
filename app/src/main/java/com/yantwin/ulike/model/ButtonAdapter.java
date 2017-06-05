package com.yantwin.ulike.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yantwin.ulike.R;

import java.util.ArrayList;
import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.Holder> {

    private final LayoutInflater mInflater;
    private List<ButtonResponse> mButtonList;
    private ButtonClickListener mListener;

    public ButtonAdapter(ButtonClickListener listener, LayoutInflater inflater) {
        mListener = listener;
        mInflater = inflater;
        mButtonList = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mInflater.inflate(R.layout.item_button, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ButtonResponse currButton = mButtonList.get(position);

        holder.mType.setText(currButton.getType());
        holder.mEnabled.setText( Boolean.toString(currButton.getEnabled()));
        holder.mCoolDown.setText( Long.toString(currButton.getCool_down()));

    }

    @Override
    public int getItemCount() {
        return mButtonList.size();
    }

    public void addButtons(List<ButtonResponse> ButtonResponses) {
        mButtonList.addAll(ButtonResponses);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mType, mEnabled,mCoolDown;

        public Holder(View itemView) {
            super(itemView);
            mCoolDown = (TextView) itemView.findViewById(R.id.buttonCoolDown);
            mType = (TextView) itemView.findViewById(R.id.buttonType);
            mEnabled = (TextView) itemView.findViewById(R.id.buttonEnabled);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getLayoutPosition(), mButtonList.get(getAdapterPosition()).getType());
        }
    }

    public interface ButtonClickListener {

        void onClick(int position, String name);
    }
}
