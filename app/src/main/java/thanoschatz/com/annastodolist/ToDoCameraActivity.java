package thanoschatz.com.annastodolist;


import android.support.v4.app.Fragment;

public class ToDoCameraActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ToDoCameraFragment();
    }
}
