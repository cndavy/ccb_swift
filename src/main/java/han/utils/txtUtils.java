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
        String pattern="";
        Scanner scanner= null;
        try {
            scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()){
                String txt=scanner.nextLine();
                String[] spl= txt.split(",");
                for (int i=0 ;i<spl.length;i++){
                    spl[i]=spl[i].substring(1,spl[i].length()-1);
                }
                lineList.add(spl);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String get账号() {
        if (lineList.size()>1){
            return  lineList.get(1)[0];
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
        return lineList.get(i+1)[13].split("-")[0];
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
}
