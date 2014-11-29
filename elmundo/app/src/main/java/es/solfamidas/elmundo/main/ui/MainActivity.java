package es.solfamidas.elmundo.main.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import es.solfamidas.elmundo.R;
import es.solfamidas.elmundo.common.framework.BaseToolBarActivity;
import es.solfamidas.elmundo.home.presenter.HomePresenter;
import es.solfamidas.elmundo.main.ui.fragments.CardsFragment;
import es.solfamidas.elmundo.main.ui.fragments.TestFragment;

import static android.view.View.OnClickListener;


public class MainActivity
        extends BaseToolBarActivity
        implements MainUi {

    private DrawerLayout mDrawer;

    // Injected vars
    private HomePresenter mPresenter;



    @Override
    public void injectModuleDependencies() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDrawer();
        setupToolBar();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new TestFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.onResume();
    }

    private void setupDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
        ListView drawerList = (ListView) findViewById(R.id.left_drawer_listview);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.drawer_titles)));

        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void setupToolBar() {
        setActionBarIcon(R.drawable.ic_ab_drawer);
        setActionBarNavigationClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                mDrawer.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Adds items to the action bar if it is present.
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (position) {
                case 0:
                    transaction.replace(R.id.container, new TestFragment());
                    break;
                case 1:
                    transaction.replace(R.id.container, new CardsFragment());
                    break;
            }
            transaction.addToBackStack(null);
            transaction.commit();
            mDrawer.closeDrawer(Gravity.LEFT);
        }
    }
}
