package controller.mytravel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> tiltes = new ArrayList<>();
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tiltes.get(position);
    }
    public void AddFragment (Fragment fragment, String tilte)
    {
        fragments.add(fragment);
        tiltes.add(tilte);
    }
}
