import com.andy.spring.util.encrypt.HmacHashUtil;

/**
 * 功能描述
 *
 * @author 庞先海 2020-04-11
 */
public class A {

    public static void main(String... args) {
        String password = HmacHashUtil.hmacSha256Hash("123456", "17780583960");
        System.out.println(password);
    }
}
