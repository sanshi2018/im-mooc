package com.lld.im.codec.utils;

import com.alibaba.fastjson.JSONObject;
import com.lld.im.codec.proto.Message;
import com.lld.im.codec.proto.MessageHeader;
import io.netty.buffer.ByteBuf;

/**
 * @author: Chackylee
 * @description: 将ByteBuf转化为Message实体，根据私有协议转换
 *               私有协议规则，
 *               4位表示Command表示消息的开始，
 *               4位表示version
 *               4位表示clientType
 *               4位表示messageType
 *               4位表示appId
 *               4位表示imei长度
 *               imei
 *               4位表示数据长度
 *               data
 *               后续将解码方式加到数据头根据不同的解码方式解码，如pb，json，现在用json字符串
 * @version: 1.0
 */
public class ByteBufToMessageUtils {

    public static Message transition(ByteBuf in){

        /** 获取command*/
        int command = in.readInt();

        /** 获取version*/
        int version = in.readInt();

        /** 获取clientType*/
        int clientType = in.readInt();

        /** 获取clientType*/
        int messageType = in.readInt();

        /** 获取appId*/
        int appId = in.readInt();

        /** 获取imeiLength*/
        int imeiLength = in.readInt();

        /** 获取bodyLen*/
        int bodyLen = in.readInt();

        /**
         * 这段代码通常出现在Netty或类似的网络编程框架中，用于处理接收到的数据。它的含义是：
         * 如果剩余可读取的字节数小于“消息体长度”（bodyLen）加“IMEI号长度”（imeiLength），那么重置读取索引并返回null。
         * 这段代码的意义是确保读取到的数据长度足够解析出一个完整的消息。如果读取的数据不足，就等待下一次读取。
         * 这是因为在网络传输中，一个消息可能被拆成多个数据包传输，因此需要等待足够的数据才能解析出完整的消息。
         * 具体来说，如果当前可读取的字节数小于消息体长度和IMEI号长度的总和，说明当前读取到的数据不足以解析出完整的消息，
         * 因此需要重置读取索引并等待下一次读取。读取索引的重置会将指针重新指向缓冲区的【起始位置】，以便下一次读取可以重新开始。
         * 返回null表示当前没有读取到完整的消息，需要等待更多的数据到达。
         */
        // 处理粘包粘包问题
        if(in.readableBytes() < bodyLen + imeiLength){
            // 重置读指针
            in.resetReaderIndex();
            return null;
        }

        byte [] imeiData = new byte[imeiLength];
        in.readBytes(imeiData);
        String imei = new String(imeiData);

        byte [] bodyData = new byte[bodyLen];
        in.readBytes(bodyData);


        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setAppId(appId);
        messageHeader.setClientType(clientType);
        messageHeader.setCommand(command);
        messageHeader.setLength(bodyLen);
        messageHeader.setVersion(version);
        messageHeader.setMessageType(messageType);
        messageHeader.setImei(imei);

        Message message = new Message();
        message.setMessageHeader(messageHeader);

        if(messageType == 0x0){
            String body = new String(bodyData);
            JSONObject parse = (JSONObject) JSONObject.parse(body);
            message.setMessagePack(parse);
        }

        in.markReaderIndex();
        return message;
    }

}
