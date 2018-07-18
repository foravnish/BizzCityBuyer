package buyer.bizzcity.bizzcityinfobuyer.Fragments;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import buyer.bizzcity.bizzcityinfobuyer.Activites.HomeAct;
import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static android.text.Html.fromHtml;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteReviewEdit extends Fragment {


    public WriteReviewEdit() {
        // Required empty public constructor
    }

    TextView comName,comAdd,editText;
    MaterialRatingBar rtbar;
    Button submitReview;
    String rate="";
    Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_write_review_edit, container, false);

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        comName=view.findViewById(R.id.comName);
        comAdd=view.findViewById(R.id.comAdd);
        editText=view.findViewById(R.id.editText);
        rtbar=view.findViewById(R.id.rtbar);
        submitReview=view.findViewById(R.id.submitReview);


        HomeAct.title.setText("Edit Rating");

        comName.setText(getArguments().getString("company_name"));
        comAdd.setText(getArguments().getString("address"));
        editText.setText(getArguments().getString("review"));

        rtbar.setRating(Float.parseFloat(getArguments().getString("rating_score")));

        rtbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, final float rating, final boolean fromUser) {
                if (fromUser) {
//                    ratingBar.setRating(Math.ceil(rating));
                    Log.d("dfsdfsdfsdfsdf", String.valueOf(rating));
                    rate= String.valueOf(rating);


                }
            }
        });


        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rate.isEmpty()) {
                    Util.showPgDialog(dialog);

                    StringRequest postRequest = new StringRequest(Request.Method.POST, Api.editSubmitedRating,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);
                                    Util.cancelPgDialog(dialog);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {

//                                            Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                            errorDialog(jsonObject.getString("message"));

                                        } else {
                                            Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(getActivity(), "Error! Please connect to the Internet.", Toast.LENGTH_SHORT).show();
                                    Util.cancelPgDialog(dialog);
                                }
                            }
                    ) {


                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("ratingId",getArguments().getString("id"));
                            params.put("review",editText.getText().toString());
                            params.put("rating_score", rate);

                            Log.d("dfdgdfgdfgh",getArguments().getString("id"));
                            Log.d("dfdgdfgdfgh",getArguments().getString("review"));
                            Log.d("dfdgdfgdfgh",rate);
                            return params;
                        }
                    };
                    postRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    postRequest.setShouldCache(false);

                    AppController.getInstance().addToRequestQueue(postRequest);
                }
                else{
                    Toast.makeText(getActivity(), "Please give review.", Toast.LENGTH_SHORT).show();
                }


            }

        });
        return view;
    }
    private void errorDialog(String res) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialogcustom2);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
        text.setText(fromHtml(res));
        Button ok = (Button) dialog.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

                Fragment fragment = new RatingView();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

            }
        });
        dialog.show();

    }


}
