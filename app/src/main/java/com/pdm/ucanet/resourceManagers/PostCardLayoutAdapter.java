package com.pdm.ucanet.resourceManagers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.text.Spanned;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.pdm.ucanet.R;
import com.pdm.ucanet.abstractEntities.Post;
import com.pdm.ucanet.abstractEntities.Thread;

import java.util.ArrayList;

import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * Created by Crash on 06/06/2017.
 */

public class PostCardLayoutAdapter extends CustomRecyclerViewAdapter {
    private Activity activity;
    private final ArrayList<Post> posts;
    private FlowTextView flowTextView;

    public PostCardLayoutAdapter(Activity activity, ArrayList<Post> posts) {
        this.activity = activity;
        this.posts = posts;
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
    }

    @Override
    public PostCardLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.post_detail_cardview, parent, false);
        flowTextView = (FlowTextView) view.findViewById(R.id.ftv);
        return new PostCardLayoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomRecycleViewHolder holder, final int position) {


        final PostCardLayoutAdapter.ViewHolder myHolder = (PostCardLayoutAdapter.ViewHolder) holder;
        myHolder.pItem = posts.get(position);
        flowTextView.setText(myHolder.pItem.getContent());
        //myHolder.postName.setText(posts.get(position).getTitle());

        /*myHolder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, CourseActivity.class);
                intent.putExtra(CourseActivity.CURRENT_COURSE, ((CourseCardLayoutAdapter.ViewHolder) holder).cItem.getCourseName());
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends CustomRecycleViewHolder {
        public final View mView;
        public TextView postContent;
        public Post pItem;
        //public Button description;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            //postContent = (TextView) itemView.findViewById(R.id.post_titulo_text_view);
            //description = (Button) itemView.findViewById(R.id.button);
        }


    }
}
