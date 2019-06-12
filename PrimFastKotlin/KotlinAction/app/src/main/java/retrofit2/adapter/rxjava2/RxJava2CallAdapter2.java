/*
 * Copyright (C) 2016 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retrofit2.adapter.rxjava2;

import androidx.annotation.Nullable;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;

import java.lang.reflect.Type;

final class RxJava2CallAdapter2<R> implements CallAdapter<R, Object> {
    private final Type responseType;
    private final @Nullable
    Scheduler netScheduler;
    Scheduler mainScheduler;
    private final boolean isAsync;
    private final boolean isResult;
    private final boolean isBody;
    private final boolean isPaging;
    private final boolean isFlowable;
    private final boolean isSingle;
    private final boolean isMaybe;
    private final boolean isCompletable;

    RxJava2CallAdapter2(Type responseType, @Nullable Scheduler netScheduler, @Nullable Scheduler mainScheduler,
                        boolean isAsync, boolean isResult, boolean isBody, boolean isPaging, boolean isFlowable,
                        boolean isSingle, boolean isMaybe, boolean isCompletable) {
        this.responseType = responseType;
        this.netScheduler = netScheduler;
        this.mainScheduler = mainScheduler;
        this.isAsync = isAsync;
        this.isResult = isResult;
        this.isBody = isBody;
        this.isPaging = isPaging;
        this.isFlowable = isFlowable;
        this.isSingle = isSingle;
        this.isMaybe = isMaybe;
        this.isCompletable = isCompletable;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call<R> call) {
        Observable<Response<R>> responseObservable = isAsync
                ? new CallEnqueueObservable<>(call)
                : new CallExecuteObservable<>(call);

        Observable<?> observable;
        if (isResult) {
            observable = new ResultObservable<>(responseObservable);
        } else if (isBody) {
            observable = new BodyObservable<>(responseObservable);
        } else if (isPaging) {
            observable = new GithubPagingObservable<>(responseObservable);
        } else {
            observable = responseObservable;
        }

        if (netScheduler != null) {
            observable = observable.subscribeOn(netScheduler);
        }

        if (mainScheduler != null) {
            observable = observable.observeOn(mainScheduler);
        }

        if (isFlowable) {
            return observable.toFlowable(BackpressureStrategy.LATEST);
        }
        if (isSingle) {
            return observable.singleOrError();
        }
        if (isMaybe) {
            return observable.singleElement();
        }
        if (isCompletable) {
            return observable.ignoreElements();
        }
        return observable;
    }
}
