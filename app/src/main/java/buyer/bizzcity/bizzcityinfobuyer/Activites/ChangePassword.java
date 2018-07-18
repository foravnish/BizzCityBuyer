package buyer.bizzcity.bizzcityinfobuyer.Activites;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gelitenight.waveview.library.WaveView;
import com.gelitenight.waveview.library.WaveView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.text.Html.fromHtml;

public class ChangePassword extends AppCompatActivity {

    Dialog dialog;

    TextView back;
    TextView tve_name,tve_mobile,tve_user_id,tve_city,tve_dob,editPic;
    LinearLayout linera2,linera1;

    CircleImageView imageView;
    String filepath1, fileName1 =null;
    ProgressDialog progress;
    Button loginNow,submitChangePassword;
    EditText newPassword2,newPassword,oldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        back = (TextView) findViewById(R.id.back);

        tve_name = (TextView) findViewById(R.id.tve_name);
        tve_mobile = (TextView) findViewById(R.id.tve_mobile);

        imageView =  findViewById(R.id.imageView);

        oldPassword =  findViewById(R.id.oldPassword);
        newPassword =  findViewById(R.id.newPassword);
        newPassword2 =  findViewById(R.id.newPassword2);

        dialog=new Dialog(ChangePassword.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);



        submitChangePassword =  findViewById(R.id.submitChangePassword);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            Picasso
                    .with(getApplicationContext())
                    .load(MyPrefrences.getImage(getApplicationContext()))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tve_name.setText(MyPrefrences.getUSENAME(getApplicationContext()).toUpperCase());
        tve_mobile.setText(MyPrefrences.getMobile(getApplicationContext()));



        final WaveView waveView = (WaveView) findViewById(R.id.wave);
        waveView.setRotation(180);
        waveView.setShapeType(WaveView.ShapeType.SQUARE);
        waveView.setWaveColor(
                Color.parseColor("#FFE0B2"),
                Color.parseColor("#FFCC80"));
        WaveHelper mWaveHelper;
        mWaveHelper = new WaveHelper(waveView);
        mWaveHelper.start();

        submitChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkValidation()){

                    //new ChangePassword(getActivity(),password.getText().toString(),newPwd.getText().toString()).execute();

                    Util.showPgDialog(dialog);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, Api.buyerChangePassword,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);
                                    Util.cancelPgDialog(dialog);
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        if (jsonObject.getString("status").equalsIgnoreCase("success")){

                                           // Toast.makeText(getApplicationContext(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                                            errorDialog(ChangePassword.this,jsonObject.getString("message") );

                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ChangePassword.this, "Error! Please connect to the Internet.", Toast.LENGTH_SHORT).show();
                                    Util.cancelPgDialog(dialog);
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("buyerId", MyPrefrences.getUserID(getApplicationContext()));
                            params.put("newPassword", newPassword.getText().toString());
                            params.put("oldPassword", oldPassword.getText().toString());

                            return params;
                        }
                    };
                    postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    postRequest.setShouldCache(false);

                    AppController.getInstance().addToRequestQueue(postRequest);


                }

            }
        });

    }

    private void errorDialog(ChangePassword changePassword, String message) {

        final Dialog dialog = new Dialog(ChangePassword.this);
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
                finish();
            }
        });
        dialog.show();
    }


    public class WaveHelper {
        private WaveView mWaveView;

        private AnimatorSet mAnimatorSet;

        public WaveHelper(WaveView waveView) {
            mWaveView = waveView;
            initAnimation();
        }

        public void start() {
            mWaveView.setShowWave(true);
            if (mAnimatorSet != null) {
                mAnimatorSet.start();
            }
        }

        private void initAnimation() {
            List<Animator> animators = new ArrayList<>();

            // horizontal animation.
            // wave waves infinitely.
            ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                    mWaveView, "waveShiftRatio", 0f, 1f);
            waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
            waveShiftAnim.setDuration(1000);
            waveShiftAnim.setInterpolator(new LinearInterpolator());
            animators.add(waveShiftAnim);

            // vertical animation.
            // water level increases from 0 to center of WaveView
            ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                    mWaveView, "waterLevelRatio", 0.8f, 0.9f);
            waterLevelAnim.setDuration(10000);
            waterLevelAnim.setInterpolator(new DecelerateInterpolator());
            animators.add(waterLevelAnim);

            // amplitude animation.
            // wave grows big then grows small, repeatedly
            ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                    mWaveView, "amplitudeRatio", 0.01f, 0.05f);
            amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
            amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
            amplitudeAnim.setDuration(5000);
            amplitudeAnim.setInterpolator(new LinearInterpolator());
            animators.add(amplitudeAnim);

            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.playTogether(animators);
        }

        public void cancel() {
            if (mAnimatorSet != null) {
//            mAnimatorSet.cancel();
                mAnimatorSet.end();
            }
        }
    }
    private boolean  checkValidation() {
        if (TextUtils.isEmpty(oldPassword.getText().toString()))
        {
            oldPassword.setError("Oops! Password blank");
            oldPassword.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(newPassword.getText().toString()))
        {
            newPassword.setError("Oops! New Password blank");
            newPassword.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(newPassword2.getText().toString())){
            newPassword2.setError("Oops! Confirm Password blank");
            newPassword2.requestFocus();
            return false;
        }
        else if (!newPassword.getText().toString().equals(newPassword2.getText().toString())){

            //Util.errorDialog(getActivity(),"Confirm Password must be same !");
            Toast.makeText(getApplicationContext(), "Confirm Password must be same !", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (oldPassword.getText().toString().length()<3){
            oldPassword.setError("Oops! Password length must 3 ");
            oldPassword.requestFocus();
            return false;
        }

        else if (newPassword.getText().toString().length()<3){
            newPassword.setError("Oops! New Password length must 3 ");
            newPassword.requestFocus();
            return false;
        }

        else if (newPassword2.getText().toString().length()<3){
            newPassword2.setError("Oops! Confirm Password length must 3 ");
            newPassword2.requestFocus();
            return false;
        }
        return true;
    }



}
