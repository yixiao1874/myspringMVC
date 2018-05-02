package com.gtja.myspringmvc.servlet;

import com.gtja.myspringmvc.annotation.*;
import com.gtja.myspringmvc.controller.UserController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="dispatcherServlet",urlPatterns="/*",loadOnStartup = 1,
initParams = {@WebInitParam(name="base-package",value="com.gtja.myspringmvc")})
public class DispatcherServlet extends HttpServlet {
    //扫描的基包
    private String basePackage = "";
    //基包下面所有的带包路径权限限定类名
    private List<String> packageNames = new ArrayList<>();
    //注解实例化 注解上的名称：实例化对象
    private Map<String,Object> instanceMap = new HashMap<>();
    //带包路径的全限定名称：注解上的名称
    private Map<String,String> nameMap = new HashMap<>();
    //URL地址和方法的映射关系 SpringMVC就是方法调用链
    private Map<String,Method> urlMethodMap = new HashMap<>();
    //method和权限定类名映射关系 主要是为了通过method找到该方法的对象利用反射执行
    private Map<Method,String> methodPackageMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String contentPath = req.getContextPath();
        String path = uri.replaceAll(contentPath,"");

        Method method = urlMethodMap.get(path);
        if (method != null){
            String packageName = methodPackageMap.get(method);
            String controllerName = nameMap.get(packageName);

            UserController userController = (UserController) instanceMap.get(controllerName);
            try{
                method.setAccessible(true);
                method.invoke(userController);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        basePackage = config.getInitParameter("base-package");
        try{
            //1扫描基包得到全部的带包路径权限定名
            scanBasePackage(basePackage);
            //2、把带有@Controller @Service @Repository的实例化放入MAP中key为注解上的名称
            instance(packageNames);
            //3、spring IOC注入
            springIOC();
            //4、完成URL地址与方法的映射关系
            handlerUrlMethodMap();
        }catch (Exception e){
            e.printStackTrace();
        }
        super.init();
    }

    private void handlerUrlMethodMap() throws Exception{
        if (packageNames.size() < 1){
            return;
        }
        for (String string:packageNames){
            Class c = Class.forName(string);
            if (c.isAnnotationPresent(Controller.class)){
                Method[] methods = c.getMethods();
                StringBuffer baseUrl = new StringBuffer();
                if (c.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping requestMapping = (RequestMapping)c.getAnnotation(RequestMapping.class);
                    baseUrl.append(requestMapping.value());
                }
                for (Method method : methods){
                    if (method.isAnnotationPresent(RequestMapping.class)){
                        RequestMapping requestMapping = (RequestMapping)method.getAnnotation(RequestMapping.class);
                        baseUrl.append(requestMapping.value());

                        urlMethodMap.put(baseUrl.toString(),method);
                        methodPackageMap.put(method,string);
                    }
                }
            }
        }
    }

    private void springIOC() throws Exception{
        for (Map.Entry<String,Object> entry:instanceMap.entrySet()){
            Field[] fields = entry.getValue().getClass().getDeclaredFields();

            for (Field field : fields){
                if (field.isAnnotationPresent(Qualifier.class)){
                    String name = field.getAnnotation(Qualifier.class).value();
                    field.setAccessible(true);
                    field.set(entry.getValue(),instanceMap.get(name));
                }
            }
        }
    }

    private void instance(List<String> packageNames) throws Exception{
        if (packageNames.size() < 1){
            return;
        }
        for (String string:packageNames){
            Class c = Class.forName(string);
            if (c.isAnnotationPresent(Controller.class)){
                Controller controller = (Controller) c.getAnnotation(Controller.class);
                String controllerName = controller.value();

                instanceMap.put(controllerName,c.newInstance());
                nameMap.put(string,controllerName);
                System.out.println("Controller: "+string + ",value : " + controller.value());
            }else if (c.isAnnotationPresent(Service.class)){
                Service service = (Service) c.getAnnotation(Service.class);
                String serviceName = service.value();

                instanceMap.put(serviceName,c.newInstance());
                nameMap.put(string,serviceName);
                System.out.println("Service: "+string + ",value : " + service.value());
            }else if (c.isAnnotationPresent(Repository.class)){
                Repository repository = (Repository) c.getAnnotation(Repository.class);
                String repositoryName = repository.value();

                instanceMap.put(repositoryName,c.newInstance());
                nameMap.put(string,repositoryName);
                System.out.println("Repository: "+string + ",value : " + repository.value());
            }

        }
    }

    private void scanBasePackage(String basePackage) {
        //需要对basePackage做转换：将.替换为/
        URL url = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.","/"));
        File basePackageFile = new File(url.getPath());
        System.out.println("scan: " + basePackageFile);
        File[] childFiles = basePackageFile.listFiles();
        for (File file:childFiles){
            if (file.isDirectory()){ //目录继续递归扫描
                scanBasePackage(basePackage + "." + file.getName());
            }else if(file.isFile()){
                packageNames.add(basePackage + "." + file.getName().split("\\.")[0]);
            }
        }
    }
}
