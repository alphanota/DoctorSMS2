package com.south.openmrs.doctorsms;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by angel on 1/2/16.
 */
public class GalleryFragment extends android.support.v4.app.DialogFragment {

    private ArrayList<Item> images;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LruCache<String,Bitmap> mMemoryCache;
    private Context context;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        images = new ArrayList<Item>();

        context = getActivity();

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
        //specify an adapter
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.browse_gallery, container, false);
        //final PopupWindow popup = new PopupWindow( popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        //popup.setAnimationStyle(R.style.PopupAnimation);
        ImageButton dismiss = (ImageButton)v.findViewById(R.id.dismiss_button);

        dismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).hideGallery();

            }

        });

        init(v);
        return v;
    }



    private void init(View v){
        mRecyclerView = (RecyclerView)     v.findViewById(R.id.gallery_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(images, context, mMemoryCache);
        mRecyclerView.setAdapter(mAdapter);

        ArrayList<ImageItem> photoItems = ImageManager.getThumbnails(context);
        images.addAll(photoItems);
        mAdapter.notifyDataSetChanged();




    }







}
