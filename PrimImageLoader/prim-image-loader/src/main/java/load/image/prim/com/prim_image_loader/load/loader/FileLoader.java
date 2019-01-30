package load.image.prim.com.prim_image_loader.load.loader;

import android.net.Uri;

import java.io.File;
import java.io.InputStream;

import load.image.prim.com.prim_image_loader.load.ModelLoaderRegistry;

/**
 * @author prim
 * @version 1.0.0
 * @desc 文件加载器
 * @time 2018/8/22 - 下午10:33
 */
public class FileLoader<Data> implements ModelLoader<File, Data> {

    private final ModelLoader<Uri, Data> loader;

    public FileLoader(ModelLoader<Uri, Data> loader) {
        this.loader = loader;
    }

    @Override
    public boolean handles(File file) {
        return true;
    }

    @Override
    public LoadData<Data> buildData(File file) {
        //将file 转换为uri
        return loader.buildData(Uri.fromFile(file));
    }

    public static class Factory implements ModelLoaderFactory<File, InputStream> {

        @Override
        public ModelLoader<File, InputStream> build(ModelLoaderRegistry registry) {
            //通过build 找到对应处理的uri
            return new FileLoader<>(registry.build(Uri.class, InputStream.class));
        }
    }
}
