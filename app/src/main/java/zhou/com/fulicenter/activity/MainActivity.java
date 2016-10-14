package zhou.com.fulicenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import zhou.com.fulicenter.R;

public class MainActivity extends AppCompatActivity {
    private RadioGroup mRb_bottom_layout;
    private RadioButton mRb_new_good, mRb_category, mRb_boutique, mRb_cart, mRb_personal_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // setListener();
    }

    private void initView() {
        mRb_bottom_layout = (RadioGroup) findViewById(R.id.rb_bottom_layout);

        mRb_new_good = (RadioButton) findViewById(R.id.rb_new_good);
        mRb_category = (RadioButton) findViewById(R.id.rb_category);
        mRb_boutique = (RadioButton) findViewById(R.id.rb_boutique);
        mRb_cart = (RadioButton) findViewById(R.id.rb_cart);
        mRb_personal_center = (RadioButton) findViewById(R.id.rb_personal_center);
    }

/*    private void setListener() {
        mRb_bottom_layout.setOnCheckedChangeListener(new onCheckedChange());
    }*/

   public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.rb_new_good:
                mutual((RadioButton) view);
                break;
            case R.id.rb_category:
                mutual((RadioButton) view);
                break;
            case R.id.rb_boutique:
                mutual((RadioButton) view);
                break;
            case R.id.rb_cart:
                mutual((RadioButton) view);
                break;
            case R.id.rb_personal_center:
                mutual((RadioButton) view);
                break;


        }
    }

    private void mutual(RadioButton radioButton) {
        if (radioButton != mRb_new_good) {
            mRb_new_good.setChecked(false);
        }
        if (radioButton != mRb_boutique) {
            mRb_boutique.setChecked(false);
        }
        if (radioButton != mRb_cart) {
            mRb_cart.setChecked(false);
        }
        if (radioButton != mRb_category) {
            mRb_category.setChecked(false);
        }
        if (radioButton != mRb_personal_center) {
            mRb_personal_center.setChecked(false);
        }
    }
}