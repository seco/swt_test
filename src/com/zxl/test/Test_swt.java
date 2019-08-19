package com.zxl.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class Test_swt {
    private static Display mDisplay;

    public static void main(String[] args) {
        mDisplay = new Display();
        Shell shell = new Shell(mDisplay);
        initList(shell);

        shell.layout();
        shell.open();

        // 开始事件处理循环，直到用户关闭窗口
        while (!shell.isDisposed()) {
            if (!mDisplay.readAndDispatch())
                mDisplay.sleep();
        }
        mDisplay.dispose();

    }

//    private static void initShell(Shell shell) {
//        //为Shell设置布局对象
//        GridLayout gShellLay = new GridLayout();
//        shell.setLayout(gShellLay);
//        //构造一个Composite构件作为文本框和按键的容器
//        Composite panel = new Composite(shell, SWT.NONE);
//        //为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
//        GridData gPanelData = new
//                GridData(GridData.GRAB_HORIZONTAL|GridData.GRAB_VERTICAL|GridData.FILL_BOTH);
//        panel.setLayoutData(gPanelData);
//        //为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
//        GridLayout gPanelLay = new GridLayout();
//        panel.setLayout(gPanelLay);
//        //为Panel生成一个背景色
//        final Color bkColor = new Color(Display.getCurrent(),200,0,200);
//        panel.setBackground(bkColor);
//        //生成文本框
//        final Text text = new Text(panel,SWT.MULTI|SWT.WRAP);
//        //为文本框指定一个布局结构对象，这里让文本框尽可能的占满Panel的空间。
//        GridData gTextData = new
//                GridData(GridData.GRAB_HORIZONTAL|GridData.GRAB_VERTICAL|GridData.FILL_BOTH);
//        text.setLayoutData(gTextData);
//        //生成按键
//        Button butt = new Button(panel,SWT.PUSH);
//        butt.setText("Push");
//        //为按键指定鼠标事件
//        butt.addMouseListener(new MouseAdapter(){
//            public void mouseDown(MouseEvent e){
//                //当用户点击按键的时候，显示信息
//                text.append("Hello SWT\n");
//            }
//        });
//        //当主窗口关闭时，会触发DisposeListener。这里用来释放Panel的背景色。
//        shell.addDisposeListener(new DisposeListener(){
//            public void widgetDisposed(DisposeEvent e) {
//                bkColor.dispose();
//            }
//        });
//
//        try {
//            BarrageMsgUtil.start(mDisplay, text);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    private static void initList(Shell shell) {
        final List list = new List(shell, SWT.BORDER | SWT.MULTI);
        list.setBounds(16, 11, 100, 45);

        // 设值按钮
        Button setButton = new Button(shell, SWT.NONE);
        setButton.setBounds(17, 65, 100, 25);
        setButton.setText("设值");
        setButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                list.removeAll(); // 先清空combo，以防多次按下“设值”按钮时出现BUG
                list.add("语文"); // 加入Combo显示值
                list.add("数学");
                list.add("政治");
                list.setData("语文", "YW");// 设置显示值的代表值
                list.setData("数学", "SX");
                list.setData("政治", "ZZ");
                list.select(new int[]{0, 2}); // 设置第一、三项为选择项
            }
        });

        // 取值按钮
        Button getButton = new Button(shell, SWT.NONE);
        getButton.setBounds(136, 66, 100, 25);
        getButton.setText("取值");
        getButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                StringBuilder bud = new StringBuilder();
                for (String s : list.getSelection())// getSelection返回所有选择项组成的String数组
                    bud.append(s).append(list.getData(s)).append(", ");
            }
        });
    }


}
