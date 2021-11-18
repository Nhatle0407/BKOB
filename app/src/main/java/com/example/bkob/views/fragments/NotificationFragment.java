package com.example.bkob.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentNotificationBinding;
import com.example.bkob.presenters.NotifyPresenter;
import com.example.bkob.views.adapters.NotifyAdapter;
import com.example.bkob.views.interfaces.NotifyInterface;


public class NotificationFragment extends Fragment implements NotifyInterface {
    private FragmentNotificationBinding binding;
    private RecyclerView notifyRv;
    private NotifyPresenter notifyPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notifyRv = binding.rvNotification;
        notifyPresenter = new NotifyPresenter(getContext(), this);

        notifyPresenter.loadNotify();
    }

    @Override
    public void showNotify(NotifyAdapter adapter) {
        notifyRv.setAdapter(adapter);
    }
}