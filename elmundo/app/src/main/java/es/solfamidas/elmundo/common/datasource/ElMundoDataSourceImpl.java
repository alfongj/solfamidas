package es.solfamidas.elmundo.common.datasource;

import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import java.lang.reflect.Type;
import java.util.List;

import es.solfamidas.elmundo.entities.Article;
import es.solfamidas.elmundo.utils.ThreadUtils;

/**
 * Fetches info from El Mundo.
 */
public class ElMundoDataSourceImpl implements ElMundoDataSource {

    private static final String TAG = ElMundoDataSourceImpl.class.getSimpleName();

    // Injected vars
    private final Handler mHandler;
    private final HttpClient mHttpClient;



    public ElMundoDataSourceImpl(
            Handler handler,
            HttpClient httpClient
    ) {
        mHandler = handler;
        mHttpClient = httpClient;

        if (!ThreadUtils.assertIsUiHandler(handler)) {
            Log.e(TAG, "Handler should be attached to UI thread");
        }
    }

    @Override
    public void getArticles(
            final Category cat,
            final int limit,
            final int from,
            final ArticlesCallback callback) {
        Thread background = new Thread(new Runnable() {
            public void run() {
                try {
                    Gson gson = new Gson();
                    String responseString;
                    HttpGet httpget = new HttpGet(buildGetArticlesUrl(cat, limit, from));
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    responseString = mHttpClient.execute(httpget, responseHandler);

                    // Convert json into POJO
                    Type listType = new TypeToken<List<Article>>(){}.getType();
                    final List<Article> articleList = gson.fromJson(responseString, listType);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(articleList);
                        }
                    });
                } catch (Throwable t) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError("Error connecting with server");
                        }
                    });
                }
            }
        });
        background.start();
    }

    @Override
    public void getArticles(Category cat, int limit, ArticlesCallback callback) {
        getArticles(cat, limit, 0, callback);
    }

    /**
     * Build URL to request articles.
     *
     * @return request url
     */
    private String buildGetArticlesUrl(Category cat, int limit, int from) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("solfamidas.herokuapp.com")
                .appendPath("articles")
                .appendPath(getCategoryName(cat))
                .appendQueryParameter("from", String.valueOf(from))
                .appendQueryParameter("limit", String.valueOf(limit));

        return builder.build().toString();
    }

    /**
     * @param cat
     * @return Category name or "espana" by default.
     */
    private String getCategoryName(Category cat) {
        switch (cat) {
            case ALBUMES:
                return "albumes";
            case CIENCIA:
                return  "ciencia";
            case ECONOMIA:
                return "economia";
            case ESPANA:
                return "espana";
            case INTERNACIONAL:
                return "internacional";
            default:
                return "espana";
        }
    }

}
