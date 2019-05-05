package load.image.prim.com.prim_image_loader.load;

import java.util.ArrayList;
import java.util.List;

import load.image.prim.com.prim_image_loader.load.loader.ModelLoader;
import load.image.prim.com.prim_image_loader.load.loader.MultiModelLoader;

/**
 * @author prim
 * @version 1.0.0
 * @desc model loader 注册机
 * @time 2018/8/21 - 下午10:53
 */
public class ModelLoaderRegistry {

    private List<Entry<?, ?>> entryList = new ArrayList<>();

    /**
     * 注册modelloader
     *
     * @param modelClass 数据来源类型 string file
     * @param dataClass  数据转换后的类型 加载后类型 http --> InputSteam
     * @param factory    创建ModelLoader 工厂
     * @param <Model>
     * @param <Data>
     */
    public synchronized <Model, Data> void add(Class<Model> modelClass, Class<Data> dataClass,
                                               ModelLoader.ModelLoaderFactory<Model, Data> factory) {
        entryList.add(new Entry<>(modelClass, dataClass, factory));
    }


    /**
     * 获取对应类型的modelLoader
     *
     * @param modelClass
     * @param dataClass
     * @param <Model>
     * @param <Data>
     * @return
     */
    public <Model, Data> ModelLoader<Model, Data> build(Class<Model> modelClass, Class<Data> dataClass) {
        List<ModelLoader<Model, Data>> loaders = new ArrayList<>();
        for (Entry<?, ?> entry : entryList) {
            //如果返回为true 是我们需要当ModelLoader
            if (entry.handles(modelClass, dataClass)) {
                loaders.add((ModelLoader<Model, Data>) entry.factory.build(this));
            }
        }
        if (loaders.size() > 1) {
            return new MultiModelLoader<>(loaders);
        } else if (loaders.size() == 1) {
            return loaders.get(0);
        }
        throw new RuntimeException("Not find ModelLoader!");
    }

    public <Model> List<ModelLoader<Model, ?>> getModleLoads(Class<Model> modelClass) {
        List<ModelLoader<Model, ?>> loaders = new ArrayList<>();
        for (Entry<?, ?> entry : entryList) {
            if (entry.handles(modelClass)) {
                loaders.add((ModelLoader<Model, ?>) entry.factory.build(this));
            }
        }
        return loaders;
    }


    private static class Entry<Model, Data> {
        Class<Model> modelClass;
        Class<Data> dataClass;

        ModelLoader.ModelLoaderFactory<Model, Data> factory;

        public Entry(Class<Model> modelClass, Class<Data> dataClass, ModelLoader.ModelLoaderFactory<Model, Data> factory) {
            this.modelClass = modelClass;
            this.dataClass = dataClass;
            this.factory = factory;
        }

        boolean handles(Class<?> modelClass, Class<?> dataClass) {
            //表示匹配 判断参数 A.isAssignableFrom(B)  B 和 A 是否是同一个class
            return this.modelClass.isAssignableFrom(modelClass) && this.dataClass.isAssignableFrom(dataClass);
        }

        boolean handles(Class<?> modelClass) {
            return this.modelClass.isAssignableFrom(modelClass);
        }
    }
}
