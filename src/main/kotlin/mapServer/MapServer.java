package mapServer;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

public class MapServer extends HttpServer {
    public MapServer(){
        createContext("/getClosestNode", exchange -> {
            InputStream input = exchange.getRequestBody();
            OutputStream output = exchange.getResponseBody();

            Map<String,String> parms = queryToMap(exchange.getRequestURI().getQuery());
            double lon = Double.parseDouble(parms.get("lon"));
            double lat = Double.parseDouble(parms.get("lat"));

            output.write(("hi lon: " + parms.get("lon") + "lat: " +  parms.get("lat")).getBytes(StandardCharsets.UTF_8));
            output.close();

            create(new InetSocketAddress(8080), 0);
            start();
        });
        
    }
    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }
    @Override
    public void bind(InetSocketAddress addr, int backlog) throws IOException {

    }

    @Override
    public void start() {

    }

    @Override
    public void setExecutor(Executor executor) {

    }

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void stop(int delay) {

    }

    @Override
    public HttpContext createContext(String path, HttpHandler handler) {
        return null;
    }

    @Override
    public HttpContext createContext(String path) {
        return null;
    }

    @Override
    public void removeContext(String path) throws IllegalArgumentException {

    }

    @Override
    public void removeContext(HttpContext context) {

    }

    @Override
    public InetSocketAddress getAddress() {
        return null;
    }

}
