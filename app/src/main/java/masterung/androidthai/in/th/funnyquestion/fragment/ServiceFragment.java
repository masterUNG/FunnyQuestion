package masterung.androidthai.in.th.funnyquestion.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import masterung.androidthai.in.th.funnyquestion.R;
import masterung.androidthai.in.th.funnyquestion.utility.MyConstant;

public class ServiceFragment extends Fragment{

    private int indexAnInt = 0;

    public static ServiceFragment serviceInstance(int index) {

        ServiceFragment serviceFragment = new ServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Index", index);
        serviceFragment.setArguments(bundle);
        return serviceFragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        indexAnInt = getArguments().getInt("Index");

//        Show Title
        showTitle();

    }

    private void showTitle() {
        TextView textView = getView().findViewById(R.id.txtShowTitle);
        MyConstant myConstant = new MyConstant();
        String[] strings = myConstant.getUnitTitleStrings();
        textView.setText(strings[indexAnInt]);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service,container, false);
        return view;
    }
}   // Main Class
