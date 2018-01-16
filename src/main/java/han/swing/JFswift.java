/*
 * Created by JFormDesigner on Thu Dec 21 10:54:38 CST 2017
 */

package han.swing;

import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.field.Field86;
import han.utils.*;
import han.vo.F61_86;
import org.jdesktop.layout.GroupLayout;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * @author han
 */
public class JFswift extends JFrame {
    private File inputTxtFile;
    private File outSwiftFile;
    private boolean fileExited;
    private JFswift() {
        initComponents();

    }
    public  static void  main(String args[]) {

        JFswift jf = new JFswift();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        jf.setVisible(true);
    }

    private void menuItemInputMouseReleased(MouseEvent e) {
        getReadFile();
    }

    private void getReadFile() {
        JFileChooser chooser= new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        //  chooser.setSelectedFile(new File("CCB.txt"));
        FileFilter filter;
//        filter = new FileNameExtensionFilter("EXCEL","xls","xlsx");
//        chooser.setAcceptAllFileFilterUsed(false);
//        chooser.addChoosableFileFilter(filter);
        filter = new FileNameExtensionFilter("文本文件","txt");
        chooser.addChoosableFileFilter(filter);
//        filter = new FileNameExtensionFilter("PDF","pdf");
//        chooser.addChoosableFileFilter(filter);
        int result= chooser.showOpenDialog (JFswift.this);
        if (result==JFileChooser.APPROVE_OPTION){
            this.inputTxtFile=chooser.getSelectedFile();
            chooser.setVisible(false);
            if (inputTxtFile.exists() ){
                textArea1.append("导入文件：->\n");
                textArea1.append(inputTxtFile.getAbsolutePath());
                textArea1.append("\n");
            }else{
                textArea1.append(inputTxtFile.getAbsolutePath());
                textArea1.append("不存在\n");
            }


        }
    }


    private void menuItemOutputMouseReleased(MouseEvent e) {
        getWriteFile();
    }

    private void getWriteFile() {
        JFileChooser chooser= new JFileChooser();
        chooser.setCurrentDirectory(inputTxtFile);
        chooser.setSelectedFile(new File(inputTxtFile.getName().substring(0,inputTxtFile.getName().lastIndexOf("."))+"-MT940.txt"));
        FileFilter filter;
        filter = new FileNameExtensionFilter("MT940客户对账单","txt");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(filter);
        int result= chooser.showSaveDialog (JFswift.this);
        if (result==JFileChooser.APPROVE_OPTION){
            this.outSwiftFile=chooser.getSelectedFile();
            chooser.setVisible(false);
            if (outSwiftFile.exists()){

                 JOptionPane jp= new JOptionPane();
                  result= jp.showConfirmDialog(chooser,"文件存在是否覆盖？");
                 if (result==JOptionPane.YES_OPTION){
                     fileExited=true;
                 }else if (result==JOptionPane.CANCEL_OPTION){
                     fileExited=false;
                 }
                     ;

            }
            textArea1.append("导出Swift文件：->\n");
            textArea1.append(outSwiftFile.getAbsolutePath());
            textArea1.append("\n");
            if(fileExited) textArea1.append("文件转换后被覆盖！\n");
        }
    }

    private void menuItemTransMouseReleased(MouseEvent e) {
        trans2swift();
        JOptionPane chooser= new JOptionPane();
        chooser.showMessageDialog(JFswift.this,"文件转换完成！\n"+outSwiftFile.getAbsolutePath());
    }

    private void trans2swift() {
          NumberFormat ddf1= NumberFormat.getNumberInstance() ;
        ddf1.setMaximumFractionDigits(2);
        boolean isSameDay=false;
        String sCurDate=null;
        int iLen=0;
        if (inputTxtFile==null) getReadFile();
        if (outSwiftFile==null)getWriteFile();
        StringBuffer buffer=new StringBuffer();
        SwiftUtils swift=SwiftUtils.getInstance();
        IccbBankFileUtils inputfile=null;
        if (inputTxtFile.getName().substring(inputTxtFile.getName().lastIndexOf(".")+1).compareTo("xls")==0 )
                inputfile= ExcelUtils.getInstance();
        else if (inputTxtFile.getName().substring(inputTxtFile.getName().lastIndexOf(".")+1).compareTo("pdf")==0 ){
            inputfile= PdfUtils.getInstance();
        }
        else {
            inputfile= txtUtils.getInstance();
        }
        inputfile.readerFile(inputTxtFile.getPath());
        sCurDate="";
        iLen=inputfile.get总交易笔数();
       // swift.resetMt940();
        ArrayList<F61_86> list = new ArrayList<F61_86>();
        BigDecimal 期初余额=null;
        for ( int i=0 ;i<iLen;i++){
            if (sCurDate.compareTo(inputfile.get记账日期(i))==0){ //same date
                isSameDay=true;
            }
            else{
                isSameDay=false;
            }
            sCurDate=inputfile.get记账日期(i);

            if (isSameDay){

            }
            else {
                swift.setMt940Head();
                swift.set发报行编号(inputfile.get账号()) ;
                swift.set账户标示(inputfile.get账号());
                swift.set对账_分页序号(new BigDecimal(inputfile.get账户明细编号(i)),1);
                BigDecimal 余额= (inputfile.get余额(i));
                BigDecimal 借发生额= (inputfile.get借方发生额(i));
                BigDecimal 贷发生额= (inputfile.get贷方发生额(i));

                期初余额=余额.add(借发生额).subtract(贷发生额)  ;
//
            }

            BigDecimal 余额= (inputfile.get余额(i) );
            BigDecimal 发生额=null;
            BigDecimal 借发生额= (inputfile.get借方发生额(i));
            BigDecimal 贷发生额= (inputfile.get贷方发生额(i));
            String sCurType="D";
            if (借发生额.compareTo(new BigDecimal(0.0))==0){
                sCurType="D";
                发生额=贷发生额;
            }
            if (贷发生额.compareTo(new BigDecimal(0.0))==0){

                sCurType="C";
                发生额=借发生额;
                continue; //只处理贷方
            }
            //BigDecimal 期初余额=余额.add(借发生额).subtract(贷发生额)  ;
            Field61 f61= new Field61().setValueDate(inputfile.get交易时间(i))
                    .setEntryDate(inputfile.getEntryDate(i)).setDCMark(sCurType).setFundsCode("D").setAmount(发生额.setScale(2,BigDecimal.ROUND_HALF_UP))
                    .setTransactionType("NMSC").setIdentificationCode("NONREF")
                    //  .setReferenceForTheAccountOwner("")
                    // .setReferenceOfTheAccountServicingInstitution("")
                    .setSupplementaryDetails(inputfile.get交易流水号(i)) //交易流水号
                    ;
            Field86 f86= null
                    ;
             {
                f86 = new Field86().setComponent1(inputfile.get交易流水号(i))
                        .setComponent2(" "+inputfile.get对方户名(i)+" "+inputfile.get对方开户机构(i) )
                        .setComponent3("//"+inputfile.get备注(i))
                        .setComponent4("对方账号"+inputfile.get对方账号(i));
            }
            F61_86 f61_86=new F61_86(f61,f86);
            list.add(f61_86);
            if(inputfile.get记账日期(i+1)==null||!(inputfile.get记账日期(i).compareTo(inputfile.get记账日期(i+1))==0) ){
                //last record in a day
                swift.set61_86(list);
                swift.set账面余额(inputfile.get币种(i),inputfile.get记账日期(i),余额.setScale(2,BigDecimal.ROUND_HALF_UP),sCurType);


                if (余额.compareTo(期初余额)>0){
                    sCurType="D";
                }else{
                    sCurType="C";
                }
                期初余额.setScale(2,BigDecimal.ROUND_HALF_UP) ;
                swift.set期初余额(inputfile.get币种(i),inputfile.get记账日期(i),
                           期初余额.setScale(2,BigDecimal.ROUND_HALF_UP) ,sCurType);



                swift.setTail();
                buffer.append(swift.getMessage());
                buffer.append("\n\n");
                swift.resetMt940();
                list = new ArrayList<F61_86>();
            }
            ;
        }
        try {
            PrintWriter pw=new PrintWriter(outSwiftFile);
            pw.print(buffer);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menuFile = new JMenu();
        menuItemInput = new JMenuItem();
        menuItemOutput = new JMenuItem();
        menuTrans = new JMenu();
        menuItemTrans = new JMenuItem();
        menuAbout = new JMenu();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();

        //======== this ========
        Container contentPane = getContentPane();

        //======== menuBar1 ========
        {

            //======== menuFile ========
            {
                menuFile.setText("\u6587\u4ef6  ");
                menuFile.setActionCommand("text");

                //---- menuItemInput ----
                menuItemInput.setText("EXCEL||\u6587\u672c|PDF");
                menuItemInput.setActionCommand("EXCEL||\u6587\u672c|PDF");
                menuItemInput.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        menuItemInputMouseReleased(e);
                    }
                });
                menuFile.add(menuItemInput);

                //---- menuItemOutput ----
                menuItemOutput.setText("MT940\u6587\u4ef6");
                menuItemOutput.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        menuItemOutputMouseReleased(e);
                    }
                });
                menuFile.add(menuItemOutput);
            }
            menuBar1.add(menuFile);

            //======== menuTrans ========
            {
                menuTrans.setText("\u8f6c\u6362   ");

                //---- menuItemTrans ----
                menuItemTrans.setText("\u7acb\u5373\u8f6c\u6362\u6587\u4ef6");
                menuItemTrans.setActionCommand("text");
                menuItemTrans.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        menuItemTransMouseReleased(e);
                    }
                });
                menuTrans.add(menuItemTrans);
            }
            menuBar1.add(menuTrans);

            //======== menuAbout ========
            {
                menuAbout.setText("\u5173\u4e8e   ");
            }
            menuBar1.add(menuAbout);
        }
        setJMenuBar(menuBar1);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea1);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .add(scrollPane1, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .add(scrollPane1, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menuFile;
    private JMenuItem menuItemInput;
    private JMenuItem menuItemOutput;
    private JMenu menuTrans;
    private JMenuItem menuItemTrans;
    private JMenu menuAbout;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
