package zhou.com.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import zhou.com.fulicenter.R;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.rb_new_good)
    RadioButton rbNewGood;
    @Bind(R.id.rb_category)
    RadioButton rbCategory;
    @Bind(R.id.rb_boutique)
    RadioButton rbBoutique;
    @Bind(R.id.rb_cart)
    RadioButton rbCart;
    @Bind(R.id.rb_personal_center)
    RadioButton rbPersonalCenter;
    @Bind(R.id.rb_bottom_layout)
    RadioGroup rbBottomLayout;

    private boolean isCart;
    private RadioButton[] rbs;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // setListener();
        initView();
    }

    private void initView() {
        rbs = new RadioButton[5];
        rbs[0] = rbNewGood;
        rbs[1] = rbCategory;
        rbs[2] = rbBoutique;
        rbs[3] = rbCart;
        rbs[4] = rbPersonalCenter;
    }

    private void setRadioButtomSatatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (i == index) {
                rbs[i].setChecked(true);
            } else {
                rbs[i].setChecked(false);
            }
        }
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.rb_new_good:
                //  setCart();
                index = 0;
                break;
            case R.id.rb_category:
                // setCart();
                index = 1;
                break;
            case R.id.rb_boutique:
                //  setCart();
                index = 2;
                break;
            case R.id.rb_cart:
                //  setOtherRb();
                index = 3;
                break;
            case R.id.rb_personal_center:
                //  setCart();
                index = 4;
                break;
        }
        setRadioButtomSatatus();
    }

  /*  private void setOtherRb() {
        if (!isCart) {
            isCart = true;
            rbNewGood.setChecked(false);
            rbBoutique.setChecked(false);
            rbCategory.setChecked(false);
            rbPersonalCenter.setChecked(false);
        }
    }

    private void setCart() {
        if (isCart) {
            rbCart.setChecked(false);
            isCart = false;
        }
    }*/

}