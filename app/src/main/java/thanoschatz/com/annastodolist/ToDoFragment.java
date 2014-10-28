package thanoschatz.com.annastodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class ToDoFragment extends Fragment {
    public static final String EXTRA_TODO_ID = "thanoschatz.com.annastodolist.todo_id";
    public static final String DIALOG_DATE = "mDate";
    public static final int REQUEST_DATE = 0;

    private ToDo mToDo;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mDoneCheckBox;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID toDoId = (UUID)getArguments().getSerializable(EXTRA_TODO_ID);
        mToDo= ToDoLab.get(getActivity()).getToDo(toDoId);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
                }
        }
        wireTitleField(view);
        wireDateButton(view);
        wireSolvedCheckBox(view);
        return  view;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mToDo.setDate(date);

            Log.d("TAG","RECEIVED DATE");
            SimpleDateFormat simpleDateFormat = getSimpleDateFormat();

            updateDate(simpleDateFormat);
        }
    }

    public ToDoFragment(){

    }

    private void updateDate(SimpleDateFormat simpleDateFormat) {
        mDateButton.setText(simpleDateFormat.format(mToDo.getDate()));
        Log.d("TAG","TEXT SET");

    }

    public static ToDoFragment newInstance(UUID mId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TODO_ID, mId);

        ToDoFragment fragment = new ToDoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void wireSolvedCheckBox(View view) {
        mDoneCheckBox = (CheckBox)view.findViewById(R.id.Todo_done);
        mDoneCheckBox.setChecked(mToDo.isDone());
        mDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mToDo.setDone(isChecked);
            }
        });
    }

    private void wireDateButton(View view) {
        SimpleDateFormat dateFormatter = getSimpleDateFormat();
        mDateButton = (Button)view.findViewById(R.id.toDo_date);


        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment datePicker = DatePickerFragment.newInstance(mToDo.getDate());
                datePicker.setTargetFragment(ToDoFragment.this, REQUEST_DATE);
                datePicker.show(fragmentManager, DIALOG_DATE);
            }
        });
        updateDate(dateFormatter);

    }

    private SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat("MM/dd/yyyy");
    }

    private void wireTitleField(View view) {
        mTitleField = (EditText)view.findViewById(R.id.toDo_title);
        mTitleField.setText(mToDo.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mToDo.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        ToDoLab.get(getActivity()).saveToDos();
    }


}