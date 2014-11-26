package es.solfamidas.elmundo;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import static android.view.View.OnClickListener;


public class MainActivity extends BaseToolBarActivity {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //drawer
        setupDrawer();


        //toolbar
        setupToolBar();


        getFragmentManager().beginTransaction()
                .add(R.id.container, new TestFragment())
                .commit();


    }

    private void setupDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
        ListView drawerList = (ListView) findViewById(R.id.left_drawer_listview);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.drawer_titles)));

        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


    }

    private void setupToolBar(){
        setActionBarIcon(R.drawable.ic_ab_drawer);
        setActionBarNavigationClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            switch (position) {
                case 0:

                    transaction.replace(R.id.container, new TestFragment());
                    break;
                case 1:

                    transaction.replace(R.id.container, new TestFragment());
                    break;
            }

            transaction.addToBackStack(null);

            transaction.commit();

            drawer.closeDrawer(Gravity.LEFT);

        }
    }
}
