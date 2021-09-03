package com.example.afishaandroidapp.util;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afishaandroidapp.R;
import com.example.afishaandroidapp.dao.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    public static DateFormat df = new SimpleDateFormat("d MMMM", new Locale("ru"));

    public interface OnItemClickListener {
        void onItemClick(Event event, int position);
    }

    private final OnItemClickListener onItemClickListener;
    private final List<Event> events;

    public EventAdapter(OnItemClickListener onItemClickListener, List<Event> events) {
        this.onItemClickListener = onItemClickListener;
        this.events = events;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.recycler_view_item, parent, false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.getImageView().setImageResource(event.getFlagResImg());
        holder.getTextViewPrice().setText("от " + event.getPrice());
        holder.getTextViewName().setText(String.valueOf(event.getName()));
        holder.getTextViewDate().setText(df.format(event.getDate()));
        holder.getTextViewType().setText(String.valueOf(event.getType().getName()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(event, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textViewPrice;
        private final TextView textViewName;
        private final TextView textViewDate;
        private final TextView textViewType;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.img);
            this.textViewPrice = (TextView) view.findViewById(R.id.textViewPrice);
            this.textViewName = (TextView) view.findViewById(R.id.textViewName);
            this.textViewDate = (TextView) view.findViewById(R.id.textViewDatetime);
            this.textViewType = (TextView) view.findViewById(R.id.textViewType);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewPrice() {
            return textViewPrice;
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewDate() {
            return textViewDate;
        }

        public TextView getTextViewType() {
            return textViewType;
        }
    }
}
