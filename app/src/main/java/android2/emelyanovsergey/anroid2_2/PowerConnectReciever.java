package android2.emelyanovsergey.anroid2_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PowerConnectReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG","получен broadcast");
        Intent bi = new Intent("ru.android2.evserg.action.updateWeather");
        bi.putExtra("action","updateAllCity");
        context.sendBroadcast(bi);


    }
}
