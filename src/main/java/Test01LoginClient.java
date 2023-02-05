import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

//# day14网络编程
//
//        # 知识点
//
//        ## 题目1（加强训练）
//
//        在京东/淘宝等商城购物时,需要先注册账户,把用户名和密码保存到京东/淘宝等商城的服务器上.购物之前必须先登录,验证用户输入的用户名和密码与京东/淘宝等商城的服务器保存的用户名和密码是否一致,如果一致登录成功,如果不一致登录失败.
//
//        本案例使用TCP协议模拟登录操作(Socket作为客户端,ServerSocket作为服务器端),在服务器创建user.properties文件,保存已经注册的用户的用户名和密码(格式:root=toor,左侧root代表用户名,右侧toor代表密码),客户端通过键盘录入用户名和密码,发送给服务器端进行验证,验证通过,服务器端给出客户端响应"登录成功"提示信息,验证失败,服务器端给出客户端响应"登录失败"提示信息.
//
//        ### 训练目标
//
//        能够编写TCP协议下的注册登录功能
//
//        ### 训练提示
//
//        1、如何创建客户端和服务器端对象?
//
//        2、如何把数据写入到.properties文件中?
//
//        3、如何按照行发送数据
//
//        ### 参考方案
//
//        使用Socket和ServerSocket实现客户端和服务器端的数据传输
//
//        ### 操作步骤
//
//        一、网络登录客户端实现步骤:
//        1、创建客户端Socket对象,指定连接的服务器端的ip地址和端口号
//        2、客户端Socket对象获取字节输出流对象,给服务器发送数据
//        3、把客户端字节输出流对象,封装成字符缓冲输出流对象
//        4、创建Scanner对象
//        5、获取键盘录入的String类型的用户名
//        6、获取键盘录入的String类型的密码
//        7、客户端字符缓冲输出流对象调用write方法给服务器端发送数据
//        8、客户端Socket对象获取字节输入流对象,读取服务器发送回来的响应数据
//        9、把客户端字节输入流对象,封装成字符缓冲输入流对象
//        10、客户端字符缓冲输入流对象调用readLine方法,读取服务器发送回来的响应数据
//        11、关闭流,释放资源
//
//        二、网络登录客户端实现步骤:
//        1、在src根目录下创建user.properties配置文件,存储多个键值对(例如: root=toor 其中root代表用户名,toor代表密码)
//        2、创建服务器端ServerSocket对象,指定端口号
//        3、加载配置文件信息到Properties集合对象中(键:用户名,值:密码)
//        4、服务器端ServerSocket对象调用accept方法,获取连接服务器的客户端Socket对象
//        5、获取服务器端的字节输入流对象
//        6、把服务器端的字节输入流对象转换成字符缓冲输入流对象
//        7、服务器端的字符缓冲输入流对象调用readLine方法,读取客户端发送的请求信息(用户登录使用的用户名和密码)
//        8、获取服务器端的字节输出流对象
//        9、把服务器端的字节输出流对象转换成字符缓冲输出流对象
//        10、遍历Properties集合,验证客户端输入的用户名和密码
//        11、如果用户名和密码正确,服务器端字符缓冲输出流对象调用write方法,写出信息"登录成功"给客户端
//        12、如果用户名和密码不正确,服务器端字符缓冲输出流对象调用write方法,写出信息"登录失败"给客户端
//        13、关闭流,释放资源
//
//
//
//        ### 参考答案
//
//        ```
//        #src根目录下,user.properties文件内容如下
//        root=toor
//        abc=cba
//        admin=nimda
//        tom=mot
//        ```
//
//
//
//        ```java
//一、网络登录客户端代码
public class Test01LoginClient {
    public static void main(String[] args) throws IOException {
        //1、创建客户端Socket对象,指定连接的服务器端的ip地址和端口号
        Socket client = new Socket("localhost", 10086);
        //2、客户端Socket对象获取字节输出流对象,给服务器发送数据
        OutputStream netOs = client.getOutputStream();
        //3、把客户端字节输出流对象,封装成字符缓冲输出流对象

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(netOs));

        //4、创建Scanner对象
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名");
        //5、获取键盘录入的String类型的用户名
        String userName = sc.nextLine();
        System.out.println("请输入密码");
        //6、获取键盘录入的String类型的密码
        String password = sc.nextLine();

        //7、客户端字符缓冲输出流对象调用write方法给服务器端发送数据
        bw.write(userName);
        bw.newLine();//给服务器发送一个回车换行,说明该行结束了,服务器端用readLine方法可以读取一行信息
        bw.write(password);
        bw.newLine();
        bw.flush();//因为数据被写入缓冲区的数组中,必须要刷新

        //告诉服务器,客户端这边数据发送完毕
        client.shutdownOutput();
        //8、客户端Socket对象获取字节输入流对象,读取服务器发送回来的响应数据
        InputStream netIs = client.getInputStream();
        //9、把客户端字节输入流对象,封装成字符缓冲输入流对象
        BufferedReader br = new BufferedReader(new InputStreamReader(netIs));

        //10、客户端字符缓冲输入流对象调用readLine方法,读取服务器发送回来的响应数据
        String info = br.readLine();
        System.out.println(info);

        //11、关闭流,释放资源
        br.close();
        bw.close();
        client.close();
    }

}

//网络登录服务器代码,单线程版本
class Test01LoginServerOneLine {
    public static void main(String[] args) throws IOException {
        //2、创建服务器端ServerSocket对象,指定端口号
        ServerSocket server = new ServerSocket(10086);
        //3、加载配置文件信息到Properties集合对象中(键:用户名,值:密码)
        Properties props = new Properties();
        props.load(new FileInputStream("tests\\src\\user.properties"));
        //4、服务器端ServerSocket对象调用accept方法,获取连接服务器的客户端Socket对象
        Socket client = server.accept();
        //5、获取服务器端的字节输入流对象
        InputStream netIs = client.getInputStream();
        //6、把服务器端的字节输入流对象转换成字符缓冲输入流对象
        BufferedReader br = new BufferedReader(new InputStreamReader(netIs));
        //7、服务器端的字符缓冲输入流对象调用readLine方法,读取客户端发送的请求信息(用户登录使用的用户名和密码)
        String userName = br.readLine();
        String password = br.readLine();
        System.out.println("--------------------------");
        //8、获取服务器端的字节输出流对象
        OutputStream netOs = client.getOutputStream();
        //9、把服务器端的字节输出流对象转换成字符缓冲输出流对象
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(netOs));
        boolean result = false;
        //10、遍历Properties集合,验证客户端输入的用户名和密码

        for (String key : props.stringPropertyNames()) {
            //11、如果用户名和密码正确,服务器端字符缓冲输出流对象调用write方法,写出信息"登录成功"给客户端
            if (key.equals(userName) && props.getProperty(key).equals(password)) {
                bw.write("登陆成功");
                bw.newLine();
                bw.flush();
                result = true;
                break;
            }
        }
        //12、如果用户名和密码不正确,服务器端字符缓冲输出流对象调用write方法,写出信息"登录失败"给客户端
        if(result==false) {
            bw.write("登陆失败");
            bw.newLine();
            bw.flush();
        }


        //13、关闭流,释放资源
        br.close();
        bw.close();
        client.close();
        server.close();
    }
}

//网络登录服务器代码,多线程版本
 class Test01LoginServer02TwoLine {
    public static void main(String[] args) throws IOException {

        //2、创建服务器端ServerSocket对象,指定端口号
        ServerSocket server = new ServerSocket(10086);
        //3、加载配置文件信息到Properties集合对象中(键:用户名,值:密码)
        Properties props = new Properties();
        props.load(new FileInputStream("tests\\src\\user.properties"));

        while(true) {
            //4、服务器端ServerSocket对象调用accept方法,获取连接服务器的客户端Socket对象
            Socket client = server.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                        //5、获取服务器端的字节输入流对象
                        InputStream netIs = client.getInputStream();
                        //6、把服务器端的字节输入流对象转换成字符缓冲输入流对象
                        BufferedReader br = new BufferedReader(new InputStreamReader(netIs));
                        //7、服务器端的字符缓冲输入流对象调用readLine方法,读取客户端发送的请求信息(用户登录使用的用户名和密码)
                        String userName = br.readLine();
                        String password = br.readLine();
                        System.out.println("--------------------------");
                        //8、获取服务器端的字节输出流对象
                        OutputStream netOs = client.getOutputStream();
                        //9、把服务器端的字节输出流对象转换成字符缓冲输出流对象
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(netOs));
                        boolean result = false;
                        //10、遍历Map集合,验证客户端输入的用户名和密码
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            //11、如果用户名和密码正确,服务器端字符缓冲输出流对象调用write方法,写出信息"登录成功"给客户端
                            if (entry.getKey().equals(userName) && entry.getValue().equals(password)) {
                                bw.write("登陆成功");
                                bw.newLine();
                                bw.flush();
                                result = true;
                                break;
                            }
                        }
                        //12、如果用户名和密码不正确,服务器端字符缓冲输出流对象调用write方法,写出信息"登录失败"给客户端
                        if(result==false) {
                            bw.write("登陆失败");
                            bw.newLine();
                            bw.flush();
                        }


                        //13、关闭流,释放资源
                        br.close();
                        bw.close();
                        client.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }

    }
}


//        ### 视频讲解
//
//        另附avi文件提供。
//
//        ## 题目2（加强训练）
//
//        在商城上买东西,可能会进行前期的商品咨询,以及购买商品后的售后服务咨询.网上商城后台的一个服务人员,可以为多个客户提供服务.请使用TCP模拟多个客户端和服务器端进行数据传输功能,每个客户端可以给服务器端不断发送请求数据,服务器端也可以给客户端不断发送响应数据,要求客户端给服务器发一条信息,服务器必须给客户端回一条信息,如果 客户端发送信息中包含"拜拜"/"再见"等字样,则表示结束与服务器端的数据发送.客户端和服务器端程序运行结果如下图:
//
//        ![](img\02_client.jpg)
//
//        ![](img\02_server.jpg)
//
//        ### 训练目标
//
//        能够编写TCP协议下的聊天程序
//
//        ### 训练提示
//
//        1、如何创建客户端和服务器端对象?
//
//        2、如何按照行发送数据?
//
//        3、如何实现停止发送数据?
//
//        ### 参考方案
//
//        使用Socket和ServerSocket实现客户端和服务器端的数据传输
//
//        ### 操作步骤
//
//        一、TCP聊天客户端,实现步骤:
//        1、创建客户端Socket对象,指定要连接的服务器端的ip地址和端口号
//        2、客户端Socket对象调用getOutputStream方法,获取客户端的字节输出流对象
//        3、把客户端字节输出流对象,封装成字符缓冲输出流对象
//        4、客户端Socket对象调用getInputStream方法,获取客户端的字节输入流对象
//        5、把客户端字节输入流对象,封装成字符缓冲输入流对象
//        6、创建Scanner对象
//        7、客户端循环读(读取服务器发送回来的数据)写(写出到服务器端的数据)
//        7.1、客户端读取键盘录入数据发送给服务器端
//        7.2、客户端读取服务器端发送回来的响应数据,打印到控制
//        7.3、判断客户端发送的信息中,如果包含 "拜拜","再见"等字样,结束与服务器端的交流,关闭客户端
//        8、关闭资源
//
//        二、TCP聊天服务器端,实现步骤:
//        1、创建服务器端ServerSocket对象,指定端口号
//        2、服务器端ServerSocket对象调用accept方法,获取连接服务器的客户端Socket对象
//        3、获取服务器端的字节输入流对象
//        4、把服务器端的字节输入流对象转换成字符缓冲输入流对象
//        5、获取服务器端的字节输出流对象
//        6、把服务器端的字节输出流对象转换成字符缓冲输出流对象
//        7、创建Scanner对象
//        8、客户端循环读(读取客户端发送过来的请求数据)写(写给客户端响应数据)
//        8.1、读取客户端发送的请求数据,打印到控制台上
//        8.2、判断客户端发送的信息中,如果包含 "拜拜","再见"等字样,服务器端直接原样回复信息,并关闭客户端
//        8.3、服务器端读取键盘录入的数据,发送给客户端
//        9、关闭资源
//
//
//
//        ### 参考答案

//一、TCP聊天客户端
 class Test02ChatClientTPC {
    public static void main(String[] args) throws IOException {
        //1、创建客户端Socket对象,指定要连接的服务器端的ip地址和端口号
        Socket client = new Socket("localhost", 16666);
        System.out.println("----客户端启动----");

        //2、客户端Socket对象调用getOutputStream方法,获取客户端的字节输出流对象
        OutputStream netOs = client.getOutputStream();
        //3、把客户端字节输出流对象,封装成字符缓冲输出流对象
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(netOs));

        //4、客户端Socket对象调用getInputStream方法,获取客户端的字节输入流对象
        InputStream netIs = client.getInputStream();
        //5、把客户端字节输入流对象,封装成字符缓冲输入流对象
        BufferedReader br = new BufferedReader(new InputStreamReader(netIs));

        //6、创建Scanner对象
        Scanner sc = new Scanner(System.in);

        String line = "";
        String line2 = "";
        //7、客户端循环读(读取服务器发送回来的数据)写(写出到服务器端的数据)
        //7.1、客户端读取键盘录入数据
        while((line = sc.nextLine())!=null) {
            //发送给服务器端
            bw.write(line);
            bw.newLine();
            bw.flush();
            //7.2、客户端读取服务器端发送回来的响应数据,打印到控制台上
            line2 = br.readLine();
            System.out.println("客户端收到服务器端信息: "+line2);
            //7.3、判断客户端发送的信息中,如果包含 "拜拜","再见"等字样,结束与服务器端的交流,关闭客户端
            if(line.contains("拜拜")||line.contains("再见")) {
                client.close();
                break;
            }
        }
        //8、关闭资源
        bw.close();
        br.close();
    }
}
 

//二、TCP聊天服务器端,单线程版本
class Test02ChatServer01 {
    public static void main(String[] args) throws IOException {
        //1、创建服务器端ServerSocket对象,指定端口号
        ServerSocket server = new ServerSocket(16666);
        System.out.println(".....服务器启动.....");
        //2、服务器端ServerSocket对象调用accept方法,获取到连接服务器端的客户端Socket对象
        Socket client = server.accept();

        //3、获取服务器端的字节输入流对象
        InputStream netIs = client.getInputStream();
        //4、把服务器端的字节输入流对象转换成字符缓冲输入流对象
        BufferedReader br = new BufferedReader(new InputStreamReader(netIs));
        //5、获取服务器端的字节输出流对象
        OutputStream netOs = client.getOutputStream();
        //6、把服务器端的字节输出流对象转换成字符缓冲输出流对象
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(netOs));
        //7、创建Scanner对象
        Scanner sc = new Scanner(System.in);

        String line = "";
        //8、客户端循环读(读取客户端发送过来的请求数据)写(写给客户端响应数据)
        //8.1、读取客户端发送的请求数据
        while((line = br.readLine())!=null) {
            //打印到控制台上
            System.out.println("服务器端收到客户端信息: "+line);
            //8.2、判断客户端发送的信息中,如果包含"拜拜","再见"等字样,服务器端直接原样回复信息,并关闭客户端
            if(line.contains("拜拜")||line.contains("再见")) {
                bw.write(line);
                bw.newLine();
                bw.flush();
                client.close();
                break;
            }
            //8.3、服务器端读取键盘录入的数据
            line = sc.nextLine();
            //发送给客户端
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
        //9、关闭流,释放资源
        netIs.close();
        netOs.close();
        server.close();
    }
}

//二、TCP聊天服务器端,多线程版本
class Test02ChatServer02 {
    public static void main(String[] args) throws IOException {
        //1、创建服务器端ServerSocket对象,指定端口号
        ServerSocket server = new ServerSocket(16666);
        System.out.println(".....服务器启动.....");
        while(true) {
            //2、服务器端ServerSocket对象调用accept方法,获取到连接服务器端的客户端Socket对象
            Socket client = server.accept();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //3、获取服务器端的字节输入流对象
                        InputStream netIs = client.getInputStream();
                        //4、把服务器端的字节输入流对象转换成字符缓冲输入流对象
                        BufferedReader br = new BufferedReader(new InputStreamReader(netIs));
                        //5、获取服务器端的字节输出流对象
                        OutputStream netOs = client.getOutputStream();
                        //6、把服务器端的字节输出流对象转换成字符缓冲输出流对象
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(netOs));
                        //7、创建Scanner对象
                        Scanner sc = new Scanner(System.in);

                        String line = "";
                        //8、客户端循环读(读取客户端发送过来的请求数据)写(写给客户端响应数据)
                        //8.1、读取客户端发送的请求数据
                        while((line = br.readLine())!=null) {
                            //打印到控制台上
                            System.out.println("服务器端收到客户端信息: "+line);
                            //8.2、判断客户端发送的信息中,如果包含 "拜拜","再见"等字样,服务器端直接原样回复信息,并关闭客户端
                            if(line.contains("拜拜")||line.contains("再见")) {
                                bw.write(line);
                                bw.newLine();
                                bw.flush();
                                client.close();
                                break;
                            }
                            //8.3、服务器端读取键盘录入的数据
                            line = sc.nextLine();
                            //发送给客户端
                            bw.write(line);
                            bw.newLine();
                            bw.flush();
                        }
                        //9、关闭流,释放资源
                        br.close();
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
}

//        ### 视频讲解
//
//        另附avi文件提供。
//
//        ## 题目3（综合扩展）
//
//        使用TCP实现文件下载,客户端通过键盘录入需要下载的文件的全路径,发送给服务器端,服务器收到客户端要下载的文件的全路径后,找到该文件,通过流把文件内容以字节的方式发送给客户端,客户端通过流以字节的方式读取服务器发送过来的文件的字节内容,从而实现客户端文件的下载.
//
//        ### 训练目标
//
//        能够编写TCP协议下的文件下载
//
//        ### 训练提示
//
//        1、如何创建客户端和服务器端对象?
//
//        2、如何按照行发送数据?

//        3、如何确定文件下载完毕？
//
//        ### 参考方案
//
//        使用Socket和ServerSocket实现客户端和服务器端的文件传输
//
//        ### 操作步骤
//
//        一、文件下载客户端,实现步骤:
//        1、创建客户端Socket对象,指定连接的服务器端的ip地址和端口号
//        2、客户端Socket对象获取字节输出流对象,给服务器发送数据
//        3、创建Scanner对象
//        4、获取键盘录入的String类型的文件全路径
//        5、客户端字节输出流对象调用write方法,给服务器发送需要下载的文件路径信息
//        6、客户端Socket对象获取字节输入流对象,读取服务器发送回来的响应信息(客户端需要下载的文件内容)
//        7、创建文件字节输出流FileOutputStream类的对象,绑定目标文件(把客户端下载的文件字节内容写出到客户端目标文件)
//        8、循环读(服务器)写(客户端本地文件)
//        9、关闭流,释放资源
//
//        二、文件下载服务器端,实现步骤:
//        1、创建服务器端ServerSocket对象,指定端口号
//        2、服务器端ServerSocket对象调用accept方法,获取连接服务器的客户端Socket对象
//        3、获取服务器端的字节输入流对象
//        4、服务器端的字节输入流对象调用read方法,读取客户端请求信息(客户端要下载的文件全路径)
//        5、创建文件字节输入流FileInputStream对象,绑定源标文件(以字节的方式读取客户端要下载的文件)
//        6、获取服务器端的字节输出流对象
//        7、循环读(服务器的本地文件)写(客户端)
//        8、服务器端Socket对象调用shutdownOutput方法,通知客户端文件传输完毕
//        9、关闭流,释放资源
//
//
//
//        ### 参考答案

//一、文件下载客户端代码
class Test03DownloadClient {

    public static void main(String[] args) throws IOException {

        //1、创建客户端Socket对象,指定连接的服务器端的ip地址和端口号
        Socket client = new Socket("127.0.0.1", 9999);

        //2、客户端Socket对象获取字节输出流对象,给服务器发送数据
        OutputStream netOs = client.getOutputStream();
        //3、创建Scanner对象
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要下载的文件路径:");
        //4、获取键盘录入的String类型的文件全路径
        String filePath = sc.nextLine();
        //5、客户端字节输出流对象调用write方法,给服务器发送需要下载的文件路径信息
        netOs.write(filePath.getBytes());
        //6、客户端Socket对象获取字节输入流对象,读取服务器发送回来的响应信息(客户端需要下载的文件内容)
        InputStream netIs = client.getInputStream();
        //7、创建文件字节输出流FileOutputStream类的对象,绑定目标文件(用于把客户端下载的文件字节内容写出到客户端目标文件)
        FileOutputStream fos = new FileOutputStream(new File("day14\\download", new File(filePath).getName()));

        byte[] bs = new byte[1024];
        int len = 0;
        //8、循环读(服务器)写(客户端本地文件)
        while ((len = netIs.read(bs)) != -1) {
            fos.write(bs, 0, len);
        }
        System.out.println("文件下载完毕！");

        //9、关闭流,释放资源
        netIs.close();
        netOs.close();
        fos.close();
        client.close();

    }

}

//二、文件下载服务器端代码,单线程版本
 class Test03DownLoadServer01 {

    public static void main(String[] args) throws IOException {
        //1、创建服务器端ServerSocket对象,指定端口号
        ServerSocket server = new ServerSocket(9999);

        System.out.println("服务器已启动！");
        //2、服务器端ServerSocket对象调用accept方法,获取连接服务器的客户端Socket对象
        Socket client = server.accept();
        //3、获取服务器端的字节输入流对象
        InputStream netIs = client.getInputStream();

        byte[] bs = new byte[1024];
        int len = 0;
        len = netIs.read(bs);
        //4、服务器端的字节输入流对象调用read方法,读取客户端请求信息(客户端要下载的文件全路径)
        String filePath = new String(bs, 0, len);
        System.out.println("客户端需要下载: "+filePath+" 文件");

        //5、创建文件字节输入流FileInputStream对象,绑定源标文件(以字节的方式读取客户端要下载的文件)
        FileInputStream fis = new FileInputStream(filePath);
        //6、获取服务器端的字节输出流对象
        OutputStream netOs = client.getOutputStream();
        //7、循环读(服务器的本地文件)写(客户端)
        while((len = fis.read(bs))!=-1) {
            netOs.write(bs,0,len);
        }

        //8、服务器端Socket对象调用shutdownOutput方法,通知客户端文件传输完毕
        client.shutdownOutput();

        //9、关闭流,释放资源
        fis.close();
        netIs.close();
        netOs.close();
        client.close();
        server.close();
        System.out.println("文件传输完毕");
    }
}

//文二、件下载服务器端代码,多线程版本
 class Test03DownLoadServer02 {

    public static void main(String[] args) throws IOException {
        //1、创建服务器端ServerSocket对象,指定端口号
        ServerSocket server = new ServerSocket(9999);

        System.out.println("服务器已启动！");
        while(true) {
            //2、服务器端ServerSocket对象调用accept方法,获取连接服务器的客户端Socket对象
            Socket client = server.accept();

            //为当前客户端开启一个线程,执行文件下载任务
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //3、获取服务器端的字节输入流对象
                        InputStream netIs = client.getInputStream();

                        byte[] bs = new byte[1024];
                        int len = 0;
                        len = netIs.read(bs);
                        //4、服务器端的字节输入流对象调用read方法,读取客户端请求信息(客户端要下载的文件全路径)
                        String filePath = new String(bs, 0, len);
                        System.out.println("客户端需要下载: "+filePath+" 文件");

                        //5、创建文件字节输入流FileInputStream对象,绑定源标文件(以字节的方式读取客户端要下载的文件)
                        FileInputStream fis = new FileInputStream(filePath);
                        //6、获取服务器端的字节输出流对象
                        OutputStream netOs = client.getOutputStream();
                        //7、循环读(服务器的本地文件)写(客户端)
                        while((len = fis.read(bs))!=-1) {
                            netOs.write(bs,0,len);
                        }

                        //8、服务器端Socket对象调用shutdownOutput方法,通知客户端文件传输完毕
                        client.shutdownOutput();

                        //9、关闭流,释放资源
                        fis.close();
                        netIs.close();
                        netOs.close();
                        client.close();

                        System.out.println("文件传输完毕");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}