package org.meerk40t.k40ntrol.ui.controller;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.meerk40t.k40ntrol.databinding.FragmentControllerBinding;

public class ControllerFragment extends Fragment {

    private ControllerViewModel controllerModel;
    private FragmentControllerBinding binding;

    public static ControllerFragment newInstance() {
        return new ControllerFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        controllerModel =
                new ViewModelProvider(this).get(ControllerViewModel.class);

        binding = FragmentControllerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textController;
        controllerModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}