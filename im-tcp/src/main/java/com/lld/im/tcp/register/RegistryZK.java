package com.lld.im.tcp.register;

import com.lld.im.codec.config.BootstrapConfig;
import com.lld.im.common.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
public class RegistryZK implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(RegistryZK.class);

    private ZKit zKit;

    private String ip;

    private BootstrapConfig.TcpConfig tcpConfig;

    public RegistryZK(ZKit zKit, String ip, BootstrapConfig.TcpConfig tcpConfig) {
        this.zKit = zKit;
        this.ip = ip;
        this.tcpConfig = tcpConfig;
    }

    @Override
    public void run() {
        // 創建根節點
        zKit.createRootNode();
        // 創建tcp節點
        String tcpPath = Constants.ImCoreZkRoot + Constants.ImCoreZkRootTcp + "/" + ip + ":" + tcpConfig.getTcpPort();
        zKit.createNode(tcpPath);

        logger.info("Registry zookeeper tcpPath success, msg=[{}]", tcpPath);
        // 創建web節點
        String webPath =
                Constants.ImCoreZkRoot + Constants.ImCoreZkRootWeb + "/" + ip + ":" + tcpConfig.getWebSocketPort();
        zKit.createNode(webPath);
        logger.info("Registry zookeeper webPath success, msg=[{}]", tcpPath);

    }
}
