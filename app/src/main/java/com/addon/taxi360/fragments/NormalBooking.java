package com.addon.taxi360.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.addon.taxi360.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NormalBooking.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NormalBooking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NormalBooking extends Fragment implements View.OnClickListener {

    private static final int PLACE_PICKER_FROM_REQUEST = 1;
    private static final int PLACE_PICKER_TO_REQUEST = 2;
    private static final String TAG = "NormalBooking";
    private OnFragmentInteractionListener mListener;


    private TextView input_for_others;
    private TextView inputFromLocation;
    private TextView inputToLocation;
    private ScrollView scrollView;
    private TextView inputDate;
    private TextView inputTime;

    public NormalBooking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NormalBooking.
     */
    // TODO: Rename and change types and number of parameters
    public static NormalBooking newInstance() {
        NormalBooking fragment = new NormalBooking();

        fragment.setArguments(null);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View currentView = getView();

        input_for_others = (TextView) currentView.findViewById(R.id.input_normal_for_others);
        inputFromLocation = (TextView) currentView.findViewById(R.id.input_normal_from_location);
        inputToLocation = (TextView) currentView.findViewById(R.id.input_normal_to_location);
        inputDate = (TextView) currentView.findViewById(R.id.input_normal_date);
        scrollView = (ScrollView) currentView.findViewById(R.id.view_normal);
        inputTime = (TextView) currentView.findViewById(R.id.input_normal_time);

        input_for_others.setOnClickListener(this);
        inputFromLocation.setOnClickListener(this);
        inputToLocation.setOnClickListener(this);
        inputDate.setOnClickListener(this);
        inputTime.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_normal_for_others:
                forOther_onClick(v);
                break;

            case R.id.input_normal_from_location:
                startPlacePicker(PLACE_PICKER_FROM_REQUEST);
                break;
            case R.id.input_normal_to_location:
                startPlacePicker(PLACE_PICKER_TO_REQUEST);
                break;

            case R.id.input_normal_date:
                showDatePicker();
                break;

            case R.id.input_normal_time:
                showTimePicker();
                break;
        }
    }

    private void showDatePicker() {
        DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                try {
                    Calendar calendar = new GregorianCalendar(year, month, day);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",
                            Locale.getAvailableLocales()[0]);
                    String date = simpleDateFormat.format(calendar.getTime());
                    inputDate.setText(date);

                } catch (Exception exception) {
                    showSnackbar("Date Picker caused Issue");
                    Log.e(TAG, exception.getMessage(), exception);
                }
            }
        };
        showDatePicker(myDateListener);
    }

    private void showDatePicker(DatePickerDialog.OnDateSetListener onDateSetListener) {

        Calendar today = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), onDateSetListener,
                today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a", Locale.getAvailableLocales()[0]);
                inputTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        showTimePicker(onTimeSetListener);
    }

    private void showTimePicker(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();

    }


    private void startPlacePicker(int requestCode) {

        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            startActivityForResult(builder.build(this.getActivity()), requestCode);
        } catch (GooglePlayServicesRepairableException repairable) {
            showSnackbar(repairable.getMessage());
            Log.e(TAG, repairable.getMessage(), repairable);

        } catch (GooglePlayServicesNotAvailableException notAvialable) {
            showSnackbar(notAvialable.getMessage());
            Log.e(TAG, notAvialable.getMessage(), notAvialable);
        }

    }

    private void showSnackbar(String message) {
        final Snackbar snackbar = Snackbar.make(scrollView, message,
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.text_ok_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)


    {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_normal_booking, container, false);


        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PLACE_PICKER_FROM_REQUEST:
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(this.getContext(), data);
                    inputFromLocation.setText(place.getAddress());
                }

                break;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void forOther_onClick(View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_other_user_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button okButton = (Button) dialogView.findViewById(R.id.input_dialog_ok);
        final TextInputEditText otherName = (TextInputEditText) dialogView.findViewById(R.id.input_dialog_name);
        final TextInputEditText otherMobile = (TextInputEditText) dialogView.findViewById(R.id.input_dialog_mobile);
        final AlertDialog alertDialog = dialogBuilder.create();

        okButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            input_for_others.setText(otherName.getText().toString()
                                                    + ";"
                                                    + otherMobile.getText().toString());
                                            alertDialog.dismiss();
                                        }
                                    }
        );
        alertDialog.show();


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
