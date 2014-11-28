package es.solfamidas.elmundo.main.presenter;

import es.solfamidas.elmundo.main.ui.MainUi;

/**
 * TODO: Do something
 */
public class MainPresenterImpl implements MainPresenter {

    // Injected vars
    MainUi mUi;


    public MainPresenterImpl(
            MainUi ui
    ) {
        mUi = ui;
    }
}
