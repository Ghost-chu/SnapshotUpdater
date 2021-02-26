import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import sun.management.Agent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class Main {
    static CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    static File versionFile = new File("version");
    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("Please wait... SnapshotUpdater is checking for update...");
        if(!versionFile.exists()){
            versionFile.createNewFile();
        }
        if(!isLatest()){
            String json = IOUtils.toString(httpClient.execute(new HttpGet("https://bmclapi2.bangbang93.com/mc/game/version_manifest.json")).getEntity().getContent(),StandardCharsets.UTF_8);
            VersionBean versionBean = new Gson().fromJson(json,VersionBean.class);
            System.out.println("Updating server to version "+versionBean.getLatest().getSnapshot());
            update(versionBean.getLatest().getSnapshot());
            Files.write(versionFile.toPath(),versionBean.getLatest().getSnapshot().getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("Selected snapshot: "+ new String(Files.readAllBytes(versionFile.toPath()), StandardCharsets.UTF_8)+", starting...");
        boot(args);
        Thread thread = new Thread(() -> {
            while (true){
                try {
                    System.out.println("Checking for snapshot updates...");
                    if(!isLatest()){
                        System.out.println("Found update, restarting...");
                        System.exit(0);
                    }else{
                        System.out.println("Snapshot server already up-to-date");
                    }
                    Thread.sleep(1000*600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

    }

    private static Instrumentation inst = null;

    public static void premain(final String agentArgs, final Instrumentation inst) {
       Main.inst = inst;
    }

    public static void agentmain(final String agentArgs, final Instrumentation inst) {
        Main.inst = inst;
    }


    @SneakyThrows
    private static boolean isLatest(){
        CloseableHttpResponse entity = httpClient.execute(new HttpGet("https://bmclapi2.bangbang93.com/mc/game/version_manifest.json"));
        if(entity.getStatusLine().getStatusCode() != 200 && entity.getStatusLine().getStatusCode() != 302){
            return true;
        }
        String json = IOUtils.toString(entity.getEntity().getContent(),StandardCharsets.UTF_8);
        VersionBean versionBean = new Gson().fromJson(json,VersionBean.class);
        String localVer = new String(Files.readAllBytes(versionFile.toPath()), StandardCharsets.UTF_8);
        return localVer.equals(versionBean.getLatest().getSnapshot()) && new File("server.jar").exists();
    }

    private static void boot(String[] args) {
        final Method mainMethod;
        {
            final Path paperJar = new File("server.jar").toPath();
            final String main = getMainClass(paperJar);
            mainMethod = getMainMethod(paperJar, main);
        }

        // By making sure there are no other variables in scope when we run mainMethod.invoke we allow the JVM to
        // GC any objects allocated during the downloading + patching process, minimizing paperclip's overhead as
        // much as possible
        try {
            mainMethod.invoke(null, new Object[] {args});
        } catch (final IllegalAccessException | InvocationTargetException e) {
            System.err.println("Error while running patched jar");
            e.printStackTrace();
            System.exit(1);
        }
    }

    @SneakyThrows
    private static void update(String version) {
        File serverJar = new File("server.jar");
        serverJar.delete();
        Files.copy(httpClient.execute(new HttpGet("https://bmclapi2.bangbang93.com/version/"+version+"/server")).getEntity().getContent(), serverJar.toPath());
    }



    static void addToClassPathJ8(final Path paperJar) {
        final ClassLoader loader = ClassLoader.getSystemClassLoader();
        if (!(loader instanceof URLClassLoader)) {
            throw new RuntimeException("System ClassLoader is not URLClassLoader");
        }
        try {
            final Method addURL = getAddMethod(loader);
            if (addURL == null) {
                System.err.println("Unable to find method to add Server jar to System ClassLoader");
                System.exit(1);
            }
            addURL.setAccessible(true);
            addURL.invoke(loader, paperJar.toUri().toURL());
        } catch (final IllegalAccessException | InvocationTargetException | MalformedURLException e) {
            System.err.println("Unable to add Server Jar to System ClassLoader");
            e.printStackTrace();
            System.exit(1);
        }
    }

    @SuppressWarnings("unused") // This class replaces the Agent class in the java8 module when run on Java9+
    static void addToClassPathJ9(final Path paperJar) {
        if (inst == null) {
            System.err.println("Unable to retrieve Instrumentation API to add Paper jar to classpath. If you're " +
                    "running paperclip without -jar then you also need to include the -javaagent:<paperclip_jar> JVM " +
                    "command line option.");
            System.exit(1);
            return;
        }
        try {
            inst.appendToSystemClassLoaderSearch(new JarFile(paperJar.toFile()));
            inst = null;
        } catch (final IOException e) {
            System.err.println("Failed to add Paper jar to ClassPath");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Method getAddMethod(final Object o) {
        Class<?> clazz = o.getClass();
        Method m = null;
        while (m == null) {
            try {
                m = clazz.getDeclaredMethod("addURL", URL.class);
            } catch (final NoSuchMethodException ignored) {
                clazz = clazz.getSuperclass();
                if (clazz == null) {
                    return null;
                }
            }
        }
        return m;
    }
    private static String getMainClass(final Path paperJar) {
        try (
                final InputStream is = new BufferedInputStream(Files.newInputStream(paperJar));
                final JarInputStream js = new JarInputStream(is)
        ) {
            return js.getManifest().getMainAttributes().getValue("Main-Class");
        } catch (final IOException e) {
            System.err.println("Error reading from server jar");
            e.printStackTrace();
            System.exit(1);
            throw new InternalError();
        }
    }

    private static Method getMainMethod(final Path paperJar, final String mainClass) {
        try {
            addToClassPathJ8(paperJar);
        }catch (Exception exception){
            System.out.println("Java9 detected, use new API to add server jar to class path.");
            addToClassPathJ9(paperJar);
        }
        try {
            final Class<?> cls = Class.forName(mainClass, true, ClassLoader.getSystemClassLoader());
            return cls.getMethod("main", String[].class);
        } catch (final NoSuchMethodException | ClassNotFoundException e) {
            System.err.println("Failed to find main method in server jar");
            e.printStackTrace();
            System.exit(1);
            throw new InternalError();
        }
    }

}
