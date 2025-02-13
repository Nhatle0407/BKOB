package com.example.bkob.views.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bkob.R;
import com.example.bkob.Singleton.DetailSingleton;
import com.example.bkob.databinding.FragmentHomeBinding;
import com.example.bkob.models.BookModel;
import com.example.bkob.presenters.HomePresenter;
import com.example.bkob.views.adapters.BookAdapter;
import com.example.bkob.views.adapters.CategoryAdapter;
import com.example.bkob.views.interfaces.DetailInterface;
import com.example.bkob.views.interfaces.HomeInterface;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment implements HomeInterface {
    private FragmentHomeBinding binding;
    private HomePresenter homePresenter;
    private RecyclerView categoryRv, bookRv;
    private ShimmerFrameLayout shimmerCategory, shimmerBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        homePresenter = new HomePresenter(this, getContext());
        categoryRv = binding.rvCategory;
        bookRv = binding.rvAllBook;
        shimmerCategory = binding.shimmerCategory;
        shimmerBook = binding.shimmerBook;
        loadCategory();
        loadAllBook();

        binding.btnCartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() == null){
                    Toast.makeText(getContext(), "Vui lòng đăng nhập trước!", Toast.LENGTH_SHORT).show();
                    return;
                }
                replaceFragment(new CartFragment());
                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
            }
        });

        binding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    binding.edtSearch.clearFocus();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                homePresenter.search(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
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

    private void loadAllBook() {
        homePresenter.loadAllBook();
    }

    private void loadCategory() {
        homePresenter.loadCategory();
    }

    @Override
    public void showCategory(CategoryAdapter adapter) {
        shimmerCategory.stopShimmer();
        shimmerCategory.setVisibility(View.GONE);
        categoryRv.setAdapter(adapter);
        adapter.onClick(this);
    }
    @Override
    public void showAllBook(BookAdapter adapter) {
        shimmerBook.stopShimmer();
        shimmerBook.setVisibility(View.GONE);
        binding.tvEmptyList.setVisibility(View.GONE);
        bookRv.setAdapter(adapter);
        adapter.onClick(new DetailInterface() {
            @Override
            public void detailScreen(BookModel bookModel) {
                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
                replaceFragment(new DetailFragment());
                DetailSingleton.setBookModel(bookModel);
            }
        });
    }

    @Override
    public void loadBookInCategory(String category) {
        binding.tvAllBook.setText(category);
        homePresenter.loadBookInCategory(category);
    }

    @Override
    public void emptyList() {
        shimmerBook.stopShimmer();
        shimmerBook.setVisibility(View.GONE);
        binding.tvEmptyList.setVisibility(View.VISIBLE);
    }
}