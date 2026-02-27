
package soap;

import javax.xml.ws.Endpoint;


public class ServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:18020/ws/users",new DefaultUserImplService());
    }
}
