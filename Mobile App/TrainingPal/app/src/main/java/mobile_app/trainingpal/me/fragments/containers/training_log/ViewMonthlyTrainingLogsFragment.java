package mobile_app.trainingpal.me.fragments.containers.training_log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import mobile_app.trainingpal.me.R;
import mobile_app.trainingpal.me.ViewTrainingLogActivity;
import mobile_app.trainingpal.me.adapters.TrainingLogListAdapter;
import mobile_app.trainingpal.me.interfaces.callbacks.IAPITrainingLogsCallback;
import mobile_app.trainingpal.me.interfaces.models.ITrainingLog;
import mobile_app.trainingpal.me.models.TrainingLog;
import mobile_app.trainingpal.me.models.network.Header;
import mobile_app.trainingpal.me.models.network.Request;
import mobile_app.trainingpal.me.services.AlertService;
import mobile_app.trainingpal.me.services.LocalAuthService;
import mobile_app.trainingpal.me.services.network.TrainingLogService;
import mobile_app.trainingpal.me.shared.CustomMonthPicker;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class ViewMonthlyTrainingLogsFragment extends Fragment {

    private View rootView;
    private CustomMonthPicker customMonthPicker;

    private EditText monthSelecter;

    private String currentMonth;
    private String currentYear;

    private ArrayList<ITrainingLog> trainingLogArrayList;
    private ListView listView;
    private TrainingLogListAdapter trainingLogListAdapter;

    private TextView emptyText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_training_log_monthly, container, false);

        initialiseVariables();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTrainingLogs();
    }

    private void initialiseVariables() {
        monthSelecter = rootView.findViewById(R.id.test1);
        customMonthPicker = new CustomMonthPicker(monthSelecter, getActivity(), true);

        currentYear = customMonthPicker.getYearIndexString();
        currentMonth = customMonthPicker.getMonthIndexString();

        monthSelecter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                currentYear = customMonthPicker.getYearIndexString();
                currentMonth = customMonthPicker.getMonthIndexString();

                getTrainingLogs();
            }
        });

        listView = rootView.findViewById(R.id.fragment_monthly_training_log_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TrainingLog trainingLog = (TrainingLog) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getContext(), ViewTrainingLogActivity.class);
                intent.putExtra("LOG_ID", trainingLog.getId());
                startActivity(intent);
            }
        });

        emptyText = rootView.findViewById(R.id.fragment_monthly_training_log_list_empty_text);
        listView.setEmptyView(emptyText);
    }

    private void getTrainingLogs() {

        final Activity currentActivity = getActivity();
        String token = NetworkConstants.BEARER_AUTH_TYPE + LocalAuthService.getAuthObject(currentActivity).getTokenString();
        String username = LocalAuthService.getAuthObject(currentActivity).getUserId();

        Request request = new Request(
                getResources().getString(R.string.app_api_base_url),
                new Header(NetworkConstants.AUTHORIZATION_HEADER, token)
        );

        TrainingLogService.getMonthlyTrainingLogs(username, currentMonth, currentYear, request, new IAPITrainingLogsCallback() {
                    @Override
                    public void OnSuccess(ITrainingLog[] trainingLogs) {
                        trainingLogArrayList = new ArrayList<ITrainingLog>();
                        trainingLogArrayList.addAll(Arrays.asList(trainingLogs));

                        trainingLogListAdapter = new TrainingLogListAdapter(getActivity(), trainingLogArrayList);
                        listView.setAdapter(trainingLogListAdapter);
                    }

                    @Override
                    public void OnError(String errorMessage) {
                        emptyText.setText(errorMessage);
                        if (trainingLogListAdapter != null) {
                            trainingLogListAdapter.clear();
                        }
                    }
                }

        );
    }
}
