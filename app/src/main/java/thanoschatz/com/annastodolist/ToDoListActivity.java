package thanoschatz.com.annastodolist;

import android.support.v4.app.Fragment;


public class ToDoListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ToDoListFragment();
    }
}
