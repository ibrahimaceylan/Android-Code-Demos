package io.innofang.rxjava2demo.imageloader;

import android.content.Context;

import io.innofang.rxjava2demo.imageloader.bean.Image;
import io.innofang.rxjava2demo.imageloader.cache.impl.DiskCacheObservable;
import io.innofang.rxjava2demo.imageloader.cache.impl.MemoryCacheObservable;
import io.innofang.rxjava2demo.imageloader.cache.impl.NetworkCacheObservable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Author: Inno Fang
 * Time: 2017/4/28 19:43
 * Description:
 */


public class RequestCreator {

    private MemoryCacheObservable mMemoryCacheObservable;
    private DiskCacheObservable mDiskCacheObservable;
    private NetworkCacheObservable mNetworkCacheObservable;

    public RequestCreator(Context context) {
        mMemoryCacheObservable = new MemoryCacheObservable();
        mDiskCacheObservable = new DiskCacheObservable(context);
        mNetworkCacheObservable = new NetworkCacheObservable();
    }

    public Observable getImageFromMemory(String url) {
        return mMemoryCacheObservable.getImage(url);
    }

    @SuppressWarnings("unchecked")
    public Observable getImageFromDisk(String url) {
        return mDiskCacheObservable
                .getImage(url)
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(Image image) throws Exception {
                        return null != image;
                    }
                })
                .doOnNext(new Consumer<Image>() {
                    @Override
                    public void accept(Image image) throws Exception {
                        /* 在加载图片之前添加至内存缓存 */
                        mMemoryCacheObservable.putDataToCache(image);
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public Observable getImageFromNetwork(String url) {
        return mNetworkCacheObservable
                .getImage(url)
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(Image image) throws Exception {
                        return null != image;
                    }
                })
                .doOnNext(new Consumer<Image>() {
                    @Override
                    public void accept(Image image) throws Exception {
                        /* 在加载图片之前添加至文件缓存和内存缓存 */
                        mDiskCacheObservable.putDataToCache(image);
                        mMemoryCacheObservable.putDataToCache(image);
                    }
                });
    }

}
