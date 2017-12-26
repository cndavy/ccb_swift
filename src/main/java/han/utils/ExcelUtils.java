package han.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by han on 2017/12/22.
 */
public class ExcelUtils implements IccbBankFileUtils {
    private static ExcelUtils ourInstance = new ExcelUtils();
    private DecimalFormat df   = new DecimalFormat("############0.00");
    private  NumberFormat ddf1= NumberFormat.getNumberInstance() ;
    public static ExcelUtils getInstance() {
        return ourInstance;
    }
    private   Workbook wbWorkbook = null;
    private ExcelUtils() {
        ddf1.setMaximumFractionDigits(2);
    }
    @Override
    public   void readerFile(String path) {
        InputStream is = null;

        try {
            if (path.endsWith(".xls")) {
                is = new FileInputStream(path);
                wbWorkbook = new HSSFWorkbook(is);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private Cell  readCell( int row,int col){
        if (wbWorkbook!=null){
            Sheet sheet= wbWorkbook.getSheetAt(0);
           HSSFRow r= (HSSFRow) sheet.getRow(row);
            HSSFCell cell;
           try{
                 cell=  r.getCell(col);
           }catch (Exception e){
               return null;
           }

            return cell;
        }
        return null;
    }
    @Override
    public String get账号(){
        return  readCell(3,1).getStringCellValue();
    }
    public double get借方交易笔数 (){
        return  (readCell(6,3).getNumericCellValue());
    }
    public double get贷方交易笔数(){
        return  (readCell(7,3).getNumericCellValue());
    }
    @Override
    public int get总交易笔数(){
        return  (int)(get借方交易笔数() +   get贷方交易笔数());
    }

    @Override
    public  String get交易时间(int i){
       return  readCell(10+i,0).getStringCellValue().substring(2,8);
    }
    @Override
    public  String getEntryDate(int i){
        return  readCell(10+i,0).getStringCellValue().substring(4,8);
    }
    @Override
    public BigDecimal get借方发生额(int i){
        BigDecimal bigDecimal = new BigDecimal(readCell(10 + i, 1).getNumericCellValue());
        df.format(bigDecimal);
        return bigDecimal;
    }
    @Override
    public BigDecimal get贷方发生额(int i){
        BigDecimal bigDecimal = new BigDecimal(readCell(10 + i, 2).getNumericCellValue());
        return bigDecimal;
    }
    @Override
    public BigDecimal get余额(int i){
        BigDecimal bigDecimal = new BigDecimal(readCell(10 + i, 3).getNumericCellValue());
        return bigDecimal;
    }
    @Override
    public  String get记账日期(int i){
        if (readCell(10+i,8)!=null)
           return       readCell(10+i,8).getStringCellValue().substring(2,8);
        else return  null;
    }
    @Override
    public  String get币种(int i){
         String cur= readCell(10+i,4).getStringCellValue();
        return new CurUtils(cur).invoke();
    }

    @Override
    public  String get对方户名(int i){
        return readCell(10+i,5).getStringCellValue();

    }
    @Override
    public  String get对方账号(int i){
        return readCell(10+i,6).getStringCellValue();

    }
    @Override
    public  String get对方开户机构(int i){
        return readCell(10+i,7).getStringCellValue();

    }

    @Override
    public  String get摘要(int i){
        return readCell(10+i,9).getStringCellValue();

    }

    @Override
    public  String get备注(int i){
        return readCell(10+i,10).getStringCellValue();

    }
    @Override
    public  String get账户明细编号(int i){
        return readCell(10+i,11).getStringCellValue().split("-")[0];

    }
    @Override
    public  String get交易流水号(int i){
        return readCell(10+i,11).getStringCellValue().split("-")[1];

    }
    @Override
    public  String get企业流水号(int i){
        return readCell(10+i,12).getStringCellValue() ;

    }
    @Override

    public  String get凭证种类(int i){
        return readCell(10+i,13).getStringCellValue() ;

    }
    @Override
    public  String get凭证号(int i){
        return readCell(10+i,14).getStringCellValue() ;

    }
    @Override
    public  String get关联账户(int i){
        return readCell(10+i,15).getStringCellValue() ;

    }
    @Test
    public static void main(String [] args){
        ExcelUtils excelUtils = ExcelUtils.getInstance();
        excelUtils.readerFile("E:\\git\\pw-swift-core\\对账单\\37101002710051003563(1).xls");
        Cell cell=excelUtils.readCell(0,0);  // 0->
        System.out.println(cell.getStringCellValue());

        for (int i =0 ;i< excelUtils.get总交易笔数() ;i++){
            System.out.println(excelUtils.get记账日期(i)
                    +excelUtils.get借方发生额(i)+ excelUtils.get余额(i)
                    + excelUtils.get币种(i)
                    +excelUtils.get摘要(i) + excelUtils.get备注(i)+"|"+ excelUtils.get账户明细编号(i)
                    +" "+excelUtils.get交易流水号(i) +excelUtils.get关联账户(i) );
        }
    }

}
