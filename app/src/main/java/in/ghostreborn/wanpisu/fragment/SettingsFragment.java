package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;

public class SettingsFragment extends Fragment {

    SwitchCompat allowAdultSwitch;
    SwitchCompat allowUnknownSwitch;
    RadioGroup subDubRadio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        allowAdultSwitch = view.findViewById(R.id.settings_allow_adult);
        allowUnknownSwitch = view.findViewById(R.id.settings_allow_unknown);
        subDubRadio = view.findViewById(R.id.settings_sub_dub_radio);

        setUpValues();
        setClickListeners();

        return view;
    }

    private void setUpValues(){
        allowAdultSwitch.setChecked(getBoolean(Constants.isAdult));
        allowUnknownSwitch.setChecked(getBoolean(Constants.isUnknown));
        if (Constants.subOrDub.equals(Constants.SUB)){
            subDubRadio.check(R.id.settings_radio_sub);
        }else {
            subDubRadio.check(R.id.settings_radio_dub);
        }
    }

    private boolean getBoolean(String check){
        return check.equals(Constants.TRUE);
    }

    private void setClickListeners(){
        allowAdultSwitch.setOnClickListener(v -> {
            if (allowAdultSwitch.isChecked()){
                Constants.WanpisuPreference.edit()
                        .putString(Constants.PREFERENCE_ALLOW_ADULT, Constants.TRUE)
                        .apply();
                Constants.isAdult = Constants.TRUE;
            }else {
                Constants.WanpisuPreference.edit()
                        .putString(Constants.PREFERENCE_ALLOW_ADULT, Constants.FALSE)
                        .apply();
                Constants.isAdult = Constants.FALSE;
            }
        });

        allowUnknownSwitch.setOnClickListener(v -> {
            if (allowUnknownSwitch.isChecked()){
                Constants.WanpisuPreference.edit()
                        .putString(Constants.PREFERENCE_ALLOW_UNKNOWN, Constants.TRUE)
                        .apply();
                Constants.isUnknown = Constants.TRUE;
            }else {
                Constants.WanpisuPreference.edit()
                        .putString(Constants.PREFERENCE_ALLOW_UNKNOWN, Constants.FALSE)
                        .apply();
                Constants.isUnknown = Constants.FALSE;
            }
        });

        subDubRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.settings_radio_sub){
                Constants.WanpisuPreference.edit()
                        .putString(Constants.PREFERENCE_SUB_DUB, Constants.SUB)
                        .apply();
                Constants.subOrDub = Constants.SUB;
            }else if(checkedId == R.id.settings_radio_dub){
                Constants.WanpisuPreference.edit()
                        .putString(Constants.PREFERENCE_SUB_DUB, Constants.DUB)
                        .apply();
                Constants.subOrDub = Constants.DUB;
            }
        });
    }

}