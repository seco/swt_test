package com.zxl.test;

import com.zxl.test.data.BarrageMsgData;
import com.zxl.test.data.ChooseResultData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class BarrageMsgUtil {
    public static boolean isLoop = false;

    public static Socket mSocket = null;
    public static InputStream mInputStream = null;
    public static OutputStream mOutputStream = null;

    public static Map<String, ChooseResultData> mChooseResultDataMap = new HashMap<>();

    public static void start(String roomNum, Display display, Composite panel, Table table) throws IOException, InterruptedException {
        System.out.println("roomNum--->" + roomNum);//85894
        if (roomNum == null || roomNum.length() <= 0) {
            return;
        }
        isLoop = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket("openbarrage.douyutv.com", 8601);
                    //发送登录请求(登入85894房间)
                    String loginCMD = "type@=loginreq/roomid@=" + roomNum + "/";
                    send(loginCMD, mSocket);

                    //读取登录请求消息
                    byte[] bytes = read(mSocket);
                    String msg = new String(Arrays.copyOfRange(bytes, 0, bytes.length));
                    System.out.println(msg);


                    //加入弹幕分组开始接收弹幕
                    String joinGroupCMD = "type@=joingroup/rid@=9999/gid@=-9999/";
                    send(joinGroupCMD, mSocket);

                    int i = 1;
                    int msgIndex = 1;
                    //循环读取弹幕消息开始
                    while (isLoop) {

                        byte[] msgBytes = read(mSocket);
                        String s = new String(Arrays.copyOfRange(msgBytes, 0, msgBytes.length));
                        //System.out.println(s);

                        BarrageMsgData barrageMsgData = new BarrageMsgData();
                        String parseArray[] = s.split("/");
                        for (String parseItem : parseArray) {
                            //System.out.println("parseItem--->" + parseItem);

                            if (parseItem.startsWith("nn@=")) {
                                String nickName = parseItem.split("@=")[1];
                                //System.out.println("nickName--->" + nickName);

                                barrageMsgData.mNickName = nickName;
                            }
                            if (parseItem.startsWith("txt@=")) {
                                String content = parseItem.split("@=")[1];
                                //System.out.println("content--->" + content);

                                barrageMsgData.mContent = content;
                            }
                        }

                        if (barrageMsgData.mNickName != null && barrageMsgData.mNickName.length() > 0 && barrageMsgData.mContent != null && barrageMsgData.mContent.length() > 0) {

                            ChooseResultData chooseResultData = mChooseResultDataMap.get(barrageMsgData.mContent);
                            if (chooseResultData != null && !chooseResultData.mNickNameList.contains(barrageMsgData.mNickName)) {
                                chooseResultData.mChooseCount++;
                                chooseResultData.mNickNameList.add(barrageMsgData.mNickName);

                                int chooseCount = chooseResultData.mChooseCount;
                                display.asyncExec(
                                        new Runnable() {
                                            public void run() {
                                                System.out.println("chooseResultData add--->" + chooseResultData.mChooseColumnIndex + "--->" + chooseCount);
                                                chooseResultData.mTableItem.setText(chooseResultData.mChooseColumnIndex, String.valueOf(chooseCount));
                                            }
                                        });
                            }

                            int finalMsgIndex = msgIndex;
                            display.asyncExec(
                                    new Runnable() {
                                        public void run() {
                                            //System.out.println("add TableItem");

                                            Rectangle area = panel.getClientArea();
                                            Point preferredSize = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                                            int width = area.width - 2 * table.getBorderWidth();
                                            table.setSize(area.width, area.height);
                                            table.getColumn(0).setWidth(width / 4);
                                            table.getColumn(1).setWidth(width / 4);

                                            TableEditor tableEditor = new TableEditor(table);
                                            TableEditor tableEditor2 = new TableEditor(table);
                                            tableEditor.grabHorizontal = true;
                                            tableEditor2.grabHorizontal = true;

                                            TableItem ti = new TableItem(table, SWT.NONE);
                                            ti.setText(0, finalMsgIndex + "." + barrageMsgData.mNickName);
                                            ti.setText(1, barrageMsgData.mContent);

//                                            ScrollBar scrollBar = table.getVerticalBar();
//                                            scrollBar.setSelection(scrollBar.getMaximum());
                                        }
                                    }
                            );

                            msgIndex++;
                        }

                        Thread.sleep(1);
                        i++;
                        if (i % 45 == 0) {
                            send("type@=mrkl/", mSocket);
                        }
                    }
                    //关闭链接
                    //socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void stop() {
        isLoop = false;
        if (mSocket != null) {
            try {
                if (mOutputStream != null) {
                    mOutputStream.close();
                }
                if (mInputStream != null) {
                    mInputStream.close();
                }
                send("type@=logout/", mSocket);
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息
     *
     * @param content
     */
    public static void send(String content, Socket socket) {
        try {
            //计算消息长度 = 消息长度(4) + 消息类型(4) + 真实消息内容长度 + 结尾标识长度(1)
            int contenLeng = 4 + 4 + content.length() + 1;
            //小端模式转换init (长度1)
            byte[] contenLeng1 = intToBytesLittle(contenLeng);
            //小端模式转换init (长度2)
            byte[] contenLeng2 = intToBytesLittle(contenLeng);
            //小端模式转换init (消息类型) (689:客户端发送给弹幕服务器的文本格式数据)
            byte[] msgType = intToBytesLittle(689);
            //标识数据结尾
            int end = 0;

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            //写入长度1
            byteArray.write(contenLeng1);
            //写入长度2（与长度1相同）
            byteArray.write(contenLeng2);
            //写入消息类型
            byteArray.write(msgType);
            //写入消息内容
            byteArray.write(content.getBytes("ISO-8859-1"));
            //写入数据结尾标识
            byteArray.write(end);

            //发送数据
            mOutputStream = socket.getOutputStream();
            mOutputStream.write(byteArray.toByteArray());
            mOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取消息
     *
     * @return
     */
    public static byte[] read(Socket socket) {
        try {
            mInputStream = socket.getInputStream();
            //下条信息的长度
            int contentLen = 0;

            //读取前4个字节，得到数据长度
            byte[] bytes1 = readStream(mInputStream, 0, 4);
            contentLen = bytesToIntLittle(bytes1, 0); //用小端模式转换byte数组为
            //System.out.println("数据长度1:" + contentLen);

            //继续读取4个字节，得到第二个 数据长度
            byte[] bytes2 = readStream(mInputStream, 0, 4);
            int contentLen2 = bytesToIntLittle(bytes2, 0);
            //System.out.println("数据长度2:" + contentLen2);

            //再次读取4个字节，得到消息类型
            byte[] bytes3 = readStream(mInputStream, 0, 4);
            //将小端整数转换为大端整数
            int msgType = bytesToIntLittle(bytes3, 0);
            //System.out.println("消息类型:" + msgType);

            if (contentLen < 8) {
                return new ByteArrayOutputStream().toByteArray();
            }
            //
            contentLen = contentLen - 8;
            //继续读取真正的消息内容
            int len = 0;        //本次读取数据长度
            int readLen = 0;    //已读数据长度
            byte[] bytes = new byte[contentLen];
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            while ((len = mInputStream.read(bytes, 0, contentLen - readLen)) != -1) {
                byteArray.write(bytes, 0, len);
                readLen += len;
                if (readLen == contentLen) {
                    break;
                }
            }

            return byteArray.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayOutputStream().toByteArray();
    }

    /**
     * 计算消息体长度
     */
    private static int calcMessageLength(String content) {
        return 4 + 4 + (content == null ? 0 : content.length()) + 1;
    }


    /**
     * 以小端模式将int转成byte[]
     *
     * @param value
     * @return
     */
    public static byte[] intToBytesLittle(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }


    /**
     * 以小端模式将byte[]转成int
     */
    public static int bytesToIntLittle(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset + 1] & 0xFF) << 8)
                | ((src[offset + 2] & 0xFF) << 16)
                | ((src[offset + 3] & 0xFF) << 24));
        return value;
    }


    /**
     * 从流中读取数据
     *
     * @param inputStream
     * @param off
     * @param len
     * @return
     * @throws IOException
     */
    public static byte[] readStream(InputStream inputStream, int off, int len) throws IOException {
        byte[] bytes = new byte[len];
        inputStream.read(bytes, 0, 4);
        return bytes;
    }
}
