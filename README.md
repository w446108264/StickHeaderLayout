StickHeaderLayout
======================

An Android library supports sticking the navigator on the top when ItemView scrolls. more flexible than 「 [github.com/w446108264/StickyHeaderViewPager](https://github.com/w446108264/StickyHeaderViewPager) 」


To sticks the navigatior on the top can overwriting the View or dispatch events,
Why choosing a way which add a header on scrolling view that looks more complex and intrusive? The answer is that is more common, stable and smoothly scrolling.
  
![Art](https://github.com/w446108264/StickHeaderLayout/raw/master/output/little.gif)

![Art](https://github.com/w446108264/StickHeaderLayout/raw/master/output/p1.jpg)  ![Art](https://github.com/w446108264/StickHeaderLayout/raw/master/output/p2.jpg)  ![Art](https://github.com/w446108264/StickHeaderLayout/raw/master/output/p3.jpg)
   
![Art](https://github.com/w446108264/StickHeaderLayout/raw/master/output/listview.gif)
  
![Art](https://github.com/w446108264/StickHeaderLayout/raw/master/output/viewpager.gif)


# Features

* API > 11 
* Support RecyclerView, ScrollView, WebView, ListView.
* Scrolling, with smooth scrolling fling, will not be interrupt when ticking the navigator.
* Don't need to preset the height value,all is automatic.
* Support pulltoRefresh

# Samples

You can [download a sample APK](https://github.com/w446108264/StickHeaderLayout/raw/master/output/simple1.0.3.apk) 



# Gradle Dependency

Users of your library will need add the jitpack.io repository:

```xml  
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

and:

```xml
dependencies { 
    compile 'com.github.w446108264:StickHeaderLayout:1.0.3'
}
```
--


# Usage
 
simple to your proejct
 
### Layout
 
howerver, `StickHeaderLayout` must have three childViews,frist childView will scroll with viewpager,second will tick on the top.

```xml
  <com.stickheaderlayout.StickHeaderLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ListViewSimpleActivity">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:background="#293"
        android:gravity="center"
        android:text="header" />

    <TextView
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:background="#874"
        android:gravity="center"
        android:text="listview simple activity" />

    <ListView
        android:id="@+id/v_scroll"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</com.stickheaderlayout.StickHeaderLayout>
```
 
### Activity

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ListView lv_data = (ListView)findViewById(R.id.v_scroll);

        int size = 100;
        String[] stringArray = new String[size];
        for (int i = 0; i < size; ++i) {
            stringArray[i] = ""+i;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringArray);

        lv_data.setAdapter(adapter);
    }
```

### ViewPager
just like other scrollview when uses `StickHeaderLayout` on ViewPager.in page which want to stick on the top when scrolling, the page must appoint a view `PlaceHoderHeaderLayout` 

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.stickheaderlayout.StickHeaderLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/shl_root"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ListViewSimpleActivity">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:background="#293"
        android:gravity="center"
        android:text="header" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:background="#874"
            android:gravity="center"
            android:text="viewpager simple activity" />

        <LinearLayout
            android:id="@+id/ly_tab"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/tv_tab1"
                android:layout_width="match_parent"
                android:layout_height="30dp"
                android:gravity="center"
                android:text="Recycler"
                android:background="#124020"
                android:textColor="@android:color/black"
                android:layout_weight="1"/>
               
             ...        

       </LinearLayout>
    </LinearLayout>
 
    <android.support.v4.view.ViewPager
        android:id="@+id/v_scroll"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</com.stickheaderlayout.StickHeaderLayout>
```

### PullToRefresh
easy to add a pulltorefresh header,such as 「 [android-Ultra-Pull-To-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh) 」

```xml
   <?xml version="1.0" encoding="utf-8"?>
<in.srain.cube.views.ptr.PtrClassicFrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:cube_ptr="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/rotate_header_list_view_frame"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    cube_ptr:ptr_duration_to_close="200"
    cube_ptr:ptr_duration_to_close_header="1000"
    cube_ptr:ptr_keep_header_when_refresh="false"
    cube_ptr:ptr_pull_to_fresh="false"
    cube_ptr:ptr_ratio_of_header_height_to_refresh="1"
    cube_ptr:ptr_resistance="1.1">

    <com.stickheaderlayout.StickHeaderLayout
        android:id="@+id/shl_root"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:scrollViewId="@+id/v_scroll"
        tools:context=".ListViewSimpleActivity">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:background="#293"
            android:gravity="center"
            android:text="header" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:background="#874"
            android:gravity="center"
            android:text="listview simple activity" />
 
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <ListView
                android:id="@+id/v_scroll"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />

            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentBottom="true"
                android:layout_centerHorizontal="true"
                android:layout_marginBottom="80dp"
                android:text="TEST" />
        </RelativeLayout>
    </com.stickheaderlayout.StickHeaderLayout>

</in.srain.cube.views.ptr.PtrClassicFrameLayout>
```


# Contact & Help

Please fell free to contact me if there is any problem when using the library.

* email: shengjun8486@gmail.com 

 

