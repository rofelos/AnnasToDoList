package thanoschatz.com.annastodolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "thanoschatz.com.annastodolist.mDate";
    private static final String TAG = "DatePickerFragment";



    private Date mDate;

    public static DatePickerFragment newInstance(Date mDate) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE,mDate);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        Log.d(TAG, "info received to datepicker");
        return fragment;

    }


    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, mDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        Log.d(TAG, "info sent");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(EXTRA_DATE);


        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Log.d(TAG, "date before is" + mDate.toString());

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        DatePicker dp = (DatePicker) v.findViewById(R.id.dialog_date_datepicker);
        dp.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                Log.d(TAG, "datechanged initialized ");
                mDate = new GregorianCalendar(year, month, day).getTime();
                Log.d(TAG, "date is stored ");
                Log.d(TAG, "date is" + mDate.toString());

                getArguments().putSerializable(EXTRA_DATE, mDate);

            }

        });

        return new DatePickerDialog.Builder(getActivity())
                .setView(v) //
                .setTitle(R.string.date_picker_title) //
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                        Log.d(TAG, "info sent back to fragment");
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_CANCELED);
                    }
                })

                .create();

    }




}
