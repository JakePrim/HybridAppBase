package load.image.prim.com.prim_image_loader.load.loader;

import android.content.ContentResolver;
import android.net.Uri;

import java.io.InputStream;

import load.image.prim.com.prim_image_loader.load.ModelLoaderRegistry;
import load.image.prim.com.prim_image_loader.load.ObjectKey;
import load.image.prim.com.prim_image_loader.load.fetcher.FileFetcher;

/**
 * @author prim
 * @version 1.0.0
 * @desc 文件类型图片加载器
 * @time 2018/8/21 - 下午10:43
 */
public class FileUriLoader implements ModelLoader<Uri, InputStream> {

    private final ContentResolver contentResolver;

    public FileUriLoader(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public boolean handles(Uri uri) {
        String scheme = uri.getScheme();
        return ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme);
    }

    @Override
    public LoadData<InputStream> buildData(Uri uri) {
        return new LoadData<>(new ObjectKey(uri), new FileFetcher(uri, contentResolver));
    }

    public static class Factory implements ModelLoaderFactory<Uri,InputStream>{

        private final ContentResolver resolver;

        public Factory(ContentResolver resolver) {
            this.resolver = resolver;
        }

        @Override
        public ModelLoader<Uri, InputStream> build(ModelLoaderRegistry registry) {
            FileUriLoader fileLoader = new FileUriLoader(resolver);
            return fileLoader;
        }
    }
}
