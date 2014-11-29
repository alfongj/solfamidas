package es.solfamidas.elmundo.home.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static es.solfamidas.elmundo.common.datasource.ElMundoDataSource.Category;

import es.solfamidas.elmundo.home.presenter.HomePresenter;
import es.solfamidas.elmundo.home.ui.fragments.ArticleFeedFragment;

/**
 * Contains the article categories.
 */
public class ArticleFeedPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TITLES = {
            "Ciencia",
            "Espana",
            "Economia",
            "Internacional",
            "Mi Mundo"};

    // Injected vars
    private final HomePresenter mHomePresenter;



    public ArticleFeedPagerAdapter(
            FragmentManager fragmentManager,
            HomePresenter homePresenter
    ) {
        super(fragmentManager);
        mHomePresenter = homePresenter;

        // mCachedFragments = new ArrayList<WeakReference<Fragment>>();
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
        ArticleFeedFragment articleFeedFragment =
                ArticleFeedFragment.newInstance(getCategoryByTabIndex(position));
        articleFeedFragment.setPresenter(mHomePresenter);

        return articleFeedFragment;
    }

    private static Category getCategoryByTabIndex(int i) {
        return new Category[] {
                Category.CIENCIA,
                Category.ESPANA,
                Category.ECONOMIA,
                Category.INTERNACIONAL,
                Category.MI_MUNDO
        }[i];
    }
}
