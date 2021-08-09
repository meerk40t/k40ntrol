package org.meerk40t.k40ntrol.ui.host;

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

import org.meerk40t.k40ntrol.databinding.FragmentHostBinding;

public class HostFragment extends Fragment {

    private HostViewModel hostViewModel;
    private FragmentHostBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hostViewModel =
                new ViewModelProvider(this).get(HostViewModel.class);

        binding = FragmentHostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHost;
        hostViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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