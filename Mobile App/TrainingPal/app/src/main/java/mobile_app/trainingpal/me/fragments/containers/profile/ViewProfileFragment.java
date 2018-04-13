package mobile_app.trainingpal.me.fragments.containers.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import mobile_app.trainingpal.me.EditUserActivity;
import mobile_app.trainingpal.me.R;
import mobile_app.trainingpal.me.interfaces.callbacks.IAPIUserCallback;
import mobile_app.trainingpal.me.interfaces.models.IUser;
import mobile_app.trainingpal.me.models.User;
import mobile_app.trainingpal.me.models.network.Header;
import mobile_app.trainingpal.me.models.network.Request;
import mobile_app.trainingpal.me.services.AlertService;
import mobile_app.trainingpal.me.services.LocalAuthService;
import mobile_app.trainingpal.me.services.network.UserService;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class ViewProfileFragment extends Fragment {

    private View rootView;

    private TextView usernameValue;
    private TextView firstnameValue;
    private TextView lastnameValue;
    private TextView emailValue;
    private TextView dobValue;
    private TextView typeValue;
    private TextView locationValue;
    private TextView bioValue;

    private Button editProfileButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_profile_view, container, false);

        initialiseVariables();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserDetails();
    }

    private void initialiseVariables() {
        usernameValue = rootView.findViewById(R.id.user_profile_username_value);
        firstnameValue = rootView.findViewById(R.id.user_profile_first_name_value);
        lastnameValue = rootView.findViewById(R.id.user_profile_last_name_value);
        emailValue = rootView.findViewById(R.id.user_profile_email_value);
        dobValue = rootView.findViewById(R.id.user_profile_date_of_birth_value);
        typeValue = rootView.findViewById(R.id.user_profile_type_value);
        locationValue = rootView.findViewById(R.id.user_profile_location_value);
        bioValue = rootView.findViewById(R.id.user_profile_bio_value);

        editProfileButton = rootView.findViewById(R.id.user_profile_edit_profile_button);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditUserActivity.class));
            }
        });
    }

    private void setUserDetails() {
        String username = LocalAuthService.getAuthObject(getActivity()).getUserId();
        String token = NetworkConstants.BEARER_AUTH_TYPE + LocalAuthService.getAuthObject(getActivity()).getTokenString();
        final Activity currentActivity = getActivity();

        Request request = new Request(
                getResources().getString(R.string.app_api_base_url),
                new Header(NetworkConstants.AUTHORIZATION_HEADER, token)
        );

        UserService.getUser(username, request, new IAPIUserCallback() {
            @Override
            public void OnSuccess(IUser user) {
                usernameValue.setText(user.getId());
                firstnameValue.setText(user.getF_Name());
                lastnameValue.setText(user.getL_Name());
                emailValue.setText(user.getE_Mail());
                dobValue.setText(user.getDOB());
                typeValue.setText(user.getType());
                locationValue.setText(user.getLocation());
                bioValue.setText((user.getBio() == null ? "" : user.getBio()));
            }

            @Override
            public void OnError(String errorMessage) {
                AlertService.LongAlert(currentActivity, errorMessage);
            }
        });
    }
}
