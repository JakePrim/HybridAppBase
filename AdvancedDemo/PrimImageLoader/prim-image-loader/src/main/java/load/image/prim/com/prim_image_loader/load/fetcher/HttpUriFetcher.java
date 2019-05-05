package load.image.prim.com.prim_image_loader.load.fetcher;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author prim
 * @version 1.0.0
 * @desc 通过一个url获取图片数据 InputStream
 * @time 2018/8/21 - 下午10:13
 */
public class HttpUriFetcher implements DataFetcher<InputStream> {

    private final Uri uri;

    private boolean isCancel = false;

    public HttpUriFetcher(Uri uri) {
        this.uri = uri;
    }

    @Override
    public void loadData(DataFetcherCallback<InputStream> callback) {
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            int responseCode = urlConnection.getResponseCode();
            if (isCancel) {//如果取消了加载 直接return
                return;
            }
            if (responseCode == HttpURLConnection.HTTP_OK) {
                callback.onFetcherReady(inputStream);
            } else {
                callback.onFetcherFaled(new RuntimeException("请求失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFetcherFaled(new RuntimeException("请求失败"));
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    public void loadCancel() {
        isCancel = true;
    }
}
