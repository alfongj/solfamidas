package es.solfamidas.elmundo.common;

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

import es.solfamidas.elmundo.entities.FlickrResult;
import es.solfamidas.elmundo.entities.Photo;
import es.solfamidas.elmundo.entities.Photos;


public class FlickrDataSourceImpl implements FlickrDataSource {


    private final String HANDLER_PARAMETER_URLS = "photo_urls";
    private final String HANDLER_PARAMETER_ERROR = "error";

    @Override
    public void getFlickrImagesByTag(final String tag) {

        Thread background = new Thread(new Runnable() {

            private final HttpClient client = new DefaultHttpClient();

            public void run() {
                try {
                    Gson gson = new Gson();
                    String responseString;
                    HttpGet httpget = new HttpGet(buildRequestUrl(tag));
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    responseString = client.execute(httpget, responseHandler);

                    //convert json into POJO
                    FlickrResult f = gson.fromJson(responseString, FlickrResult.class);

                    if (f.getStat().equals("ok")) {
                        //generate url strings from result photos metadata
                        ArrayList<String> urls = generateFlickrPhotoUrls(f);
                        threadMsgSuccess(urls);
                    } else {
                        threadMsgError("error connecting with server");
                    }

                } catch (Throwable t) {
                    threadMsgError("error connecting with server");
                }
            }

            private void threadMsgSuccess(ArrayList<String> urls) {

                if (null != urls && !urls.isEmpty()) {
                    Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putStringArrayList(HANDLER_PARAMETER_URLS, urls);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            private void threadMsgError(String error) {
                Message msgObj = handler.obtainMessage();
                Bundle b = new Bundle();
                b.putString(HANDLER_PARAMETER_ERROR, error);
                msgObj.setData(b);
                handler.sendMessage(msgObj);
            }

            // Define the Handler that receives messages from the thread and update the progress
            private final Handler handler = new Handler() {

                public void handleMessage(Message msg) {

                    String error = msg.getData().getString(HANDLER_PARAMETER_ERROR, "");
                    ArrayList<String> urls = msg.getData().getStringArrayList(HANDLER_PARAMETER_URLS);

                    if (error != null) {
                        //TODO send result info to presenter
                    } else {
                        //TODO send error to presenter
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
    private String buildRequestUrl(String tag){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.flickr.com")
                .appendPath("services")
                .appendPath("rest")
                .appendQueryParameter("method", "flickr.photos.search")
                .appendQueryParameter("api_key", "a84ba44f36d47381be62bdec75c968b1")
                .appendQueryParameter("text", tag)
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
        Photos photos = f.getPhotos();

        for (Photo photo : photos.getPhoto()) {

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
