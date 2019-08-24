package com.zxl.test;

import com.zxl.test.data.ChooseResultData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
    private static Display mDisplay;

    private static Text mRoomNumText;
    private static Table mBarrageMsgTable;
    private static Table mChooseTable;

    public static void main(String[] args) {
        System.out.println("zxl--->main thread--->" + Thread.currentThread());
        mDisplay = new Display();
        Shell shell = new Shell(mDisplay);

        Composite rootComposite = new Composite(shell, SWT.NONE);
        GridLayout rootGridLayout = new GridLayout();
        shell.setLayout(rootGridLayout);
        rootComposite.setLayout(rootGridLayout);
        GridData rootGridData = new GridData(GridData.FILL_BOTH);
        rootComposite.setLayoutData(rootGridData);
        Color bkColor = new Color(Display.getCurrent(), 200, 0, 200);
        rootComposite.setBackground(bkColor);

        initRoomNumLayout(rootComposite);
        initBarrageInfoLayout(rootComposite);
        initChooseLayout(rootComposite);


        //shell.layout();
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

    private static void initRoomNumLayout(Composite rootComposite) {
        Composite roomNumComposite = new Composite(rootComposite, SWT.NONE);
        GridLayout roomNumGridLayout = new GridLayout();
        roomNumGridLayout.numColumns = 2;
        roomNumComposite.setLayout(roomNumGridLayout);
        GridData roomNumGridData = new GridData(SWT.FILL,SWT.FILL,true,false,2,1);
        roomNumComposite.setLayoutData(roomNumGridData);

        mRoomNumText = new Text(roomNumComposite, SWT.SHADOW_IN);
        GridData roomNumTextGridData = new GridData(GridData.FILL_HORIZONTAL);
        mRoomNumText.setLayoutData(roomNumTextGridData);

        mRoomNumText.addTraverseListener(new TraverseListener() {
            public void keyTraversed(TraverseEvent e) {
                if (e.keyCode == 13) {
                    // e.detail = SWT.TRAVERSE_TAB_NEXT;
                    e.doit = true;

                }
            }
        });

        Button editRoomNumBtn = new Button(roomNumComposite, SWT.NONE);
        editRoomNumBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

            }
        });
        editRoomNumBtn.setBounds(132, 163, 61, 22);
        editRoomNumBtn.setText("设置房间号");
    }

    private static void initBarrageInfoLayout(Composite rootComposite) {
        Composite barrageInfoComposite = new Composite(rootComposite, SWT.NONE);
        GridLayout barrageInfoGridLayout = new GridLayout();
        barrageInfoComposite.setLayout(barrageInfoGridLayout);
        GridData barrageInfoGridData = new GridData(SWT.FILL,SWT.WRAP,true,false,1,1);
        barrageInfoComposite.setLayoutData(barrageInfoGridData);
        Color bkColor = new Color(Display.getCurrent(), 0, 220, 200);
        barrageInfoComposite.setBackground(bkColor);

        Table table = new Table(barrageInfoComposite, SWT.BORDER | SWT.FULL_SELECTION);
        table.setLayoutData(new GridData(mDisplay.getBounds().width,150));
        mBarrageMsgTable = table;
        table.setHeaderVisible(true);

        table.setLinesVisible(true);

        TableColumn tableColumn0 = new TableColumn(table, SWT.CENTER);
        tableColumn0.setText("昵称");

        TableColumn tableColumn1 = new TableColumn(table, SWT.CENTER);
        tableColumn1.setText("弹幕");

//        table.addControlListener(new ControlAdapter() {
//            public void controlResized(ControlEvent e) {
//                Rectangle area = barrageInfoComposite.getClientArea();
//                int width = area.width - 2 * table.getBorderWidth();
//                table.setLayoutData(new GridData(width,300));
//            }
//        });
//        table.addListener(SWT.MeasureItem, new Listener() {
//            @Override
//            public void handleEvent(Event event) {
//                ScrollBar scrollBar = table.getVerticalBar();
//                scrollBar.setSelection(scrollBar.getMaximum());
//            }
//        });
    }

    private static void initChooseLayout(Composite rootComposite) {
        Composite chooseComposite = new Composite(rootComposite, SWT.NONE);
        GridLayout chooseGridLayout = new GridLayout();
        chooseComposite.setLayout(chooseGridLayout);
        GridData chooseGridData = new GridData(SWT.FILL,SWT.WRAP,true,false,3,1);
        chooseComposite.setLayoutData(chooseGridData);
        Color bkColor = new Color(Display.getCurrent(), 220, 220, 0);
        chooseComposite.setBackground(bkColor);

        mChooseTable = new Table(chooseComposite, SWT.BORDER | SWT.FULL_SELECTION);
        mChooseTable.setLayoutData(new GridData(mDisplay.getBounds().width,150));
        mChooseTable.setHeaderVisible(true);
        mChooseTable.setLinesVisible(true);

        //=============edit================
        Composite editComposite = new Composite(rootComposite, SWT.NONE);
        GridLayout editGridLayout = new GridLayout();
        editComposite.setLayout(editGridLayout);
        editGridLayout.numColumns = 2;
        GridData editGridData = new GridData(SWT.FILL,SWT.FILL,true,false,1,2);
        editComposite.setLayoutData(editGridData);
        Color editBkColor = new Color(Display.getCurrent(), 150, 200, 200);
        editComposite.setBackground(editBkColor);

        Text editChooseText = new Text(editComposite, SWT.SHADOW_IN);
        editChooseText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        editChooseText.addTraverseListener(new TraverseListener() {
            public void keyTraversed(TraverseEvent e) {
                if (e.keyCode == 13) {
                    // e.detail = SWT.TRAVERSE_TAB_NEXT;
                    e.doit = true;

                    addChooseColumn(mChooseTable, editChooseText);
                }
            }
        });

        Button addChooseBtn = new Button(editComposite, SWT.NONE);
        addChooseBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                addChooseColumn(mChooseTable, editChooseText);
            }
        });
        addChooseBtn.setBounds(132, 163, 61, 22);
        addChooseBtn.setText("添加投票选项");

        //===========control=========
        Composite controlComposite = new Composite(rootComposite, SWT.NONE);
        GridLayout controlGridLayout = new GridLayout();
        controlGridLayout.numColumns = 3;
        controlComposite.setLayout(controlGridLayout);
        GridData controlGridData = new GridData(SWT.FILL,SWT.WRAP,true,false,1,3);
        chooseComposite.setLayoutData(controlGridData);
        Color controlBkColor = new Color(Display.getCurrent(), 0, 110, 110);
        chooseComposite.setBackground(controlBkColor);

        Button startChooseBtn = new Button(controlComposite, SWT.NONE);
        startChooseBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    mBarrageMsgTable.removeAll();
                    mChooseTable.removeAll();

                    TableItem ti = new TableItem(mChooseTable, SWT.NONE);
                    Set<String> keySet = BarrageMsgUtil.mChooseResultDataMap.keySet();
                    for(String key : keySet) {
                        ChooseResultData chooseResultData = BarrageMsgUtil.mChooseResultDataMap.get(key);
                        chooseResultData.mChooseCount = 0;
                        chooseResultData.mTableItem = ti;
                        chooseResultData.mNickNameList.clear();
                    }
                    BarrageMsgUtil.start(mRoomNumText.getText(), mDisplay, chooseComposite, mBarrageMsgTable);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        startChooseBtn.setBounds(132, 163, 61, 22);
        startChooseBtn.setText("开始投票");

        Button stopChooseBtn = new Button(controlComposite, SWT.NONE);
        stopChooseBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    BarrageMsgUtil.stop();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        stopChooseBtn.setBounds(132, 163, 61, 22);
        stopChooseBtn.setText("停止投票");

        Button clearChooseBtn = new Button(controlComposite, SWT.NONE);
        clearChooseBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    //System.out.println("zxl--->clearChooseBtn--->isLoop--->" + BarrageMsgUtil.isLoop);
                    if(!BarrageMsgUtil.isLoop) {
                        TableColumn tableColumns[] = mChooseTable.getColumns();
                        for(TableColumn tableColumn : tableColumns) {
                            tableColumn.dispose();
                        }

                        BarrageMsgUtil.mChooseResultDataMap.clear();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        clearChooseBtn.setBounds(132, 163, 61, 22);
        clearChooseBtn.setText("清除投票");
    }

    private static void addChooseColumn(Table table, Text editChooseText) {
        String chooseItemContent = editChooseText.getText();
        if (chooseItemContent != null && chooseItemContent.length() > 0 && !BarrageMsgUtil.mChooseResultDataMap.containsKey(chooseItemContent) && !BarrageMsgUtil.isLoop) {
            TableColumn tableColumn = new TableColumn(table, SWT.CENTER);
            tableColumn.setText(editChooseText.getText());
            tableColumn.setWidth(100);
            editChooseText.setText("");

            int index = table.indexOf(tableColumn);
            ChooseResultData chooseResultData = new ChooseResultData();
            chooseResultData.mChooseColumnIndex = index;

            BarrageMsgUtil.mChooseResultDataMap.put(chooseItemContent, chooseResultData);

            TableColumn tableColumn2 = new TableColumn(table, SWT.CENTER);
            tableColumn2.setText(editChooseText.getText());
            tableColumn2.setWidth(5);
        }
    }
}
