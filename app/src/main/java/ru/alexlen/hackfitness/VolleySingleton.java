/**
 * @author Almazko
 */
package ru.alexlen.hackfitness;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static VolleySingleton mInstance;
    private        RequestQueue    mRequestQueue;
    private        ImageLoader     mImageLoader;
    private static Context         mCtx;


    private final static ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
        private final LruCache<String, Bitmap> cache = new LruCache<>(20);

        @Override
        public Bitmap getBitmap(String url) {
            Log.d("ImageCache", "PUT: " + url);
            return cache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            Log.d("ImageCache", "GET: " + url);
            cache.put(url, bitmap);
        }
    };

    private VolleySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, imageCache);
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
