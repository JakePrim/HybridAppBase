package load.image.prim.com.prim_image_loader.load.fetcher;

/**
 * @author prim
 * @version 1.0.0
 * @desc 负责数据获取的接口
 * @time 2018/8/21 - 下午10:07
 */
public interface DataFetcher<Data> {

    interface DataFetcherCallback<Data>{
        /**
         * 数据加载完成
         */
        void onFetcherReady(Data data);

        /**
         * 加载失败
         * @param e
         */
        void onFetcherFaled(Exception e);
    }

    void loadData(DataFetcherCallback<Data> callback);

    void loadCancel();
}
