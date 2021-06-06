package com.example.secondexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {
    private final List<State> states;

    public StateAdapter(List<State> states) {
        this.states = states;
    }

    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {     //  возвращает один объект хранящий 1 элемент списка
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.recycler_view_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {        // привязка к выбранному элементу и его ресурсам
        State state = states.get(position);     // ох тут нихуя магия отрабатывает
        holder.getTextView().setText(state.getModel());
        holder.getImageView().setImageResource(state.getFlagResourse());
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.img);
            this.textView = (TextView) view.findViewById(R.id.descr);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
