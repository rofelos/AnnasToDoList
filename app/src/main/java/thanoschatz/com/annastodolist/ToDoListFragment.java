package thanoschatz.com.annastodolist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoListFragment extends ListFragment {

    private ArrayList<ToDo> mToDos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.ToDos_title);

        mToDos = ToDoLab.get(getActivity()).getToDos();

        ToDoAdapter adapter = new ToDoAdapter(mToDos);

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        ListView listView = (ListView)v.findViewById(android.R.id.list);

                // Use contextual action bar on Honeycomb and higher
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                      long id, boolean checked) {
                    // Required, but not used in this implementation
                }
                // ActionMode.Callback methods
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.todo_list_item_context, menu);
                    return true;
                }
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                        // Required, but not used in this implementation
                }
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_task:
                            ToDoAdapter adapter = (ToDoAdapter)getListAdapter();
                            ToDoLab crimeLab = ToDoLab.get(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; i--) {
                                if (getListView().isItemChecked(i)) {
                                    crimeLab.deleteToDo(adapter.getItem(i));
                                }
                            }
                            mode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }
                public void onDestroyActionMode(ActionMode mode) {
                // Required, but not used in this implementation
                }
            });




        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        ToDo c = ((ToDoAdapter)getListAdapter()).getItem(position);
        // Start CrimePagerActivity with this crime
        Intent i = new Intent(getActivity(), ToDoPagerActivity.class);
        i.putExtra(ToDoFragment.EXTRA_TODO_ID, c.getId());
        startActivity(i);
    }

    private class ToDoAdapter extends ArrayAdapter<ToDo> {
        public ToDoAdapter(ArrayList<ToDo> ToDos) {
            super(getActivity(), 0, ToDos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_todo, null);
            }
            // Configure the view for this Crime
            ToDo c = getItem(position);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.todo_list_item_titleTextView);
            titleTextView.setText(c.getTitle());

            TextView dateTextView =
                    (TextView)convertView.findViewById(R.id.todo_list_item_dateTextView);
            dateTextView.setText(c.getDate().toString());

            CheckBox solvedCheckBox =
                    (CheckBox)convertView.findViewById(R.id.todo_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isDone());
            return convertView;
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        ((ToDoAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_todo:
                ToDo toDo = new ToDo();
                ToDoLab.get(getActivity()).addToDo(toDo);
                Intent i = new Intent(getActivity(), ToDoPagerActivity.class);
                i.putExtra(ToDoFragment.EXTRA_TODO_ID, toDo.getId());
                startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.todo_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterViewCompat.AdapterContextMenuInfo info = (AdapterViewCompat.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        ToDoAdapter adapter = (ToDoAdapter)getListAdapter();
        ToDo toDo = adapter.getItem(position);

    switch (item.getItemId()) {
        case R.id.menu_item_delete_task:
            ToDoLab.get(getActivity()).deleteToDo(toDo);
            adapter.notifyDataSetChanged();
            return true;
    }
    return super.onContextItemSelected(item);

    }


}
