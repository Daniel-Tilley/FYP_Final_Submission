package mobile_app.trainingpal.me.shared;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mobile_app.trainingpal.me.R;

public class CustomMonthPicker {

    private Calendar calendar;
    private EditText editText;

    private int currentMonth;
    private int currentYear;

    final private YearMonthPickerDialog yearMonthPickerDialog;

    public CustomMonthPicker(final EditText editText, final Activity activity, boolean addOnClickListener){

        calendar = Calendar.getInstance();
        this.editText = editText;

        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);

        updateLabel();

        yearMonthPickerDialog = new YearMonthPickerDialog(activity, new YearMonthPickerDialog.OnDateSetListener() {
            @Override
            public void onYearMonthSet(int year, int month) {
                currentMonth = month;
                currentYear = year;
                updateLabel();
            }
        }, R.style.DialogTheme);

        if (addOnClickListener) {
            editText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    yearMonthPickerDialog.show();
                }
            });
        }
    }

    private void updateLabel() {
        String myFormat = "MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, currentMonth);

        editText.setText(sdf.format(calendar.getTime()));
    }

    public int getCurrentMonth() {
        return currentMonth + 1;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public String getMonthIndexString() {
        return (getCurrentMonth() < 10 ? ("0" + getCurrentMonth()) : ("" + getCurrentMonth()));
    }

    public String getYearIndexString() {
        return "" + currentYear;
    }

    public YearMonthPickerDialog getYearMonthPickerDialog() {
        return yearMonthPickerDialog;
    }
}
