package com.belspec.app.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.belspec.app.R;
import com.belspec.app.retrofit.model.getCarOnEvacuation.response.EvacuationData;
import com.belspec.app.utils.Converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class  CarOnEvacuationAdapter extends RecyclerView.Adapter<CarOnEvacuationAdapter.CarOnEvacuationViewHolder> {
    private  List<EvacuationData> evacuationDataList;
    private List<CarOnEvacuationViewHolder> carHolder;

    public CarOnEvacuationAdapter(){
        super();
        evacuationDataList = new ArrayList<>();
        carHolder = new ArrayList<>();

    }

    @Override
    public CarOnEvacuationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_on_evacuation_item, parent, false);
        CarOnEvacuationViewHolder pvh = new CarOnEvacuationViewHolder(v, parent.getContext());
        carHolder.add(pvh);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final CarOnEvacuationViewHolder holder, final int position) {
        holder.protocol.setText(evacuationDataList.get(position).getProtocol());
        holder.manufacture.setText( evacuationDataList.get(position).getManufacture());
        holder.model.setText(evacuationDataList.get(position).getModel());
        holder.carId.setText(evacuationDataList.get(position).getCarId());
        holder.color.setText(evacuationDataList.get(position).getColor());
        holder.policeDepartment.setText(evacuationDataList.get(position).getPoliceDepartment());
        holder.policeman.setText(evacuationDataList.get(position).getPoliceman());
        holder.organization.setText(evacuationDataList.get(position).getOrganization());
        holder.wrecker.setText(evacuationDataList.get(position).getWrecker());
        holder.clause.setText(evacuationDataList.get(position).getClause());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        holder.evacuationDate.setText(df.format(evacuationDataList.get(position).getEvacuationDate()));
        ImageListAdapter imageListAdapter = new ImageListAdapter();
        String photo1 = evacuationDataList.get(position).getPhoto1();
        if(photo1!=""){
            imageListAdapter.add(Converter.getBitmapFromBase64Stirng(holder.context, photo1));
        }
        String photo2 = evacuationDataList.get(position).getPhoto2();
        if(photo2!=""){
            imageListAdapter.add(Converter.getBitmapFromBase64Stirng(holder.context, photo2));
        }
        String photo3 = evacuationDataList.get(position).getPhoto3();
        if(photo3!=""){
            imageListAdapter.add(Converter.getBitmapFromBase64Stirng(holder.context, photo3));
        }
        String photo4 = evacuationDataList.get(position).getPhoto4();
        if(photo4!=""){
            imageListAdapter.add(Converter.getBitmapFromBase64Stirng(holder.context, photo4));
        }
        holder.rvImages.setAdapter(imageListAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(holder.context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.rvImages.setLayoutManager(llm);

    }

    @Override
    public int getItemCount() {
        return evacuationDataList.size();
    }

    public CarOnEvacuationAdapter(List<EvacuationData> data){
        evacuationDataList = data;
        notifyDataSetChanged();
    }

    public void addEvacuationData(EvacuationData data){
        evacuationDataList.add(data);
        notifyDataSetChanged();
    }

    public void addListEvacuationData(List<EvacuationData> data){
        evacuationDataList.addAll(data);
        notifyDataSetChanged();
    }

    public EvacuationData getEvacuationData(int pos){
        return evacuationDataList.get(pos);
    }

    public void clear() {
        evacuationDataList.clear();
    }


    public static class CarOnEvacuationViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rvImages;
        TextView protocol;
        TextView manufacture;
        TextView model;
        TextView carId;
        TextView color;
        TextView policeDepartment;
        TextView policeman;
        TextView organization;
        TextView wrecker;
        TextView clause;
        TextView evacuationDate;
        Context context;
        public CarOnEvacuationViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            rvImages = (RecyclerView) itemView.findViewById(R.id.rvImageList);
            protocol = (TextView) itemView.findViewById(R.id.txvProtocol);
            manufacture = (TextView)itemView.findViewById(R.id.txvManufacture);
            model = (TextView) itemView.findViewById(R.id.txvModel);
            carId = (TextView) itemView.findViewById(R.id.txvCarId);
            color = (TextView) itemView.findViewById(R.id.txvColor);
            policeDepartment = (TextView) itemView.findViewById(R.id.txvPoliceDepartment);
            policeman = (TextView) itemView.findViewById(R.id.txvPoliceman);
            organization = (TextView) itemView.findViewById(R.id.txvOrganization);
            wrecker = (TextView) itemView.findViewById(R.id.txvWrecker);
            clause = (TextView) itemView.findViewById(R.id.txvClause);
            evacuationDate = (TextView) itemView.findViewById(R.id.txvEvacuationDate);

        }

    }
}
