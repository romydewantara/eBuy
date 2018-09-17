package com.example.mobile.ebuy.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.mobile.ebuy.R;
import com.example.mobile.ebuy.activity.MainActivity;
import com.example.mobile.ebuy.adapter.ItemsAdapter;
import com.example.mobile.ebuy.model.ItemsModel;
import com.example.mobile.ebuy.recyclerview.SpaceItemDecorationGridView;

import java.util.ArrayList;
import java.util.List;


public class StoreFragment extends Fragment {

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mGridViewLayoutManager;
    private ItemsAdapter mAdapter;
    CheckBox checkBox;
    int[] selectedId;

    private List<ItemsModel> itemsModelList = new ArrayList<>();

    public StoreFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        itemsModelList = new ItemsModel().getData();
        mAdapter = new ItemsAdapter(itemsModelList, getActivity());


        recyclerView.setHasFixedSize(true);
        mGridViewLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mGridViewLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(mGridViewLayoutManager);
        recyclerView.setAdapter(mAdapter);
        SpaceItemDecorationGridView decoration = new SpaceItemDecorationGridView(10);
        recyclerView.addItemDecoration(decoration);

        return view;
    }

}