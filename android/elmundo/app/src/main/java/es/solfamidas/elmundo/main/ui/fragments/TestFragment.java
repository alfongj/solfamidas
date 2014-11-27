package es.solfamidas.elmundo.main.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.solfamidas.elmundo.R;

public class TestFragment extends Fragment {

    View mRootView;
    GridView mGridView;

    public TestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        //TODO test image urls
        ArrayList<String> testImageUrls = new ArrayList<String>();
        testImageUrls.add("https://farm4.staticflickr.com/3617/3522372425_71e3dcc4a9_m.jpg");
        testImageUrls.add("https://farm4.staticflickr.com/3617/3522372425_71e3dcc4a9_m.jpg");
        testImageUrls.add("https://farm4.staticflickr.com/3617/3522372425_71e3dcc4a9_m.jpg");
        testImageUrls.add("https://farm4.staticflickr.com/3617/3522372425_71e3dcc4a9_m.jpg");
        testImageUrls.add("https://farm4.staticflickr.com/3617/3522372425_71e3dcc4a9_m.jpg");
        testImageUrls.add("https://farm4.staticflickr.com/3617/3522372425_71e3dcc4a9_m.jpg");
        testImageUrls.add("https://farm4.staticflickr.com/3617/3522372425_71e3dcc4a9_m.jpg");

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_prueba, container, false);
            mGridView = (GridView) mRootView.findViewById(R.id.gridview);
            mGridView.setAdapter(new FlickrAdapter(getActivity(), testImageUrls));
        }
        return mRootView;
    }

    public class FlickrAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mData;

        public FlickrAdapter(Context c, ArrayList<String> data) {
            mContext = c;
            this.mData = data;
        }

        public int getCount() {
            return mData.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }

            Picasso.with(mContext).load(mData.get(position)).into(imageView);

            return imageView;
        }
    }

}
