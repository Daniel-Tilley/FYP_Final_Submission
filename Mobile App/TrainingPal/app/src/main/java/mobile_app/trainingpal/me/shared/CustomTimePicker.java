package mobile_app.trainingpal.me.shared;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import mobile_app.trainingpal.me.R;

public class CustomTimePicker {

    public CustomTimePicker(final EditText editText, final Activity activity){

        final TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(activity, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hourText = selectedHour < 10 ? "0" + selectedHour : "" + selectedHour;
                String minuteText = selectedMinute < 10 ? "0" + selectedMinute : "" + selectedMinute;
                String finalText = hourText + ":" + minuteText;
                editText.setText(finalText);
            }
        },12, 0, true);

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
