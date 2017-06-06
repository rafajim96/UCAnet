package com.pdm.ucanet.resourceManagers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.pdm.ucanet.CourseActivity;
import com.pdm.ucanet.R;
import com.pdm.ucanet.ThreadActivity;
import com.pdm.ucanet.abstractEntities.Course;
import com.pdm.ucanet.abstractEntities.Thread;

import java.util.ArrayList;

/**
 * Created by Crash on 06/06/2017.
 */

public class ThreadCardLayoutAdapter extends CustomRecyclerViewAdapter {
    private Activity activity;
    private final ArrayList<Thread> threads;

    public ThreadCardLayoutAdapter(Activity activity, ArrayList<Thread> threads) {
        this.activity = activity;
        this.threads = threads;
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
    }

    @Override
    public ThreadCardLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.thread_detail_cardview, parent, false);
        return new ThreadCardLayoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomRecycleViewHolder holder, final int position) {


        final ThreadCardLayoutAdapter.ViewHolder myHolder = (ThreadCardLayoutAdapter.ViewHolder) holder;
        myHolder.tItem = threads.get(position);
        myHolder.threadName.setText(threads.get(position).getTitle());

        myHolder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ThreadActivity.class);
                intent.putExtra(ThreadActivity.CURRENT_THREAD, ((ThreadCardLayoutAdapter.ViewHolder) holder).tItem.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }

    public class ViewHolder extends CustomRecycleViewHolder {
        public final View mView;
        public TextView threadName;
        public Thread tItem;
        public Button description;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            threadName = (TextView) itemView.findViewById(R.id.thread_titulo_text_view);
            description = (Button) itemView.findViewById(R.id.button);
        }


    }
}
