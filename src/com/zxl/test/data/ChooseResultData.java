package com.zxl.test.data;

import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import java.util.ArrayList;
import java.util.List;

public class ChooseResultData {
    public int mChooseCount = 0;

    public int mChooseColumnIndex = 0;

    public Text mChooseCountText;

    public TableItem mTableItem;

    public List<String> mNickNameList = new ArrayList<>();
}
