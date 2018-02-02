[![GitHub release](https://img.shields.io/github/release/pokk/DialogBuilder.svg?style=flat-square)](https://github.com/pokk/DialogBuilder)
[![license](https://img.shields.io/github/license/pokk/DialogBuilder.svg?style=flat-square)](https://github.com/pokk/DialogBuilder)

# DialogBuilder

When using a **_DialogFragment_** is not really convenient to create one. We even create a simple
**_DialogFragment_** that we need to write many codes to archive it!

Therefore, a builder for creatingDialogFragment is bron. Creating a **_DialogFragment_** is just like
using a builder for it. There're few advantages as the followings,

1. Don't need to write the request code from activity for catching the event callback.
2. Only few parameters, you can get a beautiful fragment dialog.

- **_DialogFragment_** Builder also supports the databinding in `MVVM`'s architecture.

# Show Case

# How to use it

Also the complete code is here.

### Simple Layout by Android SDK

```kotlin
QuickDialogFragment.Builder(this) {
    btnNegativeText = "negative" to { d -> /* What you want to do! */ }
    btnPositiveText = "positive" to { d -> /* What you want to do! */ }
    message = "The is message!"
    title = "This is title!"
    cancelable = false
}.build()
```

### Customize Layout

```kotlin
QuickDialogFragment.Builder(this) {
    viewResCustom = R.layout.fragment_dialog_test
    fetchComponents = { v ->
        v.btn.setOnClickListener { /* What you want to do! */ }
        v.tv.text = "I was clicked!"
    }
}.build()
```

## Using databinding in the MVVM architecture

### Customize Layout

In the `MVVM` architecture, you can create a dialog fragment as the following.

```kotlin
QuickDialogBindingFragment.Builder<FragmentDialogBindBinding>(this) {
    viewCustom = R.layout.fragment_dialog_bind
}.build().apply {
    bind = {
        it.vm = TestActivityViewModel()
    }
}.show()
```

The snippet reference
[sample](https://github.com/pokk/DialogBuilder/blob/68f396812c9f4059d3b5b7cd4e64bc28e6585c4e/sample/src/main/java/com/devrapid/sample/TestActivity.kt#L24-L30).
And layout xml file is
[here](https://github.com/pokk/DialogBuilder/blob/68f396812c9f4059d3b5b7cd4e64bc28e6585c4e/sample/src/main/res/layout/fragment_dialog_bind.xml#L9).

## A little track about layout XML

There're some issues I don't know how to fix it. If someone understands, please let me know kindly. 😀

In the some situations, you need to modify the size of the fragment dialog. You can do it as like this way.

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <!-- ↓↓↓↓ Make double layout to assign your layout size. ↓↓↓↓ -->
    <LinearLayout
        android:layout_width="250dp"
        android:layout_height="300dp"
        android:orientation="vertical">

        ...
    </LinearLayout>
</LinearLayout>
```

# How to import to your project

## Gradle

First you have to make sure your project `bundle.gradle` as below:

```gradle
allprojects {
    repositories {
        jcenter()
    }
}
```

And add our dependency to your app `bundle.gradle`.

```gradle
implementation 'com.devrapid.jieyi:dialogbuilder:0.1.0'
```

# Maven

```maven
<dependency>
  <groupId>com.devrapid.jieyi</groupId>
  <artifactId>dialogbuilder</artifactId>
  <version>0.1.0</version>
  <type>pom</type>
</dependency>
```

# Future Work

- [x] Change the dialog size
