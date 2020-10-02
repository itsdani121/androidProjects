package com.example.videoplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class imageAdapt extends RecyclerView.Adapter<imageAdapt.viewHolder> {

    private Context mContext;
    private List<images_details> imagesDetails;
    private imageInterface anInterface;

    public imageAdapt(Context mContext, List<images_details> imagesDetails, imageInterface anInterface) {
        this.mContext = mContext;
        this.imagesDetails = imagesDetails;
        this.anInterface = anInterface;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.images_design, parent, false);
        viewHolder holder = new viewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        final images_details obj = imagesDetails.get(position);
        holder.textViewTitle.setText(obj.getTitle());
        holder.textViewSize.setText(obj.getSize());
        holder.textViewPath.setText(obj.getPath());
        holder.imageView.setImageResource(obj.getImage_id());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anInterface.onSingleItemPreview(obj, position);
            }
        });
        holder.img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu imageMenu = new PopupMenu(mContext, holder.img_menu);
                imageMenu.getMenuInflater().inflate(R.menu.images_menu, imageMenu.getMenu());
                imageMenu.show();
                imageMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        boolean isClicked = false;

                        switch (item.getItemId()) {

                            case R.id.image_perview:
                                isClicked = true;
                                Toast.makeText(mContext, "item clicked for preview at " + position, Toast.LENGTH_SHORT).show();
                                anInterface.onSingleItemPreview(obj, position);
                                break;

                            case R.id.image_delete:
                                isClicked = true;
                                Toast.makeText(mContext, "item clicked for delete at" + position, Toast.LENGTH_SHORT).show();
                                anInterface.onActionDeleteItem(obj, position);

                                break;

                            case R.id.image_share:
                                isClicked = true;
                                Toast.makeText(mContext, "item clicked for share at" + position, Toast.LENGTH_SHORT).show();
                                anInterface.onItemShare(obj, position);
                                break;
                            default:
                                break;
                        }
                        return isClicked;
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return imagesDetails.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle, textViewPath, textViewSize;
        private ImageView imageView,img_menu;

        viewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.imageTitle);
            textViewSize = itemView.findViewById(R.id.imageSize);
            textViewPath = itemView.findViewById(R.id.imagePath);
            imageView = itemView.findViewById(R.id.image_View);
            img_menu = itemView.findViewById(R.id.imageMore);


        }
    }
}
