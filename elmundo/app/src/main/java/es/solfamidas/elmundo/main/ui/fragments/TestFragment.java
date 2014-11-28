package es.solfamidas.elmundo.main.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.solfamidas.elmundo.R;
import es.solfamidas.elmundo.common.datasource.FlickrDataSource;
import es.solfamidas.elmundo.common.datasource.FlickrDataSourceImpl;

public class TestFragment extends Fragment {

    View mRootView;
    GridView mGridView;

    public TestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        //TODO test, delete
        FlickrDataSourceImpl f = new FlickrDataSourceImpl();
        f.getFlickrImagesByTag("cristiano ronaldo", 100, new FlickrDataSource.ImagesByTagCallback() {
            @Override
            public void onSuccess(ArrayList<String> imageUrls) {
                mGridView.setAdapter(new FlickrAdapter(getActivity(), imageUrls));
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_prueba, container, false);
            mGridView = (GridView) mRootView.findViewById(R.id.gridview);
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

            Picasso.with(mContext).load(mData.get(position)).resize(100, 100).centerCrop().into(imageView);

            return imageView;
        }
    }

}
