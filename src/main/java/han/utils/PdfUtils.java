package han.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by han on 2017/12/26.
 */
public class PdfUtils implements IccbBankFileUtils{
    private static PdfUtils ourInstance = new PdfUtils();
    private int count=0;
    public static PdfUtils getInstance() {
        return ourInstance;
    }
    private  PDFTextStripperByArea stripper=null;
    private PDDocument document = null;
    private PdfUtils() {
    }

    public void readerFile(String path) {
        File pdfFile = new File(path);

        try
        {
            // 方式一：
            /**
             InputStream input = null;
             input = new FileInputStream( pdfFile );
             //加载 pdf 文档
             PDFParser parser = new PDFParser(new RandomAccessBuffer(input));
             parser.parse();
             document = parser.getPDDocument();
             **/

            // 方式二：
            document=PDDocument.load(pdfFile);

            // 获取页码
            int pages = document.getNumberOfPages();

            // 读文本内容
           stripper=new PDFTextStripperByArea();
           for (int page=0 ;page<pages;page++){
               for (int x=0;x<19;x++){
                   for (int y =0 ;y<3 ;y++){
                       stripper.addRegion(x+"_"+y,new Rectangle(10+x*32,100+10+y*43,32,43));

                   }
               }
               stripper.extractRegions(document.getPage(page));
           }
           count=getCount();

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public String get账号() {
       return stripper.getTextForRegion("0_0").replace("\r\n","");

    }

    public int get总交易笔数() {
        return count;
    }

    private int getCount() {
        try {
            PDFTextStripper s=new PDFTextStripper();
            s.setStartPage(0);
            s.setEndPage(document.getNumberOfPages());
            String content = s.getText(document);
            Pattern pattern=Pattern.compile("借方交易笔数：\\s*(\\d)+\\s*贷方发生额：[\\d|.]+\\s*贷方交易笔数：(\\d)+");
            Matcher m=pattern.matcher(new String(content.getBytes(),"UTF-8"));

            if (m.find()) {
                 return Integer.parseInt(m.group(1))+ Integer.parseInt(m.group(2));
            }  else
                return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public String get交易时间(int i) {
        return stripper.getTextForRegion("1_"+i).replace("\r\n","").substring(2,8);

    }

    public String getEntryDate(int i) {
        return stripper.getTextForRegion("1_"+i).replace("\r\n","").substring(4,8);
    }

    public BigDecimal get借方发生额(int i) {
        return new BigDecimal(stripper.getTextForRegion("2_"+i).replace("\r\n",""));
    }

    public BigDecimal get贷方发生额(int i) {
        return new BigDecimal(stripper.getTextForRegion("3_"+i).replace("\r\n",""));
    }

    public BigDecimal get余额(int i) {
        return new BigDecimal(stripper.getTextForRegion("4_"+i).replace("\r\n",""));
    }

    public String get记账日期(int i) {
        if (i>=count) return "";
        return stripper.getTextForRegion("9_"+i).replace("\r\n","");
    }

    public String get币种(int i) {
       String cur= stripper.getTextForRegion("5_"+i).replace("\r\n","");
        return  new CurUtils(cur).invoke();
    }

    public String get对方户名(int i) {
        return stripper.getTextForRegion("6_"+i).replace("\r\n","");
    }

    public String get对方账号(int i) {
        return stripper.getTextForRegion("7_"+i).replace("\r\n","");
    }

    public String get对方开户机构(int i) {
        return stripper.getTextForRegion("8_"+i).replace("\r\n","");
    }

    public String get摘要(int i) {
        return stripper.getTextForRegion("10_"+i).replace("\r\n","");
    }

    public String get备注(int i) {
        return stripper.getTextForRegion("11_"+i).replace("\r\n","");
    }

    public String get账户明细编号(int i) {
        return stripper.getTextForRegion("12_"+i).replace("\r\n","").split("-")[0];
    }

    public String get交易流水号(int i) {
        return stripper.getTextForRegion("12_"+i).replace("\r\n","").split("-")[1];
    }

    public String get企业流水号(int i) {
        return stripper.getTextForRegion("13_"+i).replace("\r\n","");
    }

    public String get凭证种类(int i) {
        return stripper.getTextForRegion("14_"+i).replace("\r\n","");
    }

    public String get凭证号(int i) {
        return stripper.getTextForRegion("15_"+i).replace("\r\n","");
    }

    public String get关联账户(int i) {
        return stripper.getTextForRegion("16_"+i).replace("\r\n","");
    }
    public static void main(String[] args){
        PdfUtils pdfUtils=PdfUtils.getInstance();
        pdfUtils.readerFile("E:\\git\\pw-swift-core\\对账单\\37101002710051003563.pdf");
        pdfUtils.get总交易笔数();
        pdfUtils.get关联账户(2);
    }
}
