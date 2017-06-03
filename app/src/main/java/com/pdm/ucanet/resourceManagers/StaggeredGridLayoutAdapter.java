package com.pdm.ucanet.resourceManagers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.CardView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdm.ucanet.R;
import com.pdm.ucanet.abstractEntities.Course;

import java.util.ArrayList;

/**
 * Created by Crash on 02/06/2017.
 */

public class StaggeredGridLayoutAdapter extends CustomRecyclerViewAdapter {
    private Activity activity;
    private final ArrayList<Course> courses;

    public StaggeredGridLayoutAdapter(Activity activity, ArrayList<Course> courses) {
        this.activity = activity;
        this.courses = courses;
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.course_detail_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomRecycleViewHolder holder, final int position) {


        final ViewHolder myHolder = (ViewHolder) holder;
        myHolder.cItem = courses.get(position);
        //myHolder.poster.setImageResource(mangas.get(position).getImgId());
        myHolder.courseName.setText(courses.get(position).getCourseName());

        myHolder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Context context = v.getContext();
                Intent intent = new Intent(context, MangaDetailActivity.class);
                intent.putExtra(MangaDetailFragment.ARG_ITEM_ID, ((ViewHolder) holder).mItem.getName());
                context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends CustomRecycleViewHolder {
        public final View mView;
        public TextView courseName;
        public Course cItem;
        public Button description;

        //public ImageView poster;
        //public TextView title;
        //private CardView cardView;
        //public Button description;
        //public Manga mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            courseName = (TextView) itemView.findViewById(R.id.course_titulo_text_view);
            description = (Button) itemView.findViewById(R.id.button);

            //poster = (ImageView) itemView.findViewById(R.id.manga_poster_image_view);
            //title = (TextView) itemView.findViewById(R.id.manga_titulo_text_view);
            //cardView = (CardView) itemView.findViewById(R.id.cardViewManga);

        }


    }
}
