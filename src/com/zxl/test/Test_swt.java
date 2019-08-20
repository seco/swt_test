package com.zxl.test;

import com.zxl.test.data.ChooseResultData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test_swt {
    public static Map<String, ChooseResultData> mChooseResultDataMap = new HashMap<>();

    private static Display mDisplay;

    public static void main(String[] args) {
        mDisplay = new Display();
        Shell shell = new Shell(mDisplay);

        //为Shell设置布局对象
        GridLayout gShellLay = new GridLayout();
        //gShellLay.numColumns = 3;
        shell.setLayout(gShellLay);

        initTable(shell);
        initTable2(shell);

        shell.layout();
        shell.open();

        // 开始事件处理循环，直到用户关闭窗口
        while (!shell.isDisposed()) {
            if (!mDisplay.readAndDispatch())
                mDisplay.sleep();
        }
        mDisplay.dispose();

        BarrageMsgUtil.isLoop = false;
        System.out.println("zxl--->main--->isLoop--->" + BarrageMsgUtil.isLoop);
    }

    private static void initShell(Shell shell) {
        //为Shell设置布局对象
        GridLayout gShellLay = new GridLayout();
        shell.setLayout(gShellLay);
        //构造一个Composite构件作为文本框和按键的容器
        Composite panel = new Composite(shell, SWT.NONE);
        //为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
        GridData gPanelData = new
                GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
        panel.setLayoutData(gPanelData);
        //为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
        GridLayout gPanelLay = new GridLayout();
        panel.setLayout(gPanelLay);
        //为Panel生成一个背景色
        final Color bkColor = new Color(Display.getCurrent(), 200, 0, 200);
        panel.setBackground(bkColor);
        //生成文本框
        final Text text = new Text(panel, SWT.MULTI | SWT.WRAP);
        //为文本框指定一个布局结构对象，这里让文本框尽可能的占满Panel的空间。
        GridData gTextData = new
                GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
        text.setLayoutData(gTextData);
        //生成按键
        Button butt = new Button(panel, SWT.PUSH);
        butt.setText("Push");
        //为按键指定鼠标事件
        butt.addMouseListener(new MouseAdapter() {
            public void mouseDown(MouseEvent e) {
                //当用户点击按键的时候，显示信息
                text.append("Hello SWT\n");
            }
        });
        //当主窗口关闭时，会触发DisposeListener。这里用来释放Panel的背景色。
        shell.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                bkColor.dispose();
            }
        });
    }

    private static void initTable(Shell shell) {
        //构造一个Composite构件作为文本框和按键的容器
        Composite panel = new Composite(shell, SWT.NONE);
        //为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
        GridData gPanelData = new
                GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
        panel.setLayoutData(gPanelData);
        //为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
        GridLayout gPanelLay = new GridLayout();
        panel.setLayout(gPanelLay);
        //为Panel生成一个背景色
        final Color bkColor = new Color(Display.getCurrent(), 200, 0, 200);
        panel.setBackground(bkColor);
        //生成文本框
        //final Text text = new Text(panel, SWT.MULTI | SWT.WRAP);
        //为文本框指定一个布局结构对象，这里让文本框尽可能的占满Panel的空间。
        GridData gTextData = new
                GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
        //text.setLayoutData(gTextData);


        final Table table = new Table(panel, SWT.BORDER | SWT.FULL_SELECTION);
        table.setLayoutData(gTextData);
        table.setHeaderVisible(true);
        //table.setFont(SWTResourceManager.getFont("幼圆", 9, SWT.NORMAL));

        table.setLinesVisible(true);

        TableColumn tableColumn0 = new TableColumn(table, SWT.CENTER);
        tableColumn0.setText("昵称");

        TableColumn tableColumn1 = new TableColumn(table, SWT.CENTER);
        tableColumn1.setText("弹幕");
    }

    private static void initTable2(Shell shell) {
        //构造一个Composite构件作为文本框和按键的容器
        Composite panel = new Composite(shell, SWT.NONE);
        //为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
        GridData gPanelData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
        panel.setLayoutData(gPanelData);
        //为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
        GridLayout gPanelLay = new GridLayout();
        panel.setLayout(gPanelLay);
        //为Panel生成一个背景色
        final Color bkColor = new Color(Display.getCurrent(), 200, 200, 0);
        panel.setBackground(bkColor);
        //为文本框指定一个布局结构对象，这里让文本框尽可能的占满Panel的空间。
        GridData gTextData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
        final Table table = new Table(panel, SWT.BORDER | SWT.FULL_SELECTION);
        table.setLayoutData(gTextData);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

//        TableColumn tableColumn0 = new TableColumn(table, SWT.CENTER);
//        tableColumn0.setText("1");
//        tableColumn0.setWidth(100);

        //构造一个Composite构件作为文本框和按键的容器
        Composite panel2 = new Composite(shell, SWT.NONE);
        //为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
        GridData gPanelData2 = new GridData(GridData.FILL_HORIZONTAL);
        panel2.setLayoutData(gPanelData2);
        //为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
        GridLayout gPanelLay2 = new GridLayout();
        gPanelLay2.numColumns = 3;
        panel2.setLayout(gPanelLay2);
        //为Panel生成一个背景色
        final Color bkColor2 = new Color(Display.getCurrent(), 0, 200, 200);
        panel2.setBackground(bkColor2);

        Text editChooseText = new Text(panel2, SWT.SHADOW_IN);
        editChooseText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        editChooseText.addTraverseListener(new TraverseListener() {
            public void keyTraversed(TraverseEvent e) {
                if (e.keyCode == 13) {
                    // e.detail = SWT.TRAVERSE_TAB_NEXT;
                    e.doit = true;

                    addChooseColumn(table, editChooseText);
                }
            }
        });

        Button addChooseBtn = new Button(panel2, SWT.NONE);
        addChooseBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                addChooseColumn(table, editChooseText);


//                TableItem ti = new TableItem(table, SWT.NONE);
//                ti.setText(0, "aaa");
//                ti.setText(1, "bbb");
//                TableEditor tableEditor = new TableEditor(table);
//                tableEditor.grabHorizontal = true;
//
//                Button chkboxApp = new Button(table, SWT.CHECK | SWT.CENTER);
//                //chkboxApp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
//                tableEditor.setEditor(chkboxApp, ti, 2);
//                TableEditor tableEditor2 = new TableEditor(table);
//                tableEditor2.grabHorizontal = true;
//
//                Button chkboxApp2 = new Button(table, SWT.CHECK | SWT.CENTER);
//                //chkboxApp2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
//                tableEditor2.setEditor(chkboxApp2, ti, 3);

            }
        });
        addChooseBtn.setBounds(132, 163, 61, 22);
        addChooseBtn.setText("添加选项");

        //构造一个Composite构件作为文本框和按键的容器
        Composite panel3 = new Composite(shell, SWT.NONE);
        //为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
        GridData gPanelData3 = new GridData(GridData.FILL_HORIZONTAL);
        panel3.setLayoutData(gPanelData3);
        //为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
        GridLayout gPanelLay3 = new GridLayout();
        gPanelLay3.numColumns = 2;
        panel3.setLayout(gPanelLay3);
        //为Panel生成一个背景色
        final Color bkColor3 = new Color(Display.getCurrent(), 100, 200, 200);
        panel3.setBackground(bkColor3);

        Button startChooseBtn = new Button(panel3, SWT.NONE);
        startChooseBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    BarrageMsgUtil.start(mDisplay, panel, table);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        startChooseBtn.setBounds(132, 163, 61, 22);
        startChooseBtn.setText("开始选择");

        Button stopChooseBtn = new Button(panel3, SWT.NONE);
        stopChooseBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    BarrageMsgUtil.isLoop = false;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        stopChooseBtn.setBounds(132, 163, 61, 22);
        stopChooseBtn.setText("停止选择");
    }

    private static void addChooseColumn(Table table, Text editChooseText) {
        String chooseItemContent = editChooseText.getText();
        if (chooseItemContent != null && chooseItemContent.length() > 0 && !mChooseResultDataMap.containsKey(chooseItemContent)) {
            TableColumn tableColumn = new TableColumn(table, SWT.CENTER);
            tableColumn.setText(editChooseText.getText());
            tableColumn.setWidth(100);
            editChooseText.setText("");

            int index = table.indexOf(tableColumn);
            ChooseResultData chooseResultData = new ChooseResultData();
            chooseResultData.mChooseColumnIndex = index;

            mChooseResultDataMap.put(chooseItemContent, chooseResultData);

            TableColumn tableColumn2 = new TableColumn(table, SWT.CENTER);
            tableColumn2.setText(editChooseText.getText());
            tableColumn2.setWidth(5);
        }
    }
}
