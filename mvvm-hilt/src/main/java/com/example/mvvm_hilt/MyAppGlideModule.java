package com.example.mvvm_hilt;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * create by silladus 2020/7/9
 * github:https://github.com/silladus
 * des:
 */
@GlideModule
public final class MyAppGlideModule extends AppGlideModule {

    /**
     * 50M
     */
    private final static int DISK_CACHE_SIZE = 50 << 20;
    private final static int MEMORY_CACHE_SIZE = 20 << 20;

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        // Default empty impl.
        builder
                .setDiskCache(new InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE))
                .setMemoryCache(new LruResourceCache(MEMORY_CACHE_SIZE));
    }

}