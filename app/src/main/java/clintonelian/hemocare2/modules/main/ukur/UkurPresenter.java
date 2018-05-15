package clintonelian.hemocare2.modules.main.ukur;

import android.app.Activity;
import android.os.Bundle;

import clintonelian.hemocare2.models.Account;
import clintonelian.hemocare2.models.Hemoglobin;
import clintonelian.hemocare2.utils.RealmBaseHelper;
import io.realm.Realm;

/**
 * Created by Admin on 4/11/2018.
 */

public class UkurPresenter extends Activity {
    private Realm realm;

    public UkurPresenter(final Realm realm) {
        this.realm = realm;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.signup);
        //fungsi untuk menambah akun
    }
    public Hemoglobin getUserLogin(final String fieldUsername, final String fieldPassword){
        RealmBaseHelper realmBaseHelper = new RealmBaseHelper();
        final Hemoglobin hemoglobin = realmBaseHelper.find(realm,Hemoglobin.class,"username",fieldUsername,"password",fieldPassword);
        return hemoglobin;
    }

}
