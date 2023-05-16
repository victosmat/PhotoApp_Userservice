package com.example.photo_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.photo_app.R;
import com.example.photo_app.api.FlickrService;
import com.example.photo_app.api.PostApiClient;
import com.example.photo_app.api.PostService;
import com.example.photo_app.fragment.FragmentUpload;
import com.example.photo_app.model.Post;
import com.example.photo_app.model.PostImgs;
import com.example.photo_app.model.call.flickr.PhotoSourceResponse;
import com.example.photo_app.model.call.flickr.PhotoURLResponse;

import java.lang.reflect.Array;
import java.net.CookieManager;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<Post> posts;
    private PostApiClient postApiClient;
    private Context context;
    private FlickrService flickrService;

    public PostAdapter(ArrayList<Post> posts, PostApiClient postApiClient, Context context, FlickrService flickrService) {
        this.posts = posts;
        this.postApiClient = postApiClient;
        this.context = context;
        this.flickrService = flickrService;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        Post post = posts.get(position);
        // later change to name
        holder.tvName.setText("user " + post.getUser_id());
        holder.tvCaption.setText(post.getCaption());
        holder.tvTime.setText(post.getCreated_at());


        ArrayList<PhotoURLResponse> imageUrls = post.getImageUrls();
        if (imageUrls == null || imageUrls.isEmpty()) {
            postApiClient.getPostImgs(post.getId(), new Callback<ArrayList<PostImgs>>() {
                @Override
                public void onResponse(Call<ArrayList<PostImgs>> call, Response<ArrayList<PostImgs>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<PostImgs> imgs = response.body();
                        ArrayList<PhotoURLResponse> imageUrls = new ArrayList<>();
                        int totalImgs = imgs.size();
                        for (int i = 0; i < totalImgs; i++) {

                            String imgId = imgs.get(i).getImage_id();
                            // TODO: Call API service to get the image URL based on the imgId value
                            Call<PhotoURLResponse> photoCall = flickrService.getImageUrlByImgId(imgId);
                            final int finalI = i;

                            photoCall.enqueue(new Callback<PhotoURLResponse>() {
                                @Override
                                public void onResponse(Call<PhotoURLResponse> call, Response<PhotoURLResponse> response) {
                                    if (response.isSuccessful()) {
                                        System.out.println("SUCCESS");
//                                        String imgUrl = response.body().getUrl();
//                                        if(!imgUrl.equals(""))
//                                            imageUrls.add(imgUrl);
                                        PhotoURLResponse photo = response.body();
                                        if(photo != null) {
                                            imageUrls.add(photo);
                                        }
                                        else {
                                            System.out.println("photo is null");
                                        }
                                    }
                                    else {
                                        System.out.println("FAILED with status code: " + response.code());
                                    }

                                    if (finalI == totalImgs - 1) {
                                        post.setImageUrls(imageUrls);
                                        holder.viewPager.setAdapter(new ImagePagerAdapter(imageUrls));
                                        holder.pageNumber.setText("1/" + imgs.size());
                                        holder.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                            @Override
                                            public void onPageSelected(int position) {
                                                holder.pageNumber.setText((position + 1) + "/" + imgs.size());
                                            }
                                        });
                                        notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<PhotoURLResponse> call, Throwable t) {
                                    System.out.println("FAILED");
                                }
                            });

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        // handle request errors depending on status code
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<PostImgs>> call, Throwable t) {
                    // handle failure
                }
            });
        } else {
            holder.viewPager.setAdapter(new ImagePagerAdapter(imageUrls));
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvCaption;
        TextView tvTime;
        ViewPager2 viewPager;
        TextView pageNumber;

        public PostViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCaption = itemView.findViewById(R.id.tvCap);
            tvTime = itemView.findViewById(R.id.tvCreateDate);
            viewPager = itemView.findViewById(R.id.viewPager);
            pageNumber = itemView.findViewById(R.id.pageNumber);
        }
    }
}
