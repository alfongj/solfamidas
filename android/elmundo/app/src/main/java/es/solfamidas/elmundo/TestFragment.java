package es.solfamidas.elmundo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by carlos on 15/11/14.
 */
public class TestFragment extends Fragment {

    View rootView;

    public TestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_prueba, container, false);
        }
        return rootView;
    }
}
