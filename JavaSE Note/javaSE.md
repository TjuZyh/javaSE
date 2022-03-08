### javaSE

java高级教程

#### I. 多线程

##### 1. 基本概念

**程序：**完成特定任务、用某种语言编写的一组执行的集合，即指**一段静态的代码**，静态对象。

**进程：**程序的一次执行过程，或是**正在运行的一个程序**。是一个动态的过程：有自身的产生、存在和消亡的过程（生命周期）

​	**程序作为资源分配的单位**，系统在运行时会为每个进程分配不同的内存区域。

**线程：**进程可以进一步细化为线程，是一个程序内部的一条执行路径

- 若一个进程同时并行执行多个线程，就是支持多线程的
- **线程作为调度和执行的单位，每个线程拥有独立的运行栈和程序计数器**，线程切换的开销小
- 一个进程中的多个线程共享相同的内存单元/内存地址空间，他们从同一堆中分配对象，可以访问相同的变量和对象，这就使线程间通信更简便高效。**但是，多个线程操作共享的系统资源可能会带来安全隐患。**

**单核CPU与多核CPU**

一个Java应用程序java.exe，至少有三个线程：

- main() 主程序
- gc() 垃圾回收线程
- 异常处理线程，当然如果发成异常会影响主线程

**并发与并行：**

- 并行：多个CPU同时执行多个任务（多个人同时做不同的事）
- 并发：一个CPU（采用时间片）同时执行多个任务（多个人做一件事，秒杀系统）

##### 2. 创建与使用

**1）存在多种创建线程的方式：**

- 编写自定义Thread类继承Thread类

  - 创建一个继承Thread的子类
  - 重写Thread中的run方法
  - 创建该对象的实例
  - 通过start()调用该线程（1.启动当前线程 2.调用当前线程的run()）

  ```java
  package com.javaSE;
  
  //1、创建一个继承Thread的子类
  class MyThread extends Thread{
      //2、重写Thread类中的run()
      @Override
      public void run() {
          for(int i = 0 ; i < 100 ; i++){
              if(i % 2 == 0){
                  System.out.println(Thread.currentThread().getName() + ": " + i);
              }
          }
      }
  }
  
  public class ThreadTest {
      public static void main(String[] args) {
          //3、创建Thread类子类的对象
          MyThread thread1 = new MyThread();
          //4、通过该对象调用start()
          //start() 1.启动当前线程 2.调用当前线程的run()
          thread1.start();
  
          //模拟主线程
          for(int i = 0 ; i < 10 ; i++){
              System.out.println(Thread.currentThread().getName() + ": " + "main Thread");
          }
      }
  }
  
  ```

- 通过匿名子类的方式创建Thread

  ```java
  public class ThreadTest {
      public static void main(String[] args) {
          
          //创建Thread类的匿名子类方式
          new Thread(){
              @Override
              public void run() {
                  for(int i = 0 ; i < 100 ; i++){
                      if(i % 2 != 0){
                          System.out.println(Thread.currentThread().getName() + ": " + i);
                      }
                  }
              }
          }.start();
  
          //模拟主线程
          for(int i = 0 ; i < 10 ; i++){
              System.out.println(Thread.currentThread().getName() + ": " + "main Thread");
          }
      }
  }
  ```

- 通过创建Runnable的实现类重新抽象方法run()实现

  ```java
  package com.javaSE.thread;
  
  //1.创建一个实现了Runnable接口的类
  class MThread implements Runnable{
  
      //2.实现类实现抽象方法：run()
      @Override
      public void run() {
          for (int i = 0; i < 100; i++) {
              if(i % 2 == 0){
                  System.out.println(Thread.currentThread().getName() + ": " + i);
              }
          }
      }
  }
  
  public class ThreadTest2 {
  
      public static void main(String[] args) {
          //3. 创建实现类的对象
          MThread mThread = new MThread();
          //4. 将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
          Thread thread1 = new Thread(mThread);
          //5.通过Thread类的对象调用start()
          //start:1. 启动线程 2. 调用当前线程的run(), 继续调用Runnable类型的target的run()
          thread1.start();
  
          Thread thread2 = new Thread(mThread);
          thread2.start();
  
      }
  }
  ```

**2）API**

1. start(): 启动当前线程，调用当前线程的run()
2. run(): 通常需要重写Thread类中的方法，将要执行的操作声明在此方法中
3. currentThread(): 静态方法，返回执行代码的线程
4. getName(): 获取当前线程的名称
5. setName(): 设置当前线程的名称
6. yield(): 释放当前cpu的执行权
7. join(): 在线程a中调用线程b的join()，此时线程a进入阻塞状态，知道线程b执行完之后，线程a结束阻塞状态
8. ~~stop(): 已过时，强制结束当前进程~~
9. sleep(long millitime): 让当前线程处于milltime时间的阻塞
10. isAlive(): 判断当前线程是否存活

```java
package com.javaSE;

class MyThread extends Thread{
    @Override
    public void run() {
        for(int i = 0 ; i < 100 ; i++){
            if(i % 2 == 0){
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
            if(i % 20 == 0){
                //释放当前cpu的执行权
                yield();
            }
        }
    }
}

public class ThreadTest {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        thread1.start();
       
        for(int i = 0 ; i < 10 ; i++){
            if(i == 5){
                try {
                    //在线程a中调用线程b的join()，此时线程a进入阻塞状态，知道线程b执行完之后，线程a结束阻塞状态
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ": " + "main Thread");
        }
    }
}
```

**3）线程的调度**

1. **调度策略：**

   - 时间片

     ![image-20220207214344821](/Users/zyh/Library/Application Support/typora-user-images/image-20220207214344821.png)

   - 抢占式：高优先级的线程抢占CPU

2. **Java的调度方式**
   - 同优先级的线程组成先进先出队列，使用时间片策略
   - 对于高优先级，使用优先调度的抢占式策略
3. **线程的优先级**
   - 分为三个等级：
     - **MAX_PRIORITY: 10**
     - **MIN_PRIORITY: 1**
     - **NORM_PRIORITY: 5**
   - 获取和设置当前线程的优先级：
     - getPriority(): 获取线程的优先级
     - setPriority(int p): 设置线程的优先级
   - 高优先的线程要抢占低优先的线程的执行权，但是只是概率上，高优先级的线程大概率被执行，并不意味这高优先级的线程执行完之后，低优先级的线程才执行

##### 3. 生命周期

<img src="/Users/zyh/Library/Application Support/typora-user-images/image-20220208205242340.png" alt="image-20220208205242340" style="zoom:80%;" />

**JDK中用Thread.State类定义了线程的几种状态**

- **新建：**当一个Thread类或其子类的对象被声明并创建时，新生的线程对象处于新建状态
- **就绪：**处于新建状态的线程被start()后，将进入线程队列等待CPU时间片，此时已经具备了运行的条件，只是没分配到CPU资源
- **运行：**当就绪的线程被调度并获得CPU资源时，便进入运行状态，run（）方法定义了线程的操作和功能
- **阻塞：**在某种特殊情况下，被认为挂起或者执行输入输出操作时，让出CPU并临时终止自己的执行，进入阻塞状态
- **死亡：**线程完成了全部的工作或线程被提前强制性的终止或出现异常导致结束

##### 4. 线程的同步

**1.线程的安全问题：**

​	**问题出现的原因：**当某个线程在操作**共享数据**时，尚未操作完成时，其他线程参与进来，也操作**共享数据**

​	**解决：**当当前线程操作共享数据时，其他线程不能参与进来，直到当前线程操作完共享数据。在这种情况下，即使当前线程出现了阻塞也不能被改变。

**2. 同步机制解决线程安全问题**

**方式一：同步代码块**

```
synchronized(同步监视器){
	//需要被同步的代码，即操作共享数据的代码
}
```

**其中同步监视器，即锁，可以为任何一个类的对象，==注意多个线程必须公用同一个锁==！**

```java
class TicketThread implements Runnable {

    private int ticket = 100;
    //生成一个锁，注意要保证线程公用同一个锁，所以需要声明为全局
    Object lock = new Object();

    @Override
    public void run() {
        while (true) {

            synchronized (lock){
                if(ticket > 0){
                    System.out.println(Thread.currentThread().getName() + ": " + ticket);
                    ticket--;
                }else {
                    break;
                }
            }

        }
    }
}
```

**方式二：同步方法** 

在方法生成前添加**synchronized**关键字

- 同步方法依然涉及到同步监视器（锁），只是不需要显示的声明
  - 非静态的同步方法，同步监视器是**this**
  - 静态的同步方法，同步监视器是**当前类本身**

```java
class SellTicket extends Thread {
    private static int ticket = 100;

    @Override
    public void run() {
        while (true) {
            show();
        }
    }

    private static synchronized void show() {
        if (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + ": sell " + ticket + "th ticket");
            ticket--;
        }
    }
}
```

**方式三：Lock锁  JDK5.0新增**

通过实例化**ReentrantLock**调用锁定以及解锁方法实现线程同步

```java
class SellThread implements Runnable{

    private int ticket = 100;
    //1. 实例化ReentrantLock
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true){
            try {
                //2. 调用锁定方法lock()
                lock.lock();

                if(ticket > 0){

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + ticket);
                    ticket--;
                }else {
                    break;
                }

            }finally {
                //3. 调用解锁方法unlock()
                lock.unlock();
            }
        }
    }
}
```

**方式四：使用callable接口实现线程的创建 --JDK5.0新增**

- call()可以有返回值
- call()可以抛出异常，被外面的操作捕获，获取异常的信息
- Callable是支持泛型的

```java
package com.javaSE.thread;

/*
* 使用callable接口实现线程的创建 --JDK5.0新增
* */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//1.创建一个实现callable的实现类
class NumThread implements Callable{

    //2.实现call方法，将此线程需要执行的操作声明在call中
    @Override
    public Object call() throws Exception {

        int sum = 0;
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(i);
                sum += i;
            }
        }
        return sum;
    }
}

public class ThreadTest3 {
    public static void main(String[] args) {

        //3.创建callable接口实现类的对象
        NumThread numThread = new NumThread();
        //4.将此callable接口实现类的对象作为传递到FutureTask构造器中，创建FutureTask对象
        FutureTask futureTask = new FutureTask(numThread);
        //5.将FutureTask的对象作为参数传递到Thread类中，创建Thread对象，并调用start()
        new Thread(futureTask).start();

        try {
            //get()返回值即为FutureTask构造器参数Callable实现类重写的call()的返回值
            Object sum = futureTask.get();
            System.out.println("sum : " + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

```

**方式五：线程池**

经常创建和销毁并且使用量特别大的资源，例如并发情况下的线程，对性能的影响很大

可以利用线程池，提前创建好多个线程，放入线程池，使用时直接获取，使用完放回池中，可以避免频繁创建销毁线程，实现了重复利用

**优点：**

- 提高响应速度（减少了创建新线程的时间）
- 降低资源的消耗（重复利用线程池中的线程，不需要每次创建）
- 便于线程管理

```java
public class ThreadPool {
    public static void main(String[] args) {
        //1. 提供指定线程数量的线程池
        ExecutorService service = Executors.newFixedThreadPool(10);

        //也可以设置线程池的属性
        /*ThreadPoolExecutor service1 = (ThreadPoolExecutor) service;
        service1.setCorePoolSize(15);*/
        

        //2. 执行指定的线程的操作，需要提供实现Runnable接口或Callable接口实现类的对象
        //execute方法适用Runnable
        service.execute(new TestThread1());
        service.execute(new TestThread2());
        //submit方法适用于Callable
        //service.submit(Callable callable);

        //3. 关闭连接池
        service.shutdown();
    }
}
```

**synchronized和Lock的异同？**

**相同：都可以解决线程安全问题**

**不同：synchronized机制在执行完相应的同步代码之后会自动释放同步监视器**

​			**Lock需要手动启动同步，即加Lock锁；同步结束后也需要手动解锁，即释放unLock**

**使用线程同步机制实现单例模式之懒汉式**

```java
//利用同步机制解决单例模式之懒汉式
class Bank {

    private Bank() {
    }

    private static Bank instance = null;

    public static Bank getInstance() {
        if (instance == null) {
            synchronized (Bank.class) {
                if (instance == null) {
                    instance = new Bank();
                }
            }
        }
        return instance;
    }
}
```

**3. 死锁问题**

**定义：**不同的线程分别占用对方需要的同步资源不放弃，都在等待对方放弃自己需要的同步资源，就形成了线程的死锁

​			出现死锁后，不会出现异常，不会出现提示，只是所有的线程都处于阻塞状态，无法继续

```java
//死锁实例
package com.javaSE.thread;

public class DeadLockTest {
    public static void main(String[] args) {

        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();

        new Thread(){
            @Override
            public void run() {
                synchronized (stringBuffer1) {
                    stringBuffer1.append("a");
                    stringBuffer2.append(1);

                    //当前已经有一个线程拿着锁1，等睡醒后需要拿锁2
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (stringBuffer2) {
                        stringBuffer1.append("b");
                        stringBuffer2.append(2);

                        System.out.println(stringBuffer1);
                        System.out.println(stringBuffer2);
                    }
                }
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (stringBuffer2) {
                    stringBuffer1.append("c");
                    stringBuffer2.append(3);

                    //当前已经有一个线程拿着锁2，等睡醒后需要拿锁1
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (stringBuffer1) {
                        stringBuffer1.append("d");
                        stringBuffer2.append(4);

                        System.out.println(stringBuffer1);
                        System.out.println(stringBuffer2);
                    }
                }
            }
        }).start();

    }
}
```

**解决方式：**

- 专门的算法与原则
- 尽量减少同步资源的定义
- 尽量避免嵌同步

**例题：两个客户向同一账户中存3000元，每次存1000，存三次，每次存完打印账户余额**

```java
package com.javaSE.thread;

/*
* 两个客户向同一账户中存3000元，每次存1000，存三次，每次存完打印账户余额
* */
class Account {

    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public void deposit(double amt) {
        if (amt > 0) {
            balance += amt;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "存入" + amt + "元，余额为： " + balance);
        }
    }
}

class Customer extends Thread {
    private Account acct;

    public Customer(Account acct) {
        this.acct = acct;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            acct.deposit(1000);
        }
    }
}

public class AccountTest {
    public static void main(String[] args) {
        Account account = new Account(0);
        Customer customer1 = new Customer(account);
        Customer customer2 = new Customer(account);

        customer1.setName("customer1");
        customer2.setName("customer2");

        customer1.start();
        customer2.start();
    }
}
```

##### 5. 线程的通信

**1）三个方法**

- wait()：一旦执行此方法，当前线程就进入到阻塞状态，并释放同步监视器（sleep不释放同步监视器）
- notify()：一旦执行此方法，就唤醒被wait的一个线程；如果有多个线程被阻塞，就唤醒优先级高的线程
- notifyAll()：一旦执行此方法，就唤醒所有被wait的一个线程

**注意：**

- wait()，notify()，notifyAll()三个方法必须同时使用在同步代码块或同步方法中
- wait()，notify()，notifyAll()三个方法的调用者必须是同步代码块或同步方法中的同步监视器
- wait()，notify()，notifyAll()三个方法是定义在java.lang.object类中

**示例：**

```java
/*
* 实现两个线程交替打印数字，1 - 100
* */

class Number implements Runnable{

    private int number = 1;

    @Override
    public void run() {
        while (true) {

            synchronized (this) {
                //唤醒wait
                notify();

                if(number <= 100){

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + ": " + number);
                    number++;

                    //打印后该进入阻塞状态
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    break;
                }
            }
        }
    }
}
```

**经典例题：生产者/消费者问题**

生产者将产品交给店员，消费者从店员取走产品，店员一次只能持有固定数量的产品（例如20），如果生产者生产更多的产品，店员会通知生产者暂停一下；同样店中没有产品了，店员会通知消费者等一下。

```java
package com.javaSE.thread;

class Clerk{

    private int productCount = 0;

    public synchronized void produceProduct(){
        if(productCount < 20){

            productCount++;
            System.out.println(Thread.currentThread().getName() + "生产了第" + productCount + "个产品");
            notify();
        }else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void consumeProduct(){
        if(productCount > 0){

            System.out.println(Thread.currentThread().getName() + "消费了第" + productCount + "个产品");
            productCount--;
            notify();
        }else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer extends Thread {
    private Clerk clerk;

    public Producer(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true){
            //模拟生产者慢一点生产
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.produceProduct();
        }
    }
}

class Customers extends Thread{
    private Clerk clerk;

    public Customers(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true){
            //模拟消费者慢一点消费
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.consumeProduct();
        }
    }
}

public class ProductTest {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer producer1 = new Producer(clerk);
        producer1.setName("producer1");
        producer1.start();

        Customers customers1 = new Customers(clerk);
        customers1.setName("customers1");
        customers1.start();
    }
}
```

#### II. 设计模式

设计模式是在大量的实践中总结和理论化之后优选的代码结构、编程风格、 以及解决问题的思考方式。

##### 1. 单例设计模式

单例（singleton）设计模式就是采用一定的方法保证在整个的软件系统中，对某个类中只能存在一个对象实例，并且该类只提供一个取得其对象实例的方法。

- **单例的饿汉式实现**

  ```java
  package com.javaSE.designModel;
  
  class People{
  
      //1. 私有化类的构造器
      private People() {
  
      }
      //2. 内部创建类的对象
      //注意要声明为静态的
      private static People instance = new People();
  
      //3. 提供公共的静态方法，返回类的对象
      public static People getInstance(){
          return instance;
      }
  }
  
  public class SingletonTest {
      public static void main(String[] args) {
  
          People people1 = People.getInstance();
          People people2 = People.getInstance();
  
          //true
          System.out.println(people1 == people2);
      }
  }
  ```

- **单例的懒汉式实现**

```java
package com.javaSE.designModel;

//懒汉式
class Student{

    //1. 私有化类的构造器
    private Student(){

    }
    //2. 声明当前类对象，没有初始化，注意声明为static
    private static Student instance = null;
    //3. 声明方法返回当前类对象
    public static Student getInstance(){
        if(instance == null){
            //注意需要用同步代码块保证线程安全
            synchronized (Student.class){
                if(instance == null){
                    instance = new Student();
                }
            }
        }
        return instance;
    }
}

public class SingletonTest2 {

    public static void main(String[] args) {
        Student student1 = Student.getInstance();
        Student student2 = Student.getInstance();

        System.out.println(student1 == student2);
    }
}
```

**单例模式的优点：**由于单例模式只生成一个实例，减少了系统性能的开销，当一个对象的产生需要比较多的资源时，如读取配置、产生其他依赖对象时，则可以通过在应用启动时直接产生一个单例对象，然后永久驻留内存的方式解决

##### 2.待完善...

https://blog.csdn.net/sugar_no1/article/details/88317950?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522164456452216780269812712%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=164456452216780269812712&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_positive~default-1-88317950.pc_search_insert_es_download&utm_term=java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F&spm=1018.2226.3001.4187

#### III. 常用类

##### 1.StringBuffer

**可变的字符序列（对原字符串操作时直接改变原字符，而String需要赋值给新字符串）；线程安全的，效率低；底层也是char数组实现**

**常用方法：**

- StringBuffer append(XXX)：提供了很多的append方法，用于进行字符串拼接
- StringBuffer delete(int start , int end)：删除指定位置的内容
- StringBuffer replace(int start , int end , String str)：把[start , end)位置替换为str
- StringBuffer insert(int offset , XXX)：在指定位置插入XXX
- StringBuffer reverse()：字符串逆转
- public int indexof(String str)
- public String substring(int start , int end)
- Public int length()
- public char charAt(int n)
- public void setCharAt(int n , char ch)

##### 2.StringBuilder

**可变的字符序列；线程不安全的，效率高；底层也是char数组实现**

常用方法与StringBuffer一致

##### 3.Java比较器

在Java中经常会涉及到对象数组的排序问题，那么就涉及到对象之间的比较问题

**Java实现对象排序的方法有两种：**

- 自然排序：java.lang.comparable

  首先实现Comparable接口，重写compareTo方法即可

  重写compareTo(obj)方法的规则：

  - 如果当前对象this大于形参对象obj，则返回正整数
  - 如果当前对象this小于形参对象obj，则返回负整数
  - 如果当前对象this等于形参对象obj，则返回零

  ```java
  public class Goods implements Comparable {
  		//重写compareTo方法，实现按商品价格从大到小排序，价格相同按名称排序
      @Override
      public int compareTo(Object o) {
          if(o instanceof Goods){
              Goods goods = (Goods) o;
              //方式一：手写比较方法
              if(this.price > goods.price){
                  return 1;
              }else if(this.price < goods.price){
                  return -1;
              }else {
                  return this.name.compareTo(goods.name);
              }
              //方式二：利用Double的比较方法
              //return Double.compare(goods.price , this.price);
          }
          throw new RuntimeException("传入的数据类型不一致！");
      }
  }
  
  		@Test
      public void test2(){
          Goods[] goods = new Goods[5];
          goods[0] = new Goods("mac", 12000);
          goods[1] = new Goods("mouse3", 45);
          goods[2] = new Goods("keyboard", 300);
          goods[3] = new Goods("ipad", 6999);
          goods[4] = new Goods("mouse2", 45);
  
          Arrays.sort(goods);
          System.out.println(Arrays.toString(goods));
      }
  ```

- 定值排序：java.util.comparator

  comparator属于临时的比较，重写compare方法即可

  ```java
  @Test
      public void test4(){
          Goods[] goods = new Goods[5];
          goods[0] = new Goods("mac", 12000);
          goods[1] = new Goods("mouse3", 45);
          goods[2] = new Goods("keyboard", 300);
          goods[3] = new Goods("ipad", 6999);
          goods[4] = new Goods("mouse2", 45);
  
          Arrays.sort(goods, new Comparator() {
              @Override
              public int compare(Object o1, Object o2) {
                  if(o1 instanceof Goods && o2 instanceof Goods){
                      Goods g1 = (Goods) o1;
                      Goods g2 = (Goods) o2;
                      if(g1.getName().equals(g2.getName())){
                          return -Double.compare(g1.getPrice(), g2.getPrice());
                      }else {
                          return g1.getName().compareTo(g2.getName());
                      }
                  }
                  throw new RuntimeException("exception！");
              }
          });
          System.out.println(Arrays.toString(goods));
      }
  ```

#### IV. 枚举类

类的对象只有有限个，是确定的（星期，性别，季节，支付方式，订单状态，线程状态）

**当需要定义一组常量时，建议使用枚举类**

##### **1. 自定义枚举类**

```java
//自定义枚举类
class Season{
    //1. 声明Season对象的属性
    private final String seasonName;
    private final String seasonDesc;

    //2. 私有化构造器，并给对象属性赋值
    private Season(String seasonName, String seasonDesc) {
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }

    //3. 提供当前枚举类的多个对象，可以直接类名.变量名直接调用
    public static final Season SPRING = new Season("春天", "春暖花开");
    public static final Season SUMMER = new Season("夏天", "夏日炎炎");
    public static final Season AUTUMN = new Season("秋天", "秋高气爽");
    public static final Season WINTER = new Season("冬天", "冰天雪地");

    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }

    @Override
    public String toString() {
        return "Season{" +
                "seasonName='" + seasonName + '\'' +
                ", seasonDesc='" + seasonDesc + '\'' +
                '}';
    }
}
```

##### **2. 使用enum关键字**

```java
enum Season1{

    //1. 提供当前枚举类的对象，多个对象之间用逗号隔开，末尾对象分号结束
    SPRING("春天", "春暖花开"),
    SUMMER("夏天", "夏日炎炎"),
    AUTUMN("秋天", "秋高气爽"),
    WINTER("冬天", "冰天雪地");

    //2. 声明Season对象的属性
    private final String seasonName;
    private final String seasonDesc;

    //3. 私有化构造器，并给对象属性赋值
    private Season1(String seasonName, String seasonDesc) {
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }
}

public class SeasonTest2 {
    public static void main(String[] args) {
        Season1 summer = Season1.SUMMER;
        System.out.println(summer.toString());

        System.out.println();
        //values():返回枚举类型的对象数组
        Season1[] seasons = Season1.values();
        for(Season1 season1 : seasons){
            System.out.println(season1);
        }
        System.out.println();
        //valueOf(objName):返回枚举类中对象名是objName的对象
        Season1 winter = Season1.valueOf("WINTER");
        System.out.println(winter);
    }
}
```

#### V. 注解

##### 1. 概述

Annotation就相当于代码里的特殊标记，这些标记可以在编译、类加载、运行时被读取，并执行相应的处理

Annotation可以像修饰符一样被使用，可以用于修饰包、类、构造器、方法、成员变量、参数、局部变量的声明，这些信息被保存在Annotation的”name = value“对中；

在JavaSE中，注解的使用目的比较简单，但在开发模式都是基于注解的，可以说：**框架 = 注解 + 反射 + 设计模式**

##### 2. 使用示例

- 生成文档相关的注解
- 在编译时进行格式检查（JDK内置的三个基本注解）
  - Override: 限定重写父类方法，该注解智能用于方法
  - Deprecated：用于表示所修饰的元素已过时
  - SuppressWarnings：抑制编译器警告
- 跟踪代码的依赖性，实现替代配置文件功能（javaweb/Spring）

##### 3. 自定义注解

```java
public @interface MyAnnotation {

    //内部定义成员，通常使用value表示
    String value() default "hello";
}

public static void main(String[] args) {
        @MyAnnotation(value = "hello")
        int hello;
    }
```

**注意：自定义注解必须配上注解的信息处理流程（使用反射）才有意义**

##### 4. **元注解**

**定义：对现有的注解进行解释说明的注解**

- **Retention:** 指定所修饰的Annotation的生命周期：**SOURSE / CLASS(默认) / RUNTIME**，只有声明为RUNTIME的生命周期的注解才能通过反射获取

  ```java
  //@Retention(RetentionPolicy.SOURCE)
  
  //枚举类RetentionPolicy：
  public enum RetentionPolicy {
      /**
       * Annotations are to be discarded by the compiler.
       */
      SOURCE,
  
      /**
       * Annotations are to be recorded in the class file by the compiler
       * but need not be retained by the VM at run time.  This is the default
       * behavior.
       */
      CLASS,
  
      /**
       * Annotations are to be recorded in the class file by the compiler and
       * retained by the VM at run time, so they may be read reflectively.
       *
       * @see java.lang.reflect.AnnotatedElement
       */
      RUNTIME
  }
  ```

- **Target:** 用于指定被修饰的Annotation能用于修饰那些程序元素

  ```java
  //@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
  
  public enum ElementType {
      /** Class, interface (including annotation type), or enum declaration */
      TYPE,
  
      /** Field declaration (includes enum constants) */
      FIELD,
  
      /** Method declaration */
      METHOD,
  
      /** Formal parameter declaration */
      PARAMETER,
  
      /** Constructor declaration */
      CONSTRUCTOR,
  
      /** Local variable declaration */
      LOCAL_VARIABLE,
  
      /** Annotation type declaration */
      ANNOTATION_TYPE,
  
      /** Package declaration */
      PACKAGE,
  
      /**
       * Type parameter declaration
       *
       * @since 1.8
       */
      TYPE_PARAMETER,
  
      /**
       * Use of a type
       *
       * @since 1.8
       */
      TYPE_USE
  }
  ```

- **Documented：**表示所修饰的注解被javadoc解析时，保留下来
- **Inherited：**被修饰的Annotation将具有继承性

##### 5. 类型注解

**ElementType.TYPE_PARAMETER**: 表示注解能写在类型变量的声明语句中

**ElementType.TYPE_USE**: 表示该注解能写在使用类型的任何语句中

```java
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.LOCAL_VARIABLE,ElementType.TYPE_PARAMETER,ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {

    //内部定义成员，通常使用value表示
    String value() default "hello";
}


class Generic<@MyAnnotation T>{
    public void show() throws @MyAnnotation RuntimeException{

        ArrayList<@MyAnnotation String> list = new ArrayList<>();

        int num = (@MyAnnotation int) 10L;
    }
}
```

#### VI. 集合

##### 1. 定义

Java集合可以分为Collection和Map两种体系

- **Collection接口:** 单列数据，定义了存取一组对象的方法的集合
  - **List：元素有序、可重复的集合**，”动态“数组
    - **Vector:** 作为List接口的古老实现类；线程安全，效率低；底层使用Object[] elementData存储
    - **ArrayList**：作为List接口的主要实现类；线程不安全，效率高；**底层使用Object[] elementData存储**
    - **LinkedList：**对于频繁的插入和删除操作，使用此类效率比ArrayList高；**底层使用双向链表存储**
  - **Set：元素无序、不可重复的集合**
    - **HashSet: **作为set接口的主要实现类；线程不安全的，可以存储null值
      - **LinkedHashSet**：作为HashSet的子类；遍历其内部数据时，可以按照添加的顺序输出；再添加数据的同时，每个数据还维护了两个引用，记录此数据的前一个数据和后一个数据；**适用于频繁的遍历操作**
    - **SortedSet: TreeSet**：可以按照添加对象的指定属性，进行排序；底层采用**红黑树**的存储结构
- **Map接口：**双列数据，保存具有映射关系的Key-Value键值对的集合
  - **HashMap: **作为Map的主要实现类；线程不安全，效率高；可以存储null的key-value；**底层数组+链表+红黑树**
    - **LinkedHashMap**：保证在遍历map元素时，可以按照添加的顺序实现遍历；适用于频繁的遍历操作
  - **SortedMap: TreeMap**：可以按照添加的key-value进行排序（对key进行排序），实现排序遍历；**底层采用红黑树**
  - **Hashtable：**作为Map的古老实现类；线程安全，效率低；不可以存储null的key-value
    - **Properties：**常用来处理配置文件，key-value都是String类型

##### 2. 迭代器

**Iterator**对象称为迭代器（设计模式的一种），主要用于遍历Collection集合中的元素。

**迭代器设计模式：**提供一种方法访问一个容器对象中各个元素，而又无需暴露该对象的内部细节。**迭代器模式就是为容器而生。**

```java
		@Test
    public void test1(){
        Collection collection = new ArrayList();
        collection.add("aaa");
        collection.add("bb");
        collection.add(new String("hello"));
        collection.add(123);
        collection.add(new Person("zyh" , 23));

        //iterator():返回Iterator接口的实例，用于遍历集合元素
        Iterator iterator = collection.iterator();
        //hasNext()：判断是否还有下一个元素
        //next()：1. 指针下移 2. 将下移后集合位置上的元素返回
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
      
      	Iterator iter = collection.iterator();
        while (iter.hasNext()){
            Object current = iter.next();
            if("aaa".equals(current)){
                iter.remove();
            }
        }
        System.out.println(collection);
    }
```

##### 3. List接口

**ArrayList（JDK7.0）：**

- 底层先通过构造器创建了长度为10的Object数组elementData
- 添加时先判断容量是否足够，若不够调用扩容方法进行扩容；所以在方法中可以直接调用带参的构造器，直接指定容量，避免后期的扩容

**ArrayList（JDK8.0）：**

- 底层object数组初始化为{}，但并未创建长度为10的数组
- 在调用add方法扩容阶段时才创建

```java
		private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};	

		transient Object[] elementData; // non-private to simplify nested class access

		public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

		public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }

		private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

		private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
      	//首次创建在这创建数组
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```

**LinkedList：**

内部声明了Node类型的First以及Last属性，通过链表增删改查方式进行操作数据，不涉及到扩容

```java
		transient Node<E> first;

    transient Node<E> last;

		public boolean add(E e) {
        linkLast(e);
        return true;
    }

		void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
    }
```

**List常用方法**

- void add( int index, object ele): 在 index位置插入eLe元素
- boolean addAll( int index, Collection eles):从 index位置开始将eLes中的所有元素添加进来 
- object get( int index):获取指定 index位置的元素
- int indexof( object obj):返回obj在集台中首次出现的位置
- int LastIndexof( object obj):返obj在当前集台中末次出现的位置 
- object remove( int index):移除指定 index位置的元素,并返回此元素 
- object set( int index, object ele):设置指定 index,位置的元素为eLe
- List sublist( int fromIndex, int toIndex):返回从 FromIndex到toIndex位置的子集合

#####  4. Set接口

**Set添加元素的原理：**

<img src="/Users/zyh/Library/Application Support/typora-user-images/image-20220216153144399.png" alt="image-20220216153144399" style="zoom:70%;" />

以HashSet为例，首先底层也是用数组来存储数据，在向HashSet中添加元素a时，首先调用元素a所在类的Hashcode方法，计算a的hash值，利用某种hash散列函数计算出该元素在底层数组中存放的位置，再添加之前需要判断该位置上是否有元素：

- 若无元素存在，则直接添加即可
- 若有元素存在，则调用两者的hashcode方法，比较两者的hash值
  - 若hash值不同，则将新元素以链表的形式链到原索引处
  - 若hash值相同，则调用两者的equals方法，相等则无需添加；不相等则元素也以链表的形式链到原索引处

**HashSet底层实际上是利用HashMap实现的，将元素值存储在HashMap的Key中（因为Key不可重复，性质与set相同），而value赋值为一个固定的对象**

**TreeSet排序方式**

- **自然排序**

  在自然排序中，比较两个对象是否相同的标准为：compareTo()返回0，而不是equals()

  ```java
  //Person类中重写的比较器，姓名升序；若相同，年龄升序
  		@Override
      public int compareTo(Object o) {
          if(o instanceof Person){
              Person person = (Person) o;
              int compare = this.name.compareTo(person.name);
              if(compare != 0){
                  return compare;
              }else {
                  return Integer.compare(this.age , person.age);
              }
          }else {
              throw new RuntimeException("exception!");
          }
      }
  
  //TreesSet测试
  		@Test
      public void test2(){
          TreeSet<Person> treeSet = new TreeSet<>();
  
          //在Person类中重写的比较器，对姓名属性进行排序
          treeSet.add(new Person("fy" , 20));
          treeSet.add(new Person("pdx" , 5));
          treeSet.add(new Person("dp" , 3));
        	//注意：TreeSet默认用compareTo方法进行比较，所以需要更具体CompareTo方法的实现（二级实现，涉及年龄）
          treeSet.add(new Person("zyh" , 22));
          treeSet.add(new Person("zyh" , 30));
  
          for(Person person : treeSet){
              System.out.println(person);
          }
      }
  ```

- **定制排序**

  在自然排序中，比较两个对象是否相同的标准为：compare()返回0，而不是equals()

```java
		@Test
    public void test3(){
      	//重写comparator中的compare方法
        Comparator<Person> comparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if(o1 != null && o2 != null){
                    return Integer.compare(((Person) o1).getAge() , ((Person) o2).getAge());
                }else {
                    throw new RuntimeException("exception!");
                }
            }
        };

        TreeSet<Person> treeSet = new TreeSet<>(comparator);
      	//基于上述比较器的定义，年龄相同的对象compare方法将返回0，即年龄相同视为重复，不写入set集
        treeSet.add(new Person("zyh" , 20));
        treeSet.add(new Person("zyh" , 30));
        treeSet.add(new Person("fy" , 20));
        treeSet.add(new Person("pdx" , 5));
        treeSet.add(new Person("dp" , 3));

        for(Person person : treeSet){
            System.out.println(person);
        }
    }
```

##### 5. Map接口

**Map结构**

- Map中的Key： 无序的、不可重复的，使用Set存储所有的Key（Key所在的类需要重新equals与hashcode方法）
- Map中的Value： 无序的、可重复的，使用Collection存储所有的value （value所在的类需要重写equals方法）
- 一个键值对：Key-value构成了一个Entry对象
- Map中的entry：无序的、不可重复的，使用Set存储所有的entry

**HashMap的底层实现原理**

- 首先利用new HashMap创建map时，在底层创建了一个空的**Node类型**数组（在JDK7.0中直接创建了一个长度为16的**Entry类型**数组table）
- 在调用map.put(key1 , value1)方法时，创建了一个长度为16的Node数组。此时首先调用key1所在类的hashCode方法计算key1的hash值，通过某种哈希散列函数确定在该key值在数组中的位置，并判断该索引上是否为空：
  - 若为空，此时的key-value键值对添加成功
  - 若不为空（表明在该位置上存在一个或多个数据（链表形式存储）），比较key1和已经存在的一个或者多个数据的hash值
    - 若比对之后全不相同，则键值对添加成功
    - 若比对之后和存在的某个key的hash值相同，则继续调用两者的equals方法进行比较；返回false则添加成功，返回ture**则使用value1替换原来的value值**
- 在添加的过程中会不断的扩容，当超出临界值且存放的位置不为空，扩容为原来容量的2倍，并将原有的数据复制过来
- **在存储的过程中，若一个位置上以链表的形式存储的数据个数超过8个，且当前的数组长度大于64，则此时索引位置上的所有数据采用红黑树进行存储**

**底层代码**

```java
		//默认加载因子
		static final float DEFAULT_LOAD_FACTOR = 0.75f;

		//默认容量，利用位运算加快效率，16
		static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
		
		//扩容的临界值：容量*加载因子
		int threshold;

		//链表中存放元素的最大个数，超过则转化为红黑树
		static final int TREEIFY_THRESHOLD = 8;

		transient Node<K,V>[] table;

		//定义Node结构，涉及链表存储添加next域
		static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
      
      	...(getter/setter/tostring/hashcode/equals)
    }

		public HashMap() {
      	//并未创建长度16的数组，只是定义了一下加载因子（0.75f）
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }
		
		//put方法的实现
		public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

		final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
      	//判断当前位置是否已经有元素存在
        if ((p = tab[i = (n - 1) & hash]) == null)
          	//没有则直接放入，添加成功
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
          	//若hash值相等，且equal，则将该数据暂存
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
          	//红黑树存储方式
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
              	//针对于该位置有其他元素的情况，判断hash值是否相等，若不相等且遍历到链表尾部，则创建新节点直接链上
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                      	//如果链表元素个数超过8，则变为红黑树进行存储同一位置上的键值对
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                  	//遍历过程发现hash值相等且equals，则将该数据暂存（后进行value值的替换）
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
          	//进行value值的替换
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }

		//首次创建长度16的数组
		//扩容方法
		final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
      	//创建了新数组
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
		
		//转换为红黑树方法
		final void treeifyBin(Node<K,V>[] tab, int hash) {
        int n, index; Node<K,V> e;
      	//先判断当前数组的长度是否小于64，若小于则不转换为红黑树，而是扩容
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();
        else if ((e = tab[index = (n - 1) & hash]) != null) {
            TreeNode<K,V> hd = null, tl = null;
            do {
                TreeNode<K,V> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;
                else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            if ((tab[index] = hd) != null)
                hd.treeify(tab);
        }
    }

```

**Map常用方法**

**添加、删除、修改操作**

- Object put( Object key, Object value):将指定key- value 添加到(或修改)当前map对象中
- void putAll(Map m):m中的所有key- value对存放到当map中
- object remove( Object key):移除指定 key的key- value对,并返回value
- void clear():清空当前map中的所有数据 

**元素查询的操作**

- object get( object key):获取指定key对应的 value 
- boolean containsKey( object key):是否包含指定的key 
- boolean containsValue( object value):是否包含指定的value 
- int size():返map中key- value对的个数 
- boolean isEmpty():判所当前map是否为空
- boolean equals( object obj):判断当前map和参数对象obj是否相等 

**元视图操作的方法**

- Set keySet():返回所有key构成的Set集合
- Collection values():返回所有 value构成的 Collection集合
- Set entryset():返回所有key- value构成Set集合

##### 6. properties

**利用Properties处理属性文件**

```java
public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            Properties properties = new Properties();
             fileInputStream = new FileInputStream("src/com/javaSE/set/user.properties");
            //加载对应文件
            properties.load(fileInputStream);

          	//读取配置文件中的属性
            String name = properties.getProperty("name");
            String password = properties.getProperty("password");

            System.out.println(name + ": " + password);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }
```

```properties
#user.properties
name=zyh
password=123456
```

##### 7. Collections工具类

**常用方法**

- reverse(List):反转List中元素的顺序 
- shuffle(List):对List集合元素随机排序
- sort(List):根据元素的自然顺序指定List集合元素按升序排序
- sort(List, Comparator):根据指定的 Comparator产生的顺序对List集合元素进行排序 
- swap(List,int,int):将指定list集合中的i处元素和j处元素进行交换
- int frequency(Collection, object):返回指定集合中指定元素的出现次数

**Collections类中提供了多个synchronizedXXX()方法，该方法可以将指定集合包装成线程同步的集合，从而解决多线程并发访问集合时的线程不安全问题**

#### VII. 泛型

##### 1. 设计背景

集合容器类在设计阶段/声明阶段不能确定这个容器到底实际存的是什么类型的对象，所以**在JDK1.5之前只能把元素类型设计为 Object，JDK1.5之后使用泛型来解决。**因为这个时候除了元素的类型不确定，其他的部分是确定的，例如关于这个元素如何保存，如何管理等是确定的，因此此时把元素的类型设计成一个参数，这个类型参数叫做泛型。 Collection<E>，List<E>， ArrayList<E>这个<E>就是类型参数，即泛型。

##### 2. 定义

所谓泛型，就是允许在定义类、接口时通过一个标识表示类中某个属性的类型或者是某个方法的返回值及参数类型。这个类型参数将在使用时(例如，继承或实现这个接口，用这个类型声明变量、创建对象时)确定(即传入实际的类型参数，也称为类型实参)。

##### 3. 自定义泛型结构

- **自定义泛型类：**

  ```java
  public class Order<T> {
  
      String orderName;
      long orderId;
      T orderT;
  
      public Order() {
      }
  
      public Order(String orderName, long orderId, T orderT) {
          this.orderName = orderName;
          this.orderId = orderId;
          this.orderT = orderT;
      }
  
      public T getOrderT() {
          return orderT;
      }
  
      public void setOrderT(T orderT) {
          this.orderT = orderT;
      }
  
      @Override
      public String toString() {
          return "Order{" +
                  "orderName='" + orderName + '\'' +
                  ", orderId=" + orderId +
                  ", orderT=" + orderT +
                  '}';
      }
  }
  
  		@Test
      public void test1(){
          //实例化时指定泛型
          Order<String> order = new Order<>("mac" , 123 , "orderT");
  
          order.setOrderT("aaa");
  
      }
  ```

- **自定义泛型方法**

  ```java
  		//自定义泛型方法
      public <E> List<E> copyFromArrayToList(E[] array){
  
          ArrayList<E> arrayList = new ArrayList<>();
  
          for(E e : array){
              arrayList.add(e);
          }
          return arrayList;
  
      }
  ```

##### 4. 通配符

**通配符：？**

类A是类B的子类，但是G<A>和G<B>是没有关系的，两者共同的父类是：G<?>

```java
		@Test
    public void test2(){
        List<Object> list1 = null;
        List<String> list2 = null;

        List<?> list = null;

        list = list1;
        list = list2;
        
        print(list1);
        print(list2);
    }

    public void print(List<?> list){
        Iterator<?> iterator = list.iterator();
        while(iterator.hasNext()){
            Object object = iterator.next();
            System.out.println(object);
        }
    }
```

#### VIII. 反射机制

##### 1. 概述

​	Reflection（反射）是被视为动态语言的关键，反射机制允许程序在执行期借助于Reflection API**取得任何类的内部消息，并能直接操作任意对象的内部属性以及方法。**

​	加载完类之后，在堆内存的方法区中就产生了一个Class类型的对象，**这个对象就包含了完整的类的结构信息，可以通过这个对象看到类的结构**（可以理解为：该对象就是一面镜子，通过这个镜子可以看到类的结构，所以称之为反射）

- **正常方式：引入需要的类包名称 > 通过New实例化 > 取得实例化对象**
- **反射方式：实例化对象 > getClass()方法 > 取得完整的类包名称 **

**关于java.lang.Class类的理解：**

​	程序经过javac.exe命令以后，会生成一个或多个字节码文件（.class结尾），接着可以使用java.exe对某个字节码文件进行解释运行。相当于将某个字节码文件加载到内存中，此过程就称为类的加载，加载到内存中的类就称为运行时类，此运行时类就作为Class的一个实例。

​	加载到内存的运行时类，会缓存一定时间，在此时间内，我们可以通过不同的方式来获取该类。

**反射的作用：**

```java
		//使用反射，对于Person的操作
    @Test
    public void test2() throws Exception {
        Class<Person> personClass = Person.class;
        //1. 通过反射，创建Person类对象
        Constructor<Person> constructor = personClass.getConstructor(String.class, int.class);
        Person person = constructor.newInstance("fy", 20);
        System.out.println(person);
        //2. 通过反射，调用对象的公有属性、方法
        Method show = personClass.getDeclaredMethod("show");
        show.invoke(person);

        System.out.println();

        //3. 通过反射，可以调用Person类的私有结构，包括私有的构造器，属性以及方法
        Method love = personClass.getDeclaredMethod("love");
        love.setAccessible(true);
        love.invoke(person);

        Field age = personClass.getDeclaredField("age");
        age.setAccessible(true);
        age.set(person , 18);
        System.out.println(person);
    }


```

##### 2. 获取Class实例

```java
		@Test
    public void test3() throws ClassNotFoundException {
        //1. 调用运行时类的属性：.class
        Class<Person> personClass1 = Person.class;
        System.out.println(personClass1);

        //2. 通过运行时类的对象，调用getClass方法
        Person person = new Person();
        Class personClass2 = person.getClass();
        System.out.println(personClass2);

        //3. 调用Class的静态方法：forName（String classPath）
        //一般使用这种方式创建Class实例，更好的体现动态性
        Class personClass3 = Class.forName("com.javaSE.reflection.Person");
        System.out.println(personClass3);

        //4. 使用类加载器：classLoader
        ClassLoader classLoader = ReflectionTest.class.getClassLoader();
        Class personClass4 = classLoader.loadClass("com.javaSE.reflection.Person");
        System.out.println(personClass4);

    }
```

##### 3. 创建运行时类的对象

```java
    @Test
    public void test1() throws IllegalAccessException, InstantiationException {
        Class<Person> personClass = Person.class;
        //newInstance方法：调用此方法，创建对应的运行时类的对象，内部调用了运行时类的空参构造器
        //注意需要运行时类提供公有的空参构造器
        Person person = personClass.newInstance();
        System.out.println(person);
    }
```

##### 4. 反射的动态性

**通过反射可以动态的创建对象，该对象是在编译前不确定的**

**代码模拟：**

```java
		//创建一个指定类的对象，classpath为全类名
		public Object getInstance(String classPath) throws Exception {
        Class<?> aClass = Class.forName(classPath);
        Object newInstance = aClass.newInstance();
        return newInstance;
    }

		@Test
    public void test2(){
        int num = new Random().nextInt(2);
        String classPath = "";
        switch (num){
            case 0:
                classPath = "java.util.Date";
                break;
            case 1:
                classPath = "com.javaSE.reflection.Person";
                break;
        }
        try {
            Object instance = getInstance(classPath);
            System.out.println(instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
```

##### 5. 获取运行时类的结构

- 获取运行时类的属性结构

  ```java
  		@Test
      public void test1(){
          Class<Person> personClass = Person.class;
          //getFields(): 获取当前运行时类及其父类中声明为public访问权限的属性
          Field[] fields = personClass.getFields();
          for(Field f : fields){
              System.out.println(f);
          }
  
          System.out.println();
  
          //getDeclaredFields(): 获取当前运行时类中声明的所有属性（不包括父类中声明的属性）
          Field[] declaredFields = personClass.getDeclaredFields();
          for(Field f : declaredFields){
              System.out.println(f);
          }
      }
  
      @Test
      public void test2(){
          Class<Person> personClass = Person.class;
          Field[] declaredFields = personClass.getDeclaredFields();
          for(Field f : declaredFields){
              //1. 权限修饰符
              int modifiers = f.getModifiers();
              System.out.println(Modifier.toString(modifiers));
  
              //2. 数据类型
              Class<?> type = f.getType();
              System.out.println(type.getName());
  
              //3. 变量名
              String name = f.getName();
              System.out.println(name);
  
              System.out.println();
  
          }
      }
  ```

- 获取运行时类的方法

  ```java
  		@Test
      public void test1(){
          Class<Person> personClass = Person.class;
          //getMethods(): 获取当前运行时类及其所有父类中声明为public权限的方法
          Method[] methods = personClass.getMethods();
          for(Method m : methods){
              System.out.println(m);
          }
  
          System.out.println();
  
          //getDeclaredMethods(): 获取当前运行时类中声明的所有方法（不包含父类中声明的方法）
          Method[] declaredMethods = personClass.getDeclaredMethods();
          for(Method m : declaredMethods){
              System.out.println(m);
          }
      }
  ```

**同样可以通过getXXX()获取到运行时类的父类、带泛型的父类、接口、包、构造器、注解**

##### 6. 调用运行时类的结构

- 调用运行时类中指定的属性

  ```java
  		@Test
      public void test1() throws Exception {
          Class<Person> personClass = Person.class;
  
          //创建运行时类的对象
          Person person = personClass.newInstance();
  
          //1. getDeclaredField()：获取运行时类中指定变量名的属性
          Field name = personClass.getDeclaredField("name");
  
          //2. 保证当前属性是可访问的
          name.setAccessible(true);
  
          //3. 获取设置指定对象的属性值
          //属性.set(运行时类对象，属性值)
          name.set(person , "zyh");
  
          //属性.get(运行时类对象)
          System.out.println(name.get(person));
      }
  ```

- 调用运行时类中指定方法

  ```java
  		@Test
      public void testMethod() throws Exception {
  
          Class personClass = Class.forName("com.javaSE.reflection.Person");
          Person person = (Person) personClass.newInstance();
  
          //getDeclaredMethod(): 参数一：方法名，参数二：形参类型
          Method love = personClass.getDeclaredMethod("love", String.class);
          love.setAccessible(true);
  
          //invoke(): 参数一：运行时类对象 参数二：形参值
          //invoke的返回值即为原函数的返回值
          Object returnValue = love.invoke(person, "fy");
          System.out.println(returnValue);
      }
  ```

##### 7. 应用—动态代理

- **静态代理示例**：代理类事先创建，无法改变，只能代理固定类型的被代理类

  ```java
  package com.javaSE.proxy;
  
  
  interface ClothFactory{
  
      void produceCloth();
  
  }
  
  //代理类
  class ProxyClothFactory implements ClothFactory{
  
      //用于被代理类对象进行实例化
      private ClothFactory factory;
  
      public ProxyClothFactory(ClothFactory factory){
          this.factory = factory;
      }
  
      @Override
      public void produceCloth() {
          System.out.println("代理工厂进行一些准备工作");
          System.out.println("...");
  
          factory.produceCloth();
  
          System.out.println("...");
          System.out.println("代理工厂做一些后续的收尾工作");
      }
  }
  
  //被代理类
  class NikeClothFactory implements ClothFactory{
      @Override
      public void produceCloth() {
          System.out.println("NIKE工厂开始生产一批衣服");
      }
  }
  
  public class StaticProxyTest {
  
      public static void main(String[] args) {
          //创建被代理类的对象
          NikeClothFactory nikeClothFactory = new NikeClothFactory();
          //创建代理类的对象
          ProxyClothFactory proxyClothFactory = new ProxyClothFactory(nikeClothFactory);
  
          proxyClothFactory.produceCloth();
  
      }
  }
  ```

- **动态代理**

  ```java
  package com.javaSE.proxy;
  
  import java.lang.reflect.InvocationHandler;
  import java.lang.reflect.Method;
  import java.lang.reflect.Proxy;
  
  interface Human{
  
      String getBelief();
  
      void eat(String food);
  }
  
  class Superman implements Human{
      @Override
      public String getBelief() {
          return "I can fly";
      }
  
      @Override
      public void eat(String food) {
          System.out.println("I like " + food);
      }
  }
  
  class ProxyFactory{
  
      //声明为static调用此方法返回一个代理类对象，
      //根据加载到内存中的被代理类，动态的创建一个代理类及其对象
      //obj为被代理类
      public static Object getProxyInstance(Object obj){
  
          MyInvocationHandler myInvocationHandler = new MyInvocationHandler();
  
          myInvocationHandler.bind(obj);
  
          return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),myInvocationHandler);
      }
  }
  
  class MyInvocationHandler implements InvocationHandler{
  
      //需要使用被代理类的对象进行赋值
      private Object obj;
  
      public void bind(Object obj){
          this.obj = obj;
      }
  
      //当通过代理类的对象调用方法a时，会自动调用如下方法invoke()
      //将被代理类要执行的方法a的功能就声明在invoke中
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          //method：代理类对象调用的方法，此方法作为了被代理类对象要调用的方法
          //obj：被代理类的对象
          Object returnValue = method.invoke(obj, args);
          //上述方法的返回值就作为当前类中的invoke()
          return returnValue;
      }
  }
  
  public class DynamicProxyTest {
      public static void main(String[] args) {
          Superman superman = new Superman();
          //proxyInstance:动态创建的代理类对象
          Human proxyInstance = (Human) ProxyFactory.getProxyInstance(superman);
  
          proxyInstance.eat("chocolate");
          System.out.println(proxyInstance.getBelief());
  
          //动态创建代理类，代理 静态代理示例 中的被代理类nikeClothFactory
          NikeClothFactory nikeClothFactory = new NikeClothFactory();
          ClothFactory proxyInstance1 = (ClothFactory) ProxyFactory.getProxyInstance(nikeClothFactory);
          proxyInstance1.produceCloth();
      }
  
  }
  ```

#### IX. Java8.0新特性

##### 1. lambda表达式

**作为函数式接口的实例**

**格式：**(o1 , o2) -> Interger.compare(o1 , o2)

- **-> : lambda操作符（箭头操作符）**
- **->左边：lambda形参列表，即抽象方法的形参列表**
- **->右边：lambda体，即重写的抽象方法的方法体**

**使用：6种情况**

```java
		//语法格式一：无参，无返回值
    @Test
    public void test1(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world!");
            }
        };
        runnable.run();

        System.out.println();

        Runnable runnable1 = () -> {
            System.out.println("hello new World!");
        };

        runnable1.run();
    }

    //语法格式二：需要一个参数，无返回值
    @Test
    public void test2(){
        Consumer<String> consumer1 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer1.accept("consumer1");

        System.out.println();

        Consumer<String> consumer2 = (String str) ->{
            System.out.println(str);
        };
        consumer2.accept("consumer2");
    }

    //语法格式三：数据类型可以省略，因为可以由编译器推断得出，称为类型推断
    @Test
    public void test3(){
        Consumer<String> consumer1 = (String str) ->{
            System.out.println(str);
        };
        consumer1.accept("consumer2");

        System.out.println();

        Consumer<String> consumer2 = (str) ->{
            System.out.println(str);
        };
        consumer2.accept("consumer3");
    }

    //语法格式四：若只需要一个参数时，参数的小括号可以省略
    @Test
    public void test4(){
        Consumer<String> consumer1 = (str) ->{
            System.out.println(str);
        };
        consumer1.accept("consumer3");

        System.out.println();

        Consumer<String> consumer2 = str ->{
            System.out.println(str);
        };
        consumer2.accept("consumer4");
    }

    //语法格式五：需要两个或以上的参数，多条执行语句，并可以有返回值
    @Test
    public void test5(){
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                System.out.println(o1);
                System.out.println(o2);
                return o1.compareTo(o2);
            }
        };
        comparator.compare(12,21);

        System.out.println();

        Comparator<Integer> comparator1 = (o1, o2) -> {
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);
        };

        comparator1.compare(34,43);
    }

    //语法格式六：当lambda体只有一条语句时，return与大括号若有可以省略
    @Test
    public void test6(){
        Comparator<Integer> comparator = (o1 , o2) -> o1.compareTo(o2);
        System.out.println(comparator.compare(12,21));
    }
```

##### 2. 函数式接口

**函数式接口：只包含一个抽象方法的接口**

可以在接口上使用@FunctionalInterface注解，标注这个接口是一个函数式接口

```java
@FunctionalInterface
public interface MyInterface {
    void method();
}
```

**Java内置四大核心函数式接口**

|        函数式接口        | 参数类型 | 返回类型 | 用途                                                         |
| :----------------------: | -------- | -------- | ------------------------------------------------------------ |
|  Consumer<T> 消费性接口  | T        | void     | 对类型为T的对象应用操作，包含方法void accept(T t)            |
|  Supplier<T> 供给性接口  | 无       | T        | 返回类型为T的对象，包含方法：Tget()                          |
| Function<T,R> 函数型接口 | T        | R        | 对类型为T的对象应用操作，并返回结果。结果是R类型的对象, R apply(T t) |
| Predicate<T> 断定型接口  | T        | boolean  | 确定类型为T的对象是否满足约束，并返回boolean值，boolean test(T t) |

**示例：**

```java
		@Test
    public void test1(){
        List<String> list = Arrays.asList("aaa" , "bba" , "acd" , "vvv");
        /*List<String> filterList = filterString(list, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("a");
            }
        });*/

        List<String> filterList = filterString(list , s -> s.contains("a"));
        System.out.println(filterList);
    }

    //过滤Predicate中定义过滤逻辑的数组
    public List<String> filterString(List<String> list , Predicate<String> predicate){
        ArrayList<String> arrayList = new ArrayList<>();
        for(String str : list){
            if(predicate.test(str)){
                arrayList.add(str);
            }
        }
        return arrayList;
    }
```

##### **3. 方法引用**

**使用情景：当要传递给Lambda体的操作已经有实现的方法了，可以使用方法引用**

**使用格式：类(或对象) : : 方法名**

**方法引用使用的要求：**要求接口中的抽象方法的形参列表以及返回值类型与方法引用的方法的形参列表以及返回值相同

**具体分为三种情况**

- 对象 : : 非静态方法

  ```java
  @Test
  public void test1(){
      Consumer<String> consumer = str -> System.out.println(str);
      consumer.accept("hello world");
  
      //方法引用
      //对象 :: 实例方法
      Consumer<String> consumer1 = System.out :: println;
      consumer1.accept("world hello");
  }
  
  @Test
  public void test2(){
      Person person = new Person("zyh", 23);
      Supplier<String> supplier = () -> person.getName();
      System.out.println(supplier.get());
  
      //方法引用
      //对象 :: 实例方法
      Supplier<String> supplier1 = person :: getName;
      System.out.println(supplier1.get());
  }
  ```

- 类 : : 静态方法

  ```java
  		@Test
      public void test3(){
          Comparator<Integer> comparator = (o1 , o2) -> Integer.compare(o1 , o2);
          System.out.println(comparator.compare(12, 21));
  
          //方法引用
          //类 :: 静态方法
          Comparator<Integer> comparator1 = Integer :: compare;
          System.out.println(comparator1.compare(34,43));
      }
  
      @Test
      public void test4(){
          Function<Double , Long> function = d -> Math.round(d);
          System.out.println(function.apply(13.4));
  
          //方法引用
          //类 :: 静态方法
          Function<Double , Long> function1 = Math :: round;
          System.out.println(function1.apply(3.14));
      }
  ```

- 类 : : 非静态方法

  ```java
  		@Test
      public void test5(){
          Comparator<String> comparator = (s1 , s2) -> s1.compareTo(s2);
          System.out.println(comparator.compare("cba" , "abc"));
  
          //方法引用
          //类 ::实例方法
          Comparator<String> comparator1 = String :: compareTo;
          System.out.println(comparator1.compare("abc" , "abc"));
      }
  
      @Test
      public void test6(){
          BiPredicate<String , String> biPredicate = (s1 , s2) -> s1.equals(s2);
          System.out.println(biPredicate.test("abc" , "abc"));
          //方法引用
          //类 ::实例方法
          BiPredicate<String , String> biPredicate1 = String :: equals;
          System.out.println(biPredicate1.test("aaa" , "bbb"));
      }
  ```

##### 4. Stream API

**a. 定义**

**使用Stream API对集合数据进行操作，类似于使用SQL执行的数据库查询**

应用场景：实际开发中大多数据源来自Mysql/Oracle等，其可以用sql进行查询；但是对于非关系型数据库（MongoDB/redis）来说，这些noSQL的数据就需要java层面去处理

Stream是数据渠道，用于操作数据源所产生的元素序列，**”集合讲的是数据，Stream讲的是计算“**

**b. 步骤：**

	1. 创建Stream：一个数据源，获取一个流
	2. 中间操作：一个中间操作链，对数据源的数据进行处理
	3. 终止操作：一旦执行终止操作，就执行中间操作链，并产生结果，之后不会再被使用

**c. Stream的实例化**：四种方式

```java
//测试数据
public static List<Employee> getEmpData(){
        ArrayList<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee(1001, "zyh", 23, 1);
        Employee employee2 = new Employee(1002, "fy", 21, 0);
        Employee employee3 = new Employee(1003, "pdx", 8, 1);
        Employee employee4 = new Employee(1004, "dp", 3, 0);
        Employee employee5 = new Employee(1005, "herry", 30, 1);

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);

        return employees;
    }
```

- 通过集合

  ```java
  @Test
  public void test1(){
      List<Employee> employeeList = EmployeeData.getEmpData();
  
      //返回一个顺序流
      Stream<Employee> stream = employeeList.stream();
  
      //返回一个并行流
      Stream<Employee> employeeStream = employeeList.parallelStream();
  }
  ```

- 通过数组

  ```java
  @Test
  public void test2(){
      int[] arr = {1, 2, 3, 4, 5, 6};
      //调用Arrays类的stream方法，返回一个流
      IntStream stream = Arrays.stream(arr);
  }
  ```

- 通过Stream的of方法

  ```java
  @Test
  public void test3(){
      //通过Stream的of()
      Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
  
  }
  ```

- 创建无限流

  ```java
  @Test
  public void test4(){
      //创建无限流
      //迭代
      Stream.iterate(0 , t -> t + 2).limit(10).forEach(System.out :: println);
  
      //生成
      Stream.generate(Math :: random).limit(10).forEach(System.out :: println);
  }
  ```

**d. 中间操作**

- 筛选与切片

  ```java
  //中间操作：筛选与切片
  @Test
  public void test5(){
      List<Employee> empData = EmployeeData.getEmpData();
    
      //filter(Predicate p): 接收Lambda，从流中排除某些元素
      empData.stream().filter(e -> e.getAge() > 10).forEach(System.out::println);
      System.out.println();
  
      //limit(n): 截断流，使其元素不超过给定数量
      empData.stream().limit(3).forEach(System.out::println);
      System.out.println();
  
      //skip(n): 跳过元素，返回一个跳过了前n个元素的流；若不足n个则返回空集合
      empData.stream().skip(2).forEach(System.out::println);
      System.out.println();
  
      //distinct(n): 筛选，通过流所生成的hashcode()以及equals()去除重复元素
      empData.add(new Employee(1006, "aaa", 19, 0));
      empData.add(new Employee(1006, "aaa", 19, 0));
      empData.add(new Employee(1006, "aaa", 19, 0));
  
      empData.stream().distinct().forEach(System.out::println);
  }
  ```

- 映射

  ```java
  //映射
  @Test
  public void test2(){
      //map(function f): 接收一个函数作为参数，将元素转换成其他形式或提取信息，该函数会被应用到每个元素上，并将其映射成一个新的元素
      List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
      list.stream().map(s -> s.toUpperCase(Locale.ROOT)).forEach(System.out::println);
  
      //获取员工姓名长度大于2的员工姓名
      Stream<String> stringStream = EmployeeData.getEmpData().stream().map(Employee::getName);
      stringStream.filter(name -> name.length() > 2).forEach(System.out::println);
  
      //flagMap(function f): 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有的流连接成一个流
      //将字符串进行拆分
      Stream<Character> characterStream = list.stream().flatMap(StreamAPITest2::forStringToStream);
      characterStream.forEach(System.out::println);
  
  }
  
  public static Stream<Character> forStringToStream(String str){
      ArrayList<Character> characters = new ArrayList<>();
      for(Character character : str.toCharArray()){
          characters.add(character);
      }
      return characters.stream();
  }
  ```

- 排序

  ```java
  //排序
  @Test
  public void test3(){
      //sorted():自然排序
      List<Integer> list = Arrays.asList(13, 34, 6, 4, 8, 0, -1, 5);
      list.stream().sorted().forEach(System.out::println);
  
      //sorted(Comparator com): 定值排序
      //根据年龄升序排序；若年龄相同则按序号升序排列
      List<Employee> employeeList = EmployeeData.getEmpData();
      employeeList.stream().sorted( (e1 , e2) ->{
          int flag = Integer.compare(e1.getAge() , e2.getAge());
          if(flag != 0){
              return flag;
          }else{
              return Long.compare(e1.getId() , e2.getId());
          }
      }
      ).forEach(System.out::println);
  }
  ```

**e. 终止操作：**

- 匹配与查找

  ```java
  List<Employee> empData = EmployeeData.getEmpData();
  @Test
  public void test1(){
      //allMatch(): 检查是否匹配所有元素
      //检查是否全部年龄都大于2
      boolean allMatch = empData.stream().allMatch(e -> e.getAge() > 2);
      System.out.println(allMatch);
  
      //anyMatch(): 检查是否至少匹配一个元素
      //检查是否有一个员工的姓名长于4
      boolean anyMatch = empData.stream().anyMatch(e -> e.getName().length() == 4);
      System.out.println(anyMatch);
  
      //noneMatch(): 检查是否没有匹配的元素
      //检查姓名是否存在z
      boolean noneMatch = empData.stream().noneMatch(e -> e.getName().equals("z"));
      System.out.println(noneMatch);
  
      //findFirst():返回第一个元素
      //findAny():返回任意元素
      Optional<Employee> employee = empData.stream().findFirst();
      System.out.println(employee);
  
      //count(): 返回流中元素的个数
      long count = empData.stream().filter(e -> e.getAge() < 10).count();
      System.out.println(count);
  
      //max(): 返回流中最大的值
      //min(): 返回流中最小的值
      Optional<Employee> max = empData.stream().max((e1, e2) -> Integer.compare(e1.getAge(), e2.getAge()));
      System.out.println(max);
  
      //forEach(): 内部迭代
      empData.stream().forEach(System.out::println);
  }
  ```

- 规约

  ```java
  //归约
  @Test
  public void test2(){
      //reduce(T identity , BinaryOperator):可以将流中元素反复结合起来，得到一个值
      List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
      Integer sum = list.stream().reduce(0, Integer::sum);
      System.out.println(sum);
  
      //计算员工的年龄总和
      Optional<Integer> sumAge = empData.stream().map(e -> e.getAge()).reduce((e1, e2) -> e1 + e2);
      Optional<Integer> sumAge1 = empData.stream().map(Employee :: getAge).reduce(Integer :: sum);
  
      System.out.println(sumAge);
      System.out.println(sumAge1);
  }
  ```

- 收集

  ```java
  //收集
  @Test
  public void test3(){
      //collect(Collectors c): 将流转换为其他形式（List或Set）
      //查找性别为男的员工
      List<Employee> employeeList = empData.stream().filter(e -> e.getGender() == 		1).collect(Collectors.toList());
      employeeList.forEach(System.out::println);
  
      System.out.println();
      Set<Employee> employeeSet = empData.stream().filter(e -> e.getAge() > 10).collect(Collectors.toSet());
      employeeSet.forEach(System.out::println);
  
  }
  ```

##### 5. Optional类

Optional是一个可以为null的容器对象，如果值存在则isPresent()方法会返回true，调用get()会返回该对象

**可以通过Optional类来表达一个值不存在代替null，可以避免空指针异常**

