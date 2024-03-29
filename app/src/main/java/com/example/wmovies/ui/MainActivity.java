package com.example.wmovies.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wmovies.models.Movie;
import com.example.wmovies.adapters.MovieAdapter;
import com.example.wmovies.adapters.MovieItemClickListener;
import com.example.wmovies.R;
import com.example.wmovies.models.Slide;
import com.example.wmovies.adapters.SliderPagerAdapter;
import com.example.wmovies.utils.DataSource;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {


    private List<Slide> lstSlides ;
    private ViewPager2 sliderpager;  // Use ViewPager2
    private TabLayout indicator;
    private RecyclerView MoviesRV,moviesRvWeek ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        LayoutInflater inflater =(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_image,null);
        getSupportActionBar().setCustomView(view);


        iniViews();
        iniSlider();
        iniPopularMovies();
        iniWeekMovies();



    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_home:
                Intent homeIntent = new Intent(this,MainActivity.class);
                startActivity(homeIntent);

                return true;

            case R.id.action_contact_us:
                Intent contactIntent = new Intent(this,contact.class);
                startActivity(contactIntent);
                return true;

            case R.id.action_about:
                Intent abouttIntent = new Intent(this,about.class);
                startActivity(abouttIntent);
                return true;

            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, loginpage.class));
                finish();
                return true;

            case R.id.action_exit:
                finish(); // Close the activity
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void iniViews() {
        sliderpager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.Rv_movies);
        moviesRvWeek = findViewById(R.id.rv_movies_week);

    }

    private void iniWeekMovies() {



        MovieAdapter weekMovieAdapter = new MovieAdapter(this, DataSource.getWeekMovies(),this);
        moviesRvWeek.setAdapter(weekMovieAdapter);
        moviesRvWeek.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void iniPopularMovies() {



        MovieAdapter movieAdapter = new MovieAdapter(this, DataSource.getPopularMovies(),this);
        MoviesRV.setAdapter(movieAdapter);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }




    private void iniSlider() {
        lstSlides = new ArrayList<>() ;
        lstSlides.add(new Slide(R.drawable.mart,"The Martian \nthe crew of the Ares"));
        lstSlides.add(new Slide(R.drawable.slide2,"The fabelmans \nafter the storm"));
        lstSlides.add(new Slide(R.drawable.onesheet2,"Moana \n Temuera Morrison"));
        lstSlides.add(new Slide(R.drawable.john2,"John Whick \nOn a January night in 1952"));


        SliderPagerAdapter adapter = new SliderPagerAdapter(this, lstSlides);
        sliderpager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(),4000,6000);

        new TabLayoutMediator(indicator, sliderpager, (tab, position) -> {
        }).attach();
    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {

        Intent intent = new Intent(this,MovieDetailActivity.class);
        // send movie information to deatilActivity
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("imgURL",movie.getThumbnail());
        intent.putExtra("imgCover",movie.getCoverPhoto());
        intent.putExtra("desc",movie.getDescription());

        // lets crezte the animation
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                movieImageView,"sharedName");


        startActivity(intent,options.toBundle());

        //Toast.makeText(this,"item clicked : " + movie.getTitle(),Toast.LENGTH_LONG).show();


    }


    class SliderTimer extends TimerTask {


        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderpager.getCurrentItem()<lstSlides.size()-1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem()+1);
                    }
                    else
                        sliderpager.setCurrentItem(0);
                }
            });


        }
    }


}