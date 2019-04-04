package mo.klib.modle.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @ author：mo
 * @ data：2019/4/4：10:22
 * @ 功能：FragmentPagerAdapter 适配器 适用于较少的碎片切换，可变换tab提示
 *  KFragmentPagerAdapter myAdapter = new KFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
 *         vp_history.setAdapter(myAdapter);
 */
public class KFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<?> mFragment;
    private List<String> mTitleList;
    public KFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public KFragmentPagerAdapter(FragmentManager fm, List<?> mFragment) {
        super(fm);
        this.mFragment = mFragment;
    }
    public KFragmentPagerAdapter(FragmentManager fm, List<?> mFragment, List<String> mTitleList) {
        super(fm);
        this.mFragment = mFragment;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitleList!=null){
            return mTitleList.get(position);
        }
        return "";
    }
}
