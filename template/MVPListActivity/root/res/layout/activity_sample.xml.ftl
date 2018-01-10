<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="${packageName}.${activityClass}">
	
	<!--you can add refresh -->
	<android.support.v7.widget.RecyclerView
        android:id="@+id/rlv_recycler"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1">
	</android.support.v7.widget.RecyclerView>
</RelativeLayout>
