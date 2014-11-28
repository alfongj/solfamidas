package es.solfamidas.elmundo.home.presenter;

import es.solfamidas.elmundo.main.ui.MainUi;

/**
 * TODO: Do something
 */
public class HomePresenterImpl implements HomePresenter {

    // Injected vars
    MainUi mUi;


    public HomePresenterImpl(
            MainUi ui
    ) {
        mUi = ui;
    }
}
