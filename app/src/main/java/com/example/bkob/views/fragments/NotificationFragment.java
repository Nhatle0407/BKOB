package com.example.bkob.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bkob.R;
import com.example.bkob.Singleton.OrderSingleton;
import com.example.bkob.databinding.FragmentNotificationBinding;
import com.example.bkob.models.NotifyModel;
import com.example.bkob.presenters.NotifyPresenter;
import com.example.bkob.views.adapters.NotifyAdapter;
import com.example.bkob.views.interfaces.NotifyInterface;
import com.example.bkob.views.interfaces.OrderRInterface;
import com.google.firebase.auth.FirebaseAuth;


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

        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        notifyRv = binding.rvNotification;
        notifyPresenter = new NotifyPresenter(getContext(), this);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            binding.notifyEmpty.setVisibility(View.VISIBLE);
        }
        else{
            notifyPresenter.loadNotify();
        }
    }

    @Override
    public void showNotify(NotifyAdapter adapter) {
        notifyRv.setAdapter(adapter);
        adapter.onClick(new OrderRInterface() {
            @Override
            public void showDetail(NotifyModel notifyModel) {
                replaceFragment(new OrderRFragment());
                OrderSingleton.setNotifyModel(notifyModel);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFragments, fragment)
                .addToBackStack(null)
                .commit();
    }
}