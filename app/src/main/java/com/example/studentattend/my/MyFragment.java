package com.example.studentattend.my;

import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.studentattend.R;

public class MyFragment extends Fragment {

//    private MyViewModel myViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*myViewModel =
                ViewModelProviders.of(this).get(MyViewModel.class);*/

        View root = inflater.inflate(R.layout.fragment_my, container, false);
        /*final TextView textView = root.findViewById(R.id.text_my);
        myViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        TextView textMy = root.findViewById(R.id.text_my);
        TextPaint textPaint = textMy.getPaint();
        textPaint.setFakeBoldText(true);
        return root;
    }


}