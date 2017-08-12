## 写在前面

最近一直在找时间重构代码，每一次重构都能带来许多好处，比如精简代码，提高代码质量，减轻团队之间的问题，当然最重要的就是以后可以偷懒啦。而这次改进也是为了节省时间，提高团队的效率。

#### DataBinding

不了解的请百度，google或翻看以前的文章

#### AspectJ

贴一张图：
![](https://user-gold-cdn.xitu.io/2017/5/13/92e2b2ecca18e2ce56406e6175e148f6)

简单理解就是在编译期和加载时进行代码注入。通俗点来说就是非侵入的在一段方法前后加入一点自己的逻辑。作用和OKhttp的Interceptor拦截请求有点像。

最开始认识到AspectJ的时候是在今年二月份，在github搜索databinding的时候偶遇[**north2016/T-MVP**](north2016/**T-MVP**)，这个项目太赞了，给了我相当多的启发，也算是我android-AOP的启蒙。



**想了解AOP的推荐文章：**

[安卓AOP三剑客:APT,AspectJ,Javassist](http://www.jianshu.com/p/dca3e2c8608a)

[专题：AndroidAOP](http://www.jianshu.com/nb/1529181)

*PS:以前上学的时候学SSH的时候有接触过AOP面向切面编程，虽然都忘了，但是思想还在（大学真该好好学，感谢泡图书馆的日子）*

### 防止多次点击

现在来说说，防止多次点击。

以前使用的是J神的Rxbinding库，结合`throttleFirst`操作符来进行控制。

由于使用的是DataBinding库，一般来说事件是绑定在xml当中的，但为了限制频繁触发和使用Rxbinding，还是在代码里进行事件处理。

```java
RxView.clicks(view).throttleFirst(600, TimeUnit.MILLISECONDS).subscribe(...);
```

这样可以处理普通布局中的点击事件，但RecyclerView中的Item就不那么好处理了,需要获取ItemBinding找到view去做以上的操作，很麻烦。

为了节约时间（偷懒），提高效率（偷懒），加入AspectJ就势在必行了。

#### 加入AspectJ,给你的DataBinding插上翅膀

如何在项目中使用AspecJ呢？

1. 使用大神们提供的脚本，android10写过一个[Android-AOPExample](https://github.com/android10/Android-AOPExample)

   的库，并提供了一段脚本，可以作为参考。

2. 使用沪江的[**gradle_plugin_android_aspectjx**](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx) 

本文推荐使用第二种，比较简单方便。

接入方法可以看ReadMe或者徐医生的这篇文章[看AspectJ在Android中的强势插入](http://www.jianshu.com/p/5c9f1e8894ec)

#### 讲重点

配置好AspectJ后，我们需要给点击事件加一个切入点，首先添加一个注解

```java
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface SingleClick {
}
```

然后编写我们的Aspect类(代码来自north2016)

```java

/**
 * Created by baixiaokang on 16/12/9.
 * {link https://github.com/north2016/T-MVP/blob/master/app/src/main/java/com/aop/SingleClickAspect.java}
 * 防止View被连续点击,间隔时间600ms
 */

@Aspect
public class SingleClickAspect {

    public static final String TAG="SingleClickAspect";
    public static final int MIN_CLICK_DELAY_TIME = 600;
    static int TIME_TAG = R.id.click_time;

    @Pointcut("execution(@com.ditclear.app.aop.annotation.SingleClick * *(..))")//方法切入点
    public void methodAnnotated(){

    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable{
        View view=null;
        for (Object arg: joinPoint.getArgs()) {
            if (arg instanceof View) view= ((View) arg);
        }
        if (view!=null){
            Object tag=view.getTag(TIME_TAG);
            long lastClickTime= (tag!=null)? (long) tag :0;
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "lastClickTime:" + lastClickTime);
            }
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {//过滤掉600毫秒内的连续点击
                view.setTag(TIME_TAG, currentTime);
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "currentTime:" + currentTime);
                }
                joinPoint.proceed();//执行原方法
            }
        }
    }
}

```

接下来是使用

```java
	/**
     * 防止多次点击
     * @param view view
     */
    @SingleClick
    public void onHookClick(View view) {
        hookText.set(String.format("%s click\n",hookText.get()));
    }
```

大功告成，当我们点击的时候在执行点击事件的同时会在控制台输出时间

![image.png](http://upload-images.jianshu.io/upload_images/3722695-884fbb9c437aade0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/800)

而且只有两次点击时间间隔在600ms以上才会执行方法，否则会被拦截。

#### 最后体验一下效果



githud地址：https://github.com/ditclear/DataBinding-AspectJ

参考资料：

[专题：AndroidAOP](http://www.jianshu.com/nb/1529181)

[看AspectJ在Android中的强势插入](http://www.jianshu.com/p/5c9f1e8894ec)

