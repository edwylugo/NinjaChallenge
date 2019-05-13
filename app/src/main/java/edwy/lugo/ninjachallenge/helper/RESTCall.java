package edwy.lugo.ninjachallenge.helper;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import edwy.lugo.ninjachallenge.interfaces.DataListener;

public class RESTCall extends AsyncTask<Void, Void, Bundle> {

    public static final int GET_POSTS_ACTION = 0;
    public static final int GET_COMMENTS_ACTION = 1;
    public static final int NEW_POST_ACTION = 2;
    public static final int GET_USERS_ACTION = 3;

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";


    /* L'activity chiamante */
    private DataListener dataListener;
    private ContentValues params;
    private int currentAction;
    private final static String TAG = "RESTCall";


    public RESTCall(DataListener dataListener, int action, ContentValues params) {
        this.dataListener = dataListener;
        this.params = params;
        this.currentAction = action;
    }

    public Bundle request(int action, ContentValues params) throws IOException {
        Bundle result = new Bundle();
        result.putInt(Constants.RESULT_ACTION_KEY, action);
        String url;
        HttpURLConnection connection;
        InputStream inputStream;
        String buffer;

        switch (action) {
            case GET_POSTS_ACTION:
                url = Constants.BASE_URL + Constants.POSTS;
                connection = createConnection(url, METHOD_GET);
                inputStream = connection.getInputStream();
                buffer = readInputStream(inputStream);
                result.putInt(Constants.RESPONSE_CODE, connection.getResponseCode());
                result.putString(Constants.DATA, buffer);
                connection.disconnect();
                break;
            case NEW_POST_ACTION:
                url = Constants.BASE_URL;
                connection = createConnection(url, METHOD_POST);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(getQuery(params));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                connection.disconnect();
                break;
            case GET_COMMENTS_ACTION:
                int postId = params.getAsInteger(Constants.POST_ID);
                result.putInt(Constants.POST_ID, postId);
                url = Constants.BASE_URL + Constants.POSTS + postId + "/" + Constants.COMMENTS;
                connection = createConnection(url, METHOD_GET);
                inputStream = connection.getInputStream();
                buffer = readInputStream(inputStream);
                result.putInt(Constants.RESPONSE_CODE, connection.getResponseCode());
                result.putString(Constants.DATA, buffer);
                connection.disconnect();
                break;

            case GET_USERS_ACTION:
                int usersId = params.getAsInteger(Constants.USERS_ID);
                result.putInt(Constants.USERS_ID, usersId);
                url = Constants.BASE_URL + Constants.USERS + usersId + "/" + Constants.USERS;
                connection = createConnection(url, METHOD_GET);
                inputStream = connection.getInputStream();
                buffer = readInputStream(inputStream);
                result.putInt(Constants.RESPONSE_CODE, connection.getResponseCode());
                result.putString(Constants.DATA, buffer);
                connection.disconnect();
                break;
        }
        return result;

    }

    private String getQuery(ContentValues contentValues) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (String key : contentValues.keySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(contentValues.getAsString(key), "UTF-8"));
        }

        return result.toString();
    }


    @Override
    protected Bundle doInBackground(Void... params) {
        Bundle result = new Bundle();
        try {
            result = request(currentAction, this.params);


        } catch (IOException e) {
            Utils.log(TAG, "************** IOException ***********");
            e.printStackTrace();
        }


        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    private HttpURLConnection createConnection(String urlstring, String method) throws IOException {
        URL url = new URL(urlstring);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setUseCaches(false);
        connection.setRequestMethod(method);
        return connection;
    }

    private String readInputStream(InputStream stream) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
        StringBuffer stringBuffer = new StringBuffer("");
        String line;
        while ((line = rd.readLine()) != null)
            stringBuffer.append(line);
        rd.close();
        return stringBuffer.toString();
    }

    @Override
    protected void onPostExecute(Bundle bundle) {
        dataListener.onDataReady(bundle);
        super.onPostExecute(bundle);
    }

}

