package com.example.photo_app.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.photo_app.LoginActivity;
import com.example.photo_app.R;
import com.example.photo_app.adapter.ImageListAdapter;
import com.example.photo_app.adapter.PostAdapter;
import com.example.photo_app.api.FlickrService;
import com.example.photo_app.api.GoClient;
import com.example.photo_app.api.PostApiClient;
import com.example.photo_app.model.Post;
import com.example.photo_app.model.call.flickr.PhotoIdResponse;
import com.example.photo_app.model.call.flickr.PhotoSourceResponse;
import com.example.photo_app.utils.Utils;

import java.io.File;
import java.lang.reflect.Array;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class FragmentUpload extends Fragment {
    private ImageListAdapter adapter;
    private EditText edtCaption;
    private ArrayList<String> selectedImages = new ArrayList<>();
    private ArrayList<File> selectedFiles = new ArrayList<>();
    private WebView webView;
    private static CookieManager cookieManager;

    public static CookieManager getCookieManager() {
        return cookieManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ArrayList<String> selectedImages = new ArrayList<>();
        Button btnSelect = view.findViewById(R.id.btnSelectPicture);
        Button btnUpload = view.findViewById(R.id.btnUpload);
        Button loginWithFlickr = view.findViewById(R.id.btnFlickrLogin);
        edtCaption = view.findViewById(R.id.edtCaption);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery to select multiple image
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT).putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Pictures"), 1);
            }
        });
        webView = view.findViewById(R.id.webViewFlickrLogin);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);

        cookieManager = new CookieManager();
        String baseURL = GoClient.getBaseUrl();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("flickr", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("flickr_user_id", "");
        String username = sharedPreferences.getString("flickr_user_username", "");
        String fullname = sharedPreferences.getString("flickr_user_fullname", "");
        String requestToken = sharedPreferences.getString("flickr_request_token", "");
        String requestTokenSecret = sharedPreferences.getString("flickr_request_token_secret", "");
        String accessToken = sharedPreferences.getString("flickr_access_token", "");
        String accessSecret = sharedPreferences.getString("flickr_access_secret", "");

        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_user_id", userId));
        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_user_username", username));
        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_user_fullname", fullname));
        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_request_token", requestToken));
        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_request_token_secret", requestTokenSecret));
        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_access_token", accessToken));
        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_access_secret", accessSecret));

        if (!userId.isEmpty() || !userId.equals("")) {
            loginWithFlickr.setVisibility(View.GONE);
            ViewGroup viewGroup= (ViewGroup) webView.getParent();
            viewGroup.removeView(webView);
        }
        FlickrService flickrService = GoClient.createService(FlickrService.class, getActivity(), cookieManager);


        String callbackUrlContains  = "redirect-callback";
        final String[] userID = new String[1];
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
                if (url.contains(callbackUrlContains)) {
                    URL directUrl = null;
                    try {
                        directUrl = new URL(url);

                        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
                        String query = directUrl.getQuery();
                        String[] pairs = query.split("&");
                        for (String pair : pairs) {
                            String[] arr = pair.split("=");
                            query_pairs.put(arr[0], arr[1]);
                        }

                        userID[0]=query_pairs.get("user_id");


                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_user_id", userID[0]));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_user_username", query_pairs.get("username")));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_user_fullname", query_pairs.get("fullname")));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_request_token", query_pairs.get("flickr_request_token")));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_request_token_secret", query_pairs.get("flickr_request_token_secret")));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_access_token", query_pairs.get("flickr_access_token")));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_access_secret", query_pairs.get("flickr_access_secret")));

                        ViewGroup parent =(ViewGroup) webView.getParent();
                        parent.removeView(webView);
                        // update SharedPreference flickr = true\
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("flickr", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("flickr_user_id", userID[0]);
                        editor.putString("flickr_user_username", query_pairs.get("username"));
                        editor.putString("flickr_user_fullname", query_pairs.get("fullname"));
                        editor.putString("flickr_request_token", query_pairs.get("flickr_request_token"));
                        editor.putString("flickr_request_token_secret", query_pairs.get("flickr_request_token_secret"));
                        editor.putString("flickr_access_token", query_pairs.get("flickr_access_token"));
                        editor.putString("flickr_access_secret", query_pairs.get("flickr_access_secret"));

                        editor.putBoolean("flickr", true);
                        editor.putString("user_id", userID[0]);
                        editor.apply();

                    } catch (MalformedURLException e) {
                        ViewGroup parent =(ViewGroup) webView.getParent();
                        parent.removeView(webView);
                        throw new RuntimeException(e);
                    }
                }
                // Return false to let the WebView load the URL normally
                super.onPageFinished(view, url);
            }
        });



        loginWithFlickr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                String url = baseURL+"/flickr/auth";
                webView.loadUrl(url);
                loginWithFlickr.setVisibility(View.GONE);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading..."); // thiết lập tin nhắn
                progressDialog.show(); // hiển thị ProgressDialog
                // upload images to server
                if(selectedFiles.size()>0) {
//                    MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    List<MultipartBody.Part> fileParts = new ArrayList<>();
                    for(File file: selectedFiles){
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//                        requestBodyBuilder.addFormDataPart("files", file.getName(),requestBody);
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                        fileParts.add(filePart);
                    }
//                    RequestBody requestBody = requestBodyBuilder.build();
                    Call<PhotoIdResponse> call = flickrService.uploadImages(fileParts);
                    call.enqueue(new Callback<PhotoIdResponse>() {
                        @Override
                        public void onResponse(Call<PhotoIdResponse> call, Response<PhotoIdResponse> response) {

                            System.out.println("SUCCESS with size --------- " + response.body().getResponse().size());
                            System.out.println(response.body().getResponse().get(0).getId());
                            List<PhotoIdResponse.photoId>  pids = response.body().getResponse();
                            ArrayList<String> photoIds = new ArrayList<>();
                            for (PhotoIdResponse.photoId pid: pids){
                                photoIds.add(pid.getId());
                            }
                            String caption = edtCaption.getText().toString();
                            PostApiClient postApiClient = new PostApiClient(getContext());
                            Map<String, Object> body = new HashMap<>();
                            body.put("caption", caption);
                            body.put("image_ids", photoIds);
                            body.put("user_id", 1);

                            postApiClient.uploadPost(body, new Callback<Post>() {
                                @Override
                                public void onResponse(Call<Post> call, Response<Post> response) {
                                   if(response.isSuccessful()){
                                       progressDialog.dismiss();
                                       Post post = response.body();
                                       Log.d("Upload success:", post.toString());
                                       Toast.makeText(getActivity(), "Post uploaded successfully", Toast.LENGTH_SHORT).show();
                                       // reset all fields
                                       edtCaption.setText("");
                                       selectedFiles.clear();
                                       selectedImages.clear();
                                       if (adapter != null) {
                                           adapter.notifyDataSetChanged();
                                       }
                                   } else {
                                       progressDialog.dismiss();
                                        Log.d("Upload error:", response.errorBody().toString());
                                   }
                                }

                                @Override
                                public void onFailure(Call<Post> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Log.e("Upload error:", t.getMessage());
                                }
                            });


                        }

                        @Override
                        public void onFailure(Call<PhotoIdResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.e("Upload error:", t.getMessage());
                        }
                    });
                    selectedFiles.clear();
                    selectedImages.clear();




                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1 && data != null) {

                selectedImages.clear();
                // get list of selected images
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        selectedImages.add(data.getClipData().getItemAt(i).getUri().toString());
                        Uri uri = data.getClipData().getItemAt(i).getUri();
                        if(uri != null) {
                            File file = Utils.getFileFromUri(uri, getActivity());
                            if(file.exists()) {
                                selectedFiles.add(file);
                            }
                        }
                    }
                } else if (data.getData() != null) {
                    selectedImages.add(data.getData().toString());
                    Uri uri = data.getData();
                    File file = Utils.getFileFromUri(uri, getActivity());
                    if (file.exists()){
                        selectedFiles.add(file);
                    }
                }
                ListView imgList = getView().findViewById(R.id.imgList);
                adapter = new ImageListAdapter(getContext(), selectedImages);
                imgList.setAdapter(adapter);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
