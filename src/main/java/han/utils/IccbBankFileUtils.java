package han.utils;

import java.math.BigDecimal;

/**
 * Created by han on 2017/12/25.
 */
public interface IccbBankFileUtils {
    void readerFile(String path);

    String get账号();

    int get总交易笔数();

    String get交易时间(int i);

    String getEntryDate(int i);

    BigDecimal get借方发生额(int i);

    BigDecimal get贷方发生额(int i);

    BigDecimal get余额(int i);

    String get记账日期(int i);

    String get币种(int i);

    String get对方户名(int i);

    String get对方账号(int i);

    String get对方开户机构(int i);

    String get摘要(int i);

    String get备注(int i);

    String get账户明细编号(int i);

    String get交易流水号(int i);

    String get企业流水号(int i);

    String get凭证种类(int i);

    String get凭证号(int i);

    String get关联账户(int i);
}
