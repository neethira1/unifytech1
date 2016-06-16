package org.edx.mobile.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.inject.Inject;

import org.edx.mobile.R;
import org.edx.mobile.base.BaseFragmentActivity;
import org.edx.mobile.databinding.ActivityDiscoveryLaunchBinding;
import org.edx.mobile.module.analytics.ISegment;
import org.edx.mobile.module.prefs.LoginPrefs;

public class DiscoveryLaunchActivity extends BaseFragmentActivity {

    @Inject
    LoginPrefs loginPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityDiscoveryLaunchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_discovery_launch);
        binding.logIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(environment.getRouter().getLogInIntent());
            }
        });
        binding.signUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                environment.getSegment().trackUserSignUpForAccount();
                startActivity(environment.getRouter().getRegisterIntent());
            }
        });
        binding.discoverCourses.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                environment.getRouter().showFindCourses(DiscoveryLaunchActivity.this);
            }
        });
        binding.exploreSubjects.setVisibility(View.GONE); // TODO: delete this line once we implement listener
        binding.exploreSubjects.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // FIXME: Where should this go?
            }
        });
        environment.getSegment().trackScreenView(ISegment.Screens.LAUNCH_ACTIVITY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (environment.getLoginPrefs().getUsername() != null) {
            finish();
            environment.getRouter().showMyCourses(this);
        }
    }

    @Override
    protected boolean createOptionsMenu(Menu menu) {
        return false; // Disable menu inherited from BaseFragmentActivity
    }
}
