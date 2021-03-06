package com.xxzj990.android.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements ViewPager.OnPageChangeListener,OnClickListener
{

	/** Context */
	// private Context mContext;

	/** Child views */
	private List<View> mChilds;

	/** ViewPager */
	private ViewPager mViewPager;

	/** Adapter */
	private PagerAdapter mAdapter;

	/** Slide image */
	private ImageView mSlide;

	/** Title one */
	private TextView mTitleOne;

	/** Title two */
	private TextView mTitleTwo;

	/** Title three */
	private TextView mTitleThree;

	/** Current page */
	private int mCurrentPage;

	/** Screen 1/3 width */
	private int SCREEN_WIDTH_PART_ONE;
	
	/** Slide image scale X value */
	private float mScaleX = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// this.mContext = MainActivity.this;

		initView();
	}

	private void initView()
	{
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		SCREEN_WIDTH_PART_ONE = dm.widthPixels / 3;

		// Init slide
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_slide);
		mSlide = (ImageView) findViewById(R.id.slide_img);
		Matrix matrix = new Matrix();
		float ff = (float) SCREEN_WIDTH_PART_ONE / (float) bitmap.getWidth();
		this.mScaleX = ff;
		matrix.postScale(ff, 1);
		mSlide.setImageMatrix(matrix);

		// Init page title
		mTitleOne = (TextView) findViewById(R.id.msg_title);
		mTitleOne.setOnClickListener(this);
		mTitleTwo = (TextView) findViewById(R.id.pic_title);
		mTitleTwo.setOnClickListener(this);
		mTitleThree = (TextView) findViewById(R.id.setting_title);
		mTitleThree.setOnClickListener(this);

		// Create child
		mChilds = new ArrayList<View>(3);
		mChilds.add(getLayoutInflater().inflate(R.layout.activity_page0, null));
		mChilds.add(getLayoutInflater().inflate(R.layout.activity_page1, null));
		mChilds.add(getLayoutInflater().inflate(R.layout.activity_page2, null));

		// Init pager
		mAdapter = new MyViewPagerAdapter(mChilds);
		mViewPager = (ViewPager) findViewById(R.id.vPager);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(this);
		mCurrentPage = 0;

		setPageTitleColor(mCurrentPage);
	}

	private void setPageTitleColor(int position)
	{
		switch (position)
		{
		case 0:
			mTitleOne.setTextColor(getResources().getColor(R.color.page_title_focus));
			mTitleTwo.setTextColor(getResources().getColor(R.color.page_title));
			mTitleThree.setTextColor(getResources().getColor(R.color.page_title));
			break;
		case 1:
			mTitleOne.setTextColor(getResources().getColor(R.color.page_title));
			mTitleTwo.setTextColor(getResources().getColor(R.color.page_title_focus));
			mTitleThree.setTextColor(getResources().getColor(R.color.page_title));
			break;
		case 2:
			mTitleOne.setTextColor(getResources().getColor(R.color.page_title));
			mTitleTwo.setTextColor(getResources().getColor(R.color.page_title));
			mTitleThree.setTextColor(getResources().getColor(R.color.page_title_focus));
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
		// Slide image 跟随Pager一起滑动方式
		float to = -1;
		if (position == mCurrentPage)
		{
			float len = positionOffset * SCREEN_WIDTH_PART_ONE;
			float from = mCurrentPage * SCREEN_WIDTH_PART_ONE;
			to = from + len;
		} else if (position > mCurrentPage)
		{
			to = position * SCREEN_WIDTH_PART_ONE;
		}

		if (to >= 0)
		{
			Matrix matrix = new Matrix();
			matrix.postScale(mScaleX, 1);
			matrix.postTranslate(to, 0);
			mSlide.setImageMatrix(matrix);
		}

		mCurrentPage = position;
		
		setPageTitleColor(mViewPager.getCurrentItem());
	}

	@Override
	public void onPageSelected(int position)
	{
		/** Pager位置确定后，Slide image再滑动方式 */
		// int from = mCurrentPage * SCREEN_WIDTH_PART_ONE;
		// int to = mCurrentPage * SCREEN_WIDTH_PART_ONE;
		// if ((position - mCurrentPage) > 0)
		// {
		// to += SCREEN_WIDTH_PART_ONE;
		// } else
		// {
		// to -= SCREEN_WIDTH_PART_ONE;
		// }
		// Animation animation = new TranslateAnimation(from, to, 0, 0);
		// mCurrentPage = position;
		// animation.setFillAfter(true);
		// animation.setDuration(300);
		// mSlide.startAnimation(animation);
		//
		// setPageTitleColor(mViewPager.getCurrentItem());
		/******************************************/
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.msg_title:
			mViewPager.setCurrentItem(0);
			break;
		case R.id.pic_title:
			mViewPager.setCurrentItem(1);
			break;
		case R.id.setting_title:
			mViewPager.setCurrentItem(2);
			break;
		default:
			break;
		}
	}
}
