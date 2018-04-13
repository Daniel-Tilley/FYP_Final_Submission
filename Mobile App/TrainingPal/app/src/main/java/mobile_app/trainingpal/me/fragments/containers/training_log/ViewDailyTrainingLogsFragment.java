package mobile_app.trainingpal.me.fragments.containers.training_log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import mobile_app.trainingpal.me.R;
import mobile_app.trainingpal.me.ViewTrainingLogActivity;
import mobile_app.trainingpal.me.adapters.TrainingLogListAdapter;
import mobile_app.trainingpal.me.interfaces.callbacks.IAPITrainingLogsCallback;
import mobile_app.trainingpal.me.interfaces.models.ITrainingLog;
import mobile_app.trainingpal.me.models.TrainingLog;
import mobile_app.trainingpal.me.models.network.Header;
import mobile_app.trainingpal.me.models.network.Request;
import mobile_app.trainingpal.me.services.LocalAuthService;
import mobile_app.trainingpal.me.services.network.TrainingLogService;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class ViewDailyTrainingLogsFragment extends Fragment {

    private View rootView;
    private ArrayList<ITrainingLog> trainingLogArrayList;
    private Calendar calendar;
    private ListView listView;
    private TextView emptyText;
    private TrainingLogListAdapter trainingLogListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_training_log_daily, container, false);

        initialiseVariables();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTrainingLogs();
    }

    private void initialiseVariables() {
        calendar = Calendar.getInstance();
        listView = rootView.findViewById(R.id.fragment_daily_training_log_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TrainingLog trainingLog = (TrainingLog) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getContext(), ViewTrainingLogActivity.class);
                intent.putExtra("LOG_ID", trainingLog.getId());
                startActivity(intent);
            }
        });

        emptyText = rootView.findViewById(R.id.fragment_daily_training_log_list_empty_text);
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

        String day = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : "" + calendar.get(Calendar.DAY_OF_MONTH);
        String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? "0" + (calendar.get(Calendar.MONTH) + 1) : "" + (calendar.get(Calendar.MONTH) + 1);
        String year = "" + calendar.get(Calendar.YEAR);

        TrainingLogService.getDailyTrainingLogs(username, day,month, year, request, new IAPITrainingLogsCallback() {
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
