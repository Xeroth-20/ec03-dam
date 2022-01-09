package dev.jx.ec03.network;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import dev.jx.ec03.CodemoniumApplication;

public class ImageRequester {

    private static ImageRequester instance;

    private final ImageLoader imageLoader;

    public ImageRequester() {
        RequestQueue requestQueue = Volley.newRequestQueue(CodemoniumApplication.getAppContext());
        requestQueue.start();

        imageLoader = new ImageLoader(requestQueue, new ImageRequesterCache(getMaxByteSize()));
    }

    public void setImageFromUrl(NetworkImageView networkImageView, String url) {
        networkImageView.setImageUrl(url, imageLoader);
    }

    public static int getMaxByteSize() {
        DisplayMetrics displayMetrics = CodemoniumApplication.getAppContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels * displayMetrics.heightPixels * 4 * 3;
    }

    public static ImageRequester getInstance() {
        if (instance == null) {
            instance = new ImageRequester();
        }

        return instance;
    }

    private static class ImageRequesterCache implements ImageLoader.ImageCache {

        private final LruCache<String, Bitmap> lruCache;

        public ImageRequesterCache(int maxByteSize) {
            lruCache = new LruCache<String, Bitmap>(maxByteSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return lruCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            lruCache.put(url, bitmap);
        }
    }
}
