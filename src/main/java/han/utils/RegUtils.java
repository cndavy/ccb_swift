package han.utils;

import org.junit.Test;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by han on 2017/12/22.
 */
public class RegUtils {
    public static final String STR = "查询账号[ Inquirer account number ]|232500474807|\n" +
            "总笔数[ Total number ]|29|\n" +
            "借方发生总笔数[ Total Numbers of Debited Payments ]|11|\n" +
            "借方发生总额[ Total Debit Amount of Payments ]|1,081,178.53|\n" +
            "贷方发生总笔数[ Total Numbers of Credited Payments ]|18|\n" +
            "贷方发生总额[ Total Credit Amount of Payments ]|713,524.55|\n" +
            "查询时间范围[ Time Range ]|20171101-20171130|\n" +
            "交易类型[ Transaction Type ]|业务类型[ Business type ]|付款人开户行号[ Account holding bank number of payer ]|付款人开户行名[ Payer account bank ]|付款人账号[ Debit Account No. ]|付款人名称[ Payer's Name ]|收款人开户行行号[ Account holding bank number of beneficiary ]|收款人开户行名[ Beneficiary account bank ]|收款人账号[ Payee's Account Number ]|收款人名称[ Payee's Name ]|交易日期[ Transaction Date ]|交易时间[ Transaction time ]|交易货币[ Trade Currency ]|交易金额[ Trade Amount ]|交易后余额[ After-transaction balance ]|起息日期[ Value Date ]|汇率[ Exchange rate ]|交易流水号[ Transaction reference number ]|客户申请号[ Online Banking Transaction Ref.(Bank Ref.) ]|客户业务编号[ Customer Transaction Ref.(Customer Ref.) ]|凭证类型[ Voucher type ]|凭证号码[ Voucher number ]|记录标识号[ Record ID ]|摘要[ Reference ]|用途[ Purpose ]|交易附言[ Remark ]|备注[ Remarks ]|预留项1[ Reserve1 ]|预留项2[ Reserve2 ]|预留项3[ Reserve3 ]|名义付款人开户行行号[ Opening bank number of nominal payer ]|名义付款人开户行名[ Opening bank name of nominal payer ]|名义付款人账号[ Payment A/C No. ]|名义付款人名称[ Name of nominal payer ]|名义收款人开户行号[ Opening bank number of nominal payee ]|名义收款人开户行名[ Opening bank name of nominal payee ]|名义收款人账号[ Account number of nominal payee ]|名义收款人名称[ Name of nominal payee ]|\n" +
            "往账|转账支出|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|||||20171101|14:22:52|USD|-400000.00|+32428.32|20171101|660.510000|109585237086|||其他凭证|07376344|109585237999995086999995086||||||||||||||||\n" +
            "来账|国际汇款|||AE730200000035011873100|CARRIER MIDDLE EAST LIMITED|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171108|10:18:11|USD|+129646.38|+162074.70|20171108|662.770000|75783669085|||||075783669999995085999995085|REFED-NO:271449412       BUSIN-NO:TI17092800040854|TI17092800040854 青岛海尔开利冷冻设备有限公司|MISCELLANEOUS PAYMENT WITH ORDERINGPURPOSE CODE GDS                   QINGDAO 266101 P.R.CHINA|||||||||||||\n" +
            "来账|国际汇款|||082774772672958 858573704|KOOLOGIK PTY LTD AS TRUSTEE FOR MEJ|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171116|10:29:59|USD|+24361.00|+186435.70|20171116|662.860000|73139253083|||||073139253999995083999995083|REFED-NO:C355119RBK103017BUSIN-NO:TI17103000009872|TI17103000009872 青岛海尔开利冷冻设备有限公司|PAYMENT FROM KOOLOGIK PTY LTD AUSTRALIA|||||||||||||\n" +
            "来账|国际汇款||||1/PT SARANA DUNIA PENDINGIN|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171116|10:46:23|USD|+1622.00|+188057.70|20171116|662.860000|75585547081|||||075585547999995081999995081|REFED-NO:F61025482858000 BUSIN-NO:TI17102700015432|TI17102700015432 青岛海尔开利冷冻设备有限公司|PAYMENT FOR PO 4005-231017 BNF ADD.1 HAIER ROAD HI TECH ZONE RFB 6WG2PT12850001|||||||||||||\n" +
            "来账|国际汇款|||001838968470|PRIME REFRIGERATION SYSTEMS|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171116|10:47:51|USD|+37385.00|+225442.70|20171116|662.860000|75799261079|||||075799261999995079999995079|REFED-NO:2017102700038490BUSIN-NO:TI17102700034850|TI17102700034850 青岛海尔开利冷冻设备有限公司|/RFB/214526016//PO 170042 FULL PAYMENT POP GOODS CONT BANXUELIAN|||||||||||||\n" +
            "来账|国际汇款|||010001000966|TERMOMONTAJES DEL VALLE INGENIERIA|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171116|10:50:07|USD|+12848.98|+238291.68|20171116|662.860000|76144963077|||||076144963999995077999995077|REFED-NO:2017103000152859BUSIN-NO:TI17103100002663|TI17103100002663 青岛海尔开利冷冻设备有限公司|/RFB/103017 000234320              103017 000234320/QINGDAO HAIER CARRANTICIPO OCI 550 POR IMPORTACION   EQUIPOS|||||||||||||\n" +
            "来账|国际汇款|||291017004400|JACK WELLING INC|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171116|10:51:31|USD|+54940.00|+293231.68|20171116|662.860000|76352920075|||||076352920999995075999995075|REFED-NO:2017102600070559BUSIN-NO:TI17102600032849|TI17102600032849 青岛海尔开利冷冻设备有限公司|/RFB/214428774//PO2008 POP GOODS CONT BANXULIAN|||||||||||||\n" +
            "来账|国际汇款|||054131628001|CARRIER AIRCONDITIONING REFRIGERA|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171116|10:56:24|USD|+158384.00|+451615.68|20171116|662.860000|77079785073|||||077079785999995073999995073|REFED-NO:297458116       BUSIN-NO:TI17102500000699|TI17102500000699 青岛海尔开利冷冻设备有限公司|PAYMENT TOWARDS INV NO QHC1707109F AND ITS MULTIPLES.|||||||||||||\n" +
            "来账|国际汇款|||212531093472|COLDTEK CORP|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171116|10:58:36|USD|+22467.00|+474082.68|20171116|662.860000|77404776071|||||077404776999995071999995071|REFED-NO:F61024060037001 BUSIN-NO:TI17102500006426|TI17102500006426 青岛海尔开利冷冻设备有限公司|PO 117-166R DOWN PAYMENT 21867.00PO 117-163R FINAL PAYMENT 600.00 RFB NONE|||||||||||||\n" +
            "来账|国际汇款|||212531093472|COLDTEK CORP|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171116|11:00:07|USD|+32395.13|+506477.81|20171116|662.860000|77644448069|||||077644448999995069999995069|REFED-NO:F61020324994001 BUSIN-NO:TI17102000038848|TI17102000038848 青岛海尔开利冷冻设备有限公司|DOWN PAYMENT PO 117-165RFINAL PAYMENT PO 117-154 RFB NONE|||||||||||||\n" +
            "来账|国际汇款||||1/SATGURU ENTERPRISES LIMITED|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171116|11:01:27|USD|+2689.00|+509166.81|20171116|662.860000|77849528067|||||077849528999995067999995067|REFED-NO:F61029005966000 BUSIN-NO:TI17103000005963|TI17103000005963 青岛海尔开利冷冻设备有限公司|INVOICE RFB IB0010406192|||||||||||||\n" +
            "来账|国际汇款|||AE730200000035011873100|CARRIER MIDDLE EAST LIMITED|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171116|16:46:24|USD|+1070.20|+510237.01|20171116|662.860000|124765671065|||||124765671999995065999995065|REFED-NO:296312995       BUSIN-NO:TI17102300001174|TI17102300001174 青岛海尔开利冷冻设备有限公司|GDI                                PURPOSE CODE GDS|||||||||||||\n" +
            "往账|国际汇款|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|||9102630960|CARRIER CORPORATION|20171124|16:06:05|USD|-38926.48|+471310.53|20171124|658.100000|118992092063|||非重空申请书|07376314|118992092999995063999995063||||||||||||||||\n" +
            "往账|收费|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|||||20171124|16:06:05|USD|-22.78|+471287.75|20171124|1.000000|118992092061|||非重空申请书|07376314|118992092999995061999995061|企业国际电讯费―国际发电|||||||||||||||\n" +
            "往账|收费|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|||||20171124|16:06:05|USD|-38.93|+471248.82|20171124|1.000000|118992092060|||非重空申请书|07376314|118992092999995060999995060|对公汇出境外汇款电汇费|||||||||||||||\n" +
            "往账|国际汇款|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|||9102630960|CARRIER CORPORATION|20171124|16:08:23|USD|-74369.94|+396878.88|20171124|658.100000|119328721059|||非重空申请书|07376333|119328721999995059999995059||||||||||||||||\n" +
            "往账|收费|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|||||20171124|16:08:23|USD|-22.78|+396856.10|20171124|1.000000|119328721057|||非重空申请书|07376333|119328721999995057999995057|企业国际电讯费―国际发电|||||||||||||||\n" +
            "往账|收费|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|||||20171124|16:08:23|USD|-74.37|+396781.73|20171124|1.000000|119328721056|||非重空申请书|07376333|119328721999995056999995056|对公汇出境外汇款电汇费|||||||||||||||\n" +
            "来账|国际汇款|||002225050001|CARRIER HONG KONG LTD|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171129|10:07:03|USD|+72714.99|+469496.72|20171129|660.110000|69507293055|||||069507293999995055999995055|REFED-NO:324443149       BUSIN-NO:TI17112100002259|TI17112100002259 青岛海尔开利冷冻设备有限公司||||||||||||||\n" +
            "来账|国际汇款|||082774772672958 858573704|KOOLOGIK PTY LTD AS TRUSTEE FOR MEJ|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171129|10:08:35|USD|+21901.00|+491397.72|20171129|660.110000|69738455053|||||069738455999995053999995053|REFED-NO:C362925RBK111717BUSIN-NO:TI17111700026185|TI17111700026185 青岛海尔开利冷冻设备有限公司|PAYMENT FROM KOOLOGIK PTY LTD AUSTRALIA|||||||||||||\n" +
            "来账|国际汇款|||212531093472|COLDTEK CORP|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171129|10:10:09|USD|+19975.50|+511373.22|20171129|660.110000|69964342051|||||069964342999995051999995051|REFED-NO:F61114012919001 BUSIN-NO:TI17111500007551|TI17111500007551 青岛海尔开利冷冻设备有限公司|PO 117-170 DOWN PAYMENT19053.00PO 117-171 DOWN PAYMENT922.50 RFB NONE|||||||||||||\n" +
            "来账|国际汇款|||010001000966|TERMOMONTAJES DEL VALLE INGENIERIA|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171129|10:31:05|USD|+12485.00|+523858.22|20171129|660.110000|73113901049|||||073113901999995049999995049|REFED-NO:2017112000138463BUSIN-NO:TI17112100003343|TI17112100003343 青岛海尔开利冷冻设备有限公司|/RFB/112017 000237156              112017 000237156/QINGDAO HAIER CARROCI 557 ANTICIPO 50XCIENTO POR     IMPORTACION 20 ISLAND FREEZER|||||||||||||\n" +
            "来账|国际汇款|||291017004400|JACK WELLING INC|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171129|10:32:23|USD|+22953.00|+546811.22|20171129|660.110000|73318488047|||||073318488999995047999995047|REFED-NO:2017112000096591BUSIN-NO:TI17112000026544|TI17112000026544 青岛海尔开利冷冻设备有限公司|/RFB/216452548//PO20172010 POP GOODS CONT BANXULIAN|||||||||||||\n" +
            "来账|国际汇款|||052801000048303|PT. UNITED REFRIGERATION|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171129|10:33:44|USD|+8112.50|+554923.72|20171129|660.110000|73536203045|||||073536203999995045999995045|REFED-NO:F61110320078000 BUSIN-NO:TI17111000038053|TI17111000038053 青岛海尔开利冷冻设备有限公司|//PAYMENT PT UNITED REFRIGERATION RFB 52802000331689|||||||||||||\n" +
            "来账|国际汇款|||212531093472|COLDTEK CORP|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|20171129|10:35:12|USD|+77573.87|+632497.59|20171129|660.110000|73746764043|||||073746764999995043999995043|REFED-NO:F61110483966001 BUSIN-NO:TI17111100011381|TI17111100011381 青岛海尔开利冷冻设备有限公司|PO 117-165 BALANCE DUE31895.50PO 117-156 BALANCE DUE45678.37 RFB NONE|||||||||||||\n" +
            "往账|转账支出|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|||||20171130|09:06:47|USD|-550000.00|+82497.59|20171130|660.310000|62837472041|||其他凭证|08202853|062837472999995041999995041||||||||||||||||\n" +
            "往账|国际汇款|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|||9102630960|CARRIER CORPORATION|20171130|09:15:52|USD|-17682.85|+64814.74|20171130|660.110000|63992044040|||非重空申请书|08202852|063992044999995040999995040|||IT BILL|||||||||||||\n" +
            "往账|收费|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|||||20171130|09:15:52|USD|-22.72|+64792.02|20171130|1.000000|63992044038|||非重空申请书|08202852|063992044999995038999995038|企业国际电讯费―国际发电||IT BILL|||||||||||||\n" +
            "往账|收费|10658|中国银行青岛市分行营业部|232500474807|青岛海尔开利冷冻设备有限公司|||||20171130|09:15:52|USD|-17.68|+64774.34|20171130|1.000000|63992044037|||非重空申请书|08202852|063992044999995037999995037|对公汇出境外汇款电汇费||IT BILL|||||||||||||\n" +
            " ";
    private static RegUtils ourInstance = new RegUtils();

    public static RegUtils getInstance() {
        return ourInstance;
    }
    private final String Reg20="查询账号\\[ Inquirer account number \\]\\|(\\d*)\\|";
    private final Pattern  P20= Pattern.compile(Reg20);
    private final String Reg25="";
    private final Pattern  P25= Pattern.compile(Reg25);
    private final String Reg28C="";
    private final Pattern  P28C= Pattern.compile(Reg28C);
    private final String Reg60F="";
    private final Pattern  P60F= Pattern.compile(Reg60F);
    private final String Reg61="";
    private final Pattern  P61= Pattern.compile(Reg61);
    private final String Reg86="";
    private final Pattern  P86= Pattern.compile(Reg86);
    private final String Reg62F="";
    private final Pattern  P62F= Pattern.compile(Reg62F);

    private RegUtils() {

    }
    public Matcher match20(String str){
        return   P20.matcher(str);
    }
    public Matcher match25(String str){
        return   P25.matcher(str);
    }
    public Matcher match28C(String str){
        return   P28C.matcher(str);
    }
    public Matcher match60F(String str){
        return   P60F.matcher(str);
    }
    public Matcher match61(String str){
        return   P61.matcher(str);
    }
    public Matcher match86(String str){
        return   P86.matcher(str);
    }
    public Matcher match62F(String str){
        return   P62F.matcher(str);
    }
    @Test
    public static void  main(String[] args){
        RegUtils reg= RegUtils.getInstance();
//        Console console = System.console();
//        if (console == null) {
//            System.err.println("No console.");
//            System.exit(1);
//        }
        Scanner scanner = new Scanner(System.in);
         {
            System.out.printf("%nEnter your regex: ");
            Pattern pattern = Pattern.compile("借方交易笔数：\\s*(\\d)+\\s*贷方发生额：[\\d|.]+\\s*贷方交易笔数：(\\d)+");
            System.out.printf("Enter input string to search: ");
            Matcher matcher = pattern.matcher(" 本页合计     借方发生额：0.94     借方交易笔数：1     贷方发生额：0.03     贷方交易笔数：2 ");
            boolean found = false;
            while (matcher.find()) {
                System.out.printf(
                        "I found the text \"%s\" starting at index %d and ending at index %d.%n",
                        matcher.group(), matcher.start(), matcher.end()
                );
                found = true;
            }
            if (!found) {
                System.out.printf("No match found.%n");
            }
        }
    }
}
