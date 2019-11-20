package www.danapharma.kz;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Account extends Fragment {

    ArrayList<History> mList = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterHistory adapter;
    public Account() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.history_recycler_view);
        ImageView update = view.findViewById(R.id.iv_update);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new AdapterHistory(getActivity(), mList);
        recyclerView.setAdapter(adapter);
        updateHistory();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHistory();
            }
        });

        return view;
    }

    public void updateHistory(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String history = sharedPref.getString(getString(R.string.history),"");

        if(history.length() > 0){
            mList.clear();
            String[] meds = history.split(",");
            for(int i = 0; i < meds.length; i++){
                String[] h = meds[i].split(":");
               mList.add(new History(h[0],h[1]));
            }
            Toast.makeText(getActivity(), "Put "+ mList.size(), Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }
    }




}
