package es.solfamidas.elmundo.common.framework;

/**
 * Activity which will act as a dependency injector for other classes.
 */
public interface BaseDIActivity {

    /**
     * Injects the dependencies of the module.
     */
    void injectModuleDependencies();

}
