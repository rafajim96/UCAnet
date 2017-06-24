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





        // Hook up clicks on the thumbnail views.
        final View thumb1View = view.findViewById(R.id.thumb_button_1);
        thumb1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageButton stuff = (ImageButton) view.findViewById(R.id.thumb_button_1);
                stuff.setTag(R.mipmap.ic_launcher);

                LayoutInflater inflater = activity.getLayoutInflater();
                View imageDialog = inflater.inflate(R.layout.dialog_image, null);
                Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(imageDialog);
                dialog.setCancelable(true);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                ImageView imgImage = (ImageView) imageDialog.findViewById(R.id.imgImage);
                imgImage.setImageResource(R.drawable.ucalogos);

                dialog.show();
            }
        });


        return new PostCardLayoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomRecycleViewHolder holder, final int position) {


        final PostCardLayoutAdapter.ViewHolder myHolder = (PostCardLayoutAdapter.ViewHolder) holder;
        myHolder.pItem = posts.get(position);
        flowTextView.setText(myHolder.pItem.getContent());
        Log.d("visibilityImage", String.valueOf(myHolder.pItem.isImgFlag()));
        if(!myHolder.pItem.isImgFlag()){
            imageButton.setVisibility(View.GONE);
        }
        else{
            Log.d("visibilityImage", myHolder.pItem.getImageName());

        }
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
