package com.example.bkob.views.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentAddBookBinding;
import com.example.bkob.models.BookModel;
import com.example.bkob.presenters.AddBookPresenter;
import com.example.bkob.views.adapters.CategoryDropdownAdapter;
import com.example.bkob.views.customView.AddBookSuccessDialog;
import com.example.bkob.views.customView.CustomProgressDialog;
import com.example.bkob.views.interfaces.AddBookInterface;


public class AddBookFragment extends Fragment implements AddBookInterface {

    private FragmentAddBookBinding binding;
    private CustomProgressDialog dialog;
    private AddBookPresenter addBookPresenter;

    //Permission constants
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;

    //image pick constants
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;

    //Permission array
    private String[] cameraPermission;
    private String[] storagePermission;

    //Image uri
    private Uri image_uri;

    private String name = "", description = "", category = "", price = "", quantity = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);

        dialog = new CustomProgressDialog(getContext());

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        addBookPresenter = new AddBookPresenter(getContext(), this);

        binding = FragmentAddBookBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addBookPresenter.getCategory();

        binding.btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAddBook();
            }
        });

        binding.btnBookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });
    }

    private void clickAddBook() {
        dialog.show();
        name = binding.etBookName.getText().toString();
        price =  binding.etPrice.getText().toString();
        quantity = binding.etQuantity.getText().toString();
        description = binding.etDescription.getText().toString();

        BookModel bookModel = new BookModel(name, description, category, price, quantity, image_uri);
        addBookPresenter.addBook(bookModel);
    }


    @Override
    public void setCategory(CategoryDropdownAdapter adapter) {
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = adapter.getItem(position).getName();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = adapter.getItem(0).getName();
            }
        });
    }

    @Override
    public void nameInvalid() {
        dialog.hide();
        binding.etBookName.setError("Tên không thể để trống!");
    }

    @Override
    public void priceInvalid() {
        dialog.hide();
        binding.etPrice.setError("Giá tiền không hợp lệ!");
    }

    @Override
    public void quatityInvalid() {
        dialog.hide();
        binding.etQuantity.setError("Số lượng phải lớn hơn 0!");
    }

    @Override
    public void imageInvalid() {
        dialog.hide();
        Toast.makeText(getContext(), "Vui lòng kèm hình ảnh sách!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addBookSuccess() {
        dialog.hide();
        AddBookSuccessDialog successDialog = new AddBookSuccessDialog(getContext());
        successDialog.show();
        clearInput();
    }

    private void clearInput() {
        binding.etBookName.setText("");
        binding.etPrice.setText("");
        binding.etQuantity.setText("");
        binding.etDescription.setText("");
    }

    @Override
    public void addBookFail(Exception e) {
        dialog.hide();
        Toast.makeText(getContext(), "Đăng bán thất bại: "+e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    // Pick image
    private void showImagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Pick Image").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle click
                if (which == 0) {         //Camera
                    if (checkCameraPermission()) {
                        pickFromCamera();
                    } else {
                        requestCameraPermission();
                    }
                } else {                   //Gallery
                    if (checkStoragePermission()) {
                        pickFromGallery();
                    } else {
                        requestStoragePermission();
                    }
                }
            }
        }).show();
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image Description");

        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(), storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(), cameraPermission, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(getContext(), "Camera permission is necessary...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(getContext(), "Storage permission is necessary...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //get image
                image_uri = data.getData();
                //set image view
                binding.btnBookImage.setImageURI(image_uri);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                binding.btnBookImage.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}