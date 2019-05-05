package load.image.prim.com.prim_image_loader.load.loader;

import load.image.prim.com.prim_image_loader.cache.Key;
import load.image.prim.com.prim_image_loader.load.ModelLoaderRegistry;
import load.image.prim.com.prim_image_loader.load.fetcher.DataFetcher;

/**
 * @author prim
 * @version 1.0.0
 * @desc ModelLoader interface
 * @time 2018/8/21 - 下午10:04
 */
public interface ModelLoader<Model, Data> {

    interface ModelLoaderFactory<Model, Data> {
        ModelLoader<Model, Data> build(ModelLoaderRegistry registry);
    }

    public class LoadData<Data> {

        //缓存的key
        public Key key;

        //负责加载数据的接口实现
        public DataFetcher fetcher;

        public LoadData(Key key, DataFetcher<Data> fetcher) {
            this.key = key;
            this.fetcher = fetcher;
        }

    }

    /**
     * 负责返回此loader是否能够处理对应Model的数据
     *
     * @param model
     * @return
     */
    boolean handles(Model model);

    /**
     * 负责创建加载数据
     *
     * @param model
     * @return
     */
    LoadData<Data> buildData(Model model);
}
