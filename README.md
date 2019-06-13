1. @Database代表一个数据库，包含了所需的数据表，包含四个配置项
    - Class[] entities();
    - Class[] views() default {};
    - int version();
    - boolean exportSchema() default true;
2. @Entity代表一张table
    - String tableName() default "";表名
    - Index[] indices() default {};索引列表
    - boolean inheritSuperIndices() default false;
    - String[] primaryKeys() default {};主键
    - ForeignKey[] foreignKeys() default {};外键
    - String[] ignoredColumns() default {};需要忽略的字段
3. @Dao代表访问table的一系列方法

[译] 关于 Room 的 7 点专业提示
[https://juejin.im/post/5ca0b8d5f265da30920059cd#heading-3]

像操作Room一样操作SharedPreferences和File文件
[https://juejin.im/post/5cf8d8faf265da1ba9156dc8]

Android Jetpack组件之数据库Room详解(一)
[https://juejin.im/post/5c4bdfd26fb9a04a006f623b]











## Kotlin知识
1. data class 只包含数据字段的类，等同于bean,entity,POJO等Java类，编译器会自动生成get/set\hashCode()\toString()等方法
2. 






## RxJava2

Reactive Streams[https://www.jianshu.com/p/f2d8356e709d]

### Publisher

> A Publisher is a provider of a potentially unbounded number of sequenced elements, 
publishing them according to the demand received from its Subscriber(s).
A Publisher can serve multiple Subscribers subscribed subscribe(Subscriber) dynamically at various points in time.

发布者是可能无限数量的有序元素的提供者,根据订阅者的需求发布这些元素，发布者可以在不同的时间点动态地为多个订阅者所订阅。

`public void subscribe(Subscriber<? super T> s);` 

### Subscriber

```java
/**
 * 一旦将Subscriber的实例传递给{@link Publisher#subscribe(Subscriber)}，Subscriber#onSubscribe(Subscription)会收到通知。
 * <p>
 * 除非{@link Subscription#request(long)}调用，否则不会收到任何通知
 * <p>
 * After signaling demand:一旦发出需求信号
 * <ul>
 * <li>{@link #onNext(Object)}会得到一次或多次调用，直到达到{@link Subscription#request(long)}设置的最大值 </li>
 * <li>{@link #onError(Throwable)} or {@link Subscriber#onComplete()}一旦调用，就会通知状态结束，之后不再发送事件。
 * </ul>
 * <p>
 * 当订阅者有能力处理更多事件时，随时可以通过{@link Subscription#request(long)}下达指令
 *
 * @param <T> the type of element signaled.
 */
public interface Subscriber<T> {
    /**
     * Invoked after calling {@link Publisher#subscribe(Subscriber)}.
     * <p>
     * No data will start flowing until {@link Subscription#request(long)} is invoked.
     * <p>
     * It is the responsibility of this {@link Subscriber} instance to call {@link Subscription#request(long)} whenever more data is wanted.
     * <p>
     * The {@link Publisher} will send notifications only in response to {@link Subscription#request(long)}.
     * 
     * @param s
     *            {@link Subscription} that allows requesting data via {@link Subscription#request(long)}
     */
    public void onSubscribe(Subscription s);

    /**
     * Data notification sent by the {@link Publisher} in response to requests to {@link Subscription#request(long)}.
     * 
     * @param t the element signaled
     */
    public void onNext(T t);

    /**
     * Failed terminal state.
     * <p>
     * No further events will be sent even if {@link Subscription#request(long)} is invoked again.
     *
     * @param t the throwable signaled
     */
    public void onError(Throwable t);

    /**
     * Successful terminal state.
     * <p>
     * No further events will be sent even if {@link Subscription#request(long)} is invoked again.
     */
    public void onComplete();
}

``` 

### Subscription

```java
/**
 * A {@link Subscription} represents a one-to-one lifecycle of a {@link Subscriber} subscribing to a {@link Publisher}.
 * <p>
 * It can only be used once by a single {@link Subscriber}.
 * <p>
 * It is used to both signal desire for data and cancel demand (and allow resource cleanup).
 *
 */
public interface Subscription {
    /**
     * No events will be sent by a {@link Publisher} until demand is signaled via this method.
     * <p>
     * It can be called however often and whenever needed—but the outstanding cumulative demand must never exceed Long.MAX_VALUE.
     * An outstanding cumulative demand of Long.MAX_VALUE may be treated by the {@link Publisher} as "effectively unbounded".
     * <p>
     * Whatever has been requested can be sent by the {@link Publisher} so only signal demand for what can be safely handled.
     * <p>
     * A {@link Publisher} can send less than is requested if the stream ends but
     * then must emit either {@link Subscriber#onError(Throwable)} or {@link Subscriber#onComplete()}.
     * 
     * @param n the strictly positive number of elements to requests to the upstream {@link Publisher}
     */
    public void request(long n);

    /**
     * Request the {@link Publisher} to stop sending data and clean up resources.
     * <p>
     * Data may still be sent to meet previously signalled demand after calling cancel.
     */
    public void cancel();
}

```
