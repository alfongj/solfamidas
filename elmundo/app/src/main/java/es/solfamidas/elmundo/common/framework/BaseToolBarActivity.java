package es.solfamidas.elmundo.common.framework;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import es.solfamidas.elmundo.R;


public abstract class BaseToolBarActivity
        extends ActionBarActivity
        implements BaseDIActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectModuleDependencies();

        setContentView(getLayoutResource());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * @return ID of the layout resource to inflate as root of the activity. Must contain an element
     * with the ID {@code R.id.toolbar}, representing the toolbar.
     */
    protected abstract int getLayoutResource();

    protected void setActionBarIcon(int iconRes) {
        mToolbar.setNavigationIcon(iconRes);
    }

    protected void setActionBarNavigationClickListener(View.OnClickListener cl) {
        mToolbar.setNavigationOnClickListener(cl);
    }

    protected void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
