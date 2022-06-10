package co.edu.unbosque.wsrestnear.dtos;

import java.util.Date;

public class WalletHistory {


    private int wallet_id;
    private String username;
    private String walletType;
    private long fcoins;
    private String art;
    private Date registeredAt;

    private String originProduct;

    public WalletHistory() {
    }

    //Método constructor de la clase FCoins y inicializa las variables declaradas con las pasadas por parámetros
    public WalletHistory(int wallet_id, String username, String walletType, long fcoins, String art, Date registeredAt, String origin_product) {
        this.wallet_id = wallet_id;
        this.username = username;
        this.walletType = walletType;
        this.fcoins = fcoins;
        this.art = art;
        this.registeredAt = registeredAt;
        this.originProduct = origin_product;
    }

    public int getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(int wallet_id) {
        this.wallet_id = wallet_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public long getFcoins() {
        return fcoins;
    }

    public void setFcoins(long fcoins) {
        this.fcoins = fcoins;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public void setOriginProduct(String originProduct) {
        this.originProduct = originProduct;
    }

    public String getOriginProduct() {
        return originProduct;
    }

    @Override
    public String toString() {
        return "WalletHistory{" +
                "wallet_id=" + wallet_id +
                ", username='" + username + '\'' +
                ", walletType='" + walletType + '\'' +
                ", fcoins=" + fcoins +
                ", art='" + art + '\'' +
                ", registeredAt=" + registeredAt +
                ", originProduct='" + originProduct + '\'' +
                '}';
    }
}
