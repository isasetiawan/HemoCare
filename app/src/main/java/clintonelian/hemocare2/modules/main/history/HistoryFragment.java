package clintonelian.hemocare2.modules.main.history;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import clintonelian.hemocare2.R;
import clintonelian.hemocare2.models.Hemoglobin;
import clintonelian.hemocare2.utils.RealmBaseHelper;
import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;

public class HistoryFragment extends Fragment {
//    public Realm realm = Realm.getDefaultInstance();

    View view;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private TextView tvhistory;
//    private   ArrayList<Hemoglobin> historyHbList;
    private LineGraphSeries<DataPoint> hb_list;
    private static final Random RANDOM = new Random();
    private int lastX = 0;
    String accountId;
    private int i= 0;
    private float hemovalueY;
    private String hemovalueX;
    List<Hemoglobin> HbList;
    GraphView graphHemo;
    ArrayList<Hemoglobin> newList = new ArrayList<Hemoglobin>();
    public static HistoryFragment newInstance() {

        Bundle args = new Bundle();

        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_riwayat, container, false);
        tvhistory = (TextView) view.findViewById(R.id.tvhistory);
        //graphic
        graphHemo = (GraphView) view.findViewById(R.id.graph_hemo);
        hb_list = new LineGraphSeries<DataPoint>();


        ///////////////////////////////////////////////////////////////

        graphHemo.getViewport().setYAxisBoundsManual(true);
        graphHemo.getViewport().setMinY(0);
        graphHemo.getViewport().setMaxY(20);

        graphHemo.getViewport().setXAxisBoundsManual(true);
        graphHemo.getViewport().setMinX(0);
        graphHemo.getViewport().setMaxX(5);

        // enable scaling and scrolling
//        graphHemo.getViewport().setScalable(true);
//        graphHemo.getViewport().setScalableY(true);
        graphHemo.getViewport().setScrollable(true);
//        graphHemo.getViewport().setScrollableY(true);


//        addEntry();
        ///////////////////////////////////////////////////////////

        //database
        SharedPreferences myPrefs = getActivity().getSharedPreferences("mySession", MODE_PRIVATE);
        accountId = myPrefs.getString("accountid",null);
        RealmBaseHelper realmBaseHelper = new RealmBaseHelper();
//        final Hemoglobin hemoglobin = realmBaseHelper.find(realm,Hemoglobin.class,"idAccount",accountId);


        //get a refrence to recyclerView
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        //set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Data for recyclerView DUMMY
//        historyHbList = new ArrayList<Hemoglobin>();

        try {
            newList = getDataSort(false); //newList merupakanList hemoglgobin yang sudah di sort
            adapter = new HistoryAdapter(newList);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (newList.size() > 0) {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            for (int i = 0; i < newList.size(); i++) {
                DataPoint point = new DataPoint(i, newList.get(i).getHb());
                series.appendData(point, true, newList.size());
            }
            graphHemo.addSeries(series);
        }


//        adapter = new HistoryAdapter(HbList);


        recyclerView.setAdapter(adapter);

        return view;
    }

    public ArrayList<Hemoglobin> getDataSort(boolean ascending) throws ParseException {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Hemoglobin> historyHbList = new ArrayList<Hemoglobin>();

//        RealmBaseHelper realmBaseHelper = new RealmBaseHelper();
        final List<Hemoglobin> hemolist = new RealmBaseHelper().getAllWhereSorted(realm,Hemoglobin.class,"idAccount",accountId,"idHb",ascending);

        if(hemolist.size() > 0){
            tvhistory.setVisibility(View.INVISIBLE);
            for(Hemoglobin hemo : hemolist){
//                if(hemo.getIdHb()!=null){
                    historyHbList.add(hemo);
                //TODO di grafik di atur supaya null jangan ditampilin
//                    hemovalueY= hemo.getHb();
////                    String dateStr = "06/27/2007";
//                    hemovalueX= hemo.getDate();
//                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//                    Date startDate = (Date)formatter.parse(hemovalueX);
//                    hb_list.appendData(new DataPoint(startDate, hemovalueY), true, 1000);
//                }else{
                    //dataHb ada 1 tapi null, dibuat saat signup
//                    tvhistory.setText("Anda belum melakukan pengukuran");
//                }

            }
        } else {
//            recyclerView.setVisibility(View.INVISIBLE);
            //TODO cek lagi
            tvhistory.setText("Anda belum melakukan pengukuran");

        }
        return historyHbList;
    }
    // add random data to graph
    private void addEntry() {
        for(int i = 0;i<10;i++ ) {
            // here, we choose to display max 10 points on the viewport and we scroll to end
            hb_list.appendData(new DataPoint(lastX++, Double.valueOf(i)), true, 1000);
        }
        //ADD TO LIST
        graphHemo.addSeries(hb_list);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
