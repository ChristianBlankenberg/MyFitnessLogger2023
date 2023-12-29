package com.example.myfitnesslogger.businesslogic

open class baseDataGate {
    private val httpService: httpRequestService = httpRequestService()

    protected fun getValue(url: String, sheetName: String, col: Int, row: Int): String {
        return httpService.sendGet(
            url,
            this.getParameter(this.getValueParameter(), sheetName, col, row, "")
        )
    }

    protected fun sendValue(url : String, sheet : String, value : String, col: Int, row: Int) : Boolean
    {
        return httpService.sendGet(
            url,
            this.getParameter(
                this.setValueParameter(),
                sheet,
                col,
                row,
                value
            )
        ) == "OK"
    }

    protected fun getParameter(
        order: String,
        sheetName: String,
        col: Int,
        row: Int,
        value: String,
    ): String {
        var result = "Ex=" + order + "&Sh=" + sheetName + "&Col=" + col + "&Row=" + row
        if (order == setValueParameter())
            result = result.plus("&Val=" + value)

        return result
    }

    private fun getValueParameter(): String {
        return "Get"
    }

    protected fun setValueParameter(): String {
        return "Set"
    }
}