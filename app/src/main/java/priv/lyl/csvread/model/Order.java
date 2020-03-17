package priv.lyl.csvread.model;

public class Order {
    //订单编号 用户Id,支付金额,商品名称,商品代码,数量,收货人,联系手机,收货地址,是否分销商订单
    String orderNum;
    // 用户Id
    String userId;
    //支付金额
    String payMoney;
    //商品名称
    String tradeName;
    //商品代码
    String tradeCode;
    //数量
    String num;
    //收货人
    String consignee;
    //联系手机
    String phone;
    //收货地址
    String receivingAddress;
    //是否分销商订单
    String isDistribution;

    public Order() {
    }

    public Order(String orderNum, String userId, String payMoney, String tradeName, String tradeCode, String num, String consignee, String phone, String receivingAddress, String isDistribution) {
        this.orderNum = orderNum;
        this.userId = userId;
        this.payMoney = payMoney;
        this.tradeName = tradeName;
        this.tradeCode = tradeCode;
        this.num = num;
        this.consignee = consignee;
        this.phone = phone;
        this.receivingAddress = receivingAddress;
        this.isDistribution = isDistribution;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public String getIsDistribution() {
        return isDistribution;
    }

    public void setIsDistribution(String isDistribution) {
        this.isDistribution = isDistribution;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNum='" + orderNum + '\'' +
                ", userId='" + userId + '\'' +
                ", payMoney='" + payMoney + '\'' +
                ", tradeName='" + tradeName + '\'' +
                ", tradeCode='" + tradeCode + '\'' +
                ", num='" + num + '\'' +
                ", consignee='" + consignee + '\'' +
                ", phone='" + phone + '\'' +
                ", receivingAddress='" + receivingAddress + '\'' +
                ", isDistribution='" + isDistribution + '\'' +
                '}';
    }
}
