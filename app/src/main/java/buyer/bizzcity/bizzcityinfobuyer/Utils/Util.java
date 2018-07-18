package buyer.bizzcity.bizzcityinfobuyer.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import buyer.bizzcity.bizzcityinfobuyer.R;

import static android.text.Html.fromHtml;

/**
 * Created by Andriod Avnish on 12-Apr-18.
 */

public class Util {

    public static String CITYID="1";  //// Ghaziabad
    ///////////////show progress dialog for Async Task
    public static void showPgDialog(Dialog dialog) {

        dialog.setContentView(R.layout.dialogprogress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


//        progressDialog.setMessage("Please Wait....");
//        progressDialog.show();
    }

    public static void cancelPgDialog(Dialog dialog) {
//        if (progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }

    public static void errorDialog(Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialogcustom2);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
        text.setText(fromHtml(message));
        Button ok = (Button) dialog.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


}
