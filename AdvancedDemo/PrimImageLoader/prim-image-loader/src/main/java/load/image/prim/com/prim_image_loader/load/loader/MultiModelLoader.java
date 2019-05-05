package load.image.prim.com.prim_image_loader.load.loader;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 多个model loader
 * @time 2018/8/21 - 下午10:57
 */
public class MultiModelLoader<Model, Data> implements ModelLoader<Model, Data> {

    private final List<ModelLoader<Model, Data>> modelLoaderList;

    public MultiModelLoader(List<ModelLoader<Model, Data>> modelLoaderList) {
        this.modelLoaderList = modelLoaderList;
    }

    @Override
    public boolean handles(Model model) {
        for (ModelLoader<Model, Data> modelLoader : modelLoaderList) {
            if (modelLoader.handles(model)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LoadData<Data> buildData(Model model) {
        for (ModelLoader<Model, Data> modelLoader : modelLoaderList) {
            if (modelLoader.handles(model)) {//再判断是否可以处理 Model 数据类型
                LoadData<Data> loadData = modelLoader.buildData(model);
                return loadData;
            }
        }
        return null;
    }
}
