package com.south.openmrs.doctorsms;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.LruCache;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by angel on 1/2/16.
 */
public class MainViewFragment extends  android.support.v4.app.Fragment {

    ArrayList<Item> postList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private LruCache<String,Bitmap> mMemoryCache;
    private Context context;
    View containerView;


    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        context = getActivity();
        postList = new ArrayList<Item>();


        // get max available VM memory, exceeding this amoung will throw
        // an OutOfMemory exception. Stored in kilobytes as Lrucache takes an
        // int in its constructor

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()) / 1024;

        //use 1/8 of the available memory for this memory cache
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap){
                // the cache size will be measured in kilobytes rather
                // than number of items
                return bitmap.getByteCount() / 1024;

            }

        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_main, container,false);
        //final PopupWindow popup = new PopupWindow( popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        //popup.setAnimationStyle(R.style.PopupAnimation);
        containerView = v;
        ImageButton browseGalleryButton  = (ImageButton) v.findViewById(R.id.browse_gallery);
        browseGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity() ).popup();
            }
        });

        ImageButton submitPostButton = (ImageButton) v.findViewById(R.id.send_message);
        submitPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });




        init(v);

        return v;
    }

    private void init(View v){
        mRecyclerView = (RecyclerView)     v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(postList, context, mMemoryCache);
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent event) {
                int actionType = event.getAction();

                int putItHere = ContextCompat.getColor(getContext(), R.color.recycler_paste_action_drag_entered);
                int dropItHere = ContextCompat.getColor(getContext(), R.color.paste_action_drag_entered);
                int defaultColor= 0xFF35302C;

                //Drawable bg = DrawableCompat.wrap(view.getBackground());
                //PorterDuffColorFilter filter_drag =   new PorterDuffColorFilter(putItHere, PorterDuff.Mode.SRC_ATOP);
                //PorterDuffColorFilter filter_drop =   new PorterDuffColorFilter(dropItHere, PorterDuff.Mode.SRC_ATOP);

                switch (actionType) {

                    case (DragEvent.ACTION_DRAG_STARTED):

                        view.setBackgroundColor(putItHere);
                        return true;

                    case (DragEvent.ACTION_DRAG_ENTERED):
                       view.setBackgroundColor(dropItHere);

                        return true;

                    case (DragEvent.ACTION_DRAG_EXITED):
                        view.setBackgroundColor(putItHere);
                        view.invalidate();
                        return true;

                    case (DragEvent.ACTION_DRAG_ENDED):
                        view.setBackgroundColor(defaultColor);
                        view.invalidate();
                        return true;

                    case (DragEvent.ACTION_DROP):
                        view.setBackgroundColor(defaultColor);
                        ClipData.Item item = event.getClipData().getItemAt(0);
                        Uri uri = item.getUri();
                        ImageItem image = new ImageItem(null, uri);
                        postList.add(image);
                        mAdapter.notifyDataSetChanged();
                        return true;

                }


                return true;
            }
        });

        final EditText attachMsg = (EditText)v.findViewById(R.id.edit_message);

        attachMsg.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent event) {
                int actionType = event.getAction();


                int putItHere = ContextCompat.getColor(getContext(), R.color.paste_action_drag_started);
                int dropItHere = ContextCompat.getColor(getContext(), R.color.paste_action_drag_entered);


                switch(actionType){


                    // context.getColor(R. id. whatever) is being deprecated in Android M
                    // use ContextCompat (as below) instead
                    case (DragEvent.ACTION_DRAG_STARTED):
                        view.setBackgroundColor(putItHere);
                        view.invalidate();
                        return true;

                    case (DragEvent.ACTION_DRAG_ENTERED):
                        view.setBackgroundColor(dropItHere);
                        view.invalidate();
                        return true;

                    case (DragEvent.ACTION_DRAG_EXITED):
                        view.setBackgroundColor(putItHere);
                        view.invalidate();
                        return true;

                    case (DragEvent.ACTION_DRAG_ENDED):
                        view.setBackgroundColor(Color.WHITE);
                        view.invalidate();
                        return true;

                    case (DragEvent.ACTION_DROP):

                        ClipData.Item item = event.getClipData().getItemAt(0);
                        Uri uri = item.getUri();
                        ImageItem image = new ImageItem(null,uri);
                        String t = attachMsg.getText().toString().trim();
                        if(t.length() > 0){

                            Time time = new Time();
                            time.setToNow();

                            MessageItem msg = new MessageItem("Angel",t,"General",time);

                            image.optional_message = msg;
                            attachMsg.setText("");

                        }

                        postList.add(image);
                        mAdapter.notifyDataSetChanged();
                        view.setBackgroundColor(Color.WHITE);
                        return true;

                }


                return true;
            }
        });

        mAdapter.notifyDataSetChanged();
    }

    public void sendMessage() {
        EditText editText = (EditText) containerView.findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        Time time = new Time();
        time.setToNow();
        MessageItem post = new MessageItem("Angel", message, "General", time);

        postList.add(post);
        mAdapter.notifyDataSetChanged();

        editText.setText("");
    }

}
