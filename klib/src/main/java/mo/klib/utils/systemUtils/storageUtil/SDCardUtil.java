package mo.klib.utils.systemUtils.storageUtil;

import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

import mo.klib.k;


/**
 * SD卡工具类
 * Created by OSen on 2016/4/25 15:06.
 */
public class SDCardUtil {

    private SDCardUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("SDCardUtil cannot be instantiated");
    }

    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 获得SD卡总大小
     * @return
     */
    public static String getSDTotalSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(k.app(), blockSize * totalBlocks);
    }
    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return
     */
    public static String getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(k.app(), blockSize * availableBlocks);
    }

    /**
     * 获得机身内存总大小
     *
     * @return
     */
    public static String getRomTotalSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(k.app(), blockSize * totalBlocks);
    }
    /**
     * 获得机身可用内存
     *
     * @return
     */
    public static String getRomAvailableSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(k.app(), blockSize * availableBlocks);
    }









    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }



    public static String getExternalStoragePublicDirectory(String type) {
        String path;
        if (!isSDCardEnable()) {
            return "";
        }

        return Environment.getExternalStoragePublicDirectory(type).getPath();
    }







    /**
     * 获取path路径的总空间大小
     *
     * @param path
     * @return
     */
    public static long getTotalCapacityInPath(String path) {
        long capacity = 0;
        try {
            StatFs stat = new StatFs(path);
            long blockSize = stat.getBlockSize();
            long blockCount = stat.getBlockCount();
            capacity = blockSize * blockCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return capacity;
    }


    public static double[] getSDCardCapacityInfo(String path) {
        double[] capacitys = new double[]{
                0.0, 0.0, 0.0
        };
        String state = Environment.getExternalStorageState();
        try {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                StatFs stat = new StatFs(path);

                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getBlockCount();
                long availaBlock = stat.getAvailableBlocks();

                double totalCapacity = availableBlocks * blockSize;
                double vailaleCapacity = availaBlock * blockSize;
                capacitys[0] = totalCapacity;
                capacitys[1] = vailaleCapacity;
                capacitys[2] = totalCapacity - vailaleCapacity;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return capacitys;
    }

    public static double getSdcardAvailableSpace() {
        String state = Environment.getExternalStorageState();
        try {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());

                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getBlockCount();
                long availaBlock = stat.getAvailableBlocks();

                double vailaleCapacity = availaBlock * blockSize;
                return vailaleCapacity;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
