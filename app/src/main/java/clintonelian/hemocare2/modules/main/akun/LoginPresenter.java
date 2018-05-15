package clintonelian.hemocare2.modules.main.akun;

import android.app.Activity;
import android.os.Bundle;

import clintonelian.hemocare2.models.Account;
import clintonelian.hemocare2.utils.RealmBaseHelper;
import io.realm.Realm;

/**
 * Created by Admin on 3/27/2018.
 */

public class LoginPresenter extends Activity{
    private Realm realm;

    public LoginPresenter(final Realm realm) {
        this.realm = realm;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.signup);
        //fungsi untuk menambah akun
    }
    public Account getUserLogin(final String fieldUsername, final String fieldPassword){
        RealmBaseHelper realmBaseHelper = new RealmBaseHelper();
        final Account account = realmBaseHelper.find(realm,Account.class,"username",fieldUsername,"password",fieldPassword);

        return account;
    }

}
