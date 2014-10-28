package thanoschatz.com.annastodolist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

public class ToDoPagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<ToDo> mToDos;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mToDos = ToDoLab.get(this).getToDos();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mToDos.size();
            }
            @Override
            public Fragment getItem(int pos) {
                ToDo toDo = mToDos.get(pos);
                return ToDoFragment.newInstance(toDo.getId());
            }
        });

        UUID ToDoId = (UUID)getIntent()
                .getSerializableExtra(ToDoFragment.EXTRA_TODO_ID);
        for (int i = 0; i < mToDos.size(); i++) {
            if (mToDos.get(i).getId().equals(ToDoId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) { }
            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) { }
            public void onPageSelected(int pos) {
                ToDo toDo = mToDos.get(pos);
                if (toDo.getTitle() != null) {
                    setTitle(toDo.getTitle());
                }
            }
        });


    }


}



