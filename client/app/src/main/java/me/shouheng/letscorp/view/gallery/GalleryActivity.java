package me.shouheng.letscorp.view.gallery;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.shouheng.commons.helper.FileHelper;
import me.shouheng.commons.model.AttachmentFile;
import me.shouheng.commons.util.SystemUiVisibilityUtil;
import me.shouheng.commons.util.ToastUtils;
import me.shouheng.commons.util.ViewUtils;
import me.shouheng.commons.widget.DepthPageTransformer;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.databinding.ActivityGalleryBinding;
import me.shouheng.letscorp.view.CommonDaggerActivity;
import ooo.oxo.library.widget.PullBackLayout;

public class GalleryActivity extends CommonDaggerActivity<ActivityGalleryBinding> implements PullBackLayout.Callback, ImageFragmentInteraction {

    public final static String EXTRA_GALLERY_IMAGES = "__extra_gallery_images";
    public final static String EXTRA_GALLERY_CLICKED_IMAGE = "__extra_gallery_clicked_image";
    public final static String EXTRA_GALLERY_TITLE = "__extra_gallery_title";

    private boolean fullScreenMode;
    private ColorDrawable mBackground;
    private ArrayList<AttachmentFile> attachments;

    private int clickedImage;
    private String title = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        handleIntent(savedInstanceState);

        configToolbar();

        configViews();
    }

    private void handleIntent(Bundle savedInstanceState) {
        attachments = new ArrayList<>();
        clickedImage = 0;

        if (getIntent() != null) {
            attachments = getIntent().getParcelableArrayListExtra(EXTRA_GALLERY_IMAGES);
            title = getIntent().getStringExtra(EXTRA_GALLERY_TITLE);
            clickedImage = getIntent().getIntExtra(EXTRA_GALLERY_CLICKED_IMAGE, 0);
        }

        if (savedInstanceState != null) {
            attachments = savedInstanceState.getParcelableArrayList(EXTRA_GALLERY_IMAGES);
            title = savedInstanceState.getString(EXTRA_GALLERY_TITLE);
            clickedImage = savedInstanceState.getInt(EXTRA_GALLERY_CLICKED_IMAGE, 0);
        }
    }

    private void configToolbar() {
        setSupportActionBar(getBinding().toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_toolbar_shade));
            actionBar.setTitle(title);
            actionBar.setSubtitle(clickedImage + 1 + "/" + attachments.size());
        }
    }

    private void configViews() {
        getBinding().pbl.setCallback(this);

        setupSystemUI();

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0){
                showSystemUI();
            } else {
                hideSystemUI();
            }
        });

        AttachmentPagerAdapter adapter = new AttachmentPagerAdapter(getSupportFragmentManager(), attachments);
        getBinding().vp.setAdapter(adapter);
        getBinding().vp.setCurrentItem(clickedImage);
        getBinding().vp.setPageTransformer(true, new DepthPageTransformer());
        getBinding().vp.setOffscreenPageLimit(3);
        getBinding().vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                getBinding().toolbar.setSubtitle((position + 1) + "/" + attachments.size());
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        Display aa = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        if (aa.getRotation() == Surface.ROTATION_90) {
            Configuration configuration = new Configuration();
            configuration.orientation = Configuration.ORIENTATION_LANDSCAPE;
            onConfigurationChanged(configuration);
        }

        mBackground = new ColorDrawable(Color.BLACK);
        ViewUtils.getRootView(this).setBackgroundDrawable(mBackground);
    }

    @Override
    public void toggleSystemUI() {
        if (fullScreenMode) {
            showSystemUI();
        } else {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        runOnUiThread(() -> {
            getBinding().toolbar.animate().translationY(-getBinding().toolbar.getHeight()).setInterpolator(
                    new AccelerateInterpolator()).setDuration(200).start();
            getWindow().getDecorView().setSystemUiVisibility(SystemUiVisibilityUtil.getSystemVisibility());
            fullScreenMode = true;
        });
    }

    private void setupSystemUI() {
        getBinding().toolbar.animate().translationY(ViewUtils.getStatusBarHeight(getResources())).setInterpolator(
                new DecelerateInterpolator()).setDuration(0).start();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void showSystemUI() {
        runOnUiThread(() -> {
            getBinding().toolbar.animate().translationY(ViewUtils.getStatusBarHeight(getResources())).setInterpolator(
                    new DecelerateInterpolator()).setDuration(240).start();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            fullScreenMode = false;
        });
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(getApplicationContext()).clearMemory();
        Glide.get(getApplicationContext()).trimMemory(TRIM_MEMORY_COMPLETE);
        System.gc();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share: {
                AttachmentFile attachment = attachments.get(getBinding().vp.getCurrentItem());
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType(FileHelper.getMimeType(this, attachment.getUri()));
                intent.putExtra(Intent.EXTRA_STREAM, attachment.getUri());
                startActivity(intent);
                break;
            }
            case R.id.action_open: {
                try {
                    AttachmentFile attachment = attachments.get(getBinding().vp.getCurrentItem());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(attachment.getUri(), FileHelper.getMimeType(this, attachment.getUri()));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    ToastUtils.makeToast(R.string.failed_to_resolve_intent);
                }
                break;
            }
            case R.id.action_info:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideOrShowStatusBar() {
        if (fullScreenMode) {
            SystemUiVisibilityUtil.enter(this);
        } else {
            SystemUiVisibilityUtil.exit(this);
        }
        fullScreenMode = !fullScreenMode;
    }

    @Override
    public void onPullStart() {
        fullScreenMode = true;
        hideOrShowStatusBar();
    }

    @Override
    public void onPull(float v) {
        v = Math.min(1f, v * 3f);
        int alpha = (int) (0xff * (1f - v));
        mBackground.setAlpha(alpha);
    }

    @Override
    public void onPullCancel() {}

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(EXTRA_GALLERY_IMAGES, attachments);
        outState.putString(EXTRA_GALLERY_TITLE, title);
        outState.putInt(EXTRA_GALLERY_CLICKED_IMAGE, clickedImage);
        super.onSaveInstanceState(outState);
    }
}
