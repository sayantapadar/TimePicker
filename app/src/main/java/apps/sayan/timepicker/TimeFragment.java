package apps.sayan.timepicker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by SAYAN on 27-05-2016.
 */
public class TimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int whatIsClicked= getArguments().getInt("what_is_clicked");
        int hour=0,minute=0;
        switch (whatIsClicked){
            case TimeHelper.START:
                hour=TimeHelper.startHour;
                minute=TimeHelper.startMin;
                break;
            case TimeHelper.END:
                hour=TimeHelper.endHour;
                minute=TimeHelper.endMin;
                break;
        }

        TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(), getTheme(),this,hour,minute,false);
        timePickerDialog.setTitle(null);

        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int whatIsClicked= getArguments().getInt("what_is_clicked");
        TextView tv;
        TextView tvAMPM;
        String time[];
        switch (whatIsClicked){
            case TimeHelper.START:
                TimeHelper.updateTemporaryTime(whatIsClicked, hourOfDay, minute);
                tv = (TextView) getActivity().findViewById(R.id.timeStart);
                tvAMPM = (TextView) getActivity().findViewById(R.id.timeStartAMPM);
                time= TimeHelper.getTime(whatIsClicked);
                tv.setText(time[0]);
                tvAMPM.setText(time[1]);
                break;
            case TimeHelper.END:
                TimeHelper.updateTemporaryTime(whatIsClicked, hourOfDay, minute);
                tv = (TextView) getActivity().findViewById(R.id.timeEnd);
                tvAMPM = (TextView) getActivity().findViewById(R.id.timeEndAMPM);
                time= TimeHelper.getTime(whatIsClicked);
                tv.setText(time[0]);
                tvAMPM.setText(time[1]);
                break;
        }
    }
}
