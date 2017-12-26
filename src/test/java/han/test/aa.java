package han.test;

import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.IConversionService;
import com.prowidesoftware.swift.model.*;
import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

import java.util.Calendar;

/**
 * Created by han on 2017/12/20.
 */
public class aa {


public static void main(String[] args){
   // readMt();
    writeMt940();
 }

    private static void readMt() {
        String str="{1:F01CEDELUL0HLIB0000000000}{2:I515OCBCSGH0XXXXN}{3:{108:331ocswcn60y3}}{4:\n"
                +":16R:GENL\n"
                +":20C::SEME//14011V7K2D\n"
                +":23G:NEWM\n"
                +":22F::TRTR//TRAD\n"
                +":16R:LINK\n"
                +":20C::RELA//S0000003-1\n"
                +":16S:LINK\n"
                +":16S:GENL\n"
                +":16R:CONFDET\n"
                +":98A::TRAD//20131230\n"
                +":98A::SETT//20131231\n"
                +":90B::DEAL//ACTU/EUR15,48\n"
                +":22H::BUSE//SUBS\n"
                +":22F::PRIC/CEDE/NAVP\n"
                +":22H::PAYM//APMT\n"
                +":16R:CONFPRTY\n"
                +":95P::SELL//CEDELULLXXX\n"
                +":97A::SAFE//85000\n"
                +":16S:CONFPRTY\n"
                +":36B::CONF//UNIT/1206,75\n"
                +":35B:ISIN LU0329592371\n"
                +"SHS BLACKROCK GL.FD-EU.SH.D.BD FD D\n"
                +"2 EUR\n"
                +":16S:CONFDET\n"
                +":16R:SETDET\n"
                +":22F::SETR//TRAD\n"
                +":22F::STCO/CEDE/AUTO\n"
                +":16R:SETPRTY\n"
                +":95P::DEAG//CEDELULLXXX\n"
                +":97A::SAFE//85000\n"
                +":16S:SETPRTY\n"
                +":16R:SETPRTY\n"
                +":95P::PSET//CEDELULLXXX\n"
                +":16S:SETPRTY\n"
                +":16R:AMT\n"
                +":19A::DEAL//EUR18680,5\n"
                +":16S:AMT\n"
                +":16R:AMT\n"
                +":19A::SETT//HKD200000,\n"
                +":16S:AMT\n"
                +":16S:SETDET\n"
                +"-}";

        IConversionService srv = new ConversionService();
        SwiftMessage msg = srv.getMessageFromFIN(str);
        SwiftBlock4 b4 = msg.getBlock4();
        String[] f20CValues = b4.getTagValues("20C");
        for (int i=0; i<f20CValues.length; i++) {
            Field20C f20C = new Field20C(f20CValues[i]);
            if ("SEME".equals(f20C.getComponent1())) {
                String strValue = f20C.getComponent2();
                System.out.println(strValue);
            }
        }
    }

    public static void writeMt103(){
    final MT103 m = new MT103();
    m.setSender("FOOSEDR0AXXX");
    m.setReceiver("FOORECV0XXXX");


    m.addField(new Field20("REFERENCE"));
    m.addField(new Field23B("CRED"));

    Field32A f32A = new Field32A()
            .setDate(Calendar.getInstance())
            .setCurrency("EUR")
            .setAmount("1234567,89");
    m.addField(f32A);

    Field50A f50A = new Field50A()
            .setAccount("12345678901234567890")
            .setBIC("FOOBANKXXXXX");
    m.addField(f50A);

    Field59 f59 = new Field59()
            .setAccount("12345678901234567890")
            .setNameAndAddress("JOE DOE");
    m.addField(f59);

    m.addField(new Field71A("OUR"));
    System.out.println(m.message());

}
    public static void writeMt940(){
        final MT940 m = new MT940();
       //m.setSender("");//青岛建行
        //m.setReceiver("");

        SwiftBlock1 b1 = new SwiftBlock1();
        b1.setApplicationId("");
        b1.setServiceId("");
        b1.setLogicalTerminal("");
        b1.setSessionNumber("");
        b1.setSequenceNumber("");
        m.getSwiftMessage().setBlock1(b1);

        SwiftBlock2Input b2 = new SwiftBlock2Input();
        b2.setMessageType("");
        b2.setReceiverAddress("");
        b2.setDeliveryMonitoring("");
        m.getSwiftMessage().setBlock2(b2);

       SwiftBlock3 block3= new SwiftBlock3();
        block3.append(new Tag("108", "CTIS"));
        m.getSwiftMessage().addBlock(block3);

        m.addField(new Field20("1050295"));//发报行编号
        m.addField(new Field25("233800474809"));//账户标示

        Field28C  f28c= new Field28C()
                  .setSequenceNumber(1)
                 .setStatementNumber(302); //对账 /分页序号
        m.addField(f28c);
        Field60F f60f=new Field60F()
                .setCurrency("CNY")  //币种
                .setDate("170724")    //日期
                .setAmount(37091580.04) //金额
                .setDCMark("D"); //借贷标志
        // 期初余额
        m.addField(f60f);


       // List list= new ArrayList();
        Field61 f61= new Field61().setValueDate("171116")
                .setEntryDate("1116").setDCMark("C").setFundsCode("D").setAmount("1622,00")
                .setTransactionType("NMSC").setIdentificationCode("NONREF")
               //  .setReferenceForTheAccountOwner("")
               // .setReferenceOfTheAccountServicingInstitution("")
                .setSupplementaryDetails("63992044037")
                ;
        m.append(new SwiftTagListBlock()
                .append( f61
                        //Field61.tag("1707240724CY158990,OONMSCNONREF\n178339456010") //细项
                )
                .append(Field86.tag("BCIPS HSBCTWTPXXX CNS320670UFRB7SW")) //附加说明

        );
        m.append(new SwiftTagListBlock()
                .append(
                        Field61.tag("1707240724CY11065844,20NMSCNONREF\n1796000000008")
                )
                .append(Field86.tag("BCIPS HSBCHKHHHHKH CNS320670UFRB7SW//31833-GOOD /IMPORT PAYME\n" +
                        "NT For \n" +
                        "QINGDAO CHINA"))

        );
        Field62F f62f=new Field62F()       //账面余额
                .setAmount(38342484.24)
                .setCurrency("CNY")
                .setDate("170724").setDCMark("D");
        m.addField(f62f);
        SwiftBlock5 block5 = new SwiftBlock5();

        block5.append(new Tag("CHK", ""));
        m.getSwiftMessage().addBlock(block5);

        System.out.println(m.message());

    }
}
