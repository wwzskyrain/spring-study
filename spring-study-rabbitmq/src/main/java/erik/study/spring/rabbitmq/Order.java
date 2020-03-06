package erik.study.spring.rabbitmq;

import lombok.Data;

/**
 * @author erik.wang
 * @date 2020-03-06 19:23
 * @description
 */
@Data
public class Order {

    private Long id;
    private String orderNo;
    private Long buyerId;
    private String productName;

}
