import me.wangcai.gypaybukkit.service.IPayService;
import me.wangcai.gypaybukkit.service.impl.PayServiceImpl;
import org.junit.Test;

public class Main {
    @Test
    public void Test(){
        IPayService payService = new PayServiceImpl();
        payService.getUnShipOrder();
    }
}
