package es.solfamidas.elmundo.home.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.solfamidas.elmundo.R;

/**
 * Contains a feed of cards of a given category
 */
public class ArticleFeedFragment extends Fragment {

    public static final String FEED_TYPE_ARG = "FEED_TYPE";

    public static enum FeedType {
        ALBUMES,
        CIENCIA,
        ESPANA,
        ECONOMIA,
        INTERNACIONAL,
        LOCAL,
        MI_MUNDO
    }

    private FeedType mFeedType;

    // Views
    private TextView dummy;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFeedType = (FeedType) getArguments().getSerializable(FEED_TYPE_ARG);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);

        View root = inflater.inflate(R.layout.fragment_article_feed, container, false);
        dummy = (TextView) root.findViewById(R.id.dummy);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        dummy.setText(mFeedType.toString());
        dummy.setVisibility(View.VISIBLE);
    }
}
