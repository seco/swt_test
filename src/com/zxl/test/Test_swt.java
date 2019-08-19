package com.zxl.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class Test_swt {
    public static void main(String [] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        initShell(shell);
        shell.open();
        // 开始事件处理循环，直到用户关闭窗口
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();

    }

    private static void initShell(Shell shell) {
        //为Shell设置布局对象
        GridLayout gShellLay = new GridLayout();
        shell.setLayout(gShellLay);
        //构造一个Composite构件作为文本框和按键的容器
        Composite panel = new Composite(shell, SWT.NONE);
        //为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
        GridData gPanelData = new
                GridData(GridData.GRAB_HORIZONTAL|GridData.GRAB_VERTICAL|GridData.FILL_BOTH);
        panel.setLayoutData(gPanelData);
        //为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
        GridLayout gPanelLay = new GridLayout();
        panel.setLayout(gPanelLay);
        //为Panel生成一个背景色
        final Color bkColor = new Color(Display.getCurrent(),200,0,200);
        panel.setBackground(bkColor);
        //生成文本框
        final Text text = new Text(panel,SWT.MULTI|SWT.WRAP);
        //为文本框指定一个布局结构对象，这里让文本框尽可能的占满Panel的空间。
        GridData gTextData = new
                GridData(GridData.GRAB_HORIZONTAL|GridData.GRAB_VERTICAL|GridData.FILL_BOTH);
        text.setLayoutData(gTextData);
        //生成按键
        Button butt = new Button(panel,SWT.PUSH);
        butt.setText("Push");
        //为按键指定鼠标事件
        butt.addMouseListener(new MouseAdapter(){
            public void mouseDown(MouseEvent e){
                //当用户点击按键的时候，显示信息
                text.append("Hello SWT\n");
            }
        });
        //当主窗口关闭时，会触发DisposeListener。这里用来释放Panel的背景色。
        shell.addDisposeListener(new DisposeListener(){
            public void widgetDisposed(DisposeEvent e) {
                bkColor.dispose();
            }
        });
    }
}
