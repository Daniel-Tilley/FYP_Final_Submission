package mobile_app.trainingpal.me.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import mobile_app.trainingpal.me.R;
import mobile_app.trainingpal.me.interfaces.models.ITrainingLog;
import mobile_app.trainingpal.me.models.TrainingLog;

public class TrainingLogListAdapter extends ArrayAdapter<ITrainingLog>{

    private static class ViewHolder {
        TextView nameField;
        TextView dateField;
        TextView typeField;
    }

    public TrainingLogListAdapter(Context context, ArrayList<ITrainingLog> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TrainingLog trainingLog = (TrainingLog) getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_training_log, parent, false);

            viewHolder.nameField = convertView.findViewById(R.id.row_training_log_name_field);
            viewHolder.dateField = convertView.findViewById(R.id.row_training_log_date_field);
            viewHolder.typeField = convertView.findViewById(R.id.row_training_log_type_field);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lookup view for data population


        // Populate the data into the template view using the data object

        viewHolder.nameField.setText(trainingLog.getLog_Name() != null ? trainingLog.getLog_Name() : "No Name");

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat targetFormat = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault());

        Date date = null;

        try {
            date = originalFormat.parse(trainingLog.getLog_Date());
            String formattedDate = targetFormat.format(date);
            viewHolder.dateField.setText(formattedDate);
        } catch (ParseException e) {
            viewHolder.dateField.setText(trainingLog.getLog_Date());
        }

        switch (trainingLog.getType_ID()) {
            case 1:
                viewHolder.typeField.setText("Rest Day");
                break;
            case 2:
                viewHolder.typeField.setText("Swimming");
                break;
            case 3:
                viewHolder.typeField.setText("Cycling");
                break;
            case 4:
                viewHolder.typeField.setText("Running");
                break;
            default:
                viewHolder.typeField.setText("");
                break;
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
