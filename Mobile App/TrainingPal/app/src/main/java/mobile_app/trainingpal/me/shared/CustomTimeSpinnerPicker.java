package mobile_app.trainingpal.me.shared;


import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.ikovac.timepickerwithseconds.TimePicker;

import mobile_app.trainingpal.me.R;

public class CustomTimeSpinnerPicker {

    public CustomTimeSpinnerPicker(final EditText editText, final Activity activity){

        final MyTimePickerDialog mTimePicker;

        mTimePicker = new MyTimePickerDialog(activity, R.style.DialogTheme, new MyTimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute, int selectedSeconds) {
                String hourText = selectedHour < 10 ? "0" + selectedHour : "" + selectedHour;
                String minuteText = selectedMinute < 10 ? "0" + selectedMinute : "" + selectedMinute;
                String secondText = selectedSeconds < 10 ? "0" + selectedSeconds : "" + selectedSeconds;
                String finalText = hourText + ":" + minuteText + ":" + secondText;
                editText.setText(finalText);
            }
        },0, 0, 0, true);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }
}
