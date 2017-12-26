package han.utils;

import com.prowidesoftware.swift.model.*;
import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import han.vo.F61_86;

import java.util.List;

public class SwiftUtils
{
    private static SwiftUtils ourInstance = new SwiftUtils();

    private MT940 m = null;

    public static SwiftUtils getInstance()
    {
        return ourInstance;
    }

    private SwiftUtils() {
        this.m = new MT940();
    }

    public SwiftUtils resetMt940() {
        this.m = new MT940();
        return this;
    }
    public SwiftUtils setMt940Head() {
        SwiftBlock1 b1 = new SwiftBlock1();
        b1.setApplicationId("");
        b1.setServiceId("");
        b1.setLogicalTerminal("");
        b1.setSessionNumber("");
        b1.setSequenceNumber("");
        this.m.getSwiftMessage().setBlock1(b1);

        SwiftBlock2Input b2 = new SwiftBlock2Input();
        b2.setMessageType("");
        b2.setReceiverAddress("");
        b2.setDeliveryMonitoring("");
        this.m.getSwiftMessage().setBlock2(b2);

        SwiftBlock3 block3 = new SwiftBlock3();
        block3.append(new Tag("108", "CTIS"));
        this.m.getSwiftMessage().addBlock(block3);

        return this;
    }
    public SwiftUtils set发报行编号(String s) {
        if (this.m.getSwiftMessage().getBlock4() == null) {
            SwiftBlock4 block4 = new SwiftBlock4();
            block4.append(new Field20(s));
            this.m.getSwiftMessage().addBlock(block4);
        } else {
            this.m.addField(new Field20(s));
        }return this;
    }
    public SwiftUtils set账户标示(String s) {
        this.m.addField(new Field25(s));
        return this;
    }

    public SwiftUtils set对账_分页序号(Number statement, Number sequence)
    {
        Field28C f28c = new Field28C()
                .setSequenceNumber(sequence)
                .setStatementNumber(statement);

        this.m.addField(f28c);
        return this;
    }

    public SwiftUtils set期初余额(String CurType, String cdate, Number 金额, String 借贷标志)
    {
        Field60F f60f = new Field60F()
                .setCurrency(CurType)
                .setDate(cdate)
                .setAmount(金额)
                .setDCMark(借贷标志);

        this.m.addField(f60f);
        return this;
    }

    public SwiftUtils set账面余额(String CurType, String cdate, Number 金额, String 借贷标志)
    {
        Field62F f62f = new Field62F()
                .setAmount(金额)
                .setCurrency(CurType)
                .setDate(cdate)
                .setDCMark(借贷标志);
        this.m.addField(f62f);
        return this;
    }
    public SwiftUtils setTail() {
        SwiftBlock5 block5 = new SwiftBlock5();

        block5.append(new Tag("CHK", ""));
        this.m.getSwiftMessage().addBlock(block5);
        return this;
    }
    public SwiftUtils set61_86(List<F61_86> list) {
        for (F61_86 o : list) {
            this.m.append(new SwiftTagListBlock()
                    .append(o.getF61())
                    .append(o.getF86()));
        }
        return this;
    }
    public String getMessage() {
        return this.m.message();
    }
}