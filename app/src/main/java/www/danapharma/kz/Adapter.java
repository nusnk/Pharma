package www.danapharma.kz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context mContext;
    List<PharmacyItem> mData;

    public Adapter(Context mContext, List<PharmacyItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.cards_layout, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(mData.get(i).getTitle());
        myViewHolder.email.setText(mData.get(i).getEmail());
        myViewHolder.phone.setText(mData.get(i).getPhone());
        myViewHolder.address.setText(mData.get(i).getAddress());
        myViewHolder.price.setText(mData.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView title, email, phone, address, price, distance;


        public MyViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            email = itemView.findViewById(R.id.tv_email);
            phone = itemView.findViewById(R.id.tv_phone);
            address = itemView.findViewById(R.id.tv_address);
            price = itemView.findViewById(R.id.tv_price);
            distance = itemView.findViewById(R.id.tv_distance);
        }

    }
}
