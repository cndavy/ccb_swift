package han.utils;

/**
 * Created by han on 2017/12/26.
 */
public class CurUtils {
    private String cur;

    public CurUtils(String cur) {
        this.cur = cur;
    }

    public String invoke() {
        switch (cur) {
            case "人民币元":
                return "CNY";

            case "折美元合计"        :  return     "UUU"      ;
            case "人民币"            :  return     "RMB"      ;
            case "折欧元合计"        :  return     "EEE"      ;
            case "折人民币合计"      :  return     "RRR"      ;
            case "英镑"              :  return     "GBP"      ;
            case "港币"              :  return     "HKD"      ;
            case "美元"              :  return     "USD"      ;
            case "瑞士法郎"          :  return     "CHF"      ;
            case "德国马克"          :  return     "DEM"      ;
            case "法国法郎"          :  return     "FRF"      ;
            case "新加坡元"          :  return     "SGD"      ;
            case "巴基斯坦卢比"      :  return     "PKR"      ;
            case "荷兰盾"            :  return     "NLG"      ;
            case "瑞典克郎"          :  return     "SEK"      ;
            case "丹麦克郎"          :  return     "DKK"      ;
            case "挪威克郎"          :  return     "NOK"      ;
            case "奥地利先令"        :  return     "ATS"      ;
            case "比利时法郎"        :  return     "BEF"      ;
            case "意大利里拉"        :  return     "ITL"      ;
            case "日元"              :  return     "JPY"      ;
            case "加拿大元"          :  return     "CAD"      ;
            case "澳大利亚元"        :  return     "AUD"      ;
            case "坦桑尼亚先令"      :  return     "TZS"      ;
            case "西班牙比塞塔"      :  return     "ESP"      ;
            case "马来西亚林吉特"    :  return     "MYR"      ;
            case "欧元"              :  return     "EUR"      ;
            case "金融比利时法郎"    :  return     "BEU"      ;
            case "科威特第纳尔"      :  return     "KWD"      ;
            case "斯里兰卡卢比"      :  return     "LKP"      ;
            case "阿尔及利亚第纳尔"  :  return     "DZD"      ;
            case "芬兰马克"          :  return     "FIM"      ;
            case "加纳塞地"          :  return     "GHC"      ;
            case "伊拉克地那尔"      :  return     "IQD"      ;
            case "马里法郎"          :  return     "MLF"      ;
            case "摩洛哥地拉姆"      :  return     "MAD"      ;
            case "塞拉利昂利昂"      :  return     "SLL"      ;
            case "伊朗里亚尔"        :  return     "IRR"      ;
            case "尼泊尔卢比"        :  return     "NPR"      ;
            case "几内亚西里"        :  return     "GNS"      ;
            case "第那儿"            :  return     "260"      ;
            case "阿尔巴尼亚列克"    :  return     "ALL"      ;
            case "罗马尼亚列依"      :  return     "ROL"      ;
            case "朝鲜币"            :  return     "KPW"      ;
            case "越南盾"            :  return     "VND"      ;
            case "匈牙利福林"        :  return     "HUF"      ;
            case "保加利亚列瓦"      :  return     "BGL"      ;
            case "捷克克郎"          :  return     "CSK"      ;
            case "波兰兹罗提"        :  return     "PLZ"      ;
            case "卢布"              :  return     "RUB"      ;
            case "南非兰特"          :  return     "ZAR"      ;
            case "韩元"              :  return     "KRW"      ;
            case "蒙古图格里克"      :  return     "TUG"      ;
            case "新台币"            :  return     "TWD"      ;
            case "赞比亚克瓦查"      :  return     "ZMK"      ;
            case "澳门元"            :  return     "MOP"      ;
            case "菲律宾比索"        :  return     "PHP"      ;
            case "缅甸币"            :  return     "BUK"      ;
            case "泰铢"              :  return     "THB"      ;
            case "印度罗比"          :  return     "INR"      ;
            case "马尔他镑"          :  return     "MTP"      ;
            case "新西兰元"          :  return     "NZD"      ;
            case "爱尔兰镑"          :  return     "IEP"      ;
            case "卢森堡法郎"        :  return     "LUF"      ;
            case "葡萄牙埃斯库多"    :  return     "PTE"      ;



            default:
                return cur;
        }
    }
}
