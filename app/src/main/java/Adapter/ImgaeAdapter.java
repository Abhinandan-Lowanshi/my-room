package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.myroom.myroom.R;
import com.myroom.myroom.app.demo.PicHolder_final;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ImgaeAdapter extends RecyclerView.Adapter<ImgaeAdapter.MyViewHolder> {
    ArrayList<PicHolder_final> imagelist = new ArrayList<PicHolder_final>();
    click click;
    public ImgaeAdapter(ArrayList<PicHolder_final> imagelist,click click) {
        this.imagelist = imagelist;
        this.click = click;
    }

    @NonNull
    @NotNull
    @Override
        public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImgaeAdapter.MyViewHolder holder, int position) {
        holder.image.setImageURI(imagelist.get(position).getUri());
         holder.tv_caption_edit.setText(imagelist.get(position).getCaption());

        holder.img_edit_caption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.editCaption(position);
            }
        });
             holder.crose.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     click.updateUI(position);
                 }
             });



    }
public interface click
 {
   public  void updateUI(int pos);
   public void editCaption(int pos);

}
    @Override
    public int getItemCount() {
        return imagelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView image,crose;
        ImageView img_edit_caption;
        AppCompatTextView tv_caption_edit;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tv_caption_edit = itemView.findViewById(R.id.tv_caption_edit);
            img_edit_caption = itemView.findViewById(R.id.img_edit_caption);
            crose = itemView.findViewById(R.id.crose);
        }
    }
}
