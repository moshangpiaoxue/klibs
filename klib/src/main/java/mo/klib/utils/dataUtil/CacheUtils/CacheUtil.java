package mo.klib.utils.dataUtil.CacheUtils;

import android.content.Context;

import java.io.File;

import mo.klib.utils.fileUtil.FileUtil;


/**
 * @ author：mo
 * @ data：2017/12/13 0013
 * @ 功能： 缓存工具类
 */
public class CacheUtil {
    /**
     * 获取缓存大小
     */
    public String getCacheSize(Context context) {

        String cacheSize = null;
        try {
            File cacheDir = new File(FileUtil.getExternalCacheDir(context));
            cacheSize = DataCleanManager.getCacheSize(cacheDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheSize == null ? "" : cacheSize;
    }

    /**
     * 清除缓存
     */
    public void cleanCache(Context context) {
        DataCleanManager.deleteFolderFile(FileUtil.getExternalCacheDir(context), false);
    }

}
