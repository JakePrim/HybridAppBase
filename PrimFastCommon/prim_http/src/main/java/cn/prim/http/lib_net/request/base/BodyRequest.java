package cn.prim.http.lib_net.request.base;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-05-10 - 16:31
 */
public abstract class BodyRequest<T, F extends NoBodyRequest> extends NoBodyRequest<T, F> {
    public BodyRequest(String url) {
        super(url);
    }
}
