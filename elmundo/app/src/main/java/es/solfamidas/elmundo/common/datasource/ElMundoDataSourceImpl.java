package es.solfamidas.elmundo.common.datasource;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

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
    private final Context mContext;



    public ElMundoDataSourceImpl(
            Context context
    ) {
        mContext = context;
    }

    @Override
    public void getArticles(
            final Category cat,
            final int limit,
            final int from,
            final ArticlesCallback callback) {
        final String url = buildGetArticlesUrl(cat, limit, from);
        try {
            Ion.with(mContext)
                    .load(url)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            if (!ThreadUtils.assertInUIThread()) {
                                throw new RuntimeException("NOT IN UI THREAD MADAFAKA");
                            }

                            if (e == null) {
                                Type listType = new TypeToken<List<Article>>() {
                                }.getType();
                                final List<Article> articleList =
                                        new Gson().fromJson(result, listType);
                                if (articleList == null) {
                                    callback.onError("Received empty article list for " +
                                                    buildGetArticlesUrl(cat, limit, from));
                                } else {
                                    callback.onSuccess(articleList);
                                }
                            } else {
                                Log.e(TAG, "Error connecting with server: " + e.getMessage());
                                Log.e(TAG, "  When calling URL: " + url);
                                callback.onError("Error connecting with server: " + e.getMessage());
                            }
                        }
                    });
        } catch (final Throwable t) {
            Log.e(TAG, "Error connecting with server: " + t.getMessage());
            Log.e(TAG, "  When calling URL: " + url);
            callback.onError("Error connecting with server: " + t.getMessage());
        }
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
            case MI_MUNDO:
                return "espana"; // TODO Change
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
