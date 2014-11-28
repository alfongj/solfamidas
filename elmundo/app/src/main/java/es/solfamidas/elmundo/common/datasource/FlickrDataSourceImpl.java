package es.solfamidas.elmundo.common.datasource;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;

import es.solfamidas.elmundo.entities.FlickrPhoto;
import es.solfamidas.elmundo.entities.FlickrPhotoContainer;
import es.solfamidas.elmundo.entities.FlickrResult;

public class FlickrDataSourceImpl implements FlickrDataSource {


    private final String HANDLER_PARAMETER_URLS_KEY = "photo_urls";
    private final String HANDLER_PARAMETER_ERROR_KEY = "error";

    private final String HANDLER_TYPE_KEY = "type";
    private final int HANDLER_CODE_ERROR = 1;
    private final int HANDLER_CODE_SUCCESS = 0;


    @Override
    public void getFlickrImagesByTag(final String tag, final int numberOfPhotos, final ImagesByTagCallback callback) {

        Thread background = new Thread(new Runnable() {

            private final HttpClient client = new DefaultHttpClient();

            public void run() {
                try {
                    Gson gson = new Gson();
                    String responseString;
                    HttpGet httpget = new HttpGet(buildRequestUrl(tag, numberOfPhotos));
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    responseString = client.execute(httpget, responseHandler);


                    //convert json into POJO
                    FlickrResult f = gson.fromJson(responseString, FlickrResult.class);

                    if (f.getStat().equals("ok")) {
                        //generate url strings from result photos metadata
                        ArrayList<String> urls = generateFlickrPhotoUrls(f);
                        if (!urls.isEmpty())
                            threadMsgSuccess(urls);
                        else {
                            threadMsgError("No results found");
                        }
                    } else {
                        threadMsgError("Server error");
                    }

                } catch (Throwable t) {
                    threadMsgError("Error connecting with server");
                }
            }

            private void threadMsgSuccess(ArrayList<String> urls) {
                Message msgObj = handler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt(HANDLER_TYPE_KEY, HANDLER_CODE_SUCCESS);
                b.putStringArrayList(HANDLER_PARAMETER_URLS_KEY, urls);
                msgObj.setData(b);
                handler.sendMessage(msgObj);
            }


            private void threadMsgError(String error) {
                Message msgObj = handler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt(HANDLER_TYPE_KEY, HANDLER_CODE_ERROR);
                b.putString(HANDLER_PARAMETER_ERROR_KEY, error);
                msgObj.setData(b);
                handler.sendMessage(msgObj);
            }

            private final Handler handler = new Handler() {

                public void handleMessage(Message msg) {

                    int type = msg.getData().getInt(HANDLER_TYPE_KEY, HANDLER_CODE_ERROR);
                    if (type == HANDLER_CODE_SUCCESS) {
                        ArrayList<String> urls =
                                msg.getData().getStringArrayList(HANDLER_PARAMETER_URLS_KEY);
                        callback.onSuccess(urls);

                    } else if (type == HANDLER_CODE_ERROR) {
                        String error = msg.getData().getString(HANDLER_PARAMETER_ERROR_KEY, "");
                        callback.onError(error);

                    }
                }
            };

        });
        background.start();
    }

    /**
     * Build flickr search request URL
     *
     * @param tag text to search in flickr
     * @return request url
     */
    private String buildRequestUrl(String tag, int numberOfPhotos) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.flickr.com")
                .appendPath("services")
                .appendPath("rest")
                .appendQueryParameter("method", "flickr.photos.search")
                .appendQueryParameter("api_key", "a84ba44f36d47381be62bdec75c968b1")
                .appendQueryParameter("text", tag)
                .appendQueryParameter("per_page", "" + numberOfPhotos)
                .appendQueryParameter("sort", "interestingness-desc")
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1");

        return builder.build().toString();
    }

    /**
     * generate photo urls based on photo parameters.
     *
     * @param f flickr response POJO containing photos.
     * @return string list with photo urls (with format https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg).
     */
    private ArrayList<String> generateFlickrPhotoUrls(FlickrResult f) {

        ArrayList<String> photoUrls = new ArrayList<String>();
        FlickrPhotoContainer photos = f.getPhotos();

        for (FlickrPhoto photo : photos.getPhoto()) {

            StringBuilder sb = new StringBuilder();
            sb.append("https://farm");
            sb.append(photo.getFarm());
            sb.append(".staticflickr.com/");
            sb.append(photo.getServer());
            sb.append("/");
            sb.append(photo.getId());
            sb.append("_");
            sb.append(photo.getSecret());
            sb.append("_");
            sb.append("m");// TODO letra que indica resolución. Si decidimos cambiarlo: https://www.flickr.com/services/api/misc.urls.html
            sb.append(".jpg");

            photoUrls.add(sb.toString());
        }

        return photoUrls;
    }


}
