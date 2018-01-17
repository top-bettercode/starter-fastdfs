package cn.bestwu.fastdfs;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Peter Wu
 * @since 1.0.0
 */
@ConfigurationProperties("fastdfs")
public class FastdfsProperties {

  /**
   * 连接超时(单位：秒)
   */
  private int connectTimeout = 5;

  /**
   * 网络超时(单位：秒)
   */
  private int networkTimeout = 30;

  private String charset = "UTF-8";

  private int trackerHttpPort = 80;

  private boolean antiStealToken = false;

  private String secretKey = "FastDFS1234567890";

  private String[] trackerServers;

  //--------------------------------------------


  public int getConnectTimeout() {
    return connectTimeout;
  }

  public void setConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public int getNetworkTimeout() {
    return networkTimeout;
  }

  public void setNetworkTimeout(int networkTimeout) {
    this.networkTimeout = networkTimeout;
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public int getTrackerHttpPort() {
    return trackerHttpPort;
  }

  public void setTrackerHttpPort(int trackerHttpPort) {
    this.trackerHttpPort = trackerHttpPort;
  }

  public boolean isAntiStealToken() {
    return antiStealToken;
  }

  public void setAntiStealToken(boolean antiStealToken) {
    this.antiStealToken = antiStealToken;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String[] getTrackerServers() {
    return trackerServers;
  }

  public void setTrackerServers(String[] trackerServers) {
    this.trackerServers = trackerServers;
  }
}
