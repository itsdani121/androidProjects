package com.example.videoplayer;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class images_frag extends Fragment implements imageInterface {

    // yahan par aapko  recyclerview ka kaam krna hai jaiay aap pehlay activity main krte arahay thay pichlay lectures main
    private List<images_details> imagesDetailsList = new ArrayList<>();
    private RecyclerView imageRecyclerView;
    private imageAdapt imgAdapt;
    private images_details imagesSwipe;


    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                imagesSwipe = imagesDetailsList.get(position);

                showDialogBox(position, true);

            }
        }
    };


    public images_frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.images_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageRecyclerView = view.findViewById(R.id.imageRecycle);
        populateImages();
    }

    private void populateImages() {
        for (int i = 0; i < 20; i++) {
            int imageView = R.drawable.ic_launcher_background;
            String title = "title is " + i;
            String size = "size is " + i;
            String path = "path is " + i;
            images_details obj = new images_details(title, size, path, imageView);
            imagesDetailsList.add(obj);

        }
        setUpRecycleView(imagesDetailsList);
    }

    private void setUpRecycleView(List<images_details> imagesDetailsList) {
        imgAdapt = new imageAdapt(getContext(), imagesDetailsList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        imageRecyclerView.setLayoutManager(linearLayoutManager);
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setAdapter(imgAdapt);
        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(imageRecyclerView);
    }


    private void showDialogBox(final int position, final boolean isSwapped) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("confirmation");
        builder.setMessage("do you want to delete this item");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteItem(position);
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isSwapped) {
                    imagesDetailsList.add(position, imagesSwipe);
                    imgAdapt.notifyItemInserted(position);
                }

                dialog.dismiss();
                // sir is main undo kaha se ho ga
            }
        });
        AlertDialog mydialog = builder.create();
        mydialog.show();
    }

    private void deleteItem(final int position) {

        imagesSwipe = imagesDetailsList.get(position);

        imagesDetailsList.remove(position);
        imgAdapt.notifyItemRemoved(position);
        Snackbar snackbar = Snackbar.make(imageRecyclerView, "item at position" + position, Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesDetailsList.add(position, imagesSwipe);
                imgAdapt.notifyItemInserted(position);
            }
        });
        snackbar.show();

    }


    @Override
    public void onSingleItemPreview(Object obj, int position) {
        images_details objct = (images_details) obj;
        Toast.makeText(getContext(), "item clicked on position" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActionDeleteItem(Object obj, int position) {
        showDialogBox(position, false);
    }

    @Override
    public void onItemShare(Object obj, int Position) {

    }
}
