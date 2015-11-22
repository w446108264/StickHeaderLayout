StickHeaderLayout
======================

An Android library supports sticking the navigator on the top when ItemView scrolls. more flexible than 「 [github.com/w446108264/StickyHeaderViewPager](https://github.com/w446108264/StickyHeaderViewPager) 」


To sticks the navigatior on the top can overwriting the View or dispatch events,
Why choosing a way which add a header on scrolling view that looks more complex and intrusive? The answer is that is more common, stable and smoothly scrolling.
 

 ![Art](https://github.com/w446108264/StickHeaderLayout/raw/master/output/listview.gif)
 
 
![Art](https://github.com/w446108264/StickHeaderLayout/raw/master/output/viewpager.gif)


# Features

* API > 11 
* Support RecyclerView, ScrollView, WebView, ListView.
* Scrolling, with smooth scrolling fling, will not be interrupt when ticking the navigator.
* Don't need to preset the height value,all is automatic.

# Samples

You can [download a sample APK](https://github.com/w446108264/StickHeaderLayout/raw/master/output/simple.apk) 



# Gradle Dependency



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
The current version of Viewpager only supports scrollview. But it'll soon support other scrolling View, such as recycleview and listview.

please follow.


# Contact & Help

Please fell free to contact me if there is any problem when using the library.

* email: shengjun8486@gmail.com 

 

