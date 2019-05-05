package load.image.prim.com.primimageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LruCache lruCache;
        RequestManager with = Glide.with(this);
    }
}
