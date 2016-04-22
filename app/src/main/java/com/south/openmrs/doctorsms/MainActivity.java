package com.south.openmrs.doctorsms;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

;

public class MainActivity extends AppCompatActivity implements ImageFragment.OnFragmentInteractionListener {

    ArrayList<Item> postList;
    ArrayList<Item> images;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private LruCache<String,Bitmap> mMemoryCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_main);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_view, new MainViewFragment(), "MAINVIEW");
        ft.addToBackStack("FIRSTVIEW");
        ft.commit();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void popup() {
        /**
        FrameLayout mainFrame = (FrameLayout)findViewById(R.id.main_view);
        RelativeLayout.LayoutParams mainFrameParams = (RelativeLayout.LayoutParams)mainFrame.
                getLayoutParams();
        System.out.println(mainFrameParams.height);
        int currentOrientation;
        currentOrientation = getResources().getConfiguration().orientation;

        int newHeight = R.dimen.activity_main_view_gallery_visible_height_portrait;
        if( currentOrientation == getResources().getConfiguration().ORIENTATION_PORTRAIT){
            mainFrameParams.height = getResources().getDimensionPixelSize(
                    R.dimen.activity_main_view_gallery_visible_height_portrait);

        }
        else if(currentOrientation == getResources().getConfiguration().ORIENTATION_LANDSCAPE){
            mainFrameParams.height = getResources().getDimensionPixelSize(
                    R.dimen.activity_main_view_gallery_visible_height_landscape);
        }
        else{
            mainFrameParams.height = getResources().getDimensionPixelSize(
                    R.dimen.activity_main_view_gallery_visible_height_portrait);
        }

        mainFrameParams.height = getResources().getDimensionPixelSize(newHeight);
        mainFrame.setLayoutParams(mainFrameParams);

        //FrameLayout galleryFrame = (FrameLayout)findViewById(R.id.images);
        //RelativeLayout.LayoutParams galleryFrameParams = (RelativeLayout.LayoutParams)mainFrame.getLayoutParams();
        //galleryFrameParams.height=200;
        //galleryFrame.setLayoutParams(galleryFrameParams);

        **/
        final DialogFragment galleryFragment = new GalleryFragment();
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ArrayList<ImageItem> photoItems = ImageManager.getThumbnails(this);
        ft.add(R.id.images, galleryFragment, "GALLERYFRAGMENT").commit();


        //mainFrame.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        //postList.addAll(photoItems);
        //for(int i =0; i < 2; i++){
        //   postList.add(photoItems.get(i));
        //}

        //postList.addAll(photoItems);
        //mAdapter.notifyDataSetChanged();


    }

    public void hideGallery(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("GALLERYFRAGMENT");
        if(prev != null){
            ft.remove(prev).commit();
        }
        else{
            System.out.println("The fragment was null!!!");
        }
        /**
        FrameLayout mainFrame = (FrameLayout)findViewById(R.id.main_view);
        RelativeLayout.LayoutParams mainFrameParams = (RelativeLayout.LayoutParams)mainFrame.getLayoutParams();
        mainFrameParams.height=RelativeLayout.LayoutParams.WRAP_CONTENT;
        mainFrame.setLayoutParams(mainFrameParams);
        **/
    }


    // When you override a method... make sure to add public in the method used to override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.parlanto.parlanto/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.parlanto.parlanto/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
