package es.solfamidas.elmundo.home.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static es.solfamidas.elmundo.home.ui.fragments.ArticleFeedFragment.FeedType;

import es.solfamidas.elmundo.home.ui.fragments.ArticleFeedFragment;

/**
 * Contains the article categories.
 */
public class ArticleFeedPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TITLES = {
            "Albumes",
            "Ciencia",
            "Espana",
            "Economia",
            "Internacional",
            "Local",
            "Mi Mundo"};



    public ArticleFeedPagerAdapter(
            FragmentManager fragmentManager
    ) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment feedFragment = new ArticleFeedFragment();
        Bundle args = new Bundle();
        args.putSerializable(
                ArticleFeedFragment.FEED_TYPE_ARG,
                getFeedTypeByTabIndex(position));
        feedFragment.setArguments(args);

        return feedFragment;
    }



    private static FeedType getFeedTypeByTabIndex(int i) {
        return new FeedType[] {
                FeedType.ALBUMES,
                FeedType.CIENCIA,
                FeedType.ESPANA,
                FeedType.ECONOMIA,
                FeedType.INTERNACIONAL,
                FeedType.LOCAL,
                FeedType.MI_MUNDO
        }[i];
    }

}
