package com.example.test.slidingmenu.model;



import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.campusconnect.R;
import com.example.test.campusconnect.SlidingTabLayout;
import com.example.test.campusconnect.ViewPagerAdapter;

public class HomeFragment extends android.support.v4.app.Fragment{
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter listingAdapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Sports Buddy","Find a Tutor"};
    int Numboftabs =2;
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        // Creating The Toolbar and setting it as the Toolbar for the activity

/*        toolbar = (Toolbar) rootView.findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);   */

        listingAdapter = new ViewPagerAdapter(getFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(listingAdapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) rootView.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
        return rootView;
    }
}
