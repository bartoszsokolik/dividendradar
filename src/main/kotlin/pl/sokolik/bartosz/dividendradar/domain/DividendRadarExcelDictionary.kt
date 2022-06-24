package pl.sokolik.bartosz.dividendradar.domain

enum class DividendRadarExcelDictionary(val value: String, val position: Int) {
    SYMBOL("symbol", 0),
    COMPANY("company", 1),
    SECTOR("sector", 2),
    NO_YEARS("no years", 3),
    PRICE("price", 4),
    DIV_YIELD("div yield", 5),
    FIVE_YEAR_AVG_YIELD("5y avg yield", 6),
    DGR_1_YEAR("dgr 1y", 7),
    DGR_3_YEAR("dgr 3y", 8),
    DGR_5_YEAR("dgr 5y", 9),
    DGR_10_YEAR("dgr 10y", 10),
    FAIR_VALUE("fair value", 11),
    FAIR_VALUE_PERCENT("fv %", 12),
    REVENUE_ONE_YEAR("revenue 1y", 13),
    ROE("roe", 14),
    ROTC("rotc", 15),
    P_E("p/e", 16),
    P_BV("p/bv", 17)
}