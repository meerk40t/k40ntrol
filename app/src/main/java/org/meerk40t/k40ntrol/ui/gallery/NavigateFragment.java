package org.meerk40t.k40ntrol.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.meerk40t.k40ntrol.databinding.FragmentNavigateBinding;

public class NavigateFragment extends Fragment {

    private NavigateViewModel navigateViewModel;
    private FragmentNavigateBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        navigateViewModel =
                new ViewModelProvider(this).get(NavigateViewModel.class);

        binding = FragmentNavigateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.statusText;
        navigateViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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