package ru.qualitylab.evotor.loyaltylab.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.evotor.framework.receipt.Position;
import ru.qualitylab.evotor.loyaltylab.R;
import ru.qualitylab.evotor.loyaltylab.model.ProductUi;
import ru.qualitylab.evotor.loyaltylab.util.Mapper;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.Holder> {

    private List<ProductUi> dataset;
    private RecommendationCLickListener listener;

    public interface RecommendationCLickListener {
        void onCLick(int position);
    }

    RecommendationAdapter(List<ProductUi> dataset, RecommendationCLickListener listener) {
        this.dataset = dataset;
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommendation, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.state.setChecked(dataset.get(position).isEnabled());
        holder.price.setText(String.valueOf(dataset.get(position).getPrice()));
        holder.name.setText(dataset.get(position).getName());
        holder.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCLick(holder.getAdapterPosition());
            }
        });
    }

    void setDataset(List<ProductUi> dataset) {
        this.dataset = dataset;
    }

    ProductUi getItemAtPosition(int position) {
        return dataset.get(position);
    }

    boolean isAnyItemSelected() {
        for (ProductUi product : dataset) {
            if (product.isEnabled()) {
                return true;
            }
        }
        return false;
    }

    List<Position> getSelectedItemsPositions(){
        List<ProductUi> returnList = new ArrayList<>();
        for(ProductUi product : dataset){
            if(product.isEnabled()){
                returnList.add(product);
            }
        }
        return Mapper.convertProductToPostitionList(returnList);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView name;
        TextView price;
        Switch state;

        Holder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_item_recommendation_name);
            price = itemView.findViewById(R.id.tv_item_recommendation_price);
            state = itemView.findViewById(R.id.switch_item_recommendation);
        }
    }
}
