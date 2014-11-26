package es.solfamidas.elmundo.main.presenter;

import es.solfamidas.elmundo.main.datasource.MainDatasource;
import es.solfamidas.elmundo.main.ui.MainUi;

/**
 * TODO: Do something
 */
public class MainPresenterImpl implements MainPresenter {

    // Injected vars
    MainDatasource mDatasource;
    MainUi mUi;



    public MainPresenterImpl(
            MainDatasource datasource,
            MainUi ui
    ) {
        mDatasource = datasource;
        mUi = ui;
    }
}
