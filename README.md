## A custom ripple click effect for Android

## Usage

1. Define it in your xml file and wrap the view that you want to appy your ripple effect on.

```xml
...
<vakili.ramin.customview.views.RippleView
        android:id="@+id/myRippleView"
        android:layout_centerInParent="true"
        app:rippleDuration="200"
        app:cornerRadius="200"
        app:maxRippleDuration="2000"
        app:rippleColor="#55555555"
        app:highlightColor="#457b7b7b"
        android:gravity="center"
        android:layout_width="120dp"
        android:layout_height="48dp">

        <TextView
            android:text="Ripple"
            android:gravity="center"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

    </vakili.ramin.customview.views.RippleView>
...
```
2. In your code after finding the reference setOnClickListener to it and add your code there.

```Java

```

