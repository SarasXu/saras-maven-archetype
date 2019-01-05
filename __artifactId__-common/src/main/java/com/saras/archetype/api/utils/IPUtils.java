package com.saras.archetype.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:27 创建
 */
public class IPUtils {
    private static final Logger logger = LoggerFactory.getLogger(IPUtils.class);
    private static final String LOOP_BACK = "127.0.0.1";

    /**
     * 获取所有本机ipv4地址
     *
     * @return
     */
    public static Collection<InetAddress> getAllHostIPV4Address() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            Collection<InetAddress> addresses = new ArrayList<InetAddress>();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address) {
                        addresses.add(inetAddress);
                    }
                }
            }
            return addresses;
        } catch (SocketException e) {
            logger.error("获取ip地址失败", e);
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    /**
     * 获取所有本机非loopback IPV4地址
     *
     * @return
     */
    public static Collection<String> getAllNoLoopbackIPV4Addresses() {
        Collection<String> noLoopbackAddresses = new ArrayList<String>();
        Collection<InetAddress> allInetAddresses = getAllHostIPV4Address();

        for (InetAddress address : allInetAddresses) {
            if (!address.isLoopbackAddress()) {
                noLoopbackAddresses.add(address.getHostAddress());
            }
        }

        return noLoopbackAddresses;
    }

    /**
     * 获取ipv4地址，如果有多个网卡的情况，获取第一个非loopback ip地址
     *
     * @return
     */
    public static String getFirstNoLoopbackIPV4Address() {
        Collection<String> allNoLoopbackAddresses;
        try {
            allNoLoopbackAddresses = getAllNoLoopbackIPV4Addresses();
        } catch (Exception e) {
            logger.error("获取ip失败", e);
            return LOOP_BACK;
        }
        if (allNoLoopbackAddresses.isEmpty()) {
            return LOOP_BACK;
        }

        return allNoLoopbackAddresses.iterator().next();
    }

}
