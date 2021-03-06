package mo.klib.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IntRange;

import mo.klib.modle.constants.ConstansePermissionGroup;
import mo.klib.modle.constants.KConstans;
import mo.klib.utils.dataUtil.KUriUtil;
import mo.klib.utils.image.BitmapUtil;
import mo.klib.utils.image.CompressScaled;
import mo.klib.utils.systemUtils.CameraUtil;
import mo.klib.utils.systemUtils.storageUtil.SDCardUtil;
import mo.klib.utils.tipsUtil.ToastUtil;


/**
 * @ author：mo
 * @ data：2019/1/8
 * @ 功能：
 */
public class KMediaActivity extends KReceiverActivity {
    /**
     * 拍照返回路径
     */
    protected Uri imageUri;
    /**
     * 视频的质量，值为0-1
     */
    private int videoQuality = 1;
    /**
     * 视频的录制长度，s为单位
     */
    private int videoLength = 10;
    /**
     * 视频文件大小，字节为单位
     */
    private long videoSize = 20 * 1024 * 1024L;
    /**
     * 操作状态
     * 0==默认无操作
     * 1==拍照
     * 2==从相册选取图片
     * 3==录像
     * 4==从相册选取录像
     */

    private int phoneStatus = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照
            case KConstans.MEDIA_TAKE_PIC:
                if (phoneStatus == KConstans.MEDIA_TAKE_PIC) {
                    if (resultCode == RESULT_OK) {
                        setMediaResult(phoneStatus, BitmapUtil.getBitmap(imageUri), null, data);
                    }
                }
                break;
            //从相册选图片
            case KConstans.MEDIA_CHOOSE_PIC:
                if (phoneStatus == KConstans.MEDIA_CHOOSE_PIC) {
                    if (resultCode == RESULT_OK) {
                        String imagePath = CameraUtil.handlerImageChooseResult(data);
                        setMediaResult(phoneStatus, CompressScaled.getScaledBitmap(BitmapFactory.decodeFile(imagePath)), imagePath, data);
//                        setMediaResult(phoneStatus, BitmapFactory.decodeFile(imagePath), imagePath, data);
                    }
                }
                break;
            //录像
            case KConstans.MEDIA_TAKE_VIDEO:
                if (phoneStatus == KConstans.MEDIA_TAKE_VIDEO) {
                    if (resultCode == RESULT_OK) {
                        if (null != data && data.getData() != null) {
                            Cursor c = getContentResolver().query(data.getData(), new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
                            if (c != null && c.moveToFirst()) {
                                setMediaResult(phoneStatus, null, c.getString(0), data);
                            }
                        }
//                        setMediaResult(phoneStatus, null, UriUtil.getPath(imageUri));
                    }
                }
                break;
            //从相册选录像
            case KConstans.MEDIA_CHOOSE_VIDEO:
                if (phoneStatus == KConstans.MEDIA_CHOOSE_VIDEO) {
                    if (resultCode == RESULT_OK) {
                        setMediaResult(phoneStatus, null, KUriUtil.getPath(data.getData()), data);
                    }
                }
                break;
            //录音
            case KConstans.MEDIA_TAKE_SOUND:
                if (phoneStatus == KConstans.MEDIA_TAKE_SOUND) {
                    if (resultCode == RESULT_OK) {
                        if (null != data && data.getData() != null) {
                            setMediaResult(phoneStatus, null, "", data);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * 媒体操作结果
     *
     * @param phontoType 操作类型  1==拍照  2==从相册选图片 3==录像 4==从相册选图片
     * @param bitmap     位图
     * @param path       路径
     * @param data       返回数据
     */
    protected void setMediaResult(int phontoType, Bitmap bitmap, String path, Intent data) {

    }


    /**
     * 申请权限成功
     */
    @Override
    public void requestPermissionSuccess(int requestCode) {
        super.requestPermissionSuccess(requestCode);
        if (phoneStatus == KConstans.MEDIA_TAKE_PIC) {
            imageUri = CameraUtil.actionPhoneTake(mActivity);
        } else if (phoneStatus == KConstans.MEDIA_CHOOSE_PIC) {
            CameraUtil.actionPhoneChoose(mActivity);
        } else if (phoneStatus == KConstans.MEDIA_TAKE_VIDEO) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, videoQuality);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, videoLength);
            intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, videoSize);
            startActivityForResult(intent, KConstans.MEDIA_TAKE_VIDEO);
        } else if (phoneStatus == KConstans.MEDIA_CHOOSE_VIDEO) {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("video/*");
            mActivity.startActivityForResult(intent, KConstans.MEDIA_CHOOSE_VIDEO);
        }

    }

    /**
     * 开启拍照
     */
    protected void actionMediaTakePic() {
        if (CameraUtil.isExistCamera()) {
//            imageUri = CameraUtil.actionPhoneTake(mActivity);
            phoneStatus = KConstans.MEDIA_TAKE_PIC;
            requestPermission(ConstansePermissionGroup.PERMISSIONS_CAMERA);

        } else {
            ToastUtil.showToast("没有找到拍照设备！");
        }
    }

    /**
     * 开启从相册选取图片
     */
    protected void actionMediaChoosePic() {
        if (SDCardUtil.isEnable()) {
            phoneStatus = KConstans.MEDIA_CHOOSE_PIC;
            requestPermission(ConstansePermissionGroup.PERMISSIONS_STORAGE);

        } else {
            ToastUtil.showToast("SD卡不可用");
        }
    }

    /**
     * 开启录像
     * 默认调用系统录像，可先调用setVideoParame（）方法设置录像质量，最大长度和文件大小
     */
    protected void actionMediaTakeVideo() {
        if (CameraUtil.isExistCamera()) {
            phoneStatus = KConstans.MEDIA_TAKE_VIDEO;
            requestPermission(ConstansePermissionGroup.PERMISSIONS_CAMERA);

        } else {
            ToastUtil.showToast("没有找到录像设备！");
        }
    }

    /**
     * 从相册选视频
     */
    protected void actionMediaChooseVideo() {
        if (SDCardUtil.isEnable()) {
            phoneStatus = KConstans.MEDIA_CHOOSE_VIDEO;
            requestPermission(ConstansePermissionGroup.PERMISSIONS_STORAGE);

        } else {
            ToastUtil.showToast("SD卡不可用");
        }
    }

    /**
     * 开启录音
     */
    protected void actionMediaTakeSound() {
        phoneStatus = KConstans.MEDIA_TAKE_SOUND;
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, KConstans.MEDIA_TAKE_SOUND);

    }

    /**
     * 设置录像参数
     *
     * @param quality 视频的质量，值为0-1
     * @param length  视频的录制长度，s为单位
     * @param size    视频文件大小，字节为单位
     */
    protected void setVideoParame(@IntRange(from = 0, to = 1) int quality, @IntRange(from = 1) int length, long size) {
        videoQuality = quality;
        videoLength = length;
        videoSize = size;
    }
}
