package com.pdm.ucanet.resourceManagers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.Spanned;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pdm.ucanet.R;
import com.pdm.ucanet.ThreadActivity;
import com.pdm.ucanet.abstractEntities.Post;
import com.pdm.ucanet.abstractEntities.Thread;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * Created by Crash on 06/06/2017.
 */

public class PostCardLayoutAdapter extends CustomRecyclerViewAdapter {
    private Activity activity;
    private final ArrayList<Post> posts;
    private FlowTextView flowTextView;
    private ImageButton imageButton;


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
        imageButton = (ImageButton) view.findViewById(R.id.thumb_button_1);

        return new PostCardLayoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomRecycleViewHolder holder, final int position) {
        final PostCardLayoutAdapter.ViewHolder myHolder = (PostCardLayoutAdapter.ViewHolder) holder;

        myHolder.pItem = posts.get(position);
        flowTextView.setText(myHolder.pItem.getContent());
        myHolder.userPost.setText(posts.get(position).getUserName());
        myHolder.userIdPost.setText(posts.get(position).getUser());
        myHolder.creationPost.setText(posts.get(position).getDate());
        Log.d("visibilityImage", String.valueOf(myHolder.pItem.isImgFlag()));

        Picasso.with(activity).load("https://mapacheproject.xyz/UCAnet/resources/images/"+posts.get(position).getImageName()).into(myHolder.imgButton);


        if(!myHolder.pItem.isImgFlag()){
            imageButton.setVisibility(View.GONE);
        }
        else{
            Log.d("visibilityImage", myHolder.pItem.getImageName());

        }


        myHolder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ImageButton stuff = (ImageButton) view.findViewById(R.id.thumb_button_1);
                //stuff.setTag(R.mipmap.ic_launcher);

                LayoutInflater inflater = activity.getLayoutInflater();
                View imageDialog = inflater.inflate(R.layout.dialog_image, null);
                Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(imageDialog);
                dialog.setCancelable(true);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                ImageView imgImage = (ImageView) imageDialog.findViewById(R.id.imgImage);
                Picasso.with(activity).load("https://mapacheproject.xyz/UCAnet/resources/images/"+posts.get(position).getImageName()).into(imgImage);

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private class ViewHolder extends CustomRecycleViewHolder {
        private final View mView;
        private TextView userPost;
        private TextView userIdPost;
        private TextView creationPost;
        private Post pItem;
        private ImageButton imgButton;

        //public Button description;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            userPost = (TextView) itemView.findViewById(R.id.userPostText);
            userIdPost = (TextView) itemView.findViewById(R.id.userIdPostText);
            creationPost = (TextView) itemView.findViewById(R.id.creationPostText);
            imgButton = (ImageButton) itemView.findViewById(R.id.thumb_button_1);

        }
    }
}
