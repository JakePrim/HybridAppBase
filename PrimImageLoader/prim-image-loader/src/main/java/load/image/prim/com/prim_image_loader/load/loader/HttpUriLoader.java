package load.image.prim.com.prim_image_loader.load.loader;

import android.net.Uri;

import java.io.InputStream;

import load.image.prim.com.prim_image_loader.load.ModelLoaderRegistry;
import load.image.prim.com.prim_image_loader.load.ObjectKey;
import load.image.prim.com.prim_image_loader.load.fetcher.HttpUriFetcher;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/21 - 下午10:10
 */
public class HttpUriLoader implements ModelLoader<Uri,InputStream> {
    @Override
    public boolean handles(Uri uri) {
        //判断uri是否是一个http地址
        String scheme = uri.getScheme();
        //如果是http地址此loader才会支持
        return scheme.equalsIgnoreCase("http")||scheme.equalsIgnoreCase("https");
    }

    @Override
    public LoadData<InputStream> buildData(Uri uri) {
        LoadData<InputStream> loadData = new LoadData<>(new ObjectKey(uri), new HttpUriFetcher(uri));
        return loadData;
    }

    public static class Factory implements ModelLoaderFactory<Uri,InputStream>{

        @Override
        public ModelLoader<Uri, InputStream> build(ModelLoaderRegistry registry) {
            return new HttpUriLoader();
        }
    }
}
