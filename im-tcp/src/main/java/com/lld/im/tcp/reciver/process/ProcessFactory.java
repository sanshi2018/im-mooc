package com.lld.im.tcp.reciver.process;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
public class ProcessFactory {

    private static BaseProcess defaultProcess;

    static {
        defaultProcess = new BaseProcess() {
            @Override
            public void processBefore() {

            }

            @Override
            public void processAfter() {

            }
        };
    }

    public static BaseProcess getMessageProcess(Integer command) {
//        if (command == "usercom") {
//            return userProcess;
//        }
        return defaultProcess;
    }

}
