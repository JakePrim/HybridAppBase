package load.image.prim.com.prim_image_loader.load.fetcher;

import android.content.ContentResolver;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/21 - 下午10:45
 */
public class FileFetcher implements DataFetcher<InputStream> {


    private final ContentResolver resolver;
    private final Uri uri;

    public FileFetcher(Uri uri, ContentResolver resolver) {
        this.uri = uri;
        this.resolver = resolver;
    }

    @Override
    public void loadData(DataFetcherCallback<InputStream> callback) {
        InputStream inputStream = null;
        try {
             inputStream = resolver.openInputStream(uri);
            callback.onFetcherReady(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFetcherFaled(e);
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void loadCancel() {

    }
}
