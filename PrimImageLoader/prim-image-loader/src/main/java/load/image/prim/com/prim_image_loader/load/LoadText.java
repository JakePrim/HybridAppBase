package load.image.prim.com.prim_image_loader.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.InputStream;

import load.image.prim.com.prim_image_loader.load.fetcher.DataFetcher;
import load.image.prim.com.prim_image_loader.load.loader.FileUriLoader;
import load.image.prim.com.prim_image_loader.load.loader.HttpUriLoader;
import load.image.prim.com.prim_image_loader.load.loader.ModelLoader;
import load.image.prim.com.prim_image_loader.load.loader.StringModelLoader;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/21 - 下午10:36
 */
public class LoadText {
    public void test(Context context) {
        Uri uri = Uri.parse("http://www.xxx.xxx");

        HttpUriLoader httpUriLoader = new HttpUriLoader();
        ModelLoader.LoadData<InputStream> loadData = httpUriLoader.buildData(uri);
        loadData.fetcher.loadData(new DataFetcher.DataFetcherCallback<InputStream>() {
            @Override
            public void onFetcherReady(InputStream o) {
                Bitmap bitmap = BitmapFactory.decodeStream(o);//通过BitmapFactory 解析inputStream
            }

            @Override
            public void onFetcherFaled(Exception e) {

            }
        });

        FileUriLoader fileLoader = new FileUriLoader(context.getContentResolver());

        ModelLoader.LoadData<InputStream> inputStreamLoadData = fileLoader.buildData(uri);

        inputStreamLoadData.fetcher.loadData(new DataFetcher.DataFetcherCallback<InputStream>() {
            @Override
            public void onFetcherReady(InputStream o) {
                Bitmap bitmap = BitmapFactory.decodeStream(o);
            }

            @Override
            public void onFetcherFaled(Exception e) {

            }
        });

        ModelLoaderRegistry modelLoaderRegistry = new ModelLoaderRegistry();
        modelLoaderRegistry.add(Uri.class, InputStream.class, new FileUriLoader.Factory(context.getContentResolver()));
        modelLoaderRegistry.add(Uri.class, InputStream.class, new HttpUriLoader.Factory());
        modelLoaderRegistry.add(String.class,InputStream.class,new StringModelLoader.Factory());


    }
}
