# DialogBuilder

When using a **_DialogFragment_** is not really convenient to create one. We even create a simple
**_DialogFragment_** that we need to write many codes to archive it!

Therefore, a builder for creatingDialogFragment is bron. Creating a **_DialogFragment_** is just like
using a builder for it.

- **_DialogFragment_** Builder also supports `MVVM`'s architecture.

# Show Case

# How to use it

### Simple Layout by Android SDK

```kotlin
```

### Customize Layout

```kotlin
```

## Using in the MVVM architecture

### Simple Layout by Android SDK

```kotlin
```

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
implementation 'com.devrapid.jieyi:dialogbuilder:0.0.4'
```

# Maven

```maven
<dependency>
  <groupId>com.devrapid.jieyi</groupId>
  <artifactId>dialogbuilder</artifactId>
  <version>0.0.4</version>
  <type>pom</type>
</dependency>
```

# Future Work

- [ ] Change the dialog size
