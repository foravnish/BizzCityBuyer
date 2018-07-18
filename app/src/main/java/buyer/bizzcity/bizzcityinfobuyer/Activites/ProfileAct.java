package buyer.bizzcity.bizzcityinfobuyer.Activites;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.gelitenight.waveview.library.WaveView;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import buyer.bizzcity.bizzcityinfobuyer.Fragments.LoginNow;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.MyFavourate;
import buyer.bizzcity.bizzcityinfobuyer.Fragments.Offers;

import buyer.bizzcity.bizzcityinfobuyer.R;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Api;
import buyer.bizzcity.bizzcityinfobuyer.Utils.AppController;
import buyer.bizzcity.bizzcityinfobuyer.Utils.JSONParser;
import buyer.bizzcity.bizzcityinfobuyer.Utils.MyPrefrences;
import buyer.bizzcity.bizzcityinfobuyer.Utils.Util;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAct extends AppCompatActivity {

    Dialog dialog;

    TextView back;
    TextView tve_name,tve_mobile,tve_user_id,tve_city,tve_dob,editPic;
    LinearLayout linera2,linera1;

    CircleImageView imageView;
    String filepath1, fileName1 =null;
    ProgressDialog progress;
    Button loginNow,changePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dialog=new Dialog(ProfileAct.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Util.showPgDialog(dialog);


        back = (TextView) findViewById(R.id.back);

        tve_name = (TextView) findViewById(R.id.tve_name);
        tve_mobile = (TextView) findViewById(R.id.tve_mobile);
        tve_user_id = (TextView) findViewById(R.id.tve_user_id);
        tve_city = (TextView) findViewById(R.id.tve_city);
        tve_dob = (TextView) findViewById(R.id.tve_dob);
        editPic = (TextView) findViewById(R.id.editPic);
        imageView =  findViewById(R.id.imageView);
        linera1 =  findViewById(R.id.linera1);
        linera2 =  findViewById(R.id.linera2);
        loginNow =  findViewById(R.id.loginNow);
        changePassword =  findViewById(R.id.changePassword);


        final WaveView waveView = (WaveView) findViewById(R.id.wave);
        waveView.setRotation(180);
        waveView.setShapeType(WaveView.ShapeType.SQUARE);
        waveView.setWaveColor(
                Color.parseColor("#FFE0B2"),
                Color.parseColor("#FFCC80"));
        WaveHelper mWaveHelper;
        mWaveHelper = new WaveHelper(waveView);
        mWaveHelper.start();

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProfileAct.this,ChangePassword.class));
            }
        });

        if (MyPrefrences.getUserLogin(getApplicationContext())==false){
            // Toast.makeText(getActivity(), "login", Toast.LENGTH_SHORT).show();
//            Fragment fragment = new LoginNow();
//            FragmentManager manager = getSupportFragmentManager();
//            FragmentTransaction ft = manager.beginTransaction();
//            ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

            linera1.setVisibility(View.GONE);
            linera2.setVisibility(View.VISIBLE);

            loginNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }
            });
        }
        else if (MyPrefrences.getUserLogin(getApplicationContext())==true){
            linera1.setVisibility(View.VISIBLE);
            linera2.setVisibility(View.GONE);

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        else
        {
            //your code

        }
        editPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageOne(1, 2);

            }
        });

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Api.buyerProfileDetails+"?buyerId="+ MyPrefrences.getUserID(getApplicationContext()), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("ResposeOffer", response.toString());
                Util.cancelPgDialog(dialog);
                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");
                    HashMap<String,String> hashMap = null;
                    if (response.getString("status").equalsIgnoreCase("success")){


                        JSONArray jsonArray=response.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);


                            tve_name.setText(jsonObject.optString("buyer_name").toUpperCase());
                            tve_mobile.setText("+91 "+jsonObject.optString("mobile"));
                            tve_user_id.setText(jsonObject.optString("id"));
                            tve_city.setText(jsonObject.optString("created_date"));
                            tve_dob.setText(jsonObject.optString("gender"));


//                            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//                            imageView.setImageUrl(jsonObject.optString("image").toString().replace(" ","%20"),imageLoader);

                            Picasso
                                    .with(getApplicationContext())
                                    .load(jsonObject.optString("image"))
                                    .into(imageView);


                        }
                        //  AllEvents.add(hashMap);
                    }
                    else if (response.getString("status").equalsIgnoreCase("failure")){
                        //Toast.makeText(getActivity(), "offer list not available", Toast.LENGTH_SHORT).show();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Util.cancelPgDialog(dialog);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Respose", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Error! Please Connect to the internet", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                Util.cancelPgDialog(dialog);

            }
        });

        // Adding request to request queue
        jsonObjReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }




    private void imageOne(final int cam, final int gal) {

        final CharSequence[] items = {"Take from Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileAct.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                try {
                    if (items[item].equals("Take from Camera")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, cam);
                    } else if (items[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(
                                Intent.createChooser(intent, "Select File"),
                                gal);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "errorrr...", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setCancelable(true);
        builder.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2)
                onSelectFromGalleryResult(data, 2);

            else if (requestCode == 1)
                onSelectFromGalleryResult(data, 1);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onSelectFromGalleryResult(Intent data, int i) {

        if (i == 2) {
            Uri fileUri = data.getData();
            filepath1 = getPathFromUri(getApplicationContext(), fileUri);
            fileName1 = imagename(getApplicationContext(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), fileUri);
                imageView.setImageBitmap(bitmap);
                HomeAct.imageView.setImageBitmap(bitmap);
//                image1.setVisibility(View.VISIBLE);
//                img1.setVisibility(View.GONE);



                new ProfilePic().execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        else if (i == 1) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            HomeAct.imageView.setImageBitmap(photo);
//            image1.setVisibility(View.VISIBLE);
//            img1.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            filepath1 = finalFile.toString();

            String filename1 = filepath1.substring(filepath1.lastIndexOf("/") + 1);
            fileName1 = filename1;
            Log.d("dgdfgdfhgdfhd", filepath1.toString());
            Log.d("dgdfgdfhgdfhd", filename1.toString());

            new ProfilePic().execute();
//                 System.out.println(mImageCaptureUri);
//            check = 1;
//            isImage1 = true;
//            isImage2 = false;
//            isImage3 =false;
//            isImage4 = false;
//            isImage5 = false;
        }

    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPathFromUri(final Context context, final Uri uri) {
        boolean isAfterKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        ///storage/emulated/0/Download/Amit-1.pdf
        Log.e("Uri Authority ", "uri:" + uri.getAuthority());
        if (isAfterKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if ("com.android.externalstorage.documents".equals(
                    uri.getAuthority())) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else {
                    return "/stroage/" + type + "/" + split[1];
                }
            } else if ("com.android.providers.downloads.documents".equals(
                    uri.getAuthority())) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if ("com.android.providers.media.documents".equals(
                    uri.getAuthority())) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                contentUri = MediaStore.Files.getContentUri("external");
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {//MediaStore
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String[] projection = {
                MediaStore.Files.FileColumns.DATA
        };
        try {
            cursor = context.getContentResolver().query(
                    uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int cindex = cursor.getColumnIndexOrThrow(projection[0]);
                return cursor.getString(cindex);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static String imagename(Context context, Uri currImageURI) {
        String displayName = "";
        File file = new File(currImageURI.toString());
        String uriString = currImageURI.toString();
        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(currImageURI, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    Log.e("display name content", ": " + displayName);
                }
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = file.getName();
            Log.e("display name file", ": " + displayName);
        }
        Log.e("display name ", ": " + displayName);
        return displayName;
    }


    private class ProfilePic extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private static final String TAG_STATUS = "status";
        private static final String TAG_MESSAGE = "msg";

        String descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand;
        HashMap<String, String> params = new HashMap<>();

        //EditText descreption,ageOfProd,headline,min,kmsDone,mobile,emailID;
        ProfilePic() {
            this.descreption = descreption;
            this.ageOfProd = ageOfProd;
            this.headline = headline;
            this.min = min;
            this.kmsDone = kmsDone;
            this.mobile = mobile;
            this.emailID = emailID;
            this.brand = brand;

        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(ProfileAct.this);
            progress.setCancelable(false);
            progress.setTitle("Please wait...");
            progress.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject jsonObject = null;
            try {

                jsonObject = uploadImage(ProfileAct.this, filepath1, fileName1);



//                if (isImage1 == true) {
//
//                }
//                if (isImage2 == true) {
//                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2,
//                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
//                }
//                if (isImage3 == true) {
//                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3,
//                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
//                }
//                if (isImage4 == true) {
//                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4,
//                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
//                }
//                if (isImage5 == true) {
//                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4, filepath5, fileName5,
//                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
//                }

                if (jsonObject != null) {

                    return jsonObject;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject json) {
            String message = "";
            String data = "";

            if (progress.isShowing())
                progress.dismiss();

            if (json != null) {


                Log.d("dfdgdgdfgh",json.toString());
                if (json.optString("status").equalsIgnoreCase("success")) {


                    Toast.makeText(getApplicationContext(), "Profile Upload Successfully..", Toast.LENGTH_LONG).show();
                    MyPrefrences.setImage(getApplicationContext(),json.optString("message"));
//                    try {
//
////                        Log.d("fgdgdfghdfhdf", "car");
////                        double amnt = Double.parseDouble(amounts.getText().toString().replace("₹ ", ""));
////                        MyPrefrences.setPostingId(getApplicationContext(), json.getString("paymentId"));
////                        MyPrefrences.setPostingId2(getApplicationContext(), json.getString("posting_id"));
//                        Log.d("rupee", String.valueOf(amnt));
//
////                            errorDialog2(ProfileAct.this, json.getString("message"), json.getString("posting_id"));
//
//                        //Toast.makeText(PostAdd.this, ""+jsonObject.optString("message") + message, Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                } else {
//                    Toast.makeText(PostAdd.this, "Error " + json, Toast.LENGTH_LONG).show();
                    Util.errorDialog(ProfileAct.this, json.optString("message"));
                }
            }
        }

    }

    private JSONObject uploadImage(Context context, String filepath1, String fileName1) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("buyerId", MyPrefrences.getUserID(getApplicationContext()))
//                    .addFormDataPart("paymentMode", "")
//                    .addFormDataPart("opening_time", "")
//                    .addFormDataPart("closing_time", "")
//                    .addFormDataPart("min_order_amnt", "")
//                    .addFormDataPart("min_order_qty", "")
//                    .addFormDataPart("closing_days", "")

                    .addFormDataPart("image", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))


                    .build();

          //  Log.d("dfdsgsdgdfgdfh",pId);

//            Log.d("fvfgdgdfhgghfhgdfh", amounts.getText().toString().replace("₹ ", ""));
//            Log.d("fvfgdgdfhgdfhqwdfs",amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("http://bizzcityinfo.com/AndroidApi.php?function=updateBuyerProfilePic")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(15, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ProfileAct.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ProfileAct.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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

}
