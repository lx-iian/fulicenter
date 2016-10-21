package zhou.com.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhou.com.fulicenter.FuLiCenterApplication;
import zhou.com.fulicenter.R;
import zhou.com.fulicenter.fragment.BoutiqueFragment;
import zhou.com.fulicenter.fragment.CategoryFragment;
import zhou.com.fulicenter.fragment.NewGoodsFragment;
import zhou.com.fulicenter.utils.L;
import zhou.com.fulicenter.utils.MFGT;

public class MainActivity extends BaseActivity {
    @BindView(R.id.rb_new_good)
    RadioButton rbNewGood;
    @BindView(R.id.rb_category)
    RadioButton rbCategory;
    @BindView(R.id.rb_boutique)
    RadioButton rbBoutique;
    @BindView(R.id.rb_cart)
    RadioButton rbCart;
    @BindView(R.id.shopping_cart_num_bg)
    LinearLayout shoppingCartNumBg;
    @BindView(R.id.rb_personal_center)
    RadioButton rbPersonalCenter;
    @BindView(R.id.rb_bottom_layout)
    RadioGroup rbBottomLayout;
    @BindView(R.id.main_bottom)
    LinearLayout mainBottom;
    @BindView(R.id.fragment_container)
    RelativeLayout fragmentContainer;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    private boolean isCart;

    private RadioButton[] rbs;
    private int currentIndex;
    private int index;
    Fragment[] mFragments;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment
            mCategoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
        super.onCreate(savedInstanceState);
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mCategoryFragment;
        mFragments[2] = mBoutiqueFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container, mBoutiqueFragment)
                .hide(mBoutiqueFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    @Override
    protected void initView() {
        rbs = new RadioButton[5];
        rbs[0] = rbNewGood;
        rbs[1] = rbCategory;
        rbs[2] = rbBoutique;
        rbs[3] = rbCart;
        rbs[4] = rbPersonalCenter;
    }

    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void setListener() {

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
                if (FuLiCenterApplication.getUsername() == null) {
                    MFGT.gotoLoginActivity(this);
                } else {
                    index = 4;
                }
                MFGT.gotoLoginActivity(this);
                return;
               // break;
        }
        setFragment();
        setRadioButtomSatatus();
    }

    private void setFragment() {
        if (index != currentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[currentIndex]);
            if (!mFragments[index].isAdded()) {
                ft.add(R.id.fragment_container, mFragments[index]);
            }
            ft.show(mFragments[index]).commit();
        }
        setRadioButtomSatatus();
        currentIndex = index;
    }

    @Override
    public void onBackPressed() {
        finish();
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