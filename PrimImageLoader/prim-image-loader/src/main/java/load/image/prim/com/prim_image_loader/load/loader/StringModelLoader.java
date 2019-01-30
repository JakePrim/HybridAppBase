package load.image.prim.com.prim_image_loader.load.loader;

import android.net.Uri;

import java.io.File;
import java.io.InputStream;

import load.image.prim.com.prim_image_loader.load.ModelLoaderRegistry;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/21 - 下午11:14
 */
public class StringModelLoader implements ModelLoader<String, InputStream> {

    private final ModelLoader<Uri, InputStream> loader;

    private Uri uri;

    public StringModelLoader(ModelLoader<Uri, InputStream> loader) {
        //这个modelLoader 其实就是MultiModelLoader list -->> FileUriLoader 和 HttpUrlLoader ，然后根据uri遍历使用哪个loader
        this.loader = loader;
    }

    @Override
    public boolean handles(String model) {
        return true;
    }

    @Override
    public LoadData<InputStream> buildData(String model) {
        if (model.startsWith("/")) {
            uri = Uri.fromFile(new File(model));
        } else {
            uri = Uri.parse(model);
        }
        return loader.buildData(uri);
    }

    public static class Factory implements ModelLoader.ModelLoaderFactory<String, InputStream> {
        @Override
        public ModelLoader<String, InputStream> build(ModelLoaderRegistry registry) {
            return new StringModelLoader(registry.build(Uri.class, InputStream.class));
        }
    }
}
