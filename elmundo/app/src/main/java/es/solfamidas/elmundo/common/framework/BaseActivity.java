package es.solfamidas.elmundo.common.framework;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public abstract class BaseActivity
        extends ActionBarActivity
        implements BaseDIActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectModuleDependencies();

        setContentView(getLayoutResource());

    }

    /**
     * @return ID of the layout resource to inflate as root of the activity. Must contain an element
     * with the ID {@code R.id.toolbar}, representing the toolbar.
     */
    protected abstract int getLayoutResource();

}
