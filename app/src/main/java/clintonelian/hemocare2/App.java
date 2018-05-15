package clintonelian.hemocare2;

import android.app.Application;
import android.content.Context;

import clintonelian.hemocare2.utils.RealmBaseHelper;


/**
 * Created by astra_000 on 30/05/2017.
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        new RealmBaseHelper().setRealmInit(this);
        

    }


}
