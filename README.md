# Floating-Progress-Bar

## Preview 
![alt text](https://github.com/ngtien137/Floating-Progress-Bar/blob/master/images/preview.png) 
## Getting Started 
* This library use Kotlin 
* Add maven in your root build.gradle at the end of repositories:

```gradle 
allprojects { 
  repositories { 
    ... 
    maven { url 'https://jitpack.io' }
  } 
} 
``` 

* Add the dependency to file build.gradle(Module:app): 

```gradle
  implementation 'com.github.ngtien137:Floating-Progress-Bar:Tag'

``` 

* TAG is the version of library. If you don't know, remove it with + 
* You can get version of this module [here](https://jitpack.io/#ngtien137/Floating-Progress-Bar)
## All Attributes 

```xml

<com.chim.floatpb.FloatingProgressBar
    android:id="@+id/pbLoading"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:paddingStart="4dp"
    android:paddingEnd="4dp"
    app:fpb_bar_corners_radius="15dp"
    app:fpb_bar_height="30dp"
    app:fpb_max="200"
    app:fpb_progress="0"
    app:fpb_text_bottom_spacing="4dp"
    app:fpb_text_size="12sp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
  
  ``` 
