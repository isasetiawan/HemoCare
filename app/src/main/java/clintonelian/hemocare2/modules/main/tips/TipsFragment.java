package clintonelian.hemocare2.modules.main.tips;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import clintonelian.hemocare2.R;
import clintonelian.hemocare2.models.Account;
import clintonelian.hemocare2.models.Hemoglobin;
import clintonelian.hemocare2.modules.main.akun.AkunFragment;
import clintonelian.hemocare2.modules.main.history.HistoryFragment;
import clintonelian.hemocare2.modules.main.ukur.UkurFragment;
import clintonelian.hemocare2.utils.RealmBaseHelper;
import static clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity.data1;
import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;

public class TipsFragment extends Fragment {
    //TIPS BERDASARKAN HASIL PENGUKURAN YANG DI DAPAT
    public Realm realm = Realm.getDefaultInstance();

    View view;
    private RecyclerView recyclerView;
    private TipsAdapter adapter;
    private   ArrayList<Tips> tipsArrayList;
    float nilaiHb;
    Integer nilaiUmur;
    String nilaiGender;
    public static   TipsFragment newInstance() {
        Bundle args = new Bundle();

        TipsFragment fragment = new TipsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tips, container, false);

        //database
        SharedPreferences myPrefs = getActivity().getSharedPreferences("mySession", MODE_PRIVATE);
        String accountId = myPrefs.getString("accountid",null);
        RealmBaseHelper realmBaseHelper = new RealmBaseHelper();
        //final Hemoglobin hemoglobin = realmBaseHelper.find(realm,Hemoglobin.class,"idAccount",accountId);

        final Account account = realmBaseHelper.find(realm,Account.class,"idAccount",accountId);
		

        //get a refrence to recyclerView
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        //set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        nilaiHb=Float.parseFloat(data1);
		//BARU DITAMBAHKAN
		
        nilaiUmur=Integer.parseInt(account.getAge());
		nilaiGender = account.getGender();
        tipsArrayList = new ArrayList<>();

        //Data for recyclerView
        //data for recycler view
        if(nilaiHb==00.0){
		//kalau Hbnya nol gmn ya, pas bikin akun baru, nilai Hbnya masih nol
		tipsArrayList.add(new Tips("Anda belum melakukan pengukuran Hemoglobin", " " ));
        }
        else if(nilaiHb>0 && nilaiHb< 30)
		{	
			if (nilaiUmur<=5) {
				//anak anak sampai umur 6 tahun
				if(nilaiHb<7){
					//anemua berat <7gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda sangat rendah", "Anda mengalami Anemia berat " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				}else if(nilaiHb<10){
					//anemua berat 7-10 gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda rendah", "Anda mengalami Anemia sedang " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				}else if(nilaiHb<11){
					//anemua ringan 10-11 gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda cukup rendah", "Anda mengalami Anemia ringan " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				} else if(nilaiHb<18){
					//tidak anemia >11 gr/dL, normal sampai 18gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda normal", "Anda mengalami tidak Anemia " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				} else {
					//Hb terlalu tinggi, lebih dari 18 , mungkin terkena Polistemia 
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda tidak normal", "Anda kemungkinan mengalami Polistemia" +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				}
			}
			else if (nilaiUmur<12) {
				//anak anak umur 5-11 tahun
				if(nilaiHb<8){
					//anemua berat <8gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda sangat rendah", "Anda mengalami Anemia berat " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				}else if(nilaiHb<11){
					//anemua berat 8-11 gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda rendah", "Anda mengalami Anemia sedang " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				}else if(nilaiHb<11.5){
					//anemua ringan 11-11.5 gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda cukup rendah", "Anda mengalami Anemia ringan " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				} else if(nilaiHb<18){
					//tidak anemia >11.5 gr/dL, normal sampai 18gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda normal", "Anda mengalami tidak Anemia " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				} else {
					//Hb terlalu tinggi, lebih dari 18 , mungkin terkena Polistemia 
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda tidak normal", "Anda kemungkinan mengalami Polistemia" +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				}
			}
			else if (nilaiUmur<15) {
				//anak anak umur 12-14
				if(nilaiHb<8){
					//anemua berat <8gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda sangat rendah", "Anda mengalami Anemia berat " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				}else if(nilaiHb<11){
					//anemua berat 8-11 gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda rendah", "Anda mengalami Anemia sedang " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				}else if(nilaiHb<12){
					//anemua ringan 11-12 gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda cukup rendah", "Anda mengalami Anemia ringan " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				} else if(nilaiHb<18){
					//tidak anemia >12 gr/dL, normal sampai 18gr/dL
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda normal", "Anda mengalami tidak Anemia " +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				} else {
					//Hb terlalu tinggi, lebih dari 18 , mungkin terkena Polistemia 
					tipsArrayList.add(new Tips("Nilai Hemoglobin Anda tidak normal", "Anda kemungkinan mengalami Polistemia" +
				" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
				}
			}
			else if (nilaiUmur<100) {
				//usia 15 tahun ke atas
				if(nilaiGender=="Laki-laki"){
					//pria berusia 15 tahun ke atas
					if(nilaiHb<8){
						//anemua berat <8gr/dL
						tipsArrayList.add(new Tips("Nilai Hemoglobin Anda sangat rendah", "Anda mengalami Anemia berat " +
					" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
					}else if(nilaiHb<11){
						//anemua berat 8-11 gr/dL
						tipsArrayList.add(new Tips("Nilai Hemoglobin Anda rendah", "Anda mengalami Anemia sedang " +
					" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
					}else if(nilaiHb<13){
						//anemua ringan 11-13 gr/dL
						tipsArrayList.add(new Tips("Nilai Hemoglobin Anda cukup rendah", "Anda mengalami Anemia ringan " +
					" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
					} else if(nilaiHb<18){
						//tidak anemia >13 gr/dL, normal sampai 18gr/dL
						tipsArrayList.add(new Tips("Nilai Hemoglobin Anda normal", "Anda mengalami tidak Anemia " +
					" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
					} else {
						//Hb terlalu tinggi, lebih dari 18 , mungkin terkena Polistemia 
						tipsArrayList.add(new Tips("Nilai Hemoglobin Anda tidak normal", "Anda kemungkinan mengalami Polistemia" +
					" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
					}
				}
				else if(nilaiGender=="Perempuan"){
					//pria berusia 15 tahun ke atas
					if(nilaiHb<8){
						//anemua berat <8gr/dL
						tipsArrayList.add(new Tips("Nilai Hemoglobin Anda sangat rendah", "Anda mengalami Anemia berat " +
					" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " + 
					" Untuk wanita hamil, Anemia berat bila nilai Hemoglobin berada di bawah 7 gr/dL, di atas 7gr/dL Anda mengalami Anemia sedang " ));
					}else if(nilaiHb<11){
						//anemua berat 8-11 gr/dL
						tipsArrayList.add(new Tips("Nilai Hemoglobin Anda rendah", "Anda mengalami Anemia sedang " +
					" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " + 
					" Untuk wanita hamil, Anemia sedang bila nilai Hemoglobin berada di antara 7 gr/dL dan 10 gr/dL, di atas 10gr/dL Anda mengalami Anemia ringan" ));
					}else if(nilaiHb<12){
						//anemua ringan 11-12 gr/dL
						tipsArrayList.add(new Tips("Nilai Hemoglobin Anda cukup rendah", "Anda mengalami Anemia ringan " +
					" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " + 
					" Untuk wanita hamil, Anemia ringan bila nilai Hemoglobin berada di antara 10 gr/dL dan 11 gr/dL, di atas 11gr/dL Anda tidak mengalami anemia" ));
					} else if(nilaiHb<18){
						//tidak anemia >12 gr/dL, normal sampai 18gr/dL
						tipsArrayList.add(new Tips("Nilai Hemoglobin Anda normal", "Anda mengalami tidak Anemia " +
					" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
					} else {
						//Hb terlalu tinggi, lebih dari 18 , mungkin terkena Polistemia 
						tipsArrayList.add(new Tips("Nilai Hemoglobin Anda tidak normal", "Anda kemungkinan mengalami Polistemia" +
					" Isi dengan TIPS Isi dengan TIPS Isi dengan TIPS " ));
					}
				}
			}
		} else
		{
		//untuk pembacaan tidak normal, kemungkinan bukan data HB yg diterima
		tipsArrayList.add(new Tips("Anda belum melakukan pengukuran Hemoglobin", " " ));
		}
		
        tipsArrayList.add(new Tips("Tahukah Anda", "menurut WHO(2015) NIlai Hemoglobin yang bagus untuk GENDER berusia UMUR adalah 10 hingga 13" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Nulla in aliquet justo. Donec tincidunt, leo imperdiet pretium posuere, sapien leo " +
                "auctor mi, ac scelerisque diam leo vel sapien. Morbi lobortis, sem sed molestie fermentum."));

        //set adapter
        adapter = new TipsAdapter(tipsArrayList);
        recyclerView.setAdapter(adapter);


        return view;
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
