package han.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by han on 2017/12/25.
 */
public class txtUtils implements IccbBankFileUtils {
    private static txtUtils ourInstance = new txtUtils();
    private List <String[]>lineList =null;
    public static txtUtils getInstance() {
        return ourInstance;
    }

    private txtUtils() {

    }

    @Override
    public void readerFile(String path) {
         lineList = new ArrayList<String[]>();
        //System.out.println(path);
        Scanner scanner= null;
        try {
            scanner = new Scanner(new File(path),"UTF-8");
            String[] spl;
            while (scanner.hasNextLine()){
                String txt= null;
                txt =   (scanner.nextLine() );
               // System.out.print(txt);
                spl= getSplit(txt);


                lineList.add(spl);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   private static String[] getSplit(String txt) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        int b=0;
        int begin=0;
        StringBuffer buff=new StringBuffer();
        int iLen= txt.length();
        for (int i =0 ;i<iLen;i++){
            String s=txt.substring(i,i+1);
            switch (b){
                case 0:
                    if (s.equals("\"")){
                        b=1;
                        begin=i;
                        continue;
                    }else
                        throw new Exception("没有引号");
                case 1:  // 进入 引号中
                    if (s.equals("\"")) {
                        b=2;

                        continue;
                    }else {
                        buff.append(s);
                        continue;
                    }
                case 2:
                    if (s.equals("\"")) { //字段中出现 引号  "asdfasd "" qweqwe"
                      buff.append("\"");
                        b=1;
                    } else if (s.equals(",")){
                        b=0;
                        list.add(buff.toString());
                        buff=new StringBuffer();
                    }


            }
        }
       int resultSize = list.size();
       String[] result = new String[resultSize];
       return list.subList(0, resultSize).toArray(result);

    }

    @Override
    public String get账号(int i ) {
        if (lineList.size()>1){
            return  lineList.get(i+1)[0];
        }
        return null;
    }

    @Override
    public int get总交易笔数() {
        return lineList.size()-1;
    }

    @Override
    public String get交易时间(int i) {
       return lineList.get(i+1)[2].substring(2,8);

    }

    @Override
    public String getEntryDate(int i) {
        return lineList.get(i+1)[2].substring(4,8);
    }

    @Override
    public BigDecimal get借方发生额(int i) {
        return new BigDecimal(lineList.get(i+1)[3]);
    }

    @Override
    public BigDecimal get贷方发生额(int i) {
        return new BigDecimal(lineList.get(i+1)[4]);
    }

    @Override
    public BigDecimal get余额(int i) {
        return new BigDecimal(lineList.get(i+1)[5]);
    }

    @Override
    public String get记账日期(int i) {
        if (i+1>=lineList.size()) {
            return "";
        }
        return lineList.get(i+1)[10];
    }

    @Override
    public String get币种(int i) {
        String cur = lineList.get(i + 1)[6];
       return  new CurUtils(cur).invoke();

    }

    @Override
    public String get对方户名(int i) {
        return lineList.get(i+1)[7];
    }

    @Override
    public String get对方账号(int i) {
        return lineList.get(i+1)[8];
    }

    @Override
    public String get对方开户机构(int i) {
        return lineList.get(i+1)[9];
    }

    @Override
    public String get摘要(int i) {
        return lineList.get(i+1)[11];
    }

    @Override
    public String get备注(int i) {
        return lineList.get(i+1)[12];
    }

    @Override
    public String get账户明细编号(int i) {
        String s=lineList.get(i+1)[13].split("-")[0];
        if (s.length()>5) {
            return   s.substring(s.length()-5,s.length());
        }
        return   s ;
    }

    @Override
    public String get交易流水号(int i) {
        return lineList.get(i+1)[13].split("-")[1];
    }

    @Override
    public String get企业流水号(int i) {
        return lineList.get(i+1)[14];
    }

    @Override
    public String get凭证种类(int i) {
        return lineList.get(i+1)[15];
    }

    @Override
    public String get凭证号(int i) {
        return lineList.get(i+1)[16];
    }

    @Override
    public String get关联账户(int i) {
        return lineList.get(i+1)[17];
    }

    public  static void main(String [] args){
        try {
          String [] ss=  txtUtils.getSplit("\"37101985510050403879\",\"青岛海尔开利冷冻设备有限公司\",\"20180102 14:48:01\",\"0.00\",\"1492587.17\",\"185909602.41\",\"人民币元\",\"上饶市大润发超市有限公司\",\"1512211009000282430\",\"中国工商银行上饶县支行\",\"20180102\",\"自定义\",\",,\",\"104964-3710800000N2P70EKKO\",\"\",\"电汇凭证\" "  );

            System.out.println(ss[12]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
