package root.mybatis.mapper.bean;

public class JmsToCalc {
    private Integer id;

    private String systemFlag;

    private Integer orderCatalog;

    private Long orderId;

    private String orderNo;

    private String gfuserFromCode;

    private String gfuserToCode;

    private String paymentTerm;

    private String startAccountId;

    private String endAccountId;

    private Integer systemFrom;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag;
    }

    public Integer getOrderCatalog() {
        return orderCatalog;
    }

    public void setOrderCatalog(Integer orderCatalog) {
        this.orderCatalog = orderCatalog;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGfuserFromCode() {
        return gfuserFromCode;
    }

    public void setGfuserFromCode(String gfuserFromCode) {
        this.gfuserFromCode = gfuserFromCode;
    }

    public String getGfuserToCode() {
        return gfuserToCode;
    }

    public void setGfuserToCode(String gfuserToCode) {
        this.gfuserToCode = gfuserToCode;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getStartAccountId() {
        return startAccountId;
    }

    public void setStartAccountId(String startAccountId) {
        this.startAccountId = startAccountId;
    }

    public String getEndAccountId() {
        return endAccountId;
    }

    public void setEndAccountId(String endAccountId) {
        this.endAccountId = endAccountId;
    }

    public Integer getSystemFrom() {
        return systemFrom;
    }

    public void setSystemFrom(Integer systemFrom) {
        this.systemFrom = systemFrom;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}