package cn.jarkata.dubbo.ext.facade;

import org.apache.dubbo.common.URL;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class DubboGenericFacadeTest {

    @Test(expected = NullPointerException.class)
    public void testInvoke() {
        DubboGenericFacade dubboGenericFacade = new DubboGenericFacade();
        Object invoked = dubboGenericFacade.invokeMethod(null, new ArrayList<>());
        Assert.assertNotNull(invoked);
    }

    @Test
    public void invokeMethod() {
        URL url = URL.valueOf("dubbo://127.0.0.1:28080?ssl-enabled=Y");
        Assert.assertEquals(url.toFullString(), "dubbo://127.0.0.1:28080?ssl-enabled=Y");
        Assert.assertEquals("Y", url.getParameter("ssl-enabled"));
    }

    @Test
    public void invokeUrl() {
        URL url = URL.valueOf("dubbo://127.0.0.1:28080");
        url = url.addParameter("ssl-enabled", "Y");
        Assert.assertEquals(url.toFullString(), "dubbo://127.0.0.1:28080?ssl-enabled=Y");
        Assert.assertEquals("Y", url.getParameter("ssl-enabled"));
    }
}