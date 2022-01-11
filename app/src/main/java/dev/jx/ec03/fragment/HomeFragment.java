package dev.jx.ec03.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dev.jx.ec03.CodemoniumApplication;
import dev.jx.ec03.R;
import dev.jx.ec03.activity.MainActivity;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        TextView homeTitleTextView = view.findViewById(R.id.home_appbar_title_text_view);
        homeTitleTextView.setText(getResources().getString(R.string.home_appbar_title, CodemoniumApplication.getUser().getName()));

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            ((MainActivity) context).setBottomNavigationVisibility(View.VISIBLE);
        }
    }
}
