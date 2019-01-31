package mo.klib.utils.systemUtils.exceptionUtil;

/**
 * @ author：mo
 * @ data：2018/1/19 0019
 * @ 功能： 异常工具类
 */

public class ExceptionUtil {
    private ExceptionUtil() {
        throw getUnsupportedOperationException();
    }

    public static UnsupportedOperationException getUnsupportedOperationException() {
        return new UnsupportedOperationException("实例化失败");
    }
}
