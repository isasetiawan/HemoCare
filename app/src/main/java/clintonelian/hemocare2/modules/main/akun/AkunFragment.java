package clintonelian.hemocare2.modules.main.akun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import clintonelian.hemocare2.R;
import clintonelian.hemocare2.models.Account;
import clintonelian.hemocare2.modules.flashscreen.FlashScreenActivity;
import clintonelian.hemocare2.utils.RealmBaseHelper;
import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;

public class AkunFragment extends Fragment {
    private final static String TAG = AkunFragment.class.getSimpleName();
    Account account;
    View view;
    Button editAkun,logout;
    SharedPreferences myPrefs;
    public static AkunFragment newInstance() {

        Bundle args = new Bundle();

        AkunFragment fragment = new AkunFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_akun, container, false);
        Realm realm = Realm.getDefaultInstance();

        TextView tvuname = (TextView) view.findViewById(R.id.TVusername);
        TextView tvname = (TextView)view.findViewById(R.id.TVname);
        TextView tvuid = (TextView)view.findViewById(R.id.TVuserid);
        TextView tvgender = (TextView)view.findViewById(R.id.TVgender);
        TextView tvage = (TextView)view.findViewById(R.id.TVage);
        TextView tvpass = (TextView)view.findViewById(R.id.TVpassword);
        myPrefs = this.getActivity().getSharedPreferences("mySession", MODE_PRIVATE);

        final String id = myPrefs.getString("accountid",null);
        account = new RealmBaseHelper().find(realm,Account.class,"idAccount",id);

        String uname = account.getUsername();
        tvuname.setText(getString(R.string.username) + " " +  uname);
        String name = account.getName();
        tvname.setText(getString(R.string.name) + " " + name);
        String uid = account.getIdAccount();
        tvuid.setText(getString(R.string.userid) + " " + uid);
        String gender=account.getGender();
        tvgender.setText(getString(R.string.gender) + " " + gender );
        String age = account.getAge();
        tvage.setText(getString(R.string.age) + " " +age );
//        String password = account.getPassword();
//        tvpass.setText("password " +password);
        //Hemoglobin hemoglobin = new Hemoglobin();
        //tvhb.setText((int) hemoglobin.getHb());//Last Hemoglobin??? ERROR MASIH NULL

        editAkun = (Button) view.findViewById(R.id.editAccount);
        editAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditAkunActivity.class);
                getActivity().startActivity(intent);
                //getActivity().finish();
            }
        });


        logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("accountid", null);
                editor.commit();
                Intent intent = new Intent(getContext(), FlashScreenActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        /*    public void onLogoutClick(View view) {
        ///INI LOGOUT
        myPrefs = getSharedPreferences("mySession",MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("accountid", null);
        editor.commit();
        Intent i = new Intent(this, FlashScreenActivity.class);
        startActivity(i);
    }
        * */
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
