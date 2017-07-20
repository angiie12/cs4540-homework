package com.sargent.mark.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

import static com.sargent.mark.todolist.R.array.todo_array;

/**
 * Created by mark on 7/4/17.
 * Modified by Angie on 7/13/17.
 */

public class AddToDoFragment extends DialogFragment{

    private EditText toDo;
    private DatePicker dp;
    private Spinner spinner;
//    private RecyclerView rv;
    private Button add;
    ArrayAdapter<CharSequence> spinnerAdapter;
    private final String TAG = "addtodofragment";


    public AddToDoFragment() {
    }

    // have a way for the activity to get the data from the dialog
    public interface OnDialogCloseListener {
        void closeDialog(int year, int month, int day, String description, String category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_adder, container, false);
        toDo = (EditText) view.findViewById(R.id.toDo);
        dp = (DatePicker) view.findViewById(R.id.datePicker);
        add = (Button) view.findViewById(R.id.add);

        // add the spinner when adding a to-do for categories
        spinner = (Spinner) view.findViewById(R.id.spinner);
        // initialize the adapter
        spinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.todo_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

//        add recycler view to the add to-do fragment
//        rv = (RecyclerView) view.findViewById(R.id.recyclerView);
//        rv.setLayoutManager(new LinearLayoutManager(this));

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dp.updateDate(year, month, day);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make sure to get the category when adding a new to-do
                String category = spinner.getSelectedItem().toString();
                OnDialogCloseListener activity = (OnDialogCloseListener) getActivity();
                activity.closeDialog(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), toDo.getText().toString(), category);
                AddToDoFragment.this.dismiss();
            }
        });

        return view;
    }

}