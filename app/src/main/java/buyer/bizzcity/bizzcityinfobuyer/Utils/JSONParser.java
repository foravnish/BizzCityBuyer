package buyer.bizzcity.bizzcityinfobuyer.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class JSONParser  {

    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    StringBuilder sbParams;
    String paramsString;

    public String makeHttpRequest(String url, String method,
                                  HashMap<String, String> params) {
        result = new StringBuilder();
        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e("JSON Parser", "Error in add param  " + e.toString());

            }
            i++;
        }

        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);
                //Log.e("complete url", ": " + url + sbParams.toString());

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                conn.connect();

                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("JSON Parser", "Error in make connection  " + e.toString());
            }
        } else if (method.equals("GET")) {
            // request method is GET

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
                Log.e("complete url", ": " + url);
            }

            try {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setConnectTimeout(15000);
                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("JSON Parser", "Error in make connection  " + e.toString());
            }

        }

        try {
            //Receive the response from the server
            int statusCode = conn.getResponseCode();
            //Log.e("status code", ": " + statusCode);
            InputStream in;

            if (statusCode >= 200 && statusCode < 400) {
                // Create an InputStream in order to extract the response object
                in = conn.getInputStream();
            }
            else {
                in = conn.getErrorStream();
            }
        /*
            if (status != HttpURLConnection.HTTP_OK) {
                in = conn.getErrorStream();
                Log.e("status code 501", ": code  "+status +"inut stream " + in);

            } else if (status==201) {
                in = conn.getErrorStream();
                Log.e("status code 201", ": code  "+status +"inut stream " + in);

            } else {
                in = conn.getInputStream();
                Log.e("status code 200", ": code  "+status +"inut stream " + in);
            }

            InputStream in = new BufferedInputStream(conn.getInputStream());
*/
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            //Log.e("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("JSON Parser", "Error in getting stream " + e.toString());

        }

        conn.disconnect();

        // try parse the string to a JSON object
        if (result.toString() != null) {
            return result.toString();
        }

        return result.toString();
    }
}