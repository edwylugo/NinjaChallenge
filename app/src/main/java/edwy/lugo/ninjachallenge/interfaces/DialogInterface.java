package edwy.lugo.ninjachallenge.interfaces;

import android.content.Context;

public interface DialogInterface {

    public void showProgressDialog(Context context);

    public void dismissProgressDialog();

    public void onIssues(Context context, String message);
}

